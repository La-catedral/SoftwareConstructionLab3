package state;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import factory.FlightEntryFactory;
import flightSchedule.FlightEntry;

public class CANCELEDTest {

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

			@Test
			public void testToString() {
				CANCELED  testOne = CANCELED.instance;
				assertEquals("已取消", testOne.toString());
			}

			@Test
			public void testallocate() {
				FlightEntry testEntry = new FlightEntryFactory().getEntry("test");
				CANCELED testOne = CANCELED.instance;
				testEntry.setState(testOne);
				assertFalse(testOne.allocate(testEntry));
				assertEquals("该任务项已取消！\n", log.getLog());
			}

			@Test
			public void testBlock() {
				log.clearLog();
				FlightEntry testEntry = new FlightEntryFactory().getEntry("test");
				CANCELED testOne = CANCELED.instance;
				testEntry.setState(testOne);
				assertFalse(testOne.block(testEntry));
				assertEquals("该任务项已取消！\n", log.getLog());
			}

			@Test
			public void testCancel() {
				FlightEntry testEntry = new FlightEntryFactory().getEntry("test");
				CANCELED testOne = CANCELED.instance;
				testEntry.setState(testOne);
				assertFalse(testOne.cancel(testEntry));
				assertEquals("该任务项已取消！\n", log.getLog());
			}

			@Test
			public void testEnd() {
				log.clearLog();
				FlightEntry testEntry = new FlightEntryFactory().getEntry("test");
				CANCELED testOne = CANCELED.instance;
				testEntry.setState(testOne);
				assertFalse(testOne.end(testEntry));
				assertEquals("该任务项已取消！\n", log.getLog());
			}

			@Test
			public void testRun() {
				log.clearLog();
				FlightEntry testEntry = new FlightEntryFactory().getEntry("test");
				CANCELED testOne = CANCELED.instance;
				testEntry.setState(testOne);
				assertFalse(testOne.run(testEntry));
				assertEquals("该任务项已取消！\n", log.getLog());
			}

}
