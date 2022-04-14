package locationNumType;

import location.Location;

public class DoubleLocationEntryImplement implements DoubleLocationEntry{
//mutable
	
	private Location fromLocation;
	private Location toLocation;
	
//Abstraction function:
//	AF(fromLocation,toLocation) =  the first and second location being controlled by this object   
//Representation invariant:
//	all the fields must be non null
//Safety from rep exposure:
//	the field is private 
//	this object is used to manage a location object,so it is allowed that the Rep is changed
//	the observer returns objects that are immutable
	
	//checkRep
	private void checkRep()
	{
		assert fromLocation != null;
		assert toLocation != null;
	}
	
	/**
	 * set the two locations being controlled by this object
	 * @param fromLoc,the first location
	 * @param toLoc,the second location
	 */
	@Override
	public boolean setLocation(Location fromLoc,Location toLoc)
	{
		this.fromLocation = fromLoc;
		this.toLocation = toLoc;
		checkRep();
		return true;
	}
	
	@Override
	public Location getfromLocation() {
		return fromLocation;
	}
	
	@Override
	public Location getToLocation() {
		return toLocation;
	}
	
}

