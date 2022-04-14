package factory;

import classSchedule.CourseEntry;

import locationChange.LocationCouldChange;
import locationChange.LocationCouldChangeImple;
import locationNumType.SingleLocationEntry;
import locationNumType.SingleLocationEntryImplement;
import resourceType.SingleResourceEntry;
import resourceType.SingleSourceEntryImplement;
import timeslot.SingleSlot;
import timeslot.SingleSlotimple;

public class CourseEntryFactory {


	public CourseEntry getEntry(String name) {
		
		LocationCouldChange changeObject  = new LocationCouldChangeImple();
		
		SingleLocationEntry singleLoc = new SingleLocationEntryImplement();
		SingleResourceEntry singleRes = new SingleSourceEntryImplement();
		SingleSlot singleSlo = new SingleSlotimple();
		return new CourseEntry(changeObject, singleLoc, singleRes, singleSlo, name);
	}
	
}
