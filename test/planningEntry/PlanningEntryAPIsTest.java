package planningEntry;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import classSchedule.CourseEntry;
import factory.CourseEntryFactory;
import factory.FlightEntryFactory;
import factory.TrainEntryFactory;
import flightSchedule.FlightEntry;
import location.Airport;
import location.ClassRoom;
import resource.Coach;
import resource.Plane;
import resource.Teacher;
import timeslot.TimeSlot;
import trainSchedule.TrainEntry;

public class PlanningEntryAPIsTest {

	//测试策略
	//checkLocationConflict(CheckLocationInterface thisCheckStrategy,List<? extends PlanningEntry<?>>   entries)
	//		该方法采用strategy模式 有checkLocationConForceImple 和  checkLocationConFast两种实现
	//		除了将输入的策略分为两类，还需要将entries列表中的计划项的位置类型分为可共享和不可共享
	//		以CourseEntry作为不可共享的代表 以FlightEntry作为可共享的代表
	//		先测试不可共享：
	//		将计划项的关系分为：两计划项间时间冲突但位置不冲突，两计划项位置冲突时间不冲突，以及两计划项时间位置均冲突
	//		分别调用函数的两种策略的实现，并观察结果
	//		再测试可共享：
	//		将计划项关系分为：两计划项时间相交、位置相同，时间不相交，位置相同；
	//		分别调用方法的两种策略的实现，并观察结果
	//checkResourceExclusiveConflict(List<? extends PlanningEntry<?>> entries)
	//		对将输入列表的计划项类型划分为：FlightEntry CourseEntry TrainEntry
	//		分别对其进行测试
	//		将调用函数输入列表的计划项的关系分为：两计划项间时间冲突但资源不冲突，两计划项资源冲突时间不冲突，以及两计划项时间资源均冲突
	//		调用方法并观察结果
	//findPreEntryPerResource(R r ,PlanningEntry<R> e, List<? extends PlanningEntry<R>> entries)
	//		测试包括了三种计划项类型，对于其中每一种：
	//		传入的计划项列表中含有使用资源r的关于e的前置计划项，使用资源r的关于e的后置计划项，使用其他资源的比e早的计划项
	//		调用方法，观察返回结果
	
	
	@Test
	public void testcheckLocationConflict() {
		
		PlanningEntryAPIs<Teacher> newAPI =new PlanningEntryAPIs<>();
		
		Calendar testCalOne= Calendar.getInstance();//第一个时间对
		testCalOne.set(2020, 4, 1,12,12);
		Calendar testCalTwo= Calendar.getInstance();
		testCalTwo.set(2021, 3, 2,13,13);
		TimeSlot testSlot= new TimeSlot(testCalOne, testCalTwo);
		
		Calendar testCalThree= Calendar.getInstance();//第二个时间对 与第一个不相交
		testCalThree.set(2021, 4, 1,12,12);
		Calendar testCalFour= Calendar.getInstance();
		testCalFour.set(2022, 3, 2,13,13);
		TimeSlot secondSlot= new TimeSlot(testCalThree, testCalFour);
		
		Calendar testCalFive= Calendar.getInstance();//第三个时间对 与第一个时间对相交
		testCalFive.set(2020, 9, 1,12,12);
		Calendar testCalSix= Calendar.getInstance();
		testCalSix.set(2021, 4, 2,13,13);
		TimeSlot thirdSlot= new TimeSlot(testCalFive, testCalSix);
		
		String nameOfLocation = "testname";//第一个位置
		double latitude = 0;
		double longitude = 1;
		ClassRoom testOne = new ClassRoom(nameOfLocation, latitude, longitude);
		String nameOfSecondLoc = "anoname";//第二个位置
		double secondLatitude = 2;
		double secondLongitude = 3;
		ClassRoom testTwo = new ClassRoom(nameOfSecondLoc, secondLatitude, secondLongitude);
		
		CourseEntry newEntry = new CourseEntryFactory().getEntry("testone");
		CourseEntry secondEntry = new CourseEntryFactory().getEntry("testtwo");
		CourseEntry thirdEntry = new CourseEntryFactory().getEntry("testthree");
		CourseEntry fourthEntry = new CourseEntryFactory().getEntry("testfour");

		newEntry.setSlot(testSlot);//第一个计划项设置第一个时间对
		secondEntry.setSlot(secondSlot);//第二个计划项设置第二个时间对
		thirdEntry.setSlot(thirdSlot);//第三个计划项设置第三个时间对 与第一个时间对相交
		fourthEntry.setSlot(thirdSlot);//第四个计划项也设置第三个时间对 与第一个时间对相交
		
		newEntry.setLocation(testOne);//前三个计划项均设置同一地点
		secondEntry.setLocation(testOne);
		thirdEntry.setLocation(testOne);
		fourthEntry.setLocation(testTwo);
		
		
		List<CourseEntry> firstList = new ArrayList<>();
		List<CourseEntry> secondList = new ArrayList<>();
		firstList.add(newEntry);
		firstList.add(secondEntry);
		firstList.add(fourthEntry);
		secondList.add(newEntry);
		secondList.add(thirdEntry);
		
		CheckLocationInterface checkStrategy =new CheckLocationConForceImp();
		//先检查checkLocationConflict（）的第一种实现
		assertFalse(newAPI.checkLocationConflict(checkStrategy, firstList));
		assertTrue(newAPI.checkLocationConflict(checkStrategy, secondList));
		//检查checkLocationConflict（）的第二种实现
		checkStrategy = new CheckLocationConFast();
		assertFalse(newAPI.checkLocationConflict(checkStrategy, firstList));
		assertTrue(newAPI.checkLocationConflict(checkStrategy, secondList));
		
		PlanningEntryAPIs<Plane> newAPI1 =new PlanningEntryAPIs<>();
		//对位置可共享的计划项进行测试
		FlightEntry firstFlight = new FlightEntryFactory().getEntry("firstFli");
		FlightEntry secondFlight = new FlightEntryFactory().getEntry("secondFli");
		FlightEntry thirdFlight = new FlightEntryFactory().getEntry("thirdFli");

		String flightLocation = "testname";//第一个位置
		double fliLatitude = 0;
		double fliLongitude = 1;
		Airport newPort =new Airport(flightLocation, fliLatitude, fliLongitude);
		String flightLocationtwo = "secondname";//第一个位置
		double fliLatitudetwo = 2;
		double fliLongitudetwo = 3;
		Airport secondPort =new Airport(flightLocationtwo, fliLatitudetwo, fliLongitudetwo);
		
		firstFlight.setLocation(newPort, secondPort);
		secondFlight.setLocation(newPort, secondPort);
		thirdFlight.setLocation(newPort, secondPort);
		
		firstFlight.setSlot(testSlot);
		secondFlight.setSlot(secondSlot);
		thirdFlight.setSlot(thirdSlot);
		List<FlightEntry>  flightList = new ArrayList<>();
		flightList.add(firstFlight);
		flightList.add(secondFlight);
		flightList.add(thirdFlight);
		
		assertFalse(newAPI1.checkLocationConflict(new CheckLocationConForceImp(), flightList));
		assertFalse(newAPI1.checkLocationConflict(new CheckLocationConFast(), flightList));
	}

	@Test
	public void test()
	{
		PlanningEntryAPIs<Coach> newAPI =new PlanningEntryAPIs<>();
		
		Calendar testCalOne= Calendar.getInstance();//第一个时间对
		testCalOne.set(2020, 4, 1,12,12);
		Calendar testCalTwo= Calendar.getInstance();
		testCalTwo.set(2021, 3, 2,13,13);
		TimeSlot testSlot= new TimeSlot(testCalOne, testCalTwo);
		
		Calendar testCalThree= Calendar.getInstance();//第二个时间对 与第一个不相交
		testCalThree.set(2021, 4, 1,12,12);
		Calendar testCalFour= Calendar.getInstance();
		testCalFour.set(2022, 3, 2,13,13);
		TimeSlot secondSlot= new TimeSlot(testCalThree, testCalFour);
		
		Calendar testCalFive= Calendar.getInstance();//第三个时间对 与第一个时间对相交
		testCalFive.set(2020, 9, 1,12,12);
		Calendar testCalSix= Calendar.getInstance();
		testCalSix.set(2021, 4, 2,13,13);
		TimeSlot thirdSlot= new TimeSlot(testCalFive, testCalSix);
		
		String ID = "testID";
		String type = "testName";
		int seats = 300;
		int birthYear = 2010;
		Coach testOne = new Coach(ID, type, seats, birthYear);
		String ID1 = "secondID";
		String type1 = "type2";
		int seats1 = 200;
		int birthYear1 = 2013;
		Coach testTwo = new Coach(ID1, type1, seats1, birthYear1);
		String ID2 = "testID2";
		String type2 = "testName2";
		int seats2 = 100;
		int birthYear2 = 2012;
		Coach testThree = new Coach(ID2, type2, seats2, birthYear2);
		String ID3= "testID3";
		String type3 = "testName3";
		int seats3 = 100;
		int birthYear3 = 2008;
		Coach testFour = new Coach(ID3, type3, seats3, birthYear3);
		List<Coach> firstTrainList = new ArrayList<>();
		List<Coach> secondTrainList = new ArrayList<>();
		firstTrainList.add(testOne);
		firstTrainList.add(testTwo);
		secondTrainList.add(testThree);
		secondTrainList.add(testFour);
		
		TrainEntry trainOne = new TrainEntryFactory().getEntry("one");
		TrainEntry trainTwo = new TrainEntryFactory().getEntry("two");
		TrainEntry trainThree = new TrainEntryFactory().getEntry("three");
		TrainEntry trainFour = new TrainEntryFactory().getEntry("four");
		
		trainOne.setSlot(testSlot);
		trainTwo.setSlot(secondSlot);
		trainThree.setSlot(thirdSlot);
		trainFour.setSlot(thirdSlot);
		
		trainOne.setResource(firstTrainList);
		trainTwo.setResource(firstTrainList);
		trainThree.setResource(firstTrainList);
		trainFour.setResource(secondTrainList);
		List<TrainEntry> trainEntryListOne= new ArrayList<>();
		List<TrainEntry> trainEntryListTwo= new ArrayList<>();
		trainEntryListOne.add(trainOne);
		trainEntryListOne.add(trainThree);
		trainEntryListTwo.add(trainOne);
		trainEntryListTwo.add(trainTwo);
		trainEntryListTwo.add(trainFour);		
		assertTrue(newAPI.checkResourceExclusiveConflict(trainEntryListOne));
		assertFalse(newAPI.checkResourceExclusiveConflict(trainEntryListTwo));

		PlanningEntryAPIs<Plane> newAPI1 =new PlanningEntryAPIs<>();
		//对Flight计划项进行判断
		String ID5 = "testID";
		String type5 = "testName";
		int seats5 = 300;
		double age5 = 2.5;
		Plane planeOne = new Plane(ID5, type5, seats5, age5);
		String ID6 = "ID";
		String type6 = "Name";
		int seats6 = 200;
		double age6 = 3.5;
		Plane planeTwo = new Plane(ID6, type6, seats6, age6);
		
		FlightEntry firstFlight = new FlightEntryFactory().getEntry("firstFli");
		FlightEntry secondFlight = new FlightEntryFactory().getEntry("secondFli");
		FlightEntry thirdFlight = new FlightEntryFactory().getEntry("thirdFli");
		FlightEntry fourthFlight = new FlightEntryFactory().getEntry("fourthFli");

		firstFlight.setResource(planeOne);
		secondFlight.setResource(planeOne);
		thirdFlight.setResource(planeOne);
		fourthFlight.setResource(planeTwo);
		
		firstFlight.setSlot(testSlot);
		secondFlight.setSlot(secondSlot);
		thirdFlight.setSlot(thirdSlot);
		fourthFlight.setSlot(thirdSlot);
		
		List<FlightEntry>  flightListOne = new ArrayList<>();
		List<FlightEntry>  flightListTwo = new ArrayList<>();
		flightListOne.add(firstFlight);
		flightListOne.add(thirdFlight);
		flightListTwo.add(firstFlight);
		flightListTwo.add(secondFlight);
		flightListTwo.add(fourthFlight);
		
		assertTrue(newAPI1.checkResourceExclusiveConflict(flightListOne));
		assertFalse(newAPI1.checkResourceExclusiveConflict(flightListTwo));
		
		PlanningEntryAPIs<Teacher> newAPI2 =new PlanningEntryAPIs<>();
		String ID7 = "testID";
		String name7 = "testName";
		String gender7 = "male";
		String title7 = "professor";
		Teacher firstTeacher = new Teacher(ID7, name7, gender7, title7);
		String ID8 = "ID";
		String name8 = "Name";
		String gender8 = "female";
		String title8 = "TA";
		Teacher secondTeacher = new Teacher(ID8, name8, gender8, title8);
		
		CourseEntry firstCourse = new CourseEntryFactory().getEntry("firstFli");
		CourseEntry secondCourse = new CourseEntryFactory().getEntry("secondFli");
		CourseEntry thirdCourse = new CourseEntryFactory().getEntry("thirdFli");
		CourseEntry fourthCourse = new CourseEntryFactory().getEntry("fourthFli");

		firstCourse.setResource(firstTeacher);
		secondCourse.setResource(firstTeacher);
		thirdCourse.setResource(firstTeacher);
		fourthCourse.setResource(secondTeacher);
		
		firstCourse.setSlot(testSlot);
		secondCourse.setSlot(secondSlot);
		thirdCourse.setSlot(thirdSlot);
		fourthCourse.setSlot(thirdSlot);
		
		List<CourseEntry>  courseListOne = new ArrayList<>();
		List<CourseEntry>  courseListTwo = new ArrayList<>();
		courseListOne.add(firstCourse);
		courseListOne.add(thirdCourse);
		courseListTwo.add(firstCourse);
		courseListTwo.add(secondCourse);
		courseListTwo.add(fourthCourse);
		
		assertTrue(newAPI2.checkResourceExclusiveConflict(courseListOne));
		assertFalse(newAPI2.checkResourceExclusiveConflict(courseListTwo));
	}
	
	//测试包括了三种计划项类型，对于其中每一种：
	//传入的计划项列表中含有使用资源r的关于e的前置计划项，使用资源r的关于e的后置计划项，使用其他资源的比e早的计划项
	@Test
	public void testfindPreEntryPerResource()
	{
		PlanningEntryAPIs<Coach> newAPI =new PlanningEntryAPIs<>();
		
		Calendar testCalOne= Calendar.getInstance();//第一个时间对
		testCalOne.set(2020, 4, 1,12,12);
		Calendar testCalTwo= Calendar.getInstance();
		testCalTwo.set(2021, 3, 2,13,13);
		TimeSlot testSlot= new TimeSlot(testCalOne, testCalTwo);
		
		Calendar testCalThree= Calendar.getInstance();//第二个时间对 与第一个不相交
		testCalThree.set(2021, 4, 1,12,12);
		Calendar testCalFour= Calendar.getInstance();
		testCalFour.set(2022, 3, 2,13,13);
		TimeSlot secondSlot= new TimeSlot(testCalThree, testCalFour);
		
		Calendar testCalFive= Calendar.getInstance();//第三个时间对 与第一个时间对相交
		testCalFive.set(2022, 9, 1,12,12);
		Calendar testCalSix= Calendar.getInstance();
		testCalSix.set(2023, 4, 2,13,13);
		TimeSlot thirdSlot= new TimeSlot(testCalFive, testCalSix);
		
		String ID = "testID";
		String type = "testName";
		int seats = 300;
		int birthYear = 2010;
		Coach testOne = new Coach(ID, type, seats, birthYear);
		String ID1 = "secondID";
		String type1 = "type2";
		int seats1 = 200;
		int birthYear1 = 2013;
		Coach testTwo = new Coach(ID1, type1, seats1, birthYear1);
		String ID2 = "testID2";
		String type2 = "testName2";
		int seats2 = 100;
		int birthYear2 = 2012;
		Coach testThree = new Coach(ID2, type2, seats2, birthYear2);
		String ID3= "testID3";
		String type3 = "testName3";
		int seats3 = 100;
		int birthYear3 = 2008;
		Coach testFour = new Coach(ID3, type3, seats3, birthYear3);
		List<Coach> firstTrainList = new ArrayList<>();
		List<Coach> secondTrainList = new ArrayList<>();
		firstTrainList.add(testOne);
		firstTrainList.add(testTwo);
		secondTrainList.add(testThree);
		secondTrainList.add(testFour);
		

		TrainEntry trainOne = new TrainEntryFactory().getEntry("one");
		TrainEntry trainTwo = new TrainEntryFactory().getEntry("two");
		TrainEntry trainThree = new TrainEntryFactory().getEntry("three");
		TrainEntry trainFour = new TrainEntryFactory().getEntry("four");
		
		trainOne.setSlot(secondSlot);//计划项e
		trainTwo.setSlot(testSlot);//前置
		trainThree.setSlot(thirdSlot);//时间晚
		trainFour.setSlot(testSlot);//仅时间早
		
		trainOne.setResource(firstTrainList);
		trainTwo.setResource(firstTrainList);
		trainThree.setResource(firstTrainList);
		trainFour.setResource(secondTrainList);
		List<TrainEntry> trainEntryListOne= new ArrayList<>();
		List<TrainEntry> trainEntryListTwo= new ArrayList<>();
		trainEntryListOne.add(trainOne);
		trainEntryListOne.add(trainTwo);
		trainEntryListTwo.add(trainOne);
		trainEntryListTwo.add(trainThree);
		trainEntryListTwo.add(trainFour);		
		
		assertTrue( newAPI.findPreEntryPerResource(testOne, trainOne, trainEntryListOne).getName().equals(trainTwo.getName()));
		assertTrue(newAPI.findPreEntryPerResource(testOne, trainOne, trainEntryListTwo)==null);
			
		PlanningEntryAPIs<Plane> newAPI1 =new PlanningEntryAPIs<>();
		//对FlightEntry进行判断
		String ID5 = "testID";
		String type5 = "testName";
		int seats5 = 300;
		double age5 = 2.5;
		Plane planeOne = new Plane(ID5, type5, seats5, age5);
		String ID6 = "ID";
		String type6 = "Name";
		int seats6 = 200;
		double age6 = 3.5;
		Plane planeTwo = new Plane(ID6, type6, seats6, age6);
		
		FlightEntry firstFlight = new FlightEntryFactory().getEntry("firstFli");
		FlightEntry secondFlight = new FlightEntryFactory().getEntry("secondFli");
		FlightEntry thirdFlight = new FlightEntryFactory().getEntry("thirdFli");
		FlightEntry fourthFlight = new FlightEntryFactory().getEntry("fourthFli");

		firstFlight.setResource(planeOne);
		secondFlight.setResource(planeOne);
		thirdFlight.setResource(planeOne);
		fourthFlight.setResource(planeTwo);
		
		firstFlight.setSlot(secondSlot);
		secondFlight.setSlot(testSlot);
		thirdFlight.setSlot(thirdSlot);
		fourthFlight.setSlot(testSlot);
		
		List<FlightEntry>  flightListOne = new ArrayList<>();
		List<FlightEntry>  flightListTwo = new ArrayList<>();
		flightListOne.add(firstFlight);//有前置项的和e放在一组
		flightListOne.add(secondFlight);
		flightListTwo.add(firstFlight);//晚于e的和e共用同一资源的、早于e但不和e共用同一资源的，和e放在另一组
		flightListTwo.add(thirdFlight);
		flightListTwo.add(fourthFlight);
		
		assertTrue(newAPI1.findPreEntryPerResource(planeOne,firstFlight , flightListOne).getName().equals(secondFlight.getName()));
		assertTrue(newAPI1.findPreEntryPerResource(planeOne, firstFlight, flightListTwo)==null);
		
		PlanningEntryAPIs<Teacher> newAPI2 =new PlanningEntryAPIs<>();
		String ID7 = "testID";
		String name7 = "testName";
		String gender7 = "male";
		String title7 = "professor";
		Teacher firstTeacher = new Teacher(ID7, name7, gender7, title7);
		String ID8 = "ID";
		String name8 = "Name";
		String gender8 = "female";
		String title8 = "professor";
		Teacher secondTeacher = new Teacher(ID8, name8, gender8, title8);
		
		CourseEntry firstCourse = new CourseEntryFactory().getEntry("firstFli");
		CourseEntry secondCourse = new CourseEntryFactory().getEntry("secondFli");
		CourseEntry thirdCourse = new CourseEntryFactory().getEntry("thirdFli");
		CourseEntry fourthCourse = new CourseEntryFactory().getEntry("fourthFli");

		firstCourse.setResource(firstTeacher);
		secondCourse.setResource(firstTeacher);
		thirdCourse.setResource(firstTeacher);
		fourthCourse.setResource(secondTeacher);
		
		firstCourse.setSlot(secondSlot);
		secondCourse.setSlot(testSlot);
		thirdCourse.setSlot(thirdSlot);
		fourthCourse.setSlot(testSlot);
		
		List<CourseEntry>  courseListOne = new ArrayList<>();
		List<CourseEntry>  courseListTwo = new ArrayList<>();
		courseListOne.add(firstCourse);
		courseListOne.add(secondCourse);
		courseListTwo.add(firstCourse);
		courseListTwo.add(thirdCourse);
		courseListTwo.add(fourthCourse);
		assertTrue(newAPI2.findPreEntryPerResource(firstTeacher, firstCourse, courseListOne).getName().equals(secondCourse.getName()));
		assertTrue(newAPI2.findPreEntryPerResource(firstTeacher, firstCourse, courseListTwo)==null);
		
	}
	
	
}
