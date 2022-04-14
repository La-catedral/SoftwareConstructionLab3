package resource;

import static org.junit.Assert.*;

import org.junit.Test;

public class CoachTest {

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
			int birthYear = 2010;
			Coach testOne = new Coach(ID, type, seats, birthYear);
			assertTrue(testOne.getID().equals("testID"));

		}

		@Test
		public void testToString() {
			String ID = "testID";
			String type = "testName";
			int seats = 300;
			int birthYear = 2010;
			Coach testOne = new Coach(ID, type, seats, birthYear);
			assertEquals("ID: "+ID +" type:" +type+" seats=" + seats + " birthYear=" + birthYear, testOne.toString());
		}

		@Test
		public void testEqualsAndHash() {
			String ID = "testID";
			String type = "testName";
			int seats = 300;
			int birthYear = 2010;
			Coach testOne = new Coach(ID, type, seats, birthYear);
			Coach testTwo = new Coach(ID, type, seats, birthYear);

			String ID1 = "secondID";
			String type1 = "type2";
			int seats1 = 200;
			int birthYear1 = 2013;
			Coach testThree = new Coach(ID1, type1, seats1, birthYear1);

			assertTrue(testOne.equals(testTwo));
			assertFalse(testOne.equals(testThree));

			assertEquals(testOne.hashCode(), testTwo.hashCode());
			assertFalse(testOne.hashCode() == testThree.hashCode());
		}

}
