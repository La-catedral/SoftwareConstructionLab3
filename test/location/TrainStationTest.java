package location;

import static org.junit.Assert.*;

import org.junit.Test;

public class TrainStationTest {

	//test strategy
		//whetherCouldShare()
		//		expect true
		//getName()
		//		no input 
		//		invoke the method,and observe the result
		//getLatitude()
		//		no input 
		//		invoke the method,and observe the result
		//getLongitude()
		//		no input 
		//		invoke the method,and observe the result
		//equals() hashCode()
		//		define three location object,two of them have same information,the third one not,
		//		invoke the method equals() and hashCode() on them and observe the result
		
		@Test
		public void testWhetherCouldShare() {
			String nameOfLocation = "testname";
			double latitude = 0;
			double longitude = 1;
			TrainStation testOne = new TrainStation(nameOfLocation, latitude, longitude);
			assertTrue(testOne.whetherCouldShare());
		}

		@Test
		public void testGetName() {
			String nameOfLocation = "testname";
			double latitude = 0;
			double longitude = 1;
			TrainStation testOne = new TrainStation(nameOfLocation, latitude, longitude);
			assertEquals("testname",testOne.getName());
		}

		
		@Test
		public void testGetLatitude() {
			String nameOfLocation = "testname";
			double latitude = 0;
			double longitude = 1;
			TrainStation testOne = new TrainStation(nameOfLocation, latitude, longitude);
			assertTrue(0 == testOne.getLatitude());
		}

		@Test
		public void testGetLongitude() {
			String nameOfLocation = "testname";
			double latitude = 0;
			double longitude = 1;
			TrainStation testOne = new TrainStation(nameOfLocation, latitude, longitude);
			assertTrue(testOne.getLongitude() == 1);
		}
		
		@Test
		public void testEqualsAndHash()
		{
			String nameOfLocation = "testname";
			double latitude = 0;
			double longitude = 1;
			TrainStation testOne = new TrainStation(nameOfLocation, latitude, longitude);
			
			String nameOfLocation1 = "testname";
			double latitude1 = 0;
			double longitude1 = 1;
			TrainStation testTwo = new TrainStation(nameOfLocation1, latitude1, longitude1);
			
			String nameOfLocation2 = "newname";
			double latitude2 = 2;
			double longitude2 = 3;
			TrainStation testThree = new TrainStation(nameOfLocation2, latitude2, longitude2);
			
			assertTrue(testOne.equals(testTwo));
			assertFalse(testOne.equals(testThree));
			
			assertTrue(testOne.hashCode() == testTwo.hashCode());
			assertFalse(testOne.hashCode() == testThree.hashCode());
		}

}
