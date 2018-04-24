package webapp.serviceIndex.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScriptSortBuilder.ScriptSortType;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import webapp.domain.request.MultiMatch;
import webapp.domain.request.SearchParam;
import webapp.serviceIndex.StrippedPlanIndexService;


@Service
public class StrippedPlanIndexServiceImpl implements StrippedPlanIndexService{
	
   /* @Autowired
    public Client client;*/
	
	@Autowired
    public RestHighLevelClient restClient;
    
   
	@Override
	public Map searchByFieldName(SearchParam reqParams,Long pageNumber,Long pageSize) {
		
		MultiMatch multiSearch = reqParams.getMultiMatch();
		
		String multiSearchQuery =null;
		String multiSearchType =null;
		List<String> multiSearchFields =null;
		if(multiSearch!=null){
			multiSearchQuery = multiSearch.getInput();
			multiSearchType = multiSearch.getType();	
			multiSearchFields= multiSearch.getFields();
		}
		
		List<String> resultFields= reqParams.getResultFields();
		String[] multiSearchArray= multiSearchFields.stream().toArray(String[]::new);
		String[]  resultArray = resultFields!=null? resultFields.stream().toArray(String[]::new):null;
		
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		if(multiSearchType!=null && "fieldsearch".equals(multiSearchType)){
			MultiMatchQueryBuilder builder = new MultiMatchQueryBuilder(multiSearchQuery, multiSearchArray );
			builder.type(MultiMatchQueryBuilder.Type.BEST_FIELDS).operator(Operator.AND).tieBreaker(1f);
		}else{
			String[] words = multiSearchQuery.split("\\s");
			for(String input:words){
				if(input!=null ){
					MultiMatchQueryBuilder builder = new MultiMatchQueryBuilder( input.trim(), multiSearchArray );
					builder.type(MultiMatchQueryBuilder.Type.BEST_FIELDS).operator(Operator.OR).tieBreaker(1f);
					queryBuilder.must(builder);
				}
			}
		}
			
		List<Map<String,Object>> strippedPlanList =new ArrayList<Map<String,Object>>();
		Map resMap = null;
	    try{
	    	
	    	SearchRequest searchRequest = new SearchRequest(reqParams.getIndex()); 
	    	searchRequest.types(reqParams.getIndexType());
	    	SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
	    	searchSourceBuilder.query(queryBuilder); 
	    	if(!StringUtils.isEmpty(resultArray)){
	    		searchSourceBuilder.fetchSource(resultArray, null);
			}
	    	searchRequest.searchType(SearchType.QUERY_THEN_FETCH);
	    	
	    	
	    	
			/*SearchRequestBuilder requestBuilder  = client.prepareSearch(reqParams.getIndex())
					 .setTypes(reqParams.getIndexType())
					 .setSearchType(SearchType.QUERY_THEN_FETCH)
					 .setQuery(queryBuilder);*/
		
			/*if(!StringUtils.isEmpty(resultArray)){
				requestBuilder.setFetchSource(resultArray, null);
			}*/
			
			SortBuilder sb = SortBuilders.scoreSort();
			searchSourceBuilder.sort(sb.order(SortOrder.DESC));
			Script sortScript = new Script(ScriptType.INLINE,"painless","Integer.parseInt(doc[\"planId.keyword\"].value)", Collections.emptyMap());
			searchSourceBuilder.sort(SortBuilders.scriptSort(sortScript, ScriptSortType.NUMBER).order(SortOrder.DESC));
			
			if(reqParams.getSortParam()!=null){
				reqParams.getSortParam().forEach((sort) -> searchSourceBuilder.sort(SortBuilders.fieldSort(sort.getSortName()).order("desc".equals(sort.getSortDir())?SortOrder.DESC:SortOrder.ASC)));
			}
			 
			if(!StringUtils.isEmpty(reqParams.getScroll()) && "Y".equals(reqParams.getScroll())){
				searchRequest.scroll(new TimeValue(60000));  
			}else{
				searchSourceBuilder.from(pageNumber.intValue()*pageSize.intValue());
			}
			
			searchSourceBuilder.size(pageSize.intValue());//.get(); //max of 100 hits will be returned for each scroll
			
			searchRequest.source(searchSourceBuilder);
			
			SearchResponse scrollResp  = restClient.search(searchRequest);
			
			String scrollId = scrollResp.getScrollId();
			SearchHit[] searchHits = scrollResp.getHits().getHits();

			
			//Scroll until no hits are returned
			boolean finshed = false;
			final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
			 if(!StringUtils.isEmpty(reqParams.getScroll())&& "Y".equals(reqParams.getScroll())){
				 while (searchHits != null && searchHits.length > 0) { 
						 for (SearchHit hit : searchHits) {
							 Map<String,Object> map=new HashMap<String,Object>();
							 hit.getSourceAsMap().put("score", hit.getScore());
							 resultFields.forEach((i) -> map.put(i, hit.getSourceAsMap().get(i)));
							 strippedPlanList.add(map);
						 }
					    SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId); 
					    scrollRequest.scroll(scroll);
					    scrollResp = restClient.searchScroll(scrollRequest);
					    scrollId = scrollResp.getScrollId();
					    searchHits = scrollResp.getHits().getHits();
					    
					}
				 
				 ClearScrollRequest clearScrollRequest = new ClearScrollRequest(); 
				 clearScrollRequest.addScrollId(scrollId);
				 ClearScrollResponse clearScrollResponse = restClient.clearScroll(clearScrollRequest);
				 boolean succeeded = clearScrollResponse.isSucceeded();
				/*do {
					 SearchHit[] sh = scrollResp.getHits().getHits();
					 for (SearchHit hit : sh) {
						 //strippedPlanList.add(hit.getSourceAsMap());
						 Map<String,Object> map=new HashMap<String,Object>();
						 hit.getSourceAsMap().put("score", hit.getScore());
						 resultFields.forEach((i) -> map.put(i, hit.getSourceAsMap().get(i)));
						 //map.put("score", hit.getScore());
						 strippedPlanList.add(map);
					 } 
					 scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().get(); 
				} while(scrollResp!=null && scrollResp.getHits().getHits().length != 0);*/
			}else{
				
				 for (SearchHit hit : scrollResp.getHits().getHits()) {
					 Map<String,Object> map=new HashMap<String,Object>();
					 hit.getSourceAsMap().put("score", hit.getScore());
					 resultFields.forEach((i) -> map.put(i, hit.getSourceAsMap().get(i)));
					 //map.put("score", hit.getScore());
					 strippedPlanList.add(map);
				 }
			}
			
			if(strippedPlanList!=null && strippedPlanList.size()>0){
				resMap = new HashMap();
				resMap.put("planExtract", strippedPlanList);
				resMap.put("total", scrollResp.getHits().getTotalHits());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resMap;
	}
	
	public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
	    ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    return is.readObject();
	}
	
	
	
}
