package planningEntry;

import java.util.List;

import classSchedule.CourseEntry;
import flightSchedule.FlightEntry;
import location.Location;
import timeslot.TimeSlot;
import trainSchedule.TrainEntry;

public class CheckLocationConForceImp implements CheckLocationInterface{

	

	@Override
	public boolean checkLocationConflict(List<? extends PlanningEntry<?>>   entries)
	   {
		   Class<?> cla = entries.get(0).getClass();
		   if(cla.equals(TrainEntry.class) || cla.equals(FlightEntry.class))
		   {
			   return false;
		   }
		   else if(cla.equals(CourseEntry.class))
		   {
			   for(int i =0;i<entries.size();i++)
				   {
					   Location thisLocation = ((CourseEntry)entries.get(i)).getLocation();
					   TimeSlot thisTime = ((CourseEntry)entries.get(i)).getSlot();
					   for(int j =0;j<entries.size();j++)
					   {
						   if(j != i)
						   {
							   CourseEntry compareEntry = (CourseEntry)entries.get(j);
							  if(compareEntry.getLocation().equals(thisLocation) && compareEntry.getSlot().checkCoinOrNot(thisTime))
								  return true;
						   }
					   }
				   }
		   }
		   return false;
	   }
}
