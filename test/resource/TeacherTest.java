package resource;

import static org.junit.Assert.*;

import org.junit.Test;

public class TeacherTest {

	// Test strategy
	// getID()
	// 		no input
	// 		invoke the method ,compare the result with a relevant String
	// toString()
	// 		no input
	// 		invoke the method ,compare the result with a relevant String
	// equals(Object object)、hashCode()
	// 		partition the object as follows:two object have same information,the third
	// 		one different from them
	// 		invoke these two method on one of the and observe the result
	@Test
	public void testGetID() {
		String ID = "testID";
		String name = "testName";
		String gender = "male";
		String title = "professor";
		Teacher newTeacher = new Teacher(ID, name, gender, title);
		assertTrue(newTeacher.getID().equals("testID"));

	}

	@Test
	public void testToString() {
		String ID = "testID";
		String name = "testName";
		String gender = "male";
		String title = "professor";
		Teacher newTeacher = new Teacher(ID, name, gender, title);
		assertEquals("ID: " + ID + " name:" + name + " gender=" + gender + " title=" + title, newTeacher.toString());
	}

	@Test
	public void testEqualsAndHash() {
		String ID = "testID";
		String name = "testName";
		String gender = "male";
		String title = "professor";
		Teacher newTeacher = new Teacher(ID, name, gender, title);
		Teacher secondTecher = new Teacher(ID, name, gender, title);

		String ID2 = "newtestID";
		String name2 = "newtestName";
		String gender2 = "female";
		String title2 = "TA";
		Teacher thirdTecher = new Teacher(ID2, name2, gender2, title2);

		assertTrue(newTeacher.equals(secondTecher));
		assertFalse(newTeacher.equals(thirdTecher));

		assertEquals(newTeacher.hashCode(), secondTecher.hashCode());
		assertFalse(newTeacher.hashCode() == thirdTecher.hashCode());
	}
	

}
