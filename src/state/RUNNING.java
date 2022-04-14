package state;

import planningEntry.CommonPlanningEntry;

public class RUNNING implements State{
	//immutable
	public static RUNNING instance = new RUNNING();
	private RUNNING() {};
	

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
		entry.setState(BLOCKED.instance);
		return true;
	}
	
	@Override
	public boolean cancel(CommonPlanningEntry<?> entry) {
		System.out.println("当前状态无法进行此操作！");
		return false;
	}
	
	@Override
	public boolean end(CommonPlanningEntry<?> entry) {
		entry.setState(ENDED.instance);
		return true;
	}
	
	@Override
	public boolean run(CommonPlanningEntry<?> entry) {
		System.out.println("当前状态无法进行此操作！");
		return false;
	}
	@Override
	public String toString() {
		return "已启动";
	}
	
}
