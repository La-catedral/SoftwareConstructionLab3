package locationNumType;

import location.Location;

public interface DoubleLocationEntry {
//	管理一对位置 
	
	/**
	 * set the two orderly locations that will be controlled by this object
	 * @param fromLoc,the first location
	 * @param toLoc,the second location
	 */
	public boolean setLocation(Location fromLoc,Location toLoc);

	/**
	 * get the first location 
	 * @return,the first location
	 */
	public Location getfromLocation();
	
	/**
	 * get the second location 
	 * @return,the second location
	 */
	public Location getToLocation();
}
