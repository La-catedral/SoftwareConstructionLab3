package factory;

import static org.junit.Assert.*;

import org.junit.Test;

import trainSchedule.TrainEntry;

public class TrainEntryFactoryTest {

	//test strategy:
	//new TrainEntryFactory().getEntry（String name）
	//	只需测试是否生成trainentry对象，调用getEntry()并观察返回的对象
	@Test
	public void test() {
		TrainEntry newFac = new TrainEntryFactory().getEntry("testname");
		assertEquals("testname",newFac.getName());
	}

}
