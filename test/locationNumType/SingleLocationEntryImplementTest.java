package locationNumType;

import static org.junit.Assert.*;

import org.junit.Test;

import location.Airport;

public class SingleLocationEntryImplementTest {

	////test strategy
	//setLocation(Location location) and getLocation()
	//		invoke the method setLocation(),then invoke another one and observe the result
	
	@Test
	public void test() {
		String nameOfLocation = "testname";
		double latitude = 0;
		double longitude = 1;
		Airport testOne = new Airport(nameOfLocation, latitude, longitude);
		SingleLocationEntryImplement newOne = new SingleLocationEntryImplement();
		newOne.setLocation(testOne);
		assertTrue(newOne.getLocation().equals(testOne));
	}

}
