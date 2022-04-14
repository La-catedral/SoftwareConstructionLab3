package timeslot;

public interface SingleSlot {
//用于管理一个特定的时间对
	/**
	 * set the management timeslot of this object 
	 * @param timeSlot
	 */
	public void setSlot(TimeSlot timeSlot);
	
	/**
	 * 返回该对象管理的时间对
	 * @return the timeSlot which is being managed by this manager
	 */
	public TimeSlot getSlot();
}
