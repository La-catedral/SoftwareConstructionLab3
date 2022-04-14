package board;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import factory.FlightEntryFactory;
import flightSchedule.FlightEntry;
import location.Airport;
import timeslot.TimeSlot;

public class FlightBoardTest {

	//测试策略
	//setIterToFro() 、setIterToArr() 、IterArrOrNot()
	//		（注：Iterator是返回列表的还是起飞列表的类型需要调用函数进行控制，同时也可以调用函数进行观察）
	//		分别调用setIterToFro()和setIterToArr()，并在每次调用相应方法后调用IterArrOrNot()
	//		观察返回值
	//timeWithin(long gap,Calendar timeNow,Calendar anotherTime)		
	//		将输入分为：两个时间对象的时间差<gap,=gap,>gap
	//		调用方法并观察结果
	//setToList(List<FlightEntry> entries) 、setFromList(List<FlightEntry> entries) 
	//与Iterator()一同测试，因为可以用该方法进行观察
	//		将entries中的计划项分为：航班从board对应的机场当天起飞，航班从board对应的机场次日起飞，
	//		航班从其他机场当天起飞，
	//		航班当天抵达board对应的机场，航班次日抵达board对应的机场，当天抵达其他机场
	//		分别调用setToList() setFromList() 并调用Iterator()观察结果
	
	//Iterator是返回列表的还是起飞列表的类型需要调用函数进行控制，同时也可以调用函数进行观察
	@Test
	public void testSetAndGetIterType() {
		Calendar testCalOne= Calendar.getInstance();
		
		String nameOfLocation = "testname";
		double latitude = 0;
		double longitude = 1;
		Airport testOne = new Airport(nameOfLocation, latitude, longitude);//this airport
		
		FlightBoard testBoard = new FlightBoard(testCalOne, testOne);
		testBoard.setIterToFro();
		assertFalse(testBoard.IterArrOrNot());
		testBoard.setIterToArr();
		assertTrue(testBoard.IterArrOrNot());
	}
	

	@Test
	public void testTimeWithin()
	{
		Calendar testCalOne= Calendar.getInstance();//当前时间
		testCalOne.set(2020, 4, 1,13,45);
		Calendar testCalTwo= Calendar.getInstance();//其他时间
		testCalTwo.set(2020, 4, 1,14,30);
		Calendar testCalThr= Calendar.getInstance();//其他时间
		testCalThr.set(2020, 4, 1,14,45);
		Calendar testCalFou= Calendar.getInstance();//其他时间
		testCalFou.set(2020, 4, 1,15,45);
		
		String nameOfLocation = "testname";
		double latitude = 0;
		double longitude = 1;
		Airport testOne = new Airport(nameOfLocation, latitude, longitude);//this airport
		FlightBoard testBoard = new FlightBoard(testCalOne, testOne);
		
		assertTrue(testBoard.timeWithin(60, testCalOne, testCalTwo));
		assertTrue(testBoard.timeWithin(60, testCalOne, testCalThr));
		assertFalse(testBoard.timeWithin(60, testCalOne, testCalFou));
	}
	
