/**
 * 
 */
package webapp.domain;

import java.io.Serializable;

/**
 * @author 501566335
 * 
 */
@SuppressWarnings("serial")
public abstract class AbstractDomainObject implements Serializable {

	protected Long		id;
	protected String	name;
	

	public abstract Long getId();

	public abstract void setId(Long id);

	public abstract String getName();

	
	public abstract void setName(String name);

	
}
