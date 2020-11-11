package cn.kgc.kmall.kmallsearchservice.service;

import cn.kgc.kmall.bean.PmsSearchSkuInfo;
import cn.kgc.kmall.bean.PmsSearchSkuParam;
import cn.kgc.kmall.bean.PmsSkuAttrValue;
import cn.kgc.kmall.service.SearchService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.dubbo.config.annotation.Service;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {
    @Resource
    JestClient jestClient;
    @Override
    public List<PmsSearchSkuInfo> list(PmsSearchSkuParam pmsSearchSkuParam) {
        List<PmsSearchSkuInfo> list = new ArrayList<>();
        String catalog3Id = pmsSearchSkuParam.getCatalog3Id();
        String keyword = pmsSearchSkuParam.getKeyword();
        String[] valueIds = pmsSearchSkuParam.getValueId();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if (catalog3Id != null) {
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("catalog3Id", catalog3Id);
            boolQueryBuilder.filter(termQueryBuilder);
        }
        if (valueIds != null) {
            for (String item : valueIds) {
                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId",item);
                boolQueryBuilder.filter(termQueryBuilder);
            }
        }
        if (keyword != null && keyword.isEmpty() == false) {
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName", keyword);
            boolQueryBuilder.must(matchQueryBuilder);
        }
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.sort("id", SortOrder.ASC);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(20);
        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("skuName");
        highlightBuilder.preTags("<span style='color:red;'>");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);

        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex("kmall")
                .addType("PmsSkuInfo").build();
        try {
            SearchResult searchResult = jestClient.execute(search);
            List<SearchResult.Hit<PmsSearchSkuInfo, Void>> list1 = searchResult.getHits(PmsSearchSkuInfo.class);
            for (SearchResult.Hit<PmsSearchSkuInfo, Void> item : list1) {
                PmsSearchSkuInfo pmsSkuInfo = item.source;
                Map<String, List<String>> highlight = item.highlight;
                if (highlight != null) {
                    String skuName = highlight.get("skuName").get(0);
                    pmsSkuInfo.setSkuName(skuName);
                }
                list.add(pmsSkuInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

}
