package resource;

/**
 *immutable ,define the coach object
 */
public class Coach implements Resource {
//immutable
	private final String ID;
	private final String type;
	private final int seats;
	private final int birthYear;
	
	// Abstraction function:
    // 	AF(ID,type,seats,birthYear) = the ID of the coach,what type is the coach,how many seats does the coach have,when did it born  
    // Representation invariant:
    //	the field must be non null,seats >0;birthYear > 1900
    // Safety from rep exposure:
    //  all fields are private and final
	//	the Observer return an immutable


	//constructor
	public Coach(String iD, String type, int seats, int birthYear) {
		ID = iD;
		this.type = type;
		this.seats = seats;
		this.birthYear = birthYear;
		checkRep();
	}
	
	//checkRep
	private void checkRep()
	{
		assert ID != null;
		assert type != null;
		assert seats > 0 ;
		assert birthYear > 1900;
	}
	
	

	@Override
		public String getID() {
			return this.ID;
		}
	

	@Override
	public String toString() {
		return "ID: "+ID +" type:" +type+" seats=" + seats + " birthYear=" + birthYear ;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		result = prime * result + birthYear;
		result = prime * result + seats;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Coach other = (Coach) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		if (birthYear != other.birthYear)
			return false;
		if (seats != other.seats)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}





	
	
	

}
