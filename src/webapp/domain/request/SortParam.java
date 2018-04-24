package webapp.domain.request;

public class SortParam extends AbstractRequestParam {

	

	private String sortName ;
	
	private String sortDir;

	/**
	 * @return the sortName
	 */
	public String getSortName() {
		return sortName;
	}

	/**
	 * @param sortName the sortName to set
	 */
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	/**
	 * @return the sortDir
	 */
	public String getSortDir() {
		return sortDir;
	}

	/**
	 * @param sortDir the sortDir to set
	 */
	public void setSortDir(String sortDir) {
		this.sortDir = sortDir;
	}
	
	

}