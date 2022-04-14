package factory;

import static org.junit.Assert.*;


import org.junit.Test;

import classSchedule.CourseEntry;

public class CourseEntryFactoryTest {

	
	//test strategy:
	//new TrainEntryFactory().getEntry（String name）
	//	只需测试是否生成trainentry对象，调用getEntry()并观察返回的对象
	@Test
	public void test() {
		CourseEntry newFac = new CourseEntryFactory().getEntry("testname");
		assertEquals("testname",newFac.getName());
	}

}
