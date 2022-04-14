package board;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import classSchedule.CourseEntry;
import factory.CourseEntryFactory;
import location.ClassRoom;
import timeslot.TimeSlot;

public class ClassBoardTest {

	//测试策略
	//setClasses(List<CourseEntry> entries) 、 Iterator()
	//		两者一同测试 即先调用前者为board对象设置成员变量 再调用后者观察成员变量
	//		将计划项列表中的计划项分为：在该教室中、在其他教室中、
	//		今天上课、非今天上课
	//		调用Iterator进行观察
	@Test
	public void testSetClassesAndIterator() {
		String nameOfLocation = "testname";
		double latitude = 0;
		double longitude = 1;
		ClassRoom thisRoom = new ClassRoom(nameOfLocation, latitude, longitude);
		
		String nameOfLocation1 = "othername";
		double latitude1 = 1;
		double longitude1 = 2;
		ClassRoom otherRoom = new ClassRoom(nameOfLocation1, latitude1, longitude1);
		
		Calendar testCalOne= Calendar.getInstance();
		testCalOne.set(2020, 4, 1,10,00);
		Calendar testCalTwo= Calendar.getInstance();
		testCalTwo.set(2020, 4, 1,11,45);
		TimeSlot testSlot1= new TimeSlot(testCalOne, testCalTwo);//today
		
		Calendar testCalThr= Calendar.getInstance();
		testCalThr.set(2020, 4, 1,13,45);
		Calendar testCalFou= Calendar.getInstance();
		testCalFou.set(2020, 4, 1,15,30);
		TimeSlot testSlot2= new TimeSlot(testCalThr, testCalFou);//today
		
		Calendar testCalFiv= Calendar.getInstance();
		testCalFiv.set(2020, 4, 2,13,45);
		Calendar testCalSix= Calendar.getInstance();
		testCalSix.set(2020, 4, 2,15,30);
		TimeSlot testSlot3= new TimeSlot(testCalFiv, testCalSix);//other day's class
		 
		CourseEntry courseOne = new CourseEntryFactory().getEntry("courseOne");
		CourseEntry courseTwo = new CourseEntryFactory().getEntry("courseTwo");
		CourseEntry courseThr = new CourseEntryFactory().getEntry("courseThr");
		CourseEntry courseFou = new CourseEntryFactory().getEntry("courseFou");
		
		courseOne.setSlot(testSlot1);
		courseTwo.setSlot(testSlot2);
		courseThr.setSlot(testSlot3);
		courseFou.setSlot(testSlot2);
		courseOne.setLocation(thisRoom);
		courseTwo.setLocation(thisRoom);
		courseThr.setLocation(thisRoom);
		courseFou.setLocation(otherRoom);
		List<CourseEntry> courseList = new ArrayList<>();
		courseList.add(courseOne);
		courseList.add(courseTwo);
		courseList.add(courseThr);
		courseList.add(courseFou);
		
		ClassBoard newBoard = new ClassBoard(testCalOne, thisRoom);
		newBoard.setClasses(courseList);
		Iterator<CourseEntry> newIte = newBoard.iterator();
		int i =0;
		while(newIte.hasNext())//这里board的列表中应该只有两个计划项
		{
			CourseEntry thisCourse = newIte.next();
			if(i==0)
			{
				assertTrue(thisCourse.getName().equals("courseOne"));
				i++;
			}
			else if(i==1)
			{
				assertTrue(thisCourse.getName().equals("courseTwo"));
				i++;
			}
			else {
				assertTrue(false);
			}
		}
		
		
		
	}

}
