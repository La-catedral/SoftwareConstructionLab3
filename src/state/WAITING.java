package state;

import planningEntry.CommonPlanningEntry;

public class WAITING implements State{
	//immutable
	public static final WAITING instance = new WAITING();
	private WAITING() {};
	
	//Abstraction function:
	//	AF(instance) = the static instance of this state  
	//Representation invariant:
	//	the field must be non null
	//Safety from rep exposure:
	//	the field is final
	
	//checkRep()
	private void checkRep()
	{
		assert instance != null;
	}
	
	@Override
	public boolean allocate(CommonPlanningEntry<?> entry) {
		entry.setState(ALLOCATED.instance);
		checkRep();
		return true;
	}
	
	
	@Override
	public boolean block(CommonPlanningEntry<?> entry) {
		System.out.println("当前状态无法进行此操作！");
		return false;
	}
	
	
	@Override
	public boolean cancel(CommonPlanningEntry<?> entry) {
		entry.setState(CANCELED.instance);
		return true;	
	}
	
	@Override
	public boolean end(CommonPlanningEntry<?> entry) {
		System.out.println("当前状态无法进行此操作！");
		return false;
	}
	
	
	@Override
	public boolean run(CommonPlanningEntry<?> entry) {
		System.out.println("未分配资源！");
		return false;
	}
	@Override
	public String toString() {
		return "未分配资源";
	}
	

}
