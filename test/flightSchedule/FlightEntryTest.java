package flightSchedule;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import factory.FlightEntryFactory;
import location.Airport;
import location.ClassRoom;
import resource.Plane;
import resource.Teacher;
import state.ALLOCATED;
import state.CANCELED;
import state.ENDED;
import state.RUNNING;
import state.WAITING;
import timeslot.TimeSlot;

public class FlightEntryTest {

		//test strategy
		//setLocation(Location fromLoc,Location toLoc) 、 getfromLocation() getTolocation()
		//		test them together,partition the input as follows:Airport type location,other type of location
		//		invoke the method setLocation() , then invoke the two getters and observe the result
		//setSlot(TimeSlot timeSlot)  、 getSlot()  
		//		test them together
		//		invoke the method setSlot() , then invoke getSlot() and observe the result		
		//setResource(Resource resource) 、 getResource()
		//		test them together, partition the input as:Plane type resource,other type resource
		//		invoke the method setResource() , then invoke getResource() and observe the result		
		//setState(State state) 、 getState()
		//		test them together
		//		partition the input state as WAITING ALLOCATED RUNNING CANCELED ENDED 
		//		invoke the setState() then invoke the method getState() 
		//		observe the result
		//allocateResource()
		//		partition the current state of the object as:
		//		WAITING ALLOCATED RUNNING CANCELED ENDED
		//		on each state of them ,invoke the method and observe the result
		//run()
		//		partition the current state of the object as:
		//		WAITING ALLOCATED RUNNING CANCELED ENDED
		//		on each state of them ,invoke the method and observe the result
		//end()
		//		partition the current state of the object as:
		//		WAITING ALLOCATED RUNNING CANCELED ENDED
		//		on each state of them ,invoke the method and observe the result
		//cancel()
		//		partition the current state of the object as:
		//		WAITING ALLOCATED RUNNING CANCELED ENDED
		//		on each state of them ,invoke the method and observe the result
		
		
		
		@Rule
		public final SystemOutRule log = new SystemOutRule().enableLog();
		@Test
		public void testSetAndGetLocation() {
			FlightEntry newEntry = new FlightEntryFactory().getEntry("testname");
			String nameOfLocation = "testname";
			double latitude = 0;
			double longitude = 1;
			Airport testOne = new Airport(nameOfLocation, latitude, longitude);
			
			String secondNameOfLocation = "secondname";
			double secondLatitude = 2;
			double secondLongitude = 3;
			Airport testTwo = new Airport(secondNameOfLocation, secondLatitude, secondLongitude);
			
			String nameOfLocation2 = "newname";
			double latitude2 = 2;
			double longitude2 = 3;
			ClassRoom testThree = new ClassRoom(nameOfLocation2, latitude2, longitude2);
			
			assertTrue(newEntry.setLocation(testOne, testTwo));
			assertFalse(newEntry.setLocation(testOne, testThree));
			assertTrue(newEntry.getfromLocation().equals(testOne));
			assertTrue(newEntry.getToLocation().equals(testTwo));
		}
		
		@Test
		public void testSetAndGetSlot()
		{
			FlightEntry newEntry = new FlightEntryFactory().getEntry("testname");
			Calendar testCalOne= Calendar.getInstance();
			testCalOne.set(2020, 4, 1,12,12);
			Calendar testCalTwo= Calendar.getInstance();
			testCalTwo.set(2021, 3, 2,13,13);
			TimeSlot testSlot= new TimeSlot(testCalOne, testCalTwo);
			newEntry.setSlot(testSlot);
			assertTrue(newEntry.getSlot().equals(testSlot));
		}
		
