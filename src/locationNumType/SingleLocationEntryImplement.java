package locationNumType;

import location.Location;

public class SingleLocationEntryImplement implements SingleLocationEntry{
//mutable 
	
	private Location thisLocation;
	
//Abstraction function:
//	AF(thisLocation) =  the location to be controlled by this object
//Representation invariant:
//	thisLocation must be non-null
//Safety from rep exposure:
//	the field is private 
// 	the observer return an immutable object	
	
	//checkRep
	private void checkRep()
	{
		assert thisLocation != null;
	}
	
	@Override
	public boolean setLocation(Location thisLocation) {
		this.thisLocation = thisLocation;
		checkRep();
		return true;
	}
	
	@Override
		public Location getLocation() {
			return thisLocation;
		}
	

}
