package location;

public interface Location {

	
	/**
	 * get the name of this location
	 * @return the name of this location
	 */
	public String getName();
	
	/**
	 * get whether it could share
	 * @return whether it could share
	 */
	public boolean whetherCouldShare();
	
	
	/**
	 * get the latitude of the location
	 * @return the latitude of the location
	 */
	public double getLatitude();
	
	/**
	 * get the longitude of the location
	 * @return the longi
	 * tude of the location
	 */
	public double getLongitude();
}
