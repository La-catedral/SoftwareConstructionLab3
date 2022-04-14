package locationNumType;

import static org.junit.Assert.*;

import org.junit.Test;

import location.Airport;

public class DoubleLocationEntryImplementTest {

	
	//test strategy
	//setLocation(List<Location> location) and getLocation()
	//		invoke the method setLocation(),then invoke another one and observe the result
	
	
	@Test
	public void test() {
		String nameOfLocation = "testname";
		double latitude = 0;
		double longitude = 1;
		Airport testOne = new Airport(nameOfLocation, latitude, longitude);
		
		String nameOfLocation1 = "testname1";
		double latitude1 = 2;
		double longitude1 = 3;
		Airport testTwo = new Airport(nameOfLocation1, latitude1, longitude1);
		
		DoubleLocationEntryImplement newOne = new DoubleLocationEntryImplement();
		newOne.setLocation(testOne, testTwo);
		assertTrue(newOne.getfromLocation().equals(testOne));
		assertTrue(newOne.getToLocation().equals(testTwo));
	}

}
