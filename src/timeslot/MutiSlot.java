package timeslot;

import java.util.List;

public interface MutiSlot extends SingleSlot{
//管理一个或者多个时间对
	
	/**
	 * 设置该对象管理的时间对序列
	 * @param timeSlot,被管理的时间对
	 */
	public void setSlot(List<TimeSlot> timeSlot);
	
	/**
	 * get the list of TimeSlot that this manager have 
	 * @return,the TimeSlot list
	 */
	public List<TimeSlot> getSlotList();
}
