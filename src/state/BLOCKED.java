package state;

import planningEntry.CommonPlanningEntry;

public class BLOCKED implements State{
	//immutable
	public static BLOCKED instance = new BLOCKED();
	private BLOCKED() {};
	

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
		System.out.println("当前状态无法进行此操作！");
		checkRep();
		return false;
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
		entry.setState(RUNNING.instance);
		return true;
	}
	@Override
	public String toString() {
		return "已阻塞";
	}
	
	
}
