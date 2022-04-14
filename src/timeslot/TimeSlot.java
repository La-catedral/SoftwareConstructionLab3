package timeslot;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *	一个时间对 
 *	immutable
 */
public class TimeSlot {

	private final String startTime;
	private final String endTime;
	private final Calendar from;
	private final Calendar to;

	// Abstraction function:
    // 	AF(startTime,endTime,from,to) = the string format of the begin time, the string format of the end time,the real time of begin and end.
    // Representation invariant:
    //	all fields must be non-null
    // Safety from rep exposure:
    //  the fields are private and final
	//	the Observers make defensive copy
	
	//constructor
	public TimeSlot(Calendar from,Calendar to) {
		this.from = (Calendar)from.clone();
		this.to = (Calendar)to.clone();
		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		startTime = form.format(from.getTime());
		endTime = form.format(to.getTime());
		checkRep();
	}
	
	//checkrep
	private void checkRep()
	{
		assert from != null;
		assert to != null;
		assert startTime != null;
		assert endTime != null;
	}
	
	/**
	 * 获得时间对的字符串表示形式
	 * @return ,the readable format of the timeSlot
	 */
	@Override
	public String toString()
	{
		return "("+startTime+","+endTime+")";
	}
	

	/**
	 * 获取约定字符串形式的开始时间
	 * @return 字符串形式的开始时间
	 */
	public String getStrBegin()
	{
		return this.startTime;
	}
	
	/**
	 * 获取约定字符串形式的结束时间
	 * @return 字符串形式的结束时间
	 */
	public String getStrEnd()
	{
		return this.endTime;
	}
	
	/**
	 * get the begin time of the slot
	 * @return,the begin time of the slot
	 */
	public Calendar getBeginTime()
	{
		Calendar thisCal = (Calendar)from.clone();
		return thisCal;
	}

	/**
	 * get the end time of the slot
	 * @return,the end time of the slot
	 */
	public Calendar getEndTime()
	{
		Calendar thisCal = (Calendar)to.clone();
		return thisCal;
	}
	
	/**
	 * 判断给定时间是否在该对象对应的时间对范围内,临界时不在范围内
	 * @param thisTime ,要用于判断的给定时间
	 * @return true 若是，否则false
	 */
	public boolean timeWithintheSLot(Calendar thisTime)
	{
		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String thisStr = form.format(thisTime.getTime());
		if(thisTime.before(from) || thisTime.after(to) ||thisStr.equals(startTime) ||thisStr.equals(endTime))
			return false;
		else
			return true;
	}
	
	/**
	 * 判断两个TimeSlot是否有重合部分 不包括临界相交
	 * @param first 第一个时间对
	 * @param second 第二个时间对
	 * @return true 如果存在重合部分 否则false
	 */
	public boolean checkCoinOrNot(TimeSlot first) {
		if(first.getStrBegin().equals(endTime) || first.getStrEnd().equals(startTime))
			return false;
		if(first.from.after(this.to) || this.from.after(first.to))
		return false;
		else
		return true;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimeSlot other = (TimeSlot) obj;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}
	
	
//	public static void main(String[] args) {
//		Calendar newC= Calendar.getInstance();
//		newC.set(1, 1, 1);
//		Calendar newB= Calendar.getInstance();
//		newB.set(2, 2, 2);
//		TimeSlot newSl= new TimeSlot(newC, newB);
//		System.out.println(newSl.getTime());
//	}
}
