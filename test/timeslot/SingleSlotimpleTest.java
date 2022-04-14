package timeslot;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

public class SingleSlotimpleTest {

	
	//test strategy
	//setSlot(TimeSlot slot) together with getSlot():
	//		调用setSlot（）设置一个时间对，随后再对其调用getSlot（）
	//		观察返回结果
	
	@Test
	public void testSetAndGet() {
		Calendar testCalOne= Calendar.getInstance();
		testCalOne.set(2020, 4, 1,12,12);
		Calendar testCalTwo= Calendar.getInstance();
		testCalTwo.set(2021, 3, 2,13,13);
		TimeSlot testSlot= new TimeSlot(testCalOne, testCalTwo);
		TimeSlot testSlot2 = new TimeSlot(testCalOne,testCalTwo);
		TimeSlot testSlot3 = new TimeSlot(testCalTwo,testCalOne);
		
		
		SingleSlotimple newOne = new SingleSlotimple();
		newOne.setSlot(testSlot);
		assertTrue(newOne.getSlot().equals(testSlot2));
		assertFalse(newOne.getSlot().equals(testSlot3));

	}


}
