package planningEntry;

import java.util.List;


public interface CheckLocationInterface {

	public boolean checkLocationConflict(List<? extends PlanningEntry<?>>   entries);
}