		@Test
		public void testSetAndGetResource()
		{
			String ID = "testID";
			String type = "testName";
			int seats = 300;
			double age = 2.5;
			Plane testOne = new Plane(ID, type, seats, age);
			
			String IDtwo = "testID";
			String nametwo = "testName";
			String gendertwo = "male";
			String titletwo = "professor";
			Teacher newTeacher = new Teacher(IDtwo, nametwo, gendertwo, titletwo);
			
			FlightEntry newEntry = new FlightEntryFactory().getEntry("testname");
			assertTrue(newEntry.setResource(testOne));
			assertFalse(newEntry.setResource(newTeacher));
			assertTrue(newEntry.getResource().equals(testOne));
		}
		
		
		@Test //getState()
		public void testSetAndGetState()
		{
			FlightEntry newEntry = new FlightEntryFactory().getEntry("testname");
			newEntry.setState(WAITING.instance);
			assertTrue(newEntry.getState().equals(WAITING.instance));
			newEntry.setState(ALLOCATED.instance);
			assertTrue(newEntry.getState().equals(ALLOCATED.instance));
			newEntry.setState(RUNNING.instance);
			assertTrue(newEntry.getState().equals(RUNNING.instance));
			newEntry.setState(CANCELED.instance);
			assertTrue(newEntry.getState().equals(CANCELED.instance));
			newEntry.setState(ALLOCATED.instance);
			assertTrue(newEntry.getState().equals(ALLOCATED.instance));
			newEntry.setState(ENDED.instance);
			assertTrue(newEntry.getState().equals(ENDED.instance));
		}
		
		
		@Test
		public void testAllocateResource()
		{
			log.clearLog();
			FlightEntry newEntry = new FlightEntryFactory().getEntry("testname");
			newEntry.setState(WAITING.instance);
			assertTrue(newEntry.allocateResource());
			assertTrue(newEntry.getState().equals(ALLOCATED.instance));
			log.clearLog();

			assertFalse(newEntry.allocateResource());
			assertEquals("当前状态无法进行此操作！\n",log.getLog());
			log.clearLog();

			newEntry.setState(RUNNING.instance);
			assertFalse(newEntry.allocateResource());
			assertEquals("当前状态无法进行此操作！\n",log.getLog());
			log.clearLog();

			newEntry.setState(CANCELED.instance);
			assertFalse(newEntry.allocateResource());
			assertEquals("该任务项已取消！\n",log.getLog());
			log.clearLog();

			newEntry.setState(ENDED.instance);
			assertFalse(newEntry.allocateResource());
			assertEquals("该任务已结束！\n",log.getLog());
		}
		
		@Test
		public void testRun()
		{
			log.clearLog();
			FlightEntry newEntry = new FlightEntryFactory().getEntry("testname");
			newEntry.setState(WAITING.instance);
			assertFalse(newEntry.run());
			assertEquals("未分配资源！\n",log.getLog());
			log.clearLog();

			newEntry.setState(ALLOCATED.instance);
			assertTrue(newEntry.run());
			assertTrue(newEntry.getState().equals(RUNNING.instance));
			log.clearLog();

			newEntry.setState(RUNNING.instance);
			assertFalse(newEntry.run());
			assertEquals("当前状态无法进行此操作！\n",log.getLog());
			log.clearLog();

			newEntry.setState(CANCELED.instance);
			assertFalse(newEntry.run());
			assertEquals("该任务项已取消！\n",log.getLog());
			log.clearLog();

			newEntry.setState(ENDED.instance);
			assertFalse(newEntry.run());
			assertEquals("该任务已结束！\n",log.getLog());
		}
		
		@Test
		public void testEnd()
		{
			log.clearLog();
			FlightEntry newEntry = new FlightEntryFactory().getEntry("testname");
			newEntry.setState(WAITING.instance);
			assertFalse(newEntry.end());
			assertEquals("当前状态无法进行此操作！\n",log.getLog());
			log.clearLog();

			newEntry.setState(ALLOCATED.instance);
			assertFalse(newEntry.end());
			assertEquals("当前状态无法进行此操作！\n",log.getLog());
			log.clearLog();

			newEntry.setState(RUNNING.instance);
			assertTrue(newEntry.end());
			assertTrue(newEntry.getState().equals(ENDED.instance));
			log.clearLog();

			newEntry.setState(ENDED.instance);
			assertFalse(newEntry.end());
			assertEquals("该任务已结束！\n",log.getLog());
			log.clearLog();

			newEntry.setState(CANCELED.instance);
			assertFalse(newEntry.end());
			assertEquals("该任务项已取消！\n",log.getLog());
		}
		
		@Test
		public void testCancel()
		{
			log.clearLog();
			FlightEntry newEntry = new FlightEntryFactory().getEntry("testname");
			newEntry.setState(WAITING.instance);
			assertTrue(newEntry.cancel());
			assertTrue(newEntry.getState().equals(CANCELED.instance));
			
			newEntry.setState(ALLOCATED.instance);
			assertTrue(newEntry.cancel());
			assertTrue(newEntry.getState().equals(CANCELED.instance));
			log.clearLog();

			newEntry.setState(RUNNING.instance);
			assertFalse(newEntry.cancel());
			assertEquals("当前状态无法进行此操作！\n",log.getLog());
			log.clearLog();

			newEntry.setState(CANCELED.instance);
			assertFalse(newEntry.cancel());
			assertEquals("该任务项已取消！\n",log.getLog());
			log.clearLog();

			newEntry.setState(ENDED.instance);
			assertFalse(newEntry.cancel());
			assertEquals("该任务已结束！\n",log.getLog());
		}

}