	@Test
	public void testSetToAndFromListAndIterator()
	{
		Calendar testCalOne= Calendar.getInstance();
		testCalOne.set(2020, 4, 1,10,00);
		Calendar testCalTwo= Calendar.getInstance();
		testCalTwo.set(2020, 4, 1,10,45);
		TimeSlot testSlot1= new TimeSlot(testCalOne, testCalTwo);//today
		
		Calendar testCalThr= Calendar.getInstance();
		testCalThr.set(2020, 4, 1,10,45);
		Calendar testCalFou= Calendar.getInstance();
		testCalFou.set(2020, 4, 1,10,55);
		TimeSlot testSlot2= new TimeSlot(testCalThr, testCalFou);//today
		
		Calendar testCalFiv= Calendar.getInstance();
		testCalFiv.set(2020, 4, 2,10,00);
		Calendar testCalSix= Calendar.getInstance();
		testCalSix.set(2020, 4, 2,10,30);
		TimeSlot testSlot3= new TimeSlot(testCalFiv, testCalSix);//other day's flight
		
		String nameOfLocation = "testname";
		double latitude = 0;
		double longitude = 1;
		Airport testOne = new Airport(nameOfLocation, latitude, longitude);//this airport
		
		String nameOfLocation1 = "anothername";
		double latitude1 = 1;
		double longitude1 = 2;
		Airport testTwo = new Airport(nameOfLocation1, latitude1, longitude1);
		
		String nameOfLocation2 = "thirdname";
		double latitude2 = 2;
		double longitude2 = 3;
		Airport testThree = new Airport(nameOfLocation2, latitude2, longitude2);
		
		FlightEntry firstEntry = new FlightEntryFactory().getEntry("firstEntry");
		FlightEntry secondEntry = new FlightEntryFactory().getEntry("secondEntry");
		FlightEntry thirdEntry = new FlightEntryFactory().getEntry("thirdEntry");
		FlightEntry fourthEntry = new FlightEntryFactory().getEntry("fourthEntry");
		FlightEntry fifthEntry = new FlightEntryFactory().getEntry("fifthEntry");
		FlightEntry sixthEntry = new FlightEntryFactory().getEntry("sixthEntry");
		FlightEntry seventhEntry = new FlightEntryFactory().getEntry("seventhEntry");		
		FlightEntry eighthEntry = new FlightEntryFactory().getEntry("eighthEntry");

		FlightBoard testBoard = new FlightBoard(testCalOne, testOne);
		
		firstEntry.setLocation(testOne, testTwo); //当日起飞计划项
		secondEntry.setLocation(testOne, testThree);//当日起飞计划项
		thirdEntry.setLocation(testTwo, testThree);//当日从其他机场起飞计划项
		fourthEntry.setLocation(testOne, testTwo);//他日从该机场起飞计划项
		fifthEntry.setLocation(testTwo, testOne);
		sixthEntry.setLocation(testThree, testOne);
		seventhEntry.setLocation(testThree, testTwo);
		eighthEntry.setLocation(testTwo, testOne);
		
		firstEntry.setSlot(testSlot1);
		secondEntry.setSlot(testSlot2);
		thirdEntry.setSlot(testSlot2);
		fourthEntry.setSlot(testSlot3);//他日航班起飞
		fifthEntry.setSlot(testSlot1);
		sixthEntry.setSlot(testSlot2);
		seventhEntry.setSlot(testSlot2);
		eighthEntry.setSlot(testSlot3);//他日航班抵达
		
		List<FlightEntry> testEntry = new ArrayList<>();
		testEntry.add(eighthEntry);
		testEntry.add(seventhEntry);
		testEntry.add(sixthEntry);
		testEntry.add(fifthEntry);
		testEntry.add(fourthEntry);
		testEntry.add(thirdEntry);
		testEntry.add(secondEntry);
		testEntry.add(firstEntry);
	
		testBoard.setFromList(testEntry);
		testBoard.setIterToFro();
		Iterator<FlightEntry> fromIte = testBoard.iterator();
		int i =0;
		while(fromIte.hasNext())
		{
			FlightEntry thisEntry = fromIte.next();
			if(i==0)
			{
				assertTrue(thisEntry.getName().equals("firstEntry"));
				i++;
			}
			else if(i==1)
			{
				assertTrue(thisEntry.getName().equals("secondEntry"));
				i++;
			}
			else {
				assertTrue(false);
			}
		}
		
		testBoard.setToList(testEntry);
		testBoard.setIterToArr();
		Iterator<FlightEntry> toIte = testBoard.iterator();
		i =0;
		while(toIte.hasNext())
		{
			FlightEntry thisEntry = toIte.next();
			if(i==0)
			{
				assertTrue(thisEntry.getName().equals("fifthEntry"));
				i++;
			}
			else if(i==1)
			{
				assertTrue(thisEntry.getName().equals("sixthEntry"));
				i++;
			}
			else {
				assertTrue(false);
			}
		}
	}
	
	
	

}
