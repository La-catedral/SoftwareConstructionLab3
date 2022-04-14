package state;

import planningEntry.CommonPlanningEntry;

public interface State {
	
	//分配 启动 完成 取消 挂起
	

	/**
	 * 改为分配状态
	 * @param entry 状态所属的计划项
	 * @return 状态成功改变则返回true 否则返回false
	 */
	public boolean allocate(CommonPlanningEntry<?> entry);

	/**
	 * 改为启动状态
	 * @param entry 状态所属的计划项
	 * @return 状态成功改变则返回true 否则返回false
	 */
	public boolean run(CommonPlanningEntry<?> entry);
	

	/**
	 * 改为结束状态
	 * @param entry 状态所属的计划项
	 * @return 状态成功改变则返回true 否则返回false
	 */
	public boolean end(CommonPlanningEntry<?> entry);
	
	/**
	 * 改为取消状态
	 * @param entry 状态所属的计划项
	 * @return 状态成功改变则返回true 否则返回false
	 */
	public boolean cancel(CommonPlanningEntry<?> entry);

	/**
	 * 改为挂起状态
	 * @param entry 状态所属的计划项
	 * @return 状态成功改变则返回true 否则返回false
	 */
	public boolean block(CommonPlanningEntry<?> entry);

}
