package trainSchedule;

import block.BlockableEntry;

import locationNumType.MultipleLocationEntry;
import resourceType.MutipleSortedResourceEntry;
import timeslot.MutiSlot;
public interface TrainPlanningEntry extends MutiSlot,MultipleLocationEntry,MutipleSortedResourceEntry,BlockableEntry{
	

}
