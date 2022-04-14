package resource;

/**
 *immutable ,define the teacher object
 */
public class Teacher implements Resource {
//身份证号、姓名、性别、职称
	private final String ID;
	private final String name;
	private final String gender;
	private final String title;

	// Abstraction function:
    // 	AF(ID,name,gender,title) = the ID of the teacher,the name of the teacher,the gender of the teacher,teacher's title
    // Representation invariant:
    //	the field must be non null,gender equals male or female
    // Safety from rep exposure:
    //  all fields are private and final
	//	the Observer return an immutable 
	
	//constructor 
	public Teacher(String ID, String name, String gender, String title) {
		this.ID =ID;
		this.name = name;
		this.gender = gender;
		this.title = title;
		checkRep();
	}
	
	//checkRep
	private void checkRep()
	{
		assert ID != null;
		assert  name != null;
		assert gender != null;
		assert title != null;
		assert gender.equals("male") || gender.equals("female");
	}

	@Override
		public String getID() {
			return this.ID;
		}
	/**
	 * 获取授课教师的姓名
	 * @return 授课教师的姓名
	 */
	public String getname() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return "ID: "+ID +" name:" +name+" gender=" + gender + " title=" + title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Teacher other = (Teacher) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}


	
	
	
	
	
	
	
}
