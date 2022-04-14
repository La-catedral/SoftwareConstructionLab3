package planningEntry;



import state.State;

/**
 * build a new entry  
 * @param <R> type of resource of the entry, must be immutable
 */
public interface PlanningEntry<R> {
//利用特定资源、在特定地点/位置开展的一项任务
	
	
	//获取计划项的名字
	/**
	 * get the name of the Entry
	 * @return,the name of the entry
	 */
	public String getName();	

	//启动任务项
	/**
	 * make the entry running 
	 */
	public  boolean  run();
	
	//结束任务项
	/**
	 * end the entry 
	 */
	public boolean end();
	
	//取消任务项
	/**
	 * cancel the entry
	 */
	public boolean cancel();
	
//	public 
	
	//获取当前状态
	/**
	 * get the state of the entry
	 * @return,the state of the entry
	 */
	public State getState();
	
	
	
	
}
