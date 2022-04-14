package classSchedule;

import location.ClassRoom;
import location.Location;
import locationChange.LocationCouldChange;
import locationChange.LocationCouldChangeImple;
import locationNumType.SingleLocationEntry;
import planningEntry.CommonPlanningEntry;
import resource.Resource;
import resource.Teacher;
import resourceType.SingleResourceEntry;
import timeslot.SingleSlot;
import timeslot.TimeSlot;

public class CourseEntry extends CommonPlanningEntry<Teacher> implements CoursePlanningEntry{
	//mutable 

	private final LocationCouldChange changeObject;
	private final SingleLocationEntry	singleLoc;
	private final SingleResourceEntry singleRes;
	private final SingleSlot  singleSlo;
	
	
//	 Abstraction function:
//    	AF(changeObject,singleLoc,singleRes，singleSlo) = the object to change the location ,the manager of the locations of the object,the manager of the resources of the object, the manager of the timeSlots of the object,  
//    Representation invariant:
//   	the field must be non null
//    Safety from rep exposure:
//     all fields are private and final 
//		all the Observer return an immutable object
	
	//constructor，创建一个计划项实例；例如：周三上午10:00-11:45 在正心楼 23 教室上“软件构造”课程。
	
public CourseEntry(LocationCouldChange changeObject, SingleLocationEntry singleLoc,
		SingleResourceEntry singleRes, SingleSlot singleSlo,String name) {
	super(name);
	this.changeObject = changeObject;
	this.singleLoc = singleLoc;
	this.singleRes = singleRes;
	this.singleSlo = singleSlo;
	checkRep();
}
		
	//checkRep
	private void checkRep()
	{
		assert changeObject != null;
		assert singleRes != null;
		assert singleLoc != null;
		assert singleSlo != null;
	}

		


	//需要特定的资源（教师）
	@Override
	public boolean setResource(Resource thisresource)
	{
		
		if(super.allocateResource() && thisresource.getClass().equals(Teacher.class))
		{
			singleRes.setResource(thisresource);
			System.out.println("已分配教师");
			return true;
		}
		System.out.println("资源类型不匹配或已经分配！！");
		return false;
	}
	
	@Override
	public Resource getResource() {
		return singleRes.getResource();
	}
	
	
	//上课需要特定的物理位置（教室）
	@Override 
	public boolean setLocation(Location thisLocation)
	{
		if(thisLocation.getClass().equals(ClassRoom.class))
		{
			singleLoc.setLocation(thisLocation);
			return true;
		}
		System.out.println("位置类型不匹配！");
		return false;
	}
	
	@Override
	public Location getLocation() {
		return singleLoc.getLocation();
	}
	
	//确定时间。
	@Override
	public void setSlot(TimeSlot timeSlot) {
		singleSlo.setSlot(timeSlot);
	}
	//获取时间
	@Override
	public TimeSlot getSlot() {
		return singleSlo.getSlot();
	}
	
	//教师可以上课、
	@Override
	public boolean run() {
		if(super.run())
		{
			System.out.println("上课");
			return true;
		}
		else
			return false;
	}
	
	
	//下课
	@Override
	public boolean end() {
		if(super.end())
		{
			System.out.println("下课了");
			return true;
		}
		else
			return false;
	}
	
	//更换教室
	@Override
	public void changeSingleLocation(Location thisLocation) {
	((LocationCouldChangeImple)changeObject).setsingleLoc(singleLoc);
	changeObject.changeSingleLocation(thisLocation);
	}
	
	//但在未启动之前，教师可以因为临时有事而取消该次课程
	@Override
	public boolean cancel() {
		if(super.cancel())
		{
			System.out.println("课程已取消");
			return true;
		}
		else
			return false;
	}
	
}
