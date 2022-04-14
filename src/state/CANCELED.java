package state;

import planningEntry.CommonPlanningEntry;

public class CANCELED implements State{
	//immutable
	public static CANCELED instance = new CANCELED();
	private CANCELED() {};
	

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
		System.out.println("该任务项已取消！");
		checkRep();
		return false;
	}
	
	@Override
	public boolean block(CommonPlanningEntry<?> entry) {
		System.out.println("该任务项已取消！");
		return false;
	}
	
	@Override
	public boolean cancel(CommonPlanningEntry<?> entry) {
		System.out.println("该任务项已取消！");
		return false;
	}
	
	@Override
	public boolean end(CommonPlanningEntry<?> entry) {
		System.out.println("该任务项已取消！");
		return false;
	}
	
	@Override
	public boolean run(CommonPlanningEntry<?> entry) {
		System.out.println("该任务项已取消！");
		return false;
	}
	
	@Override
	public String toString() {
		return "已取消";
	}
	
	
}
