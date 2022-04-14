package resourceType;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import resource.Plane;

public class MutipleSortedResourceEntryImplementTest {

	
	//test strategy:
	//setResource(List<Resource> resource) and getResource()
	//		invoke the method setResource(),then invoke another one and observe the result
		
	@Test
	public void testSetAndGet() {
		String ID = "testID";
		String type = "testName";
		int seats = 300;
		double age = 2.5;
		Plane testOne = new Plane(ID, type, seats, age);
		
		String ID1 = "testID1";
		String type1 = "testName1";
		int seats1 = 200;
		double age1 = 3.5;
		Plane testTwo = new Plane(ID1, type1, seats1, age1);
		
		List<Plane> testList = new ArrayList<>();
		testList.add(testOne);
		testList.add(testTwo);
		
		MutipleSortedResourceEntryImplement newOne = new MutipleSortedResourceEntryImplement();
		newOne.setResource(testList);
		assertTrue(newOne.getResource().contains(testOne));
		assertTrue(newOne.getResource().contains(testTwo));
	}

}
