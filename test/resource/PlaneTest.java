package resource;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlaneTest {

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
		String type = "testName";
		int seats = 300;
		double age = 2.5;
		Plane testOne = new Plane(ID, type, seats, age);
		assertTrue(testOne.getID().equals("testID"));

	}

	@Test
	public void testToString() {
		String ID = "testID";
		String type = "testName";
		int seats = 300;
		double age = 2.5;
		Plane testOne = new Plane(ID, type, seats, age);
		assertEquals("ID: " + ID + " type:" + type + " seats=" + seats + " age=" + age, testOne.toString());
	}

	@Test
	public void testEqualsAndHash() {
		String ID = "testID";
		String type = "testName";
		int seats = 300;
		double age = 2.5;
		Plane testOne = new Plane(ID, type, seats, age);
		Plane testTwo = new Plane(ID, type, seats, age);

		String ID1 = "secondID";
		String type1 = "type2";
		int seats1 = 200;
		double age1 = 3.5;
		Plane testThree = new Plane(ID1, type1, seats1, age1);

		assertTrue(testOne.equals(testTwo));
		assertFalse(testOne.equals(testThree));

		assertEquals(testOne.hashCode(), testTwo.hashCode());
		assertFalse(testOne.hashCode() == testThree.hashCode());
	}

}
