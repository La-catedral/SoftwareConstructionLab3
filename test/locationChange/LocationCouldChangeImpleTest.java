package locationChange;

import static org.junit.Assert.*;


import org.junit.Test;

import location.Airport;
import locationNumType.SingleLocationEntryImplement;

public class LocationCouldChangeImpleTest {

	//test strategy
	//setsingleLoc(SingleLocationEntryImplement singleLoc) 、 changeSingleLocation(Location loc)
	//		test them together
	//		at first invoke setsingleLoc(),then invoke changeSingleLocation() 
	//		to change the Location to be managed ,observe the Location that
	//		singleLoc manage now
	@Test
	public void test() {
		String nameOfLocation = "testname";
		double latitude = 0;
		double longitude = 1;
		Airport testOne = new Airport(nameOfLocation, latitude, longitude);
		SingleLocationEntryImplement singleTest = new SingleLocationEntryImplement();
		singleTest.setLocation(testOne);
		
		LocationCouldChangeImple newOne = new LocationCouldChangeImple();
		newOne.setsingleLoc(singleTest);
		
		String nameOfLocation1 = "testname1";
		double latitude1 = 2;
		double longitude1 = 3;
		Airport testTwo = new Airport(nameOfLocation1, latitude1, longitude1);
		newOne.changeSingleLocation(testTwo);
		assertTrue(singleTest.getLocation().equals(testTwo));
		
	}

}
