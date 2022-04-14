package classSchedule;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import factory.CourseEntryFactory;
import location.ClassRoom;
import resource.Teacher;
import state.ALLOCATED;
import state.CANCELED;
import state.ENDED;
import state.RUNNING;
import state.WAITING;
import timeslot.TimeSlot;

public class CourseEntryTest {

	//test strategy
	//setLocation(Location location) 、 getLocation()
	//		test them together
	//		invoke the method setLocation() , then invoke getLocation() and observe the result
	//changeSingleLocation(Location)
	// 		no output
	//		check the result by invoking the method getLocation
	//setSlot(TimeSlot timeSlot)  、 getSlot()  
	//		test them together
	//		invoke the method setSlot() , then invoke getSlot() and observe the result		
	//setResource(Resource resource) 、 getResource()
	//		test them together
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
		CourseEntry newEntry = new CourseEntryFactory().getEntry("testname");
		String nameOfLocation = "testname";
		double latitude = 0;
		double longitude = 1;
		ClassRoom testOne = new ClassRoom(nameOfLocation, latitude, longitude);
		newEntry.setLocation(testOne);
		assertTrue(newEntry.getLocation().equals(testOne));
	}
	
	//		check the result by invoking the method getLocation
	@Test
	public void testChangeSingleLocation()
	{
		CourseEntry newEntry = new CourseEntryFactory().getEntry("testname");
		String nameOfLocation = "testname";
		double latitude = 0;
		double longitude = 1;
		ClassRoom testOne = new ClassRoom(nameOfLocation, latitude, longitude);
		String nameOfSecondLoc = "anoname";
		double secondLatitude = 2;
		double secondLongitude = 3;
		ClassRoom testTwo = new ClassRoom(nameOfSecondLoc, secondLatitude, secondLongitude);
		newEntry.setLocation(testOne);
		newEntry.changeSingleLocation(testTwo);
		assertTrue(newEntry.getLocation().equals(testTwo));
	}
	
	@Test
	public void testSetAndGetSlot()
	{
		CourseEntry newEntry = new CourseEntryFactory().getEntry("testname");
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
		String name = "testName";
		String gender = "male";
		String title = "professor";
		Teacher newTeacher = new Teacher(ID, name, gender, title);
		
		CourseEntry newEntry = new CourseEntryFactory().getEntry("testname");
		newEntry.setResource(newTeacher);
		newEntry.getResource().equals(newTeacher);
	}
	
	
	@Test //getState()
	public void testSetAndGetState()
	{
		CourseEntry newEntry = new CourseEntryFactory().getEntry("testname");
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
		CourseEntry newEntry = new CourseEntryFactory().getEntry("testname");
		newEntry.setState(WAITING.instance);
		newEntry.allocateResource();
		assertTrue(newEntry.getState().equals(ALLOCATED.instance));
		log.clearLog();

		newEntry.allocateResource();
		assertEquals("当前状态无法进行此操作！\n",log.getLog());
		log.clearLog();

		newEntry.setState(RUNNING.instance);
		newEntry.allocateResource();
		assertEquals("当前状态无法进行此操作！\n",log.getLog());
		log.clearLog();

		newEntry.setState(CANCELED.instance);
		newEntry.allocateResource();
		assertEquals("该任务项已取消！\n",log.getLog());
		log.clearLog();

		newEntry.setState(ENDED.instance);
		newEntry.allocateResource();
		assertEquals("该任务已结束！\n",log.getLog());
	}
	
	@Test
	public void testRun()
	{
		log.clearLog();
		CourseEntry newEntry = new CourseEntryFactory().getEntry("testname");
		newEntry.setState(WAITING.instance);
		newEntry.run();
		assertEquals("未分配资源！\n",log.getLog());
		log.clearLog();

		newEntry.setState(ALLOCATED.instance);
		newEntry.run();
		assertTrue(newEntry.getState().equals(RUNNING.instance));
		log.clearLog();

		newEntry.setState(RUNNING.instance);
		newEntry.run();
		assertEquals("当前状态无法进行此操作！\n",log.getLog());
		log.clearLog();

		newEntry.setState(CANCELED.instance);
		newEntry.run();
		assertEquals("该任务项已取消！\n",log.getLog());
		log.clearLog();

		newEntry.setState(ENDED.instance);
		newEntry.run();
		assertEquals("该任务已结束！\n",log.getLog());
	}
	
	@Test
	public void testEnd()
	{
		log.clearLog();
		CourseEntry newEntry = new CourseEntryFactory().getEntry("testname");
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
		CourseEntry newEntry = new CourseEntryFactory().getEntry("testname");
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
