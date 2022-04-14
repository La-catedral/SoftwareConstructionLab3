package location;
// 应用到的三个类的父类，设计的三个类与其为继承关系，如需要扩展CPU的设计，可以在CPU中使用委托
public class CommonLocation implements Location{
	//immutable
	
	//user field
	private final String nameOfLocation;
	private final boolean shareAavilable;
	private final double latitude ;
	private final double longitude;
	
//	 Abstraction function:
//	AF(nameOfLocation,shareAavilable,latitude，longitude) = the name of the location,if the location is available to share,the latitude and the longitude of the location  
//Representation invariant:
//	the field must be non null,latitude and longitude must be in (-180,180]
//Safety from rep exposure:
// all fields are private and final 
//	all the Observers return an immutable object

	//constructor
	public CommonLocation(String nameOfLocation, boolean shareAavilable, double latitude, double longitude) {
		this.nameOfLocation = nameOfLocation;
		this.shareAavilable = shareAavilable;
		this.latitude = latitude;
		this.longitude = longitude;
		checkRep();
	}
	
	//checkRep
	private void checkRep()
	{
		assert nameOfLocation != null;
		assert latitude >-180 && latitude <=180;
		assert longitude >-180 && longitude <=180;
	}

	
	
	@Override
	public String getName() {
		return nameOfLocation ;
	}
	
	@Override
	public boolean whetherCouldShare()
	{
		return shareAavilable;
	}
	
	@Override
	public double getLatitude()
	{
		return latitude;
	}
	
	@Override
	public double getLongitude() {
		return longitude;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((nameOfLocation == null) ? 0 : nameOfLocation.hashCode());
		result = prime * result + (shareAavilable ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommonLocation other = (CommonLocation) obj;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		if (nameOfLocation == null) {
			if (other.nameOfLocation != null)
				return false;
		} else if (!nameOfLocation.equals(other.nameOfLocation))
			return false;
		if (shareAavilable != other.shareAavilable)
			return false;
		return true;
	}

	
	
	
	
	
	

}
