package state;

import static org.junit.Assert.*;


import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import factory.FlightEntryFactory;
import flightSchedule.FlightEntry;

public class ALLOCATEDTest {

	//Test strategy
	//toString()
	// 		invoke it,compare it with the relevant String
	//allocate()
	//		no input
	//		invoke it,catch the system output and observe it
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
	//		invoke it,observe the next state 

	
@Rule
public final SystemOutRule log= new SystemOutRule().enableLog();

	@Test
	public void testToString() {
		ALLOCATED testOne = ALLOCATED.instance;
		assertEquals("已分配资源", testOne.toString());
	}

	@Test
	public void testallocate() {
		FlightEntry testEntry = new FlightEntryFactory().getEntry("test");
		ALLOCATED testOne = ALLOCATED.instance;
		testEntry.setState(testOne);
		assertFalse(testOne.allocate(testEntry));
		assertEquals("当前状态无法进行此操作！\n", log.getLog());
	}

	@Test
	public void testBlock() {
		log.clearLog();
		FlightEntry testEntry = new FlightEntryFactory().getEntry("test");
		ALLOCATED testOne = ALLOCATED.instance;
		testEntry.setState(testOne);
		assertFalse(testOne.block(testEntry));
		assertEquals("当前状态无法进行此操作！\n", log.getLog());
	}

	@Test
	public void testCancel() {
		FlightEntry testEntry = new FlightEntryFactory().getEntry("test");
		ALLOCATED testOne = ALLOCATED.instance;
		testEntry.setState(testOne);
		assertTrue(testOne.cancel(testEntry));
		assertEquals(CANCELED.instance, testEntry.getState());
	}

	@Test
	public void testEnd() {
		log.clearLog();
		FlightEntry testEntry = new FlightEntryFactory().getEntry("test");
		ALLOCATED testOne = ALLOCATED.instance;
		testEntry.setState(testOne);
		assertFalse(testOne.end(testEntry));
		assertEquals("当前状态无法进行此操作！\n", log.getLog());
	}

	@Test
	public void testRun() {
		log.clearLog();
		FlightEntry testEntry = new FlightEntryFactory().getEntry("test");
		ALLOCATED testOne = ALLOCATED.instance;
		testEntry.setState(testOne);
		assertTrue(testOne.run(testEntry));
		assertEquals(RUNNING.instance, testEntry.getState());
	}
}
