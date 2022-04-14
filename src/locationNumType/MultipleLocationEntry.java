package locationNumType;

import java.util.List;

import location.Location;

public interface MultipleLocationEntry {

	/**
	 * set a list of locations to be controlled by this object
	 * @param locaitonList, the locations 
	 */
	public boolean setLocationList(List<? extends Location> locaitonList);
	
	/**
	 * 获取位置列表
	 */
	public List<? extends Location> getLocationList();
}
