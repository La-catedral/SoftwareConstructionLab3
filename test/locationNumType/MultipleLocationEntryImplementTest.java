package locationNumType;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import location.Airport;

public class MultipleLocationEntryImplementTest {

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
		
		List<Airport> testList = new ArrayList<>();
		testList.add(testOne);
		testList.add(testTwo);
		
		MultipleLocationEntryImplement newOne = new MultipleLocationEntryImplement();
		newOne.setLocationList(testList);
		assertTrue(newOne.getLocationList().contains(testOne));
		assertTrue(newOne.getLocationList().contains(testTwo));

	}

}
