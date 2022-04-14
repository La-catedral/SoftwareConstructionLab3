package factory;

import flightSchedule.FlightEntry;

import locationNumType.DoubleLocationEntry;
import locationNumType.DoubleLocationEntryImplement;
import resourceType.SingleResourceEntry;
import resourceType.SingleSourceEntryImplement;
import timeslot.SingleSlot;
import timeslot.SingleSlotimple;

public class FlightEntryFactory{


	public FlightEntry getEntry(String name) {
		
		
		SingleResourceEntry singleRes = new SingleSourceEntryImplement();
		DoubleLocationEntry doubleLoc = new DoubleLocationEntryImplement();
		SingleSlot singleSlot = new SingleSlotimple();
		return  new FlightEntry(singleRes, doubleLoc, singleSlot, name);
	}
	
}
