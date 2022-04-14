package trainSchedule;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import factory.TrainEntryFactory;
import location.ClassRoom;
import location.TrainStation;
import resource.Coach;
import resource.Teacher;
import state.ALLOCATED;
import state.BLOCKED;
import state.CANCELED;
import state.ENDED;
import state.RUNNING;
import state.WAITING;
import timeslot.TimeSlot;

public class TrainEntryTest {

	//test strategy
	//setLocation(List<Location> locList) 、 getLocationList()
	//		test them together,partition the input as follows:TrainStation type location,other type of location
	//		invoke the method setLocation() , then invoke the two getters and observe the result
	//setSlot(TimeSlot timeSlot)  、 getSlot()  
	//		test them together
	//		invoke the method setSlot() , then invoke getSlot() and observe the result		
	//setResource(List<Resource> resource) 、 getResource()
	//		test them together, partition the input as:Coach type resource,other type resource
	//		invoke the method setResource() , then invoke getResource() and observe the result		
	//setState(State state) 、 getState()
	//		test them together
	//		partition the input state as WAITING ALLOCATED RUNNING CANCELED ENDED 
	//		invoke the setState() then invoke the method getState() 
	//		observe the result
	//allocateResource()
	//		partition the current state of the object as:
	//		WAITING ALLOCATED RUNNING CANCELED ENDED BLOCKED
	//		on each state of them ,invoke the method and observe the result
	//run()
	//		partition the current state of the object as:
	//		WAITING ALLOCATED RUNNING CANCELED ENDED BLOCKED
	//		on each state of them ,invoke the method and observe the result
	//blockTheEntry(Calendar atTime)
	//		partition the input as :atTime is within the legal block time,atTime isn't within the legal block time
	//		partition the current state of the object as:
	//		WAITING ALLOCATED RUNNING CANCELED ENDED BLOCKED
	//		on these kinds of situations ,invoke the method and observe the result
	//reStart(Calendar atTime)
	//		partition the input as :atTime is within the legal block time,atTime isn't within the legal block time
	//		the state now also has the influence,but it had been tested in testRun()
	//end()
	//		partition the current state of the object as:
	//		WAITING ALLOCATED RUNNING CANCELED ENDED BLOCKED
	//		on each state of them ,invoke the method and observe the result
	//cancel()
	//		partition the current state of the object as:
	//		WAITING ALLOCATED RUNNING CANCELED ENDED BLOCKED
	//		on each state of them ,invoke the method and observe the result
	
	
	
	@Rule
	public final SystemOutRule log = new SystemOutRule().enableLog();
	@Test
	public void testSetAndGetLocation() {
		TrainEntry newEntry = new TrainEntryFactory().getEntry("testname");
		String nameOfLocation = "testname";
		double latitude = 0;
		double longitude = 1;
		TrainStation testOne = new TrainStation(nameOfLocation, latitude, longitude);
		
		String secondNameOfLocation = "secondname";
		double secondLatitude = 2;
		double secondLongitude = 3;
		TrainStation testTwo = new TrainStation(secondNameOfLocation, secondLatitude, secondLongitude);
		
		String nameOfLocation2 = "newname";
		double latitude2 = 2;
		double longitude2 = 3;
		ClassRoom testThree = new ClassRoom(nameOfLocation2, latitude2, longitude2);
		
		List<TrainStation> firstList = new ArrayList<>();
		firstList.add(testOne);
		firstList.add(testTwo);

		List<ClassRoom> secondList = new ArrayList<>();
		secondList.add(testThree);
		
		assertTrue(newEntry.setLocationList(firstList));
		assertFalse(newEntry.setLocationList(secondList));
		assertTrue(newEntry.getLocationList().contains(testOne));
		assertTrue(newEntry.getLocationList().contains(testTwo));
		assertFalse(newEntry.getLocationList().contains(testThree));
	}
	
	@Test
	public void testSetAndGetSlot()
	{
		TrainEntry newEntry = new TrainEntryFactory().getEntry("testname");
		Calendar testCalOne= Calendar.getInstance();
		testCalOne.set(2020, 4, 1,12,12);
		Calendar testCalTwo= Calendar.getInstance();
		testCalTwo.set(2021, 3, 2,13,13);
		TimeSlot testSlot= new TimeSlot(testCalOne, testCalTwo);
		newEntry.setSlot(testSlot);
		assertTrue(newEntry.getSlot().equals(testSlot));
	}
	
	@Test
	public void testSetAndGetSlotList()
	{
		TrainEntry newEntry = new TrainEntryFactory().getEntry("testname");
		Calendar testCalOne= Calendar.getInstance();
		testCalOne.set(2020, 4, 1,12,12);
		Calendar testCalTwo= Calendar.getInstance();
		testCalTwo.set(2021, 3, 2,13,13);
		TimeSlot testSlot= new TimeSlot(testCalOne, testCalTwo);
		
		Calendar testCalThree= Calendar.getInstance();
		testCalOne.set(2022, 4, 1,12,12);
		Calendar testCalFour= Calendar.getInstance();
		testCalTwo.set(2023, 3, 2,13,13);
		TimeSlot testSlottwo= new TimeSlot(testCalThree, testCalFour);

		List<TimeSlot> newList =new ArrayList<>();
		newList.add(testSlot);
		newList.add(testSlottwo);
		
		newEntry.setSlot(newList);
		assertTrue(newEntry.getSlotList().contains(testSlot));
		assertTrue(newEntry.getSlotList().contains(testSlottwo));
		
	}
	
	
	
