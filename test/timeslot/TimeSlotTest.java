package timeslot;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;

public class TimeSlotTest {

	//test strategy
	//getTime():
	//		no input
	//		invoke it,compare it with the relevant String
	//getBeginTime()
	//		partition the compared calendar as:equals to the begin time of the TimeSlot、different from the begin time of the TimeSlot
	//		observe the result
	//getBeginTime()
	//		partition the compared calendar as:equals to the end time of the TimeSlot、different from the end time of the TimeSlot
	//		observe the result
	//timeWithintheSLot(Calendar thisTime)
	//		partition the input as follows:输入时间比时间对的起始时间早、比终止时间晚、等于起始时间、等于终止时间、在二者之间
	//checkCoinOrNot(TimeSlot thisSlot)
	//		partition the input as follows:输入的时间对完全比调用者早、完全比调用者晚、包含调用者、相交
	//		
	
	@Test
	public void testGetTime() {
		Calendar testCalOne= Calendar.getInstance();
		testCalOne.set(2020, 4, 1,12,12);
		Calendar testCalTwo= Calendar.getInstance();
		testCalTwo.set(2021, 3, 2,13,13);
		TimeSlot testSlot= new TimeSlot(testCalOne, testCalTwo);
		assertEquals("(2020-05-01 12:12,2021-04-02 13:13)",testSlot.toString());
	}
	
	@Test
	public void testGetBeginTime()
	{
		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar testCalOne= Calendar.getInstance();
		testCalOne.set(2020, 4, 1,12,12);
		Calendar testCalTwo= Calendar.getInstance();
		testCalTwo.set(2021, 3, 2,13,13);
		String calTwoStr = form.format(testCalTwo.getTime());
		Calendar testCalThree= Calendar.getInstance();
		testCalThree.set(2020, 4, 1,12,12);
		String calThrStr = form.format(testCalThree.getTime());
		TimeSlot testSlot= new TimeSlot(testCalOne, testCalTwo);
		assertTrue(calThrStr.equals(testSlot.getStrBegin()));
		assertFalse(calTwoStr.equals(testSlot.getStrBegin()));
	}
	
	@Test
	public void testGetEndTime()
	{
		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar testCalOne= Calendar.getInstance();
		testCalOne.set(2020, 4, 1,12,12);
		String calOneStr = form.format(testCalOne.getTime());
		Calendar testCalTwo= Calendar.getInstance();
		testCalTwo.set(2021, 3, 2,13,13);
		Calendar testCalThree= Calendar.getInstance();
		testCalThree.set(2021, 3, 2,13,13);
		String calThrStr = form.format(testCalThree.getTime());
		TimeSlot testSlot= new TimeSlot(testCalOne, testCalTwo);
		assertTrue(calThrStr.equals(testSlot.getStrEnd()));
		assertFalse(calOneStr.equals(testSlot.getStrEnd()));
	}
	
	@Test
	public void TestTimeWithintheSLot() {
		Calendar testCalOne= Calendar.getInstance();
		testCalOne.set(2020, 4, 1,12,12);
		Calendar testCalTwo= Calendar.getInstance();
		testCalTwo.set(2021, 3, 2,13,13);
		TimeSlot testSlot= new TimeSlot(testCalOne, testCalTwo);
		
		Calendar testCalThree= Calendar.getInstance();
		testCalThree.set(2020, 4, 1,12,12);
		assertFalse(testSlot.timeWithintheSLot(testCalThree));
		
		Calendar testCalFour= Calendar.getInstance();
		testCalFour.set(2021, 3, 2,13,13);
		assertFalse(testSlot.timeWithintheSLot(testCalFour));
		
		Calendar testCalFive= Calendar.getInstance();
		testCalFive.set(2020, 5, 1,12,12);
		assertTrue(testSlot.timeWithintheSLot(testCalFive));
		
		Calendar testCalSix= Calendar.getInstance();
		testCalSix.set(2019, 4, 1,12,12);
		assertFalse(testSlot.timeWithintheSLot(testCalSix));
		
	}
	
	@Test
	public void testCheckCoinOrNot()
	{
		Calendar testCalOne= Calendar.getInstance();
		testCalOne.set(2020, 4, 1,12,12);
		Calendar testCalTwo= Calendar.getInstance();
		testCalTwo.set(2021, 3, 2,13,13);
		TimeSlot testSlot1= new TimeSlot(testCalOne, testCalTwo);
		
		
		//起始时间晚于第一个时间对的结束时间
		Calendar testCal3= Calendar.getInstance();
		testCal3.set(2021, 4, 1,12,12);
		Calendar testCal4= Calendar.getInstance();
		testCal4.set(2022, 3, 2,13,13);
		TimeSlot testSlot2= new TimeSlot(testCal3, testCal4);
		assertFalse(testSlot1.checkCoinOrNot(testSlot2));
		
		//结束时间早于第一个时间对的起始时间
		Calendar testCal5= Calendar.getInstance();
		testCal5.set(2019, 4, 1,12,12);
		Calendar testCal6= Calendar.getInstance();
		testCal6.set(2020, 3, 2,13,13);
		TimeSlot testSlot3= new TimeSlot(testCal5, testCal6);
		assertFalse(testSlot1.checkCoinOrNot(testSlot3));
		
		//覆盖第一个时间对
		Calendar testCal7= Calendar.getInstance();
		testCal7.set(2019, 4, 1,12,12);
		Calendar testCal8= Calendar.getInstance();
		testCal8.set(2021, 3, 2,13,13);
		TimeSlot testSlot4= new TimeSlot(testCal7, testCal8);
		assertTrue(testSlot1.checkCoinOrNot(testSlot4));
		
		//相交
		Calendar testCal9= Calendar.getInstance();
		testCal9.set(2020, 4, 1,12,12);
		Calendar testCal10= Calendar.getInstance();
		testCal10.set(2020, 5, 2,13,13);
		TimeSlot testSlot5= new TimeSlot(testCal9, testCal10);
		assertTrue(testSlot1.checkCoinOrNot(testSlot5));
	}
	
	@Test
	public void testEqualsAndHashCode()
	{
		Calendar testCalOne= Calendar.getInstance();
		testCalOne.set(2020, 4, 1,12,12);
		Calendar testCalTwo= Calendar.getInstance();
		testCalTwo.set(2021, 3, 2,13,13);
		TimeSlot testSlot1= new TimeSlot(testCalOne, testCalTwo);
		
		
		//起始时间晚于第一个时间对的结束时间
		Calendar testCal3= Calendar.getInstance();
		testCal3.set(2021, 4, 1,12,12);
		Calendar testCal4= Calendar.getInstance();
		testCal4.set(2022, 3, 2,13,13);
		TimeSlot testSlot2= new TimeSlot(testCal3, testCal4);
		
		TimeSlot testSlot3= new TimeSlot(testCalOne, testCalTwo);
		assertTrue(testSlot1.equals(testSlot3));
		assertFalse(testSlot1.equals(testSlot2));
		assertEquals(testSlot1.hashCode(),testSlot3.hashCode());
		assertFalse(testSlot1.hashCode()==testSlot2.hashCode());
	}

}

