package cn.kgc.kmall.kmallsearchservice;

import cn.kgc.kmall.bean.PmsSearchSkuInfo;
import cn.kgc.kmall.bean.PmsSkuInfo;
import cn.kgc.kmall.service.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.dubbo.config.annotation.Reference;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class KmallSearchServiceApplicationTests {
    @Reference
    SkuService skuService;

    @Resource
    JestClient jestClient;

    @Test
    void contextLoads() {
        List<PmsSkuInfo> allSku = skuService.getAll();
        List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();
        for (PmsSkuInfo item : allSku) {
            PmsSearchSkuInfo pmsSearchSkuInfo = new PmsSearchSkuInfo();
            BeanUtils.copyProperties(item, pmsSearchSkuInfo);
            pmsSearchSkuInfo.setProductId(item.getSpuId());
            pmsSearchSkuInfos.add(pmsSearchSkuInfo);
        }
        for (PmsSearchSkuInfo item : pmsSearchSkuInfos) {
            Index index = new Index.Builder(item).index("kmall").type("PmsSkuInfo").id(item.getId() + "").build();
            try {
                jestClient.execute(index);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void testSearch() {
        List<PmsSearchSkuInfo> list = new ArrayList<>();
        //查询条件
        String json = "{\n" +
                "  \"query\":{\n" +
                "    \"bool\":{\n" +
                "      \"filter\":[\n" +
                "        {\"term\":{\"skuAttrValueList.valueId\":\"117\"}},\n" +
                "        {\"term\":{\"skuAttrValueList.valueId\":\"150\"}}\n" +
                "      ],\n" +
                "      \"must\": \n" +
                "      {\n" +
                "        \"match\": {\n" +
                "          \"skuName\": \"iphone\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Search search = new Search.Builder(json).addIndex("kmall").addType("PmsSkuInfo").build();
        try {
            SearchResult searchResult = jestClient.execute(search);
            List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = searchResult.getHits(PmsSearchSkuInfo.class);
            for (SearchResult.Hit<PmsSearchSkuInfo, Void> item : hits) {
                PmsSearchSkuInfo pmsSearchSkuInfo = item.source;
                list.add(pmsSearchSkuInfo);
                System.out.println(pmsSearchSkuInfo.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSearchBuilder() {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //bool
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //term和terms
        //TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId", 116);
        TermsQueryBuilder termQueryBuilder = new TermsQueryBuilder("skuAttrValueList.valueId", "116", "117");
        TermQueryBuilder termQueryBuilder1 = new TermQueryBuilder("skuAttrValueList.valueId", 150);
        //filter
        boolQueryBuilder.filter(termQueryBuilder);
        boolQueryBuilder.filter(termQueryBuilder1);
        //match
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName", "iphone");
        //must
        boolQueryBuilder.must(matchQueryBuilder);
        //query
        searchSourceBuilder.query(boolQueryBuilder);
        //排序
        searchSourceBuilder.sort("id", SortOrder.DESC);
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex("kmall").addType("PmsSkuInfo").build();
        try {
            SearchResult searchResult = jestClient.execute(search);
            List<SearchResult.Hit<PmsSearchSkuInfo, Void>> list = searchResult.getHits(PmsSearchSkuInfo.class);
            for (SearchResult.Hit<PmsSearchSkuInfo, Void> item : list) {
                PmsSearchSkuInfo pmsSearchSkuInfo = item.source;
                System.out.println(pmsSearchSkuInfo.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
