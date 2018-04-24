package webapp.serviceIndex;

import java.util.Map;

import webapp.domain.request.SearchParam;

/**
 * Created by hungnguyen on 12/28/14.
 */
public interface StrippedPlanIndexService {
	
	Map searchByFieldName(SearchParam params,Long pageNumber,Long pageSize);
	
}
