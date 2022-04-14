package factory;

import static org.junit.Assert.*;


import org.junit.Test;

import flightSchedule.FlightEntry;

public class FlightEntryFactoryTest {

	
	//test strategy:
	//new TrainEntryFactory().getEntry（String name）
	//	只需测试是否生成trainentry对象，调用getEntry()并观察返回的对象
	@Test
	public void test() {
		FlightEntry newFac = new FlightEntryFactory().getEntry("testname");
		assertEquals("testname",newFac.getName());
	}

}
