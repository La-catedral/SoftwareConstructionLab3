package locationChange;

import location.Location;

public interface LocationCouldChange {
//提供更改位置的方法
	/**
	 * change the location
	 * @param thisLocation , the location to be changed to
	 */
	public void changeSingleLocation(Location thisLocation);
}