	@Test
	public void testSetAndGetResource()
	{
		String ID = "testID";
		String type = "testName";
		int seats = 300;
		int birthYear = 2010;
		Coach testOne = new Coach(ID, type, seats, birthYear);
		List<Coach> newList = new ArrayList<>();
		
		String IDtwo = "testID";
		String nametwo = "testName";
		String gendertwo = "male";
		String titletwo = "professor";
		Teacher newTeacher = new Teacher(IDtwo, nametwo, gendertwo, titletwo);
		List<Teacher> secondList = new ArrayList<>();
	
		newList.add(testOne);
		secondList.add(newTeacher);
		
		TrainEntry newEntry = new TrainEntryFactory().getEntry("testname");
		assertTrue(newEntry.setResource(newList));
		assertFalse(newEntry.setResource(secondList));
		assertTrue(newEntry.getResource().contains(testOne));
		assertFalse(newEntry.getResource().contains(newTeacher));
	}
	
	
	@Test //getState()
	public void testSetAndGetState()
	{
		TrainEntry newEntry = new TrainEntryFactory().getEntry("testname");
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
		newEntry.setState(BLOCKED.instance);
		assertTrue(newEntry.getState().equals(BLOCKED.instance));
	}
	
	
	@Test
	public void testAllocateResource()
	{
		log.clearLog();
		TrainEntry newEntry = new TrainEntryFactory().getEntry("testname");
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
		newEntry.setState(BLOCKED.instance);
		assertFalse(newEntry.allocateResource());
		assertEquals("当前状态无法进行此操作！\n",log.getLog());
		log.clearLog();
		newEntry.setState(ENDED.instance);
		assertFalse(newEntry.allocateResource());
		assertEquals("该任务已结束！\n",log.getLog());
	}
	
	@Test
	public void testRun()
	{
		log.clearLog();
		TrainEntry newEntry = new TrainEntryFactory().getEntry("testname");
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
		
		newEntry.setState(BLOCKED.instance);
		assertTrue(newEntry.run());
		assertTrue(newEntry.getState().equals(RUNNING.instance));
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
	public void testBlock()
	{
		TrainEntry newEntry = new TrainEntryFactory().getEntry("testname");
		Calendar testCalOne= Calendar.getInstance();
		testCalOne.set(2020, 4, 1,12,12);
		Calendar testCalTwo= Calendar.getInstance();
		testCalTwo.set(2021, 3, 2,13,13);
		TimeSlot testSlot= new TimeSlot(testCalOne, testCalTwo);
		List<TimeSlot> slotList = new ArrayList<>();
		slotList.add(testSlot);
		newEntry.setSlot(slotList);
		
		newEntry.setState(WAITING.instance);
		assertFalse(newEntry.blockTheEntry(testCalOne));
		newEntry.setState(ALLOCATED.instance);
		assertFalse(newEntry.blockTheEntry(testCalOne));
		newEntry.setState(RUNNING.instance);
		
		assertFalse(newEntry.blockTheEntry(testCalTwo));
		assertFalse(newEntry.getState().equals(BLOCKED.instance));
		assertTrue(newEntry.blockTheEntry(testCalOne));
		assertTrue(newEntry.getState().equals(BLOCKED.instance));
		newEntry.setState(BLOCKED.instance);
		assertFalse(newEntry.blockTheEntry(testCalOne));
		newEntry.setState(CANCELED.instance);
		assertFalse(newEntry.blockTheEntry(testCalOne));
		newEntry.setState(ENDED.instance);
		assertFalse(newEntry.blockTheEntry(testCalOne));
	}
	
	@Test
	public void testRestart()
	{
		TrainEntry newEntry = new TrainEntryFactory().getEntry("testname");
		Calendar testCalOne= Calendar.getInstance();
		testCalOne.set(2020, 4, 1,12,12);
		Calendar testCalTwo= Calendar.getInstance();
		testCalTwo.set(2021, 3, 2,13,13);
		TimeSlot testSlot= new TimeSlot(testCalOne, testCalTwo);
		List<TimeSlot> slotList = new ArrayList<>();
		slotList.add(testSlot);
		newEntry.setSlot(slotList);
		newEntry.setState(BLOCKED.instance);
		
		assertFalse(newEntry.restart(testCalOne));
		assertTrue(newEntry.restart(testCalTwo));
	}
	
	
	@Test
	public void testEnd()
	{
		log.clearLog();
		TrainEntry newEntry = new TrainEntryFactory().getEntry("testname");
		newEntry.setState(WAITING.instance);
		assertFalse(newEntry.end());
		assertEquals("当前状态无法进行此操作！\n",log.getLog());
		log.clearLog();
		newEntry.setState(ALLOCATED.instance);
		assertFalse(newEntry.end());
		assertEquals("当前状态无法进行此操作！\n",log.getLog());
		
		newEntry.setState(RUNNING.instance);
		assertTrue(newEntry.end());
		assertTrue(newEntry.getState().equals(ENDED.instance));
		log.clearLog();
		newEntry.setState(BLOCKED.instance);
		assertFalse(newEntry.end());
		assertEquals("当前状态无法进行此操作！\n",log.getLog());
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
		TrainEntry newEntry = new TrainEntryFactory().getEntry("testname");
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
		
		newEntry.setState(BLOCKED.instance);
		assertTrue(newEntry.cancel());
		assertTrue(newEntry.getState().equals(CANCELED.instance));
		
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
