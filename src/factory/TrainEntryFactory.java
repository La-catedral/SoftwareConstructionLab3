package factory;

import locationNumType.MultipleLocationEntry;
import locationNumType.MultipleLocationEntryImplement;
import resourceType.MutipleSortedResourceEntry;
import resourceType.MutipleSortedResourceEntryImplement;
import timeslot.MutiSlot;
import timeslot.MutiSlotimple;
import trainSchedule.TrainEntry;

public class TrainEntryFactory {

	public TrainEntry getEntry(String name)
	{
		MutipleSortedResourceEntry mutiSorResource = new MutipleSortedResourceEntryImplement();
		MultipleLocationEntry multiLocation = new MultipleLocationEntryImplement();
		MutiSlot mutiSlo = new MutiSlotimple();
		return new TrainEntry(mutiSorResource, multiLocation, mutiSlo, name);
	}
}
