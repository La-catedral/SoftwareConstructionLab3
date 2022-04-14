package locationChange;

import location.Location;
import locationNumType.SingleLocationEntry;

public class LocationCouldChangeImple implements LocationCouldChange{
//mutable
	
	private SingleLocationEntry singleLoc;
	
//Abstraction function:
//	AF(singleLoc) =  the location manager that would be controlled by this object   
//Representation invariant:
//	the field must be non null
//Safety from rep exposure:
//	the field is private 
//	this object is used to manage a location object,so it is allowed that the Rep is changed
	
	//checkRep
	private void checkRep()
	{
		assert singleLoc != null;
	}
	
	/**
	 * set the location manager that would be controlled by this object   
	 * @param singleLoc,the location manager to be controled
	 */
	public void setsingleLoc(SingleLocationEntry singleLoc)
	{
		this.singleLoc = singleLoc;
		checkRep();
	}
	
	@Override
	public void changeSingleLocation(Location thisLocation)
	{
		singleLoc.setLocation(thisLocation);
		checkRep();
	}
}
