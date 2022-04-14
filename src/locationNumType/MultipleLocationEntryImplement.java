package locationNumType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import location.Location;

public class MultipleLocationEntryImplement implements MultipleLocationEntry{
//mutable
	
	private List<? extends Location> locationList;
	
//Abstraction function:
//	AF(locationList) =  a list of locations to be controlled by this object
//Representation invariant:
//	locationList must be non-null ,all the objects in locationList must be non-null
//Safety from rep exposure:
//	the field is private 
// 	the observer return an unmodified object
	
	//checkRep
	private void checkRep()
	{
		assert locationList != null;
		for(Location thisLoc :locationList)
		{
			assert thisLoc != null;
		}
	}
	
	@Override
	public boolean setLocationList(List<? extends Location> locaitonList) {

		this.locationList = new ArrayList<>(locaitonList);
		checkRep();
		return true;
	}
	
	@Override
		public List<? extends Location> getLocationList() {
			return Collections.unmodifiableList(locationList);
		}
}
