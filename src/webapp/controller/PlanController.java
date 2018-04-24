package webapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import webapp.domain.request.SearchParam;
import webapp.domain.response.ResponseObject;
import webapp.serviceIndex.StrippedPlanIndexService;;

@RestController
public class PlanController /*extends AbstractController*/{
	
	@Autowired
	private StrippedPlanIndexService strippedPlanIndexService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/{index}/{indexType}", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> extractPlans( @RequestBody String json,
			@RequestParam(required=false) Long pageSize, @PathVariable String index,@PathVariable String indexType ){
		List<Map<String,Object>> planExtract = null;
		Map resMap = new HashMap();
		ResponseObject resObj = null;
		boolean searchFlag=true;
		int loopCount=0;
		int totalPage=0;
		int remainder=0;
		int totalResult=0;
		try{
			SearchParam params = new SearchParam();
		    ObjectMapper mapper = new ObjectMapper();
		    params = mapper.readValue(json, SearchParam.class);
		    pageSize= pageSize!=null?pageSize:100;
		    params.setScroll("Y");
		    params.setIndex(indexType);
		    params.setIndexType(indexType);
		    resMap = strippedPlanIndexService.searchByFieldName(params,null,pageSize);
			
			if(resMap!=null){
				totalResult=Integer.parseInt(resMap.get("total").toString());
				totalPage=(totalResult/pageSize.intValue());
				remainder = totalResult % pageSize.intValue();
				if(remainder>0){
					totalPage=totalPage+1;
				}
				resMap.put("totalPage", totalPage);
				planExtract=(List<Map<String,Object>>)resMap.get("planExtract");
			}
		if(planExtract!=null){
			resObj = new ResponseObject();
			resObj.setSuccess(true);
			resObj.setTotalCount(totalResult);
			resObj.setCount(planExtract.size());
			resObj.setResult(planExtract);   
			
			return new ResponseEntity<ResponseObject> (resObj, HttpStatus.OK);
		}
		else
            return new ResponseEntity<ResponseObject>(HttpStatus.NO_CONTENT);
		}catch(Exception e){
			resObj = new ResponseObject(e.getMessage());
			return new ResponseEntity<ResponseObject>(resObj, HttpStatus.PRECONDITION_FAILED);
		}	
	}
	
	
		
	@RequestMapping(value="/{index}/{indexType}/page/{pageNumber}", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> extractPlansByPage( @RequestBody String json,
			@PathVariable Long pageNumber,
			@RequestParam(required=false) Long pageSize ,
			@PathVariable String index,@PathVariable String indexType){
		List<Map<String,Object>> planExtract = null;
		Map resMap = new HashMap();
		ResponseObject resObj = null;
		boolean searchFlag=true;
		int loopCount=0;
		int totalPage=0;
		int remainder=0;
		int totalResult=0;
		Long zeroValue=0L;
		
		try{
			SearchParam params = new SearchParam();
			ObjectMapper mapper = new ObjectMapper();
		    params = mapper.readValue(json, SearchParam.class);
		    params.setIndex(indexType);
		    params.setIndexType(indexType);
		    
		    if(params.getMultiMatch()!=null){
		    	if(StringUtils.isEmpty(params.getMultiMatch().getInput())){
		    		resObj = new ResponseObject("Please enter input value");
			    	return new ResponseEntity<ResponseObject> (resObj, HttpStatus.BAD_REQUEST);
		    	}
		    	
		    	if(params.getMultiMatch().getFields()==null||
		    			params.getMultiMatch().getFields()!=null
		    			&& params.getMultiMatch().getFields().size()<1){
		    		resObj = new ResponseObject("Please enter the fields against which the search needs to be done");
			    	return new ResponseEntity<ResponseObject> (resObj, HttpStatus.BAD_REQUEST);
		    	}
		    	
		    }
		    pageSize= pageSize!=null?pageSize:100;
		    if(zeroValue.equals(pageNumber)){
		    	resObj = new ResponseObject("Not a valid page");
		    	return new ResponseEntity<ResponseObject> (resObj, HttpStatus.BAD_REQUEST);
		    }
		    pageNumber=pageNumber-1;
			resMap = strippedPlanIndexService.searchByFieldName(params,pageNumber,pageSize);
			
			if(resMap!=null){
				totalResult=Integer.parseInt(resMap.get("total").toString());
				totalPage=(totalResult/pageSize.intValue());
				remainder = totalResult % pageSize.intValue();
				if(remainder>0){
					totalPage=totalPage+1;
				}
				resMap.put("totalPage", totalPage);
				planExtract=(List<Map<String,Object>>)resMap.get("planExtract");
			}
			resObj = new ResponseObject();
			resObj.setSuccess(true);
			resObj.setTotalCount(totalResult);
			resObj.setTotalPage(totalPage);
		if(planExtract!=null && planExtract.size()>0){
			/*resObj = new ResponseObject();
			resObj.setSuccess(true);
			resObj.setTotalCount(totalResult);
			resObj.setTotalPage(totalPage);*/
			resObj.setCount(planExtract.size());
			resObj.setResult(planExtract);  
			return new ResponseEntity<ResponseObject> (resObj, HttpStatus.OK);
		}
		else{
			resObj.setCount(0);
			resObj.setResult(new ArrayList<Map<String,Object>>()); 
			return new ResponseEntity<ResponseObject>(resObj,HttpStatus.OK);
            }
		}catch(Exception e){
			resObj = new ResponseObject(e.getMessage());
			return new ResponseEntity<ResponseObject>(resObj, HttpStatus.PRECONDITION_FAILED);
		}
	}	
	
}