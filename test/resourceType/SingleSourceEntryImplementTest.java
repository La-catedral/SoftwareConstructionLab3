package resourceType;

import static org.junit.Assert.*;

import org.junit.Test;

import resource.Plane;

public class SingleSourceEntryImplementTest {

	
	//test strategy
	//setResource(Resource resour) and getResource()
	//		invoke the method setResource(),then invoke another one and observe the result
	
	@Test
	public void testSetAndGet() {
		String ID = "testID";
		String type = "testName";
		int seats = 300;
		double age = 2.5;
		Plane testOne = new Plane(ID, type, seats, age);
		SingleSourceEntryImplement newOne = new SingleSourceEntryImplement();
		newOne.setResource(testOne);
		assertTrue(newOne.getResource().equals(testOne));
	}

}
