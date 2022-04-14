package board;

import java.awt.BorderLayout;



import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import classSchedule.CourseEntry;
import factory.CourseEntryFactory;
import location.ClassRoom;
import location.Location;
import planningEntry.PlanningEntry;
import resource.Teacher;
import timeslot.TimeSlot;
/**
 * mutable
 */
public class ClassBoard implements Iterable<CourseEntry>{
	private final Calendar timeNow; // 当前时间
	private final Location thisLocation;// 该board所在地点
	private List<CourseEntry> thisList = new ArrayList<>();

	//Abstraction function:
	// 	AF(timeNow,thisLocation,thisList) = the time now,the location of the board,the list of the entrys that will be in this location  
	//Representation invariant:
	//	all fields must be non null
	//Safety from rep exposure:
	//  all fields are private
	//	timeNow and thisLocation are final,thisList is allowed to be changed

	
	//constructor
	public ClassBoard(Calendar timeNow, Location thisLocation) {
		this.timeNow = timeNow;
		this.thisLocation = thisLocation;
		checkRep();
	}
	
	private void checkRep()
	{
		assert timeNow != null;
		assert thisLocation != null;
		assert thisList != null;
		for(CourseEntry thisCour:thisList)
		{
			assert thisCour != null;
		}
	}
	
	/**
	 * 将当天在该教室上课的课程加入列表
	 * @param totalEntrys 所有的计划项
	 */
	public void setClasses(List<? extends PlanningEntry<?>> totalEntrys )
	{
		for(PlanningEntry<?> thisClass:totalEntrys)
		{
			if(((CourseEntry)thisClass).getLocation().equals(thisLocation))//若抵达地点为该机场
			{    
				Calendar thisTime = ((CourseEntry)thisClass).getSlot().getBeginTime();//获取该飞机抵达时间
//				 if(timeWithin(60,timeNow,thisTime))
//			    	ToTherePlane.add((FlightEntry)thisPlane); //若时间差小于60min 则将其加入列表
				if( thisTime.get(Calendar.DAY_OF_YEAR) ==timeNow.get(Calendar.DAY_OF_YEAR) 
					&& thisTime.get(Calendar.YEAR) ==timeNow.get(Calendar.YEAR) )
					thisList.add((CourseEntry)thisClass);
			}
		}
	}
	
	public void visualize()
	{
		JFrame classBoard = new JFrame();
		Object [][] thisInfo = new Object [thisList.size()][4];
		
		int i = 0;
		for(CourseEntry thisCourse :thisList)
		{
			SimpleDateFormat form = new SimpleDateFormat("HH:mm");
			String calOneStr = form.format(thisCourse.getSlot().getBeginTime().getTime());
			String calTwoStr = form.format(thisCourse.getSlot().getEndTime().getTime());
			thisInfo[i][0] = calOneStr +"-"+ calTwoStr;//获取抵达时间
			thisInfo[i][1] = thisCourse.getName();//课程的名字
			thisInfo[i][2] = ((Teacher)(thisCourse.getResource())).getname();//授课教师名
			thisInfo[i++][3] = thisCourse.getState();//课程状态
		}
	    // 创建表格中的横标题
	    String[] Names = { "课程时间" , "课程名称", "授课教师", "课程状态"};
	    // 以Names和playerInfo为参数，创建一个表格
	    JTable table = new JTable(thisInfo, Names);
	    // 设置此表视图的首选大小
	    table.setPreferredScrollableViewportSize(new Dimension(550, 100));
	    // 将表格加入到滚动条组件中
	    JScrollPane scrollPane = new JScrollPane(table);
	    classBoard.getContentPane().add(scrollPane, BorderLayout.CENTER);
	    // 再将滚动条组件添加到中间容器中
		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String timeNowStr = form.format(timeNow.getTime());
	    classBoard.setTitle(timeNowStr + " (当前时间)，"+thisLocation.getName()+"教室");
	    classBoard.pack();
	    classBoard.setBounds(750, 200, 550, 150);
	    classBoard.setVisible(true);
	    classBoard.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	            classBoard.dispose();
	        }
	    });
	}
	
	@Override
	public Iterator<CourseEntry> iterator() {
		Collections.sort(thisList, new CourseComparator());
		return new CourseIterator();
	}
	
	private class CourseIterator implements Iterator<CourseEntry>
	{
		private int num =0;
		@Override
		public boolean hasNext() {
			
			return num<thisList.size();
		}

		@Override
		public CourseEntry next() {
			CourseEntry thisEntry = thisList.get(num);
			num++;		
			return thisEntry;
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	//抵达航班比较器
	 protected class CourseComparator implements Comparator<CourseEntry>
	 {
		 @Override
		public int compare(CourseEntry o1, CourseEntry o2) {
			if(o1.getSlot().getBeginTime().after(o2.getSlot().getBeginTime()))
				return 1;
			else if(o1.getSlot().getBeginTime().equals(o2.getSlot().getBeginTime()))
				return 0;
			else
				return -1;
				
		}
	 }
	 
	 
		public static void main(String[] args) throws IOException {
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
			testCalFiv.set(2020, 4, 1,15,45);
			Calendar testCalSix= Calendar.getInstance();
			testCalSix.set(2020, 4, 2,17,30);
			TimeSlot testSlot3= new TimeSlot(testCalFiv, testCalSix);//other day's class
			 
			CourseEntry courseOne = new CourseEntryFactory().getEntry("线性代数");
			CourseEntry courseTwo = new CourseEntryFactory().getEntry("数学分析");
			CourseEntry courseThr = new CourseEntryFactory().getEntry("抽象代数");
			CourseEntry courseFou = new CourseEntryFactory().getEntry("操作系统");
			
			courseOne.setSlot(testSlot1);
			courseTwo.setSlot(testSlot2);
			courseThr.setSlot(testSlot3);
			courseFou.setSlot(testSlot2);
			courseOne.setLocation(thisRoom);
			courseTwo.setLocation(thisRoom);
			courseThr.setLocation(thisRoom);
			courseFou.setLocation(otherRoom);
			String ID = "123";
			String name = "张三";
			String gender = "male";
			String title = "professor";
			Teacher newTeacher = new Teacher(ID, name, gender, title);
			String ID1 = "456";
			String name1 = "李四";
			String gender1 = "male";
			String title1 = "professor";
			Teacher newTeacher1 = new Teacher(ID1, name1, gender1, title1);
			String ID2 = "789";
			String name2 = "王五";
			String gender2 = "male";
			String title2 = "professor";
			Teacher newTeacher2 = new Teacher(ID2, name2, gender2, title2);
			courseOne.setResource(newTeacher);
			courseTwo.setResource(newTeacher1);
			courseThr.setResource(newTeacher2);
			courseFou.setResource(newTeacher);
			
			courseOne.run();
			
			
			List<CourseEntry> courseList = new ArrayList<>();
			courseList.add(courseOne);
			courseList.add(courseTwo);
			courseList.add(courseThr);
			courseList.add(courseFou);
			
			ClassBoard newBoard = new ClassBoard(testCalOne, thisRoom);
			newBoard.setClasses(courseList);
			newBoard.iterator();
			newBoard.visualize();
		}
}
