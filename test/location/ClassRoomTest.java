package location;

import static org.junit.Assert.*;

import org.junit.Test;

public class ClassRoomTest {

	// test strategy
	// whetherCouldShare()
	// 		expect false
	// getName()
	// 		no input
	// 		invoke the method,and observe the result
	// getLatitude()
	// 		no input
	// 		invoke the method,and observe the result
	// getLongitude()
	// 		no input
	// 		invoke the method,and observe the result
	// equals() hashCode()
	// 		define three location object,two of them have same information,the third one
	// 		not,
	// 		invoke the method equals() and hashCode() on them and observe the result

	@Test
	public void testWhetherCouldShare() {
		String nameOfLocation = "testname";
		double latitude = 0;
		double longitude = 1;
		ClassRoom testOne = new ClassRoom(nameOfLocation, latitude, longitude);
		assertFalse(testOne.whetherCouldShare());
	}

	@Test
	public void testGetName() {
		String nameOfLocation = "testname";
		double latitude = 0;
		double longitude = 1;
		ClassRoom testOne = new ClassRoom(nameOfLocation, latitude, longitude);
		assertEquals("testname",testOne.getName());
	}

	
	@Test
	public void testGetLatitude() {
		String nameOfLocation = "testname";
		double latitude = 0;
		double longitude = 1;
		ClassRoom testOne = new ClassRoom(nameOfLocation, latitude, longitude);
		assertTrue(0 == testOne.getLatitude());
	}

	@Test
	public void testGetLongitude() {
		String nameOfLocation = "testname";
		double latitude = 0;
		double longitude = 1;
		ClassRoom testOne = new ClassRoom(nameOfLocation, latitude, longitude);
		assertTrue(testOne.getLongitude() == 1);
	}
	
	@Test
	public void testEqualsAndHash()
	{
		String nameOfLocation = "testname";
		double latitude = 0;
		double longitude = 1;
		ClassRoom testOne = new ClassRoom(nameOfLocation, latitude, longitude);
		
		String nameOfLocation1 = "testname";
		double latitude1 = 0;
		double longitude1 = 1;
		ClassRoom testTwo = new ClassRoom(nameOfLocation1, latitude1, longitude1);
		
		String nameOfLocation2 = "newname";
		double latitude2 = 2;
		double longitude2 = 3;
		ClassRoom testThree = new ClassRoom(nameOfLocation2, latitude2, longitude2);
		
		assertTrue(testOne.equals(testTwo));
		assertFalse(testOne.equals(testThree));
		
		assertTrue(testOne.hashCode() == testTwo.hashCode());
		assertFalse(testOne.hashCode() == testThree.hashCode());
	}
	
}
