package planningEntry;



import state.State;
import state.WAITING;

public abstract class CommonPlanningEntry<R> implements PlanningEntry<R>{
//mutable
	private State state;
	private final String name;

	// Abstraction function:
    // 	AF(state,name) = the state of the entry now,the name of the entry  
    // Representation invariant:
    //	the field must be non null
    // Safety from rep exposure:
    //  all fields are private ,the field name is final
	//	the Observer return an immutable object
	
	//constructor
	public CommonPlanningEntry(String name) {
		state = WAITING.instance;
		this.name = name;
		checkRep();
}
	
	private void checkRep()
	{
		assert state != null;
		assert name != null;
	}

	/**
	 * 为该计划项设置resource
	 */
	public boolean allocateResource()
	{
		return state.allocate(this);
	}
	
	//return the name of the planningEntry
	@Override
		public String getName()
		{
			return name;
		}	
	
		//启动任务项
		@Override
			public boolean run()
		{
			return state.run(this);
		}
		
		//结束任务项
	@Override
		public boolean end()
		{
			return state.end(this);
		}
		
		//取消任务项
		@Override
		public boolean cancel()
		{
//			canceler.cancel();
			return state.cancel(this);
		}
		
//		/**
//		 * cancel the entry at a specific time
//		 * @param atTime
//		 */
//		public abstract boolean cancel(Calendar atTime);
		
		//设置状态
		/**
		 * 设置该对象的状态
		 * @param state ，要被设置成的状态类型
		 */
		public void setState(State state)
		{
			this.state = state;
		}
		
		@Override
		public State getState()
		{
			return state;
		}
		
		
//		public abstract State getstate(Calendar atTime); 
		
		
	

}
