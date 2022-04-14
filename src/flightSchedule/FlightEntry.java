package flightSchedule;


import location.Airport;
import location.Location;
import locationNumType.DoubleLocationEntry;
import planningEntry.CommonPlanningEntry;
import resource.Plane;
import resource.Resource;
import resourceType.SingleResourceEntry;
import timeslot.SingleSlot;
import timeslot.TimeSlot;

public class FlightEntry extends CommonPlanningEntry<Plane> implements FlightPlanningEntry{
//mutable
	
	
	private final SingleResourceEntry singleRes;
	private final DoubleLocationEntry doubleLoc;
	private final SingleSlot singleSlot;
	
//	 Abstraction function:
//     	AF(singleRes,doubleLoc,singleSlot) = the manager of the resources of the object, the manager of the locations of the object,the manager of the timeSlots of the object,  
//     Representation invariant:
//    	the field must be non null
//     Safety from rep exposure:
//      all fields are private and final 
//		all the Observer return an immutable object
	
	//航空公司发布新航班 发布后客户端需要设置位置 、设置时间 、分配资源（飞机）
	public FlightEntry(SingleResourceEntry singleRes, DoubleLocationEntry doubleLoc,
			SingleSlot singleSlot,String name) {
		super(name);
		this.singleRes = singleRes;
		this.doubleLoc = doubleLoc;
		this.singleSlot = singleSlot;
		checkRep();
	}

	//checkRep
	private void checkRep()
	{
		assert singleRes != null;
		assert doubleLoc != null;
		assert singleSlot != null;
	}
	
	//航空需要分配一架具体飞机（资源）
	@Override
	public boolean setResource(Resource thisresource) {
		// TODO Auto-generated method st
		if (thisresource.getClass().equals(Plane.class)) {
			if(super.allocateResource())
			{
				singleRes.setResource(thisresource);
				return true;
			}
		}
		System.out.println("资源类型不匹配或已经分配！");
		return false;
	}
	
	@Override
		public Resource getResource() {
			return singleRes.getResource();
		}
	

	//分配位置 设置后不可更改
	@Override 
	public boolean setLocation(Location fromLocation, Location toLocation) {
		if(fromLocation.getClass().equals(Airport.class) && toLocation.getClass().equals(Airport.class))
		{
			doubleLoc.setLocation(fromLocation, toLocation);
			return true;
		}
		System.out.println("位置类型不匹配！");
		return false;
	}

	@Override
	public Location getfromLocation() {
		return doubleLoc.getfromLocation();
	}

	@Override
	public Location getToLocation() {
		return doubleLoc.getToLocation();
	}

	// 分配特定日期时间 一经设定不可修改
	@Override
	public void setSlot(TimeSlot timeSlot) {
		singleSlot.setSlot(timeSlot);
	}

	@Override
	public TimeSlot getSlot() {
		return singleSlot.getSlot();
	}

	// 起飞
	@Override
	public boolean run() {
		if (super.run()) {
			System.out.println("起飞");
			return true;
		} else
			return false;
	}

	// 抵达目的地。
	@Override
	public boolean end() {
		if (super.end()) {
			System.out.println("航班已降落");
			return true;
		} else
			return false;
	}

	// 航班在出发前可以被取消。
	@Override
	public boolean cancel() {
		if (super.cancel()) {
			System.out.println("航班已取消");
			return true;
		} else
			return false;
	}
	
	
	
}
