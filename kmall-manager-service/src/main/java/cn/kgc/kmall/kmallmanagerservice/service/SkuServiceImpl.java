package cn.kgc.kmall.kmallmanagerservice.service;

import cn.kgc.kmall.bean.PmsSkuAttrValue;
import cn.kgc.kmall.bean.PmsSkuImage;
import cn.kgc.kmall.bean.PmsSkuInfo;
import cn.kgc.kmall.kmallmanagerservice.mapper.PmsSkuAttrValueMapper;
import cn.kgc.kmall.kmallmanagerservice.mapper.PmsSkuImageMapper;
import cn.kgc.kmall.kmallmanagerservice.mapper.PmsSkuInfoMapper;
import cn.kgc.kmall.kmallmanagerservice.mapper.PmsSkuSaleAttrValueMapper;
import cn.kgc.kmall.service.SkuService;
import cn.kgc.kmall.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    private PmsSkuInfoMapper pmsSkuInfoMapper;
    @Autowired
    private PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Autowired
    private PmsSkuImageMapper pmsSkuImageMapper;
    @Autowired
    private PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void saveSkuInfo(PmsSkuInfo skuInfo) {
        int i = 0;
        i = pmsSkuInfoMapper.insert(skuInfo);
        if (i > 0) {
            i = pmsSkuImageMapper.insertBatch(skuInfo.getSkuImageList(), skuInfo.getId());
            i = pmsSkuAttrValueMapper.insertBatch(skuInfo.getSkuAttrValueList(), skuInfo.getId());
            i = pmsSkuSaleAttrValueMapper.insertBatch(skuInfo.getSkuSaleAttrValueList(), skuInfo.getId());
        }
    }

    @Override
    //redis 主键格式："sku:"+id+":info"
    public PmsSkuInfo selectBySkuId(Long id) {
        Jedis jedis = redisUtil.getJedis();
        String key = "sku:" + id + ":info";
        String skuJson = jedis.get(key);
        PmsSkuInfo pmsSkuInfo = null;
        if (skuJson == null) {
            String lockKey = "sku:" + id + ":lock";
            //看一下有没有写入成功（NX分布式锁）
            String lockVal = UUID.randomUUID().toString();
            String lock = jedis.set(lockKey, lockVal, "NX", "PX", 60 * 10000);
            if (lock != null && lock.equals("OK")) {
                pmsSkuInfo = pmsSkuInfoMapper.selectBySpuId(id);
                if (pmsSkuInfo == null) {
                    jedis.setex(key, 5 * 60 * 1000, "empty");
                } else {

                    String skuInfoJson = JSON.toJSONString(pmsSkuInfo);
                    Random random = new Random();
                    jedis.setex(key, random.nextInt(10) * 60 * 1000, skuInfoJson);
                }
                String lockVal2 = jedis.get(lockKey);
                if (lockVal2 != null && lockVal.equals(lockVal2)) {
                    jedis.del(lockKey);
                }
            } else {
                //如果没有写入成功线程休眠三秒
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return selectBySkuId(id);
            }
        } else if (skuJson.equals("empty")) {
            jedis.close();
            return null;
        } else {
            pmsSkuInfo = JSON.parseObject(skuJson, PmsSkuInfo.class);
        }
        jedis.close();
        return pmsSkuInfo;
    }

    @Override
    public List<PmsSkuInfo> selectSkuBySpuId(Long spuId) {
        return pmsSkuInfoMapper.selectSkuBySpuId(spuId);
    }

}
