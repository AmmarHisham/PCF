package webapp.domain.request;

import java.util.List;

public class SearchParam extends AbstractRequestParam {

	

	private List<String> resultFields;
	
	private List<SortParam> sortParam;
	
	private String scroll;
	
	private String index;
	
	private String indexType;
	
	private MultiMatch multiMatch;	
	
	
	/**
	 * @return the multiMatch
	 */
	public MultiMatch getMultiMatch() {
		return multiMatch;
	}

	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * @return the indexType
	 */
	public String getIndexType() {
		return indexType;
	}

	/**
	 * @param indexType the indexType to set
	 */
	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	/**
	 * @param multiMatch the multiMatch to set
	 */
	public void setMultiMatch(MultiMatch multiMatch) {
		this.multiMatch = multiMatch;
	}

	/**
	 * @return the scroll
	 */
	public String getScroll() {
		return scroll;
	}

	/**
	 * @param scroll the scroll to set
	 */
	public void setScroll(String scroll) {
		this.scroll = scroll;
	}

	/**
	 * @return the resultFields
	 */
	public List<String> getResultFields() {
		return resultFields;
	}

	/**
	 * @param resultFields the resultFields to set
	 */
	public void setResultFields(List<String> resultFields) {
		this.resultFields = resultFields;
	}

	/**
	 * @return the sortParam
	 */
	public List<SortParam> getSortParam() {
		return sortParam;
	}

	/**
	 * @param sortParam the sortParam to set
	 */
	public void setSortParam(List<SortParam> sortParam) {
		this.sortParam = sortParam;
	}

	

	

}