package board;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import factory.TrainEntryFactory;
import location.TrainStation;
import timeslot.TimeSlot;
import trainSchedule.TrainEntry;

public class TrainBoardTest {


	//测试策略
		//setIterToFro() 、setIterToArr() 、IterArrOrNot()
		//		（注：Iterator是返回列表的还是发车列表的类型需要调用函数进行控制，同时也可以调用函数进行观察）
		//		分别调用setIterToFro()和setIterToArr()，并在每次调用相应方法后调用IterArrOrNot()
		//		观察返回值
		//timeWithin(long gap,Calendar timeNow,Calendar anotherTime)		
		//		将输入分为：两个时间对象的时间差<gap,=gap,>gap
		//		调用方法并观察结果
		//setToList(List<TrainEntry> entries) 、setFromList(List<TrainEntry> entries) 
		//与Iterator()一同测试，因为可以用该方法进行观察
		//		将entries中的计划项分为：火车当天从board对应车站发车，火车次日从board对应车站发车
		//		火车当天从其他车站发车，
		//		火车当天抵达board对应的车站，火车次日抵达board对应的车站，当天抵达其他车站
		//		分别调用setToList() setFromList() 并调用Iterator()观察结果
		//getFromTime(TrainEntry thisEntry) getToTime(TrainEntry thisEntry)
		//		将entries中的计划项分为：火车当天从board对应车站发车，火车次日从board对应车站发车
		//		火车当天从其他车站发车，
		//		火车当天抵达board对应的车站，火车次日抵达board对应的车站，当天抵达其他车站
		//		调用函数观察结果
		//getStateOfFrom(TrainEntry thisEntry)、getStateOfTo(TrainEntry thisEntry)
		//		将entries中的计划项分为：火车当天从board对应车站发车，火车次日从board对应车站发车
		//		火车当天从其他车站发车，
		//		火车当天抵达board对应的车站，火车次日抵达board对应的车站，当天抵达其他车站
		//		调用函数观察结果
		
	
	
		//Iterator是返回列表的还是起飞列表的类型需要调用函数进行控制，同时也可以调用函数进行观察
		@Test
		public void testSetAndGetIterType() {
			Calendar testCalOne= Calendar.getInstance();
			
			String nameOfLocation = "testname";
			double latitude = 0;
			double longitude = 1;
			TrainStation testOne = new TrainStation(nameOfLocation, latitude, longitude);//this airport
			
			TrainBoard testBoard = new TrainBoard(testCalOne, testOne);
			testBoard.setIterToFro();
			assertFalse(testBoard.IteArrOrNot());
			testBoard.setIterToArr();
			assertTrue(testBoard.IteArrOrNot());
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
		TrainStation testOne = new TrainStation(nameOfLocation, latitude, longitude);//this airport
		TrainBoard testBoard = new TrainBoard(testCalOne, testOne);
		
		assertTrue(testBoard.timeWithin(60, testCalOne, testCalTwo));
		assertTrue(testBoard.timeWithin(60, testCalOne, testCalThr));
		assertFalse(testBoard.timeWithin(60, testCalOne, testCalFou));
	}
	
	@Test
	public void testSetToAndFromListAndIterator()
	{
		
		String nameOfLocation = "testname";
		double latitude = 0;
		double longitude = 1;
		TrainStation testOne = new TrainStation(nameOfLocation, latitude, longitude);//this station
		
		String nameOfLocation1 = "anothername";
		double latitude1 = 1;
		double longitude1 = 2;
		TrainStation testTwo = new TrainStation(nameOfLocation1, latitude1, longitude1);
		
		String nameOfLocation2 = "thirdname";
		double latitude2 = 2;
		double longitude2 = 3;
		TrainStation testThree = new TrainStation(nameOfLocation2, latitude2, longitude2);
		
		TrainEntry firstEntry = new TrainEntryFactory().getEntry("firstEntry");
		TrainEntry secondEntry = new TrainEntryFactory().getEntry("secondEntry");
		TrainEntry thirdEntry = new TrainEntryFactory().getEntry("thirdEntry");
		TrainEntry fourthEntry = new TrainEntryFactory().getEntry("fourthEntry");
		
		Calendar testCalOne= Calendar.getInstance();
		testCalOne.set(2020, 4, 1,10,00);
		Calendar testCalTwo= Calendar.getInstance();
		testCalTwo.set(2020, 4, 1,10,45);
		TimeSlot testSlot1= new TimeSlot(testCalOne, testCalTwo);//today
		
		Calendar testCalThr= Calendar.getInstance();
		testCalThr.set(2020, 4, 1,10,25);
		Calendar testCalFou= Calendar.getInstance();
		testCalFou.set(2020, 4, 1,10,30);
		TimeSlot testSlot2= new TimeSlot(testCalThr, testCalFou);//today
		
		Calendar testCalFiv= Calendar.getInstance();
		testCalFiv.set(2020, 4, 2,10,25);
		Calendar testCalSix= Calendar.getInstance();
		testCalSix.set(2020, 4, 2,10,30);
		TimeSlot testSlot3= new TimeSlot(testCalFiv, testCalSix);//other day's train
		
		Calendar testCalSev= Calendar.getInstance();
		testCalSev.set(2020, 4, 2,10,00);
		Calendar testCalEig= Calendar.getInstance();
		testCalEig.set(2020, 4, 2,10,45);
		TimeSlot testSlot4= new TimeSlot(testCalSev, testCalEig);//today
		
		TrainBoard testBoard = new TrainBoard(testCalOne, testOne);
		
		List<TrainStation> stationListOne = new ArrayList<>();
		List<TrainStation> stationListTwo = new ArrayList<>();
		List<TrainStation> stationListThr = new ArrayList<>();
		
		stationListOne.add(testOne);
		stationListOne.add(testTwo);
		stationListOne.add(testThree);

		stationListTwo.add(testTwo);
		stationListTwo.add(testOne);
		stationListTwo.add(testThree);

		stationListThr.add(testTwo);
		stationListThr.add(testThree);
		stationListThr.add(testOne);

		firstEntry.setLocationList(stationListOne);//为当日发车计划项设置中途时间列表
		secondEntry.setLocationList(stationListTwo);//当日途径计划项
		thirdEntry.setLocationList(stationListThr);//当日抵达
		fourthEntry.setLocationList(stationListOne);//次日发车

		firstEntry.setSlot(testSlot1);//设置列车总的起止时间
		secondEntry.setSlot(testSlot1);
		thirdEntry.setSlot(testSlot1);
		fourthEntry.setSlot(testSlot4);
		
		List<TimeSlot> slotListOne = new ArrayList<>();
		List<TimeSlot> slotListTwo = new ArrayList<>();
		slotListOne.add(testSlot2);
		slotListTwo.add(testSlot3);
		firstEntry.setSlot(slotListOne);
		secondEntry.setSlot(slotListOne);
		thirdEntry.setSlot(slotListOne);
		fourthEntry.setSlot(slotListTwo);
		
		List<TrainEntry> testEntry = new ArrayList<>();
		testEntry.add(fourthEntry);
		testEntry.add(thirdEntry);
		testEntry.add(secondEntry);
		testEntry.add(firstEntry);
	
		testBoard.setFromList(testEntry);
		testBoard.setIterToFro();
		Iterator<TrainEntry> fromIte = testBoard.iterator();
		int i =0;
		while(fromIte.hasNext())
		{
			TrainEntry thisEntry = fromIte.next();
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
		Iterator<TrainEntry> toIte = testBoard.iterator();
		i =0;
		while(toIte.hasNext())
		{
			TrainEntry thisEntry = toIte.next();
			if(i==0)
			{
				assertTrue(thisEntry.getName().equals("secondEntry"));
				i++;
			}
			else if(i==1)
			{
				assertTrue(thisEntry.getName().equals("thirdEntry"));
				i++;
			}
			else {
				assertTrue(false);
			}
		}
	}
	
	@Test
	public void testGetFromAndToTime()
	{
		String nameOfLocation = "testname";
		double latitude = 0;
		double longitude = 1;
		TrainStation testOne = new TrainStation(nameOfLocation, latitude, longitude);//this station
		
		String nameOfLocation1 = "anothername";
		double latitude1 = 1;
		double longitude1 = 2;
		TrainStation testTwo = new TrainStation(nameOfLocation1, latitude1, longitude1);
		
		String nameOfLocation2 = "thirdname";
		double latitude2 = 2;
		double longitude2 = 3;
		TrainStation testThree = new TrainStation(nameOfLocation2, latitude2, longitude2);
		
		TrainEntry firstEntry = new TrainEntryFactory().getEntry("firstEntry");
		TrainEntry secondEntry = new TrainEntryFactory().getEntry("secondEntry");
		TrainEntry thirdEntry = new TrainEntryFactory().getEntry("thirdEntry");
		TrainEntry fourthEntry = new TrainEntryFactory().getEntry("fourthEntry");
		
		Calendar testCalOne= Calendar.getInstance();
		testCalOne.set(2020, 4, 1,10,00);
		Calendar testCalTwo= Calendar.getInstance();
		testCalTwo.set(2020, 4, 1,10,45);
		TimeSlot testSlot1= new TimeSlot(testCalOne, testCalTwo);//today
		
		Calendar testCalThr= Calendar.getInstance();
		testCalThr.set(2020, 4, 1,10,25);
		Calendar testCalFou= Calendar.getInstance();
		testCalFou.set(2020, 4, 1,10,30);
		TimeSlot testSlot2= new TimeSlot(testCalThr, testCalFou);//today
		
		Calendar testCalFiv= Calendar.getInstance();
		testCalFiv.set(2020, 4, 2,10,25);
		Calendar testCalSix= Calendar.getInstance();
		testCalSix.set(2020, 4, 2,10,30);
		TimeSlot testSlot3= new TimeSlot(testCalFiv, testCalSix);//other day's train
		
		Calendar testCalSev= Calendar.getInstance();
		testCalSev.set(2020, 4, 2,10,00);
		Calendar testCalEig= Calendar.getInstance();
		testCalEig.set(2020, 4, 2,10,45);
		TimeSlot testSlot4= new TimeSlot(testCalSev, testCalEig);//today
		
		TrainBoard testBoard = new TrainBoard(testCalOne, testOne);
		
		List<TrainStation> stationListOne = new ArrayList<>();
		List<TrainStation> stationListTwo = new ArrayList<>();
		List<TrainStation> stationListThr = new ArrayList<>();
		
		stationListOne.add(testOne);
		stationListOne.add(testTwo);
		stationListOne.add(testThree);

		stationListTwo.add(testTwo);
		stationListTwo.add(testOne);
		stationListTwo.add(testThree);

		stationListThr.add(testTwo);
		stationListThr.add(testThree);
		stationListThr.add(testOne);

		firstEntry.setLocationList(stationListOne);//为当日发车计划项设置中途时间列表
		secondEntry.setLocationList(stationListTwo);//当日途径计划项
		thirdEntry.setLocationList(stationListThr);//当日抵达
		fourthEntry.setLocationList(stationListOne);//次日发车

		firstEntry.setSlot(testSlot1);//设置列车总的起止时间
		secondEntry.setSlot(testSlot1);
		thirdEntry.setSlot(testSlot1);
		fourthEntry.setSlot(testSlot4);
		
		List<TimeSlot> slotListOne = new ArrayList<>();
		List<TimeSlot> slotListTwo = new ArrayList<>();
		slotListOne.add(testSlot2);
		slotListTwo.add(testSlot3);
		firstEntry.setSlot(slotListOne);
		secondEntry.setSlot(slotListOne);
		thirdEntry.setSlot(slotListOne);
		fourthEntry.setSlot(slotListTwo);
		
		List<TrainEntry> testEntry = new ArrayList<>();
		testEntry.add(fourthEntry);
		testEntry.add(thirdEntry);
		testEntry.add(secondEntry);
		testEntry.add(firstEntry);
		
		testBoard.setFromList(testEntry);
		testBoard.setToList(testEntry);
		
		assertTrue(testBoard.getFromTime(firstEntry).equals(testCalOne));
		assertTrue(testBoard.getFromTime(secondEntry).equals(testCalFou));
		assertTrue(testBoard.getFromTime(thirdEntry)==null);
		assertTrue(testBoard.getFromTime(fourthEntry)==null);

		assertTrue(testBoard.getToTime(secondEntry).equals(testCalThr));
		assertTrue(testBoard.getToTime(thirdEntry).equals(testCalTwo));
		assertTrue(testBoard.getToTime(firstEntry)==null);
		assertTrue(testBoard.getToTime(fourthEntry)==null);
		
	}
	
	@Test
	public void test()
	{
		String nameOfLocation = "testname";
		double latitude = 0;
		double longitude = 1;
		TrainStation testOne = new TrainStation(nameOfLocation, latitude, longitude);//this station
		
		String nameOfLocation1 = "anothername";
		double latitude1 = 1;
		double longitude1 = 2;
		TrainStation testTwo = new TrainStation(nameOfLocation1, latitude1, longitude1);
		
		String nameOfLocation2 = "thirdname";
		double latitude2 = 2;
		double longitude2 = 3;
		TrainStation testThree = new TrainStation(nameOfLocation2, latitude2, longitude2);
		
		TrainEntry firstEntry = new TrainEntryFactory().getEntry("firstEntry");
		TrainEntry secondEntry = new TrainEntryFactory().getEntry("secondEntry");
		TrainEntry thirdEntry = new TrainEntryFactory().getEntry("thirdEntry");
		TrainEntry fourthEntry = new TrainEntryFactory().getEntry("fourthEntry");
		
		Calendar testCalOne= Calendar.getInstance();
		testCalOne.set(2020, 4, 1,10,00);
		Calendar testCalTwo= Calendar.getInstance();
		testCalTwo.set(2020, 4, 1,10,45);
		TimeSlot testSlot1= new TimeSlot(testCalOne, testCalTwo);//today
		
		Calendar testCalThr= Calendar.getInstance();
		testCalThr.set(2020, 4, 1,10,25);
		Calendar testCalFou= Calendar.getInstance();
		testCalFou.set(2020, 4, 1,10,30);
		TimeSlot testSlot2= new TimeSlot(testCalThr, testCalFou);//today
		
		Calendar testCalFiv= Calendar.getInstance();
		testCalFiv.set(2020, 4, 2,10,25);
		Calendar testCalSix= Calendar.getInstance();
		testCalSix.set(2020, 4, 2,10,30);
		TimeSlot testSlot3= new TimeSlot(testCalFiv, testCalSix);//other day's train
		
		Calendar testCalSev= Calendar.getInstance();
		testCalSev.set(2020, 4, 2,10,00);
		Calendar testCalEig= Calendar.getInstance();
		testCalEig.set(2020, 4, 2,10,45);
		TimeSlot testSlot4= new TimeSlot(testCalSev, testCalEig);//today
		
		TrainBoard testBoard = new TrainBoard(testCalOne, testOne);
		
		List<TrainStation> stationListOne = new ArrayList<>();
		List<TrainStation> stationListTwo = new ArrayList<>();
		List<TrainStation> stationListThr = new ArrayList<>();
		
		stationListOne.add(testOne);
		stationListOne.add(testTwo);
		stationListOne.add(testThree);

		stationListTwo.add(testTwo);
		stationListTwo.add(testOne);
		stationListTwo.add(testThree);

		stationListThr.add(testTwo);
		stationListThr.add(testThree);
		stationListThr.add(testOne);

		firstEntry.setLocationList(stationListOne);//为当日发车计划项设置中途时间列表
		secondEntry.setLocationList(stationListTwo);//当日途径计划项
		thirdEntry.setLocationList(stationListThr);//当日抵达
		fourthEntry.setLocationList(stationListOne);//次日发车

		firstEntry.setSlot(testSlot1);//设置列车总的起止时间
		secondEntry.setSlot(testSlot1);
		thirdEntry.setSlot(testSlot1);
		fourthEntry.setSlot(testSlot4);
		
		List<TimeSlot> slotListOne = new ArrayList<>();
		List<TimeSlot> slotListTwo = new ArrayList<>();
		slotListOne.add(testSlot2);
		slotListTwo.add(testSlot3);
		firstEntry.setSlot(slotListOne);
		secondEntry.setSlot(slotListOne);
		thirdEntry.setSlot(slotListOne);
		fourthEntry.setSlot(slotListTwo);
		
		List<TrainEntry> testEntry = new ArrayList<>();
		testEntry.add(fourthEntry);
		testEntry.add(thirdEntry);
		testEntry.add(secondEntry);
		testEntry.add(firstEntry);
		
		testBoard.setFromList(testEntry);
		testBoard.setToList(testEntry);
		
		assertEquals("已出发",testBoard.getStateOfFrom(firstEntry));
		assertEquals("即将出发",testBoard.getStateOfFrom(secondEntry));
		assertTrue(testBoard.getStateOfFrom(thirdEntry)==null);
		assertTrue(testBoard.getStateOfFrom(fourthEntry)==null);

		assertEquals("即将抵达",testBoard.getStateOfTo(secondEntry));
		assertEquals("即将抵达",testBoard.getStateOfTo(thirdEntry));
		assertTrue(testBoard.getStateOfTo(firstEntry)==null);
		assertTrue(testBoard.getStateOfTo(fourthEntry)==null);
	}

}
