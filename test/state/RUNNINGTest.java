package state;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import factory.FlightEntryFactory;
import flightSchedule.FlightEntry;

public class RUNNINGTest {

	//Test strategy
	//toString()
	// 		invoke it,compare it with the relevant String
	//allocate()
	//		no input
	//		invoke it,catch the system output and observe it
	//block()
	//		no input
	//		invoke it,observe the next state 
	//cancel()
	//		no input
	//		invoke it,catch the system output and observe it
	//end()
	//		no input
	//		invoke it,observe the next state 
	//run()
	//		no input
	//		invoke it,catch the system output and observe it

@Rule
public final SystemOutRule log= new SystemOutRule().enableLog();


	@Test
	public void testToString() {
		RUNNING testOne = RUNNING.instance;
		assertEquals("已启动", testOne.toString());
	}

	@Test
	public void testallocate() {
		FlightEntry testEntry = new FlightEntryFactory().getEntry("test");
		RUNNING testOne = RUNNING.instance;
		testEntry.setState(testOne);
		assertFalse(testOne.allocate(testEntry));
		assertEquals("当前状态无法进行此操作！\n", log.getLog());
	}

	@Test
	public void testBlock() {
		log.clearLog();
		FlightEntry testEntry = new FlightEntryFactory().getEntry("test");
		RUNNING testOne = RUNNING.instance;
		testEntry.setState(testOne);
		assertTrue(testOne.block(testEntry));
		assertEquals(BLOCKED.instance, testEntry.getState());
	}

	@Test
	public void testCancel() {
		FlightEntry testEntry = new FlightEntryFactory().getEntry("test");
		RUNNING testOne = RUNNING.instance;
		testEntry.setState(testOne);
		assertFalse(testOne.cancel(testEntry));
		assertEquals("当前状态无法进行此操作！\n", log.getLog());
	}

	@Test
	public void testEnd() {
		log.clearLog();
		FlightEntry testEntry = new FlightEntryFactory().getEntry("test");
		RUNNING testOne = RUNNING.instance;
		testEntry.setState(testOne);
		assertTrue(testOne.end(testEntry));
		assertEquals(ENDED.instance, testEntry.getState());
	}

	@Test
	public void testRun() {
		log.clearLog();
		FlightEntry testEntry = new FlightEntryFactory().getEntry("test");
		RUNNING testOne = RUNNING.instance;
		testEntry.setState(testOne);
		assertFalse(testOne.run(testEntry));
		assertEquals("当前状态无法进行此操作！\n", log.getLog());
	}
	

}
