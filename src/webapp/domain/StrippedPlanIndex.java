package webapp.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The persistent class for the PLAN database table.
 * 
 */
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class StrippedPlanIndex {

	private String id;
	private String name;
	private String					planVersionNumber;

	/**
	 * @return the planVersionNumber
	 */
	public String getPlanVersionNumber() {
		return planVersionNumber;
	}

	/**
	 * @param planVersionNumber the planVersionNumber to set
	 */
	public void setPlanVersionNumber(String planVersionNumber) {
		this.planVersionNumber = planVersionNumber;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	 

}