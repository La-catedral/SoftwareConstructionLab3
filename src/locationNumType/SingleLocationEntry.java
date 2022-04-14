package locationNumType;

import location.Location;

public interface SingleLocationEntry {
//管理一个位置
	

	/**
	 * 设置被管理的位置
	 * @param thisLocation
	 */
	public boolean setLocation(Location thisLocation);
	
	/**
	 * 获取被管理的位置
	 * @return 被管理的位置
	 */
	public Location getLocation();
}
