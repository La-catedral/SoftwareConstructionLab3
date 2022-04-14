package state;

import static org.junit.Assert.*;


import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import factory.FlightEntryFactory;
import flightSchedule.FlightEntry;

public class ENDEDTest {

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
	//		invoke it,catch the system output and observe it
	//end()
	//		no input
	//		invoke it,catch the system output and observe it
	//run()
	//		no input
	//		invoke it,catch the system output and observe it

	
@Rule
public final SystemOutRule log= new SystemOutRule().enableLog();

	
	
	
//	@Test
//	public void testToString() {
//		ENDED testOne = ENDED.instance;
//		assertEquals("已结束", testOne.toString());
//	}

	@Test
	public void testallocate() {
		FlightEntry testEntry = new FlightEntryFactory().getEntry("test");
		ENDED testOne = ENDED.instance;
		testEntry.setState(testOne);
		log.clearLog();
		assertFalse(testOne.allocate(testEntry));
		assertEquals("该任务已结束！\n", log.getLog());
	}

	@Test
	public void testBlock() {
		log.clearLog();
		FlightEntry testEntry = new FlightEntryFactory().getEntry("test");
		ENDED testOne = ENDED.instance;
		testEntry.setState(testOne);
		assertFalse(testOne.block(testEntry));
		assertEquals("该任务已结束！\n", log.getLog());
	}

	@Test
	public void testCancel() {
		FlightEntry testEntry = new FlightEntryFactory().getEntry("test");
		ENDED testOne = ENDED.instance;
		testEntry.setState(testOne);
		assertFalse(testOne.cancel(testEntry));
		assertEquals("该任务已结束！\n", log.getLog());
	}

	@Test
	public void testEnd() {
		log.clearLog();
		FlightEntry testEntry = new FlightEntryFactory().getEntry("test");
		ENDED testOne = ENDED.instance;
		testEntry.setState(testOne);
		assertFalse(testOne.end(testEntry));
		assertEquals("该任务已结束！\n", log.getLog());
	}

	@Test
	public void testRun() {
		log.clearLog();
		FlightEntry testEntry = new FlightEntryFactory().getEntry("test");
		ENDED testOne = ENDED.instance;
		testEntry.setState(testOne);
		assertFalse(testOne.run(testEntry));
		assertEquals("该任务已结束！\n", log.getLog());
	}
}
