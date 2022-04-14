package timeslot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 	设置一个或者多个时间对进行管理
 *	mutable
 */
public class MutiSlotimple extends SingleSlotimple implements MutiSlot{
	
	private List<TimeSlot> mutiSlot = new ArrayList<>();

	// Abstraction function:
    // 	AF(mutiSlot) = the timeSlot list controlled by the object
    // Representation invariant:
    //	all fields must be non-null,and all the objects in thisResource must be non-null
    // Safety from rep exposure:
    //  the fields are private 
	//	the Observer return an unmodifiable collection
	
	//checkrep
	private void checkRep()
	{
		assert mutiSlot != null;
		for(TimeSlot thisSlot :mutiSlot)
		{
			assert thisSlot != null;
		}
	}
	
	@Override
	public void setSlot(List<TimeSlot> timeSlot)
	{
		this.mutiSlot =new ArrayList<TimeSlot>(timeSlot);
		checkRep();
	}
	


	
	@Override
		public List<TimeSlot> getSlotList() {
			return Collections.unmodifiableList(mutiSlot);
		}
	
	
}
