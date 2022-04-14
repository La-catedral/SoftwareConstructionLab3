package trainSchedule;

import java.util.Calendar;
import java.util.List;
import location.Location;
import location.TrainStation;
import locationNumType.MultipleLocationEntry;
import planningEntry.CommonPlanningEntry;
import resource.Coach;
import resource.Resource;
//import planningEntry.CommonPlanningEntry;
import resourceType.MutipleSortedResourceEntry;
import timeslot.MutiSlot;
import timeslot.TimeSlot;

//高铁车次管理 记得把新增功能设为接口
public class TrainEntry extends CommonPlanningEntry<Coach> implements TrainPlanningEntry {
//mutable
	
	private final MutipleSortedResourceEntry mutiSorResource ;
	private final MultipleLocationEntry multiLocation;
	private final MutiSlot mutiSlo;
	

	// Abstraction function:
    // 	AF(mutiSorresource,multiLocation,mutiSlo) = the manager of the resources of the object, the manager of the locations of the object,the manager of the timeSlots of the object,  
    // Representation invariant:
    //	the field must be non null
    // Safety from rep exposure:
    //  all fields are private 
	//	all the Observer return an immutable object
	
	//铁路局可以增加一个新车次、 （创建）
	//constructor
	public TrainEntry(MutipleSortedResourceEntry mutiSorResource, MultipleLocationEntry multiLocation,
			 MutiSlot mutiSlo,String name) {
		super(name);
		this.mutiSorResource = mutiSorResource;
		this.multiLocation = multiLocation;
		this.mutiSlo = mutiSlo;
		checkRep();
	}
	
	private void checkRep()
	{
		assert mutiSorResource != null;
		assert multiLocation != null;
		assert mutiSlo !=  null;
	}
		//为新车次分配一组特定的车厢（有序）
	@Override
	public boolean setResource(List<? extends Resource> thisresource)
	{
		if(super.allocateResource() && thisresource.get(0).getClass().equals(Coach.class))
		{
			mutiSorResource.setResource(thisresource);
			System.out.println("分配车厢成功～");
			return true;
		}
		System.out.println("资源类型不匹配或已经分配！！");
		return false;
	}
	
	@Override
		public List<? extends Resource> getResource() {
			return mutiSorResource.getResource();
		}
	
		//分配始发终止时间对 （总出发时间，总预计到达时间）
	@Override
	public void setSlot(TimeSlot BegandEnd)
	{
		mutiSlo.setSlot(BegandEnd);
	}
	
	//中途停靠时间对 (到站时间，离站时间) 设定后不可更改
	@Override
	public void setSlot(List<TimeSlot> slotList)
	{
		mutiSlo.setSlot(slotList);
	}
	
	@Override
	public TimeSlot getSlot()
	{
		return mutiSlo.getSlot();
	}
	
	@Override
	public List<TimeSlot> getSlotList() 
	{
		return mutiSlo.getSlotList();
	}
	
	//分配高铁站的序列 设定后不可更改
	@Override
	public boolean setLocationList(List<? extends Location> locaitonList) {
		if(locaitonList.get(0).getClass().equals(TrainStation.class))
		{
			multiLocation.setLocationList(locaitonList);		
			return true;
		}
		System.out.println("位置类型不匹配");
		return false;
	}

	@Override
		public List<? extends Location> getLocationList() {
			return multiLocation.getLocationList();
		}
		 //始发 start 
	@Override
	public boolean run() {
//		System.out.println("火车启动");
		return super.run();
	}	
	
		  //在中间站停车
	@Override
	public boolean blockTheEntry(Calendar atTime)
	{
		for(TimeSlot thisSlot:mutiSlo.getSlotList())
		{
			if(thisSlot.getBeginTime().equals(atTime))
			{	
//				/**
//				 * block the entry
//				 */
//				public boolean block()
//				{
//					return state.block(this);
//				}
				return this.getState().block(this);
//				return super.block();
			}
		}
		System.out.println("非法的阻塞时间或状态");
		return false;
	}
	
	
	//在中间站发车
	@Override
	public boolean restart(Calendar atThisTime)
	{
		for(TimeSlot thisSlot:mutiSlo.getSlotList())
		{
			if(thisSlot.getEndTime().equals(atThisTime))
			{
				return run();
			}
		}
		System.out.println("非法的重新开启时间");
		return false;
	}
		
	//抵达终点站。end 
	@Override
	public boolean end() {
//		System.out.println("火车到站");
		return super.end();
	}
	
		     //高铁在起点站未出发前或中间站停车时可以被取消。 
	@Override
	public boolean cancel()
	{
		if(super.cancel())
		{
			System.out.println("该列车车次被取消");
			return true;
		}
		else
			return false;
	}
	
//		public boolean cancel(Calendar atTime) {
//			if(mutiSlo.getSlot().getBeginTime().after(atTime))//如果atTime在允许取消时间范围内 则可以取消 否则报错
//			{
//					return super.cancel();
//			}
//			else
//			{
//				for(TimeSlot thisSlot:mutiSlo.getSlotList())
//				{
//					if(thisSlot.timeWithintheSLot(atTime))
//					{
//						return super.cancel();
//					}
//				}
//				System.out.println("未在允许取消的时间范围内");
//				return false;
//			}	
////			System.out.println("课程已取消。");
//		}
}
