package state;

import static org.junit.Assert.*;


import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import factory.FlightEntryFactory;
import flightSchedule.FlightEntry;
public class WAITINGTest {

	//Test strategy
			//toString()
			// 		invoke it,compare it with the relevant String
			//allocate()
			//		no input
			//		invoke it,observe the next state 
			//block()
			//		no input
			//		invoke it,catch the system output and observe it
			//cancel()
			//		no input
			//		invoke it,observe the next state 
			//end()
			//		no input
			//		invoke it,catch the system output and observe it
			//run()
			//		no input
			//		invoke it,catch the system output and observe it
	
	@Rule
	public final SystemOutRule log= new SystemOutRule().enableLog();
	
	
	@Test
	public void testToString() {
//		log.clearLog();
		WAITING testOne = WAITING.instance;
		assertEquals("未分配资源",testOne.toString());
	}
	
	@Test
	public void testallocate()
	{
		FlightEntry testEntry = new FlightEntryFactory().getEntry("test"); 
		WAITING testOne = WAITING.instance;
		testEntry.setState(testOne);
		assertTrue(testOne.allocate(testEntry));
		assertEquals(ALLOCATED.instance,testEntry.getState());
	}
	
	@Test
	public void testBlock()
	{
		log.clearLog();
		FlightEntry testEntry = new FlightEntryFactory().getEntry("test"); 
		WAITING testOne = WAITING.instance;
		testEntry.setState(testOne);
		assertFalse(testOne.block(testEntry));
		assertEquals("当前状态无法进行此操作！\n",log.getLog());
	}
	
	@Test
	public void testCancel()
	{
		FlightEntry testEntry = new FlightEntryFactory().getEntry("test"); 
		WAITING testOne = WAITING.instance;
		testEntry.setState(testOne);
		assertTrue(testOne.cancel(testEntry));
		assertEquals(CANCELED.instance,testEntry.getState());
	}
	
	@Test
	public void  testEnd()
	{
		log.clearLog();
		FlightEntry testEntry = new FlightEntryFactory().getEntry("test"); 
		WAITING testOne = WAITING.instance;
		testEntry.setState(testOne);
		assertFalse(testOne.end(testEntry));
		assertEquals("当前状态无法进行此操作！\n",log.getLog());
	}
	
	@Test
	public void testRun()
	{
		log.clearLog();
		FlightEntry testEntry = new FlightEntryFactory().getEntry("test"); 
		WAITING testOne = WAITING.instance;
		testEntry.setState(testOne);
		assertFalse(testOne.run(testEntry));
		assertEquals("未分配资源！\n",log.getLog());
	}

}
