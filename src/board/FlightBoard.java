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

import factory.FlightEntryFactory;
import flightSchedule.FlightEntry;
import location.Airport;
import location.Location;
import planningEntry.PlanningEntry;
import resource.Plane;
import timeslot.TimeSlot;

/**
 * mutable
 * 用于实时公布该位置上过去发生过的和后续即将发生的计划项，
 */
public class FlightBoard implements Iterable<FlightEntry>{
	
	private final Calendar timeNow;	//当前时间
	private final Location thisLocation;//该board所在地点
	private List<FlightEntry> ToTherePlane = new ArrayList<>();//过去未来一小时抵达该地点的飞机
	private List<FlightEntry> FromTherePlane = new ArrayList<>();//过去未来一小时从该地点出发的飞机
	private boolean arrivIterator = true;
		
	//Abstraction function:
	//	AF(timeNow,thisLocation,ToTherePlane,FromTherePlane,arrivIterator) = the time now,the location of the board,
	//	the list of the flight will start from this locaiton,the list of the flight will end in this locaiton,  
	//	the controller of the type of the iterator
	//Representation invariant:
	//	all fields must be non null
	//Safety from rep exposure:
	// 	all fields are private
	//	timeNow and thisLocation are final,ToTherePlane and FromTherePlane and arrivIterator are allowed to be changed
		
	
	//constructor
	public FlightBoard(Calendar timeNow, Location thisLocation) {
		super();
		this.timeNow = timeNow;
		this.thisLocation = thisLocation;
		checkRep();
	}
	
	//checkrep
	private void checkRep()
	{
		assert timeNow != null;
		assert thisLocation != null;
		assert ToTherePlane != null;
		assert FromTherePlane != null;
		for(FlightEntry thisCour:ToTherePlane)
		{
			assert thisCour != null;
		}
		for(FlightEntry thisCour:FromTherePlane)
		{
			assert thisCour != null;
		}
	}
	
	
	/**
	 * 为信息板设置抵达航班列表（前后1小时抵达该机场的的计划项）
	 * @param totalEntrys ，所有的计划项
	 */
	public void setToList(List<? extends PlanningEntry<?>> totalEntrys)
	{
		for(PlanningEntry<?> thisPlane:totalEntrys)
		{
			if(((FlightEntry)thisPlane).getToLocation().equals(thisLocation))//若抵达地点为该机场
			{    
				Calendar thisTime = ((FlightEntry)thisPlane).getSlot().getEndTime();//获取该飞机抵达时间
				 if(timeWithin(60,timeNow,thisTime))
			    	ToTherePlane.add(((FlightEntry)thisPlane)); //若时间差小于60min 则将其加入列表
			}
		}
		
	}

	/**
	 * 为信息板设置起飞航班列表（前后1小时从该机场起飞的的计划项）
	 * @param totalEntrys ，所有的计划项
	 */
	public void setFromList(List<? extends PlanningEntry<?>> totalEntrys)
	{
		for(PlanningEntry<?> thisPlane:totalEntrys)
		{
			if(((FlightEntry)thisPlane).getfromLocation().equals(thisLocation))//若抵达地点为该机场
			{    
				Calendar thisTime = ((FlightEntry)thisPlane).getSlot().getBeginTime();//获取该飞机抵达时间
			    if(timeWithin(60,timeNow,thisTime))
			    	FromTherePlane.add((FlightEntry)thisPlane); //若时间差小于60min 则将其加入列表
			}
		}
	}
	
	/**
	 * 判断两个时间差距是否在withinGap分钟数之内
	 * @param withinGap，可以接受的分钟数差距的范围
	 * @param timeNow ，现在的时间
	 * @param anoTime ，需要比较的时间
	 * @return 若是返回true 否则false
	 */
	public  boolean timeWithin(long withinGap,Calendar timeNow,Calendar anoTime)
	{
		long nowInLong = timeNow.getTimeInMillis();
		long thisTimeInLong = anoTime.getTimeInMillis();//转化为long形式 便于判断时间差
		long gap;
		    if(anoTime.after(timeNow) || anoTime.equals(timeNow))
		    {
		    	gap =(thisTimeInLong - nowInLong) / (1000 * 60);
		    }
		    else
		    {
		    	gap =(nowInLong - thisTimeInLong) / (1000 * 60); 
		    }
		    if(gap <= withinGap)
		    	return true;
		    else
		    	return false;
	}
	//时间 
	//状态
	//等等
	public void visualize()
	{
		JFrame arriveBoard = new JFrame();
		Object [][] thisInfo = new Object [ToTherePlane.size()][4];
		
		int i = 0;
		for(FlightEntry thisFlight :ToTherePlane)
		{
			SimpleDateFormat form = new SimpleDateFormat("HH:mm");
			String endStr = form.format(thisFlight.getSlot().getEndTime().getTime());
			thisInfo[i][0] = endStr;//获取抵达时间
			thisInfo[i][1] = thisFlight.getName();//飞机的名字
			thisInfo[i][2] = thisFlight.getfromLocation().getName() +"-"+ thisFlight.getToLocation().getName();
			thisInfo[i++][3] = thisFlight.getState();//上一行获取航班方向，该行获取航班状态
		}
	    // 创建表格中的横标题
	    String[] Names = { "抵达时间" , "航班号", "方向", "状态"};
	    // 以Names和playerInfo为参数，创建一个表格
	    JTable table = new JTable(thisInfo, Names);
	    // 设置此表视图的首选大小
	    table.setPreferredScrollableViewportSize(new Dimension(550, 100));
	    // 将表格加入到滚动条组件中
	    JScrollPane scrollPane = new JScrollPane(table);
	    arriveBoard.getContentPane().add(scrollPane, BorderLayout.CENTER);
	    // 再将滚动条组件添加到中间容器中
	    SimpleDateFormat form1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String timeNowStr = form1.format(timeNow.getTime());
	    arriveBoard.setTitle(timeNowStr + " (当前时间)，"+thisLocation.getName()+"抵达航班");
	    arriveBoard.pack();
	    arriveBoard.setBounds(750, 200, 550, 150);
	    arriveBoard.setVisible(true);
	    arriveBoard.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	        	arriveBoard.dispose();;
	        }
	    });
	    
	    
	    JFrame startBoard = new JFrame();
	    Object [][] anotherInfo = new Object [FromTherePlane.size()][4];
	    int j = 0;
		for(FlightEntry thisFlight :FromTherePlane)
		{
			SimpleDateFormat form = new SimpleDateFormat("HH:mm");
			String beginStr = form.format(thisFlight.getSlot().getBeginTime().getTime());
			anotherInfo[j][0] = beginStr;//获取抵达时间
			anotherInfo[j][1] = thisFlight.getName();//飞机的名字
			anotherInfo[j][2] = thisFlight.getfromLocation().getName() +"-"+ thisFlight.getToLocation().getName();
			anotherInfo[j++][3] = thisFlight.getState();//上一行获取航班方向，该行获取航班状态
		}

	    String[] newNames = { "起飞时间" , "航班号", "方向", "状态"};
	    JTable newTable = new JTable(anotherInfo, newNames);
	    newTable.setPreferredScrollableViewportSize(new Dimension(550, 100));
	    JScrollPane newScrollPane = new JScrollPane(newTable);
	    startBoard.getContentPane().add(newScrollPane, BorderLayout.CENTER);
	    
	    SimpleDateFormat form2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String timeNowStr2 = form2.format(timeNow.getTime());
	    startBoard.setTitle(timeNowStr2+" (当前时间)，"+thisLocation.getName()+"起飞航班");
	    startBoard.pack();
	    startBoard.setBounds(200, 200, 550, 150);
	    startBoard.setVisible(true);
	    startBoard.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	        	startBoard.dispose();
	        }
	    });
	}
	
	/**
	 * 将Iterator修改为抵达计划项Iterator模式
	 */
	public void setIterToArr()
	{
		arrivIterator = true;
	}
	
	/**
	 * 将Iterator修改为出发计划项Iterator模式
	 */
	public void setIterToFro()
	{
		arrivIterator = false;
	}
	
	/**
	 *  返回Iterator的模式 
	 * @return 若为arrive的iterator返回true 否则若为departure的模式返回false
	 */
	public boolean IterArrOrNot()
	{
		return arrivIterator;
	}
	
	@Override
	public Iterator<FlightEntry> iterator() {
		if(arrivIterator == true)
		{
			Collections.sort(ToTherePlane, new FlightComparatorOne());
			return new FlightIteratorOne(ToTherePlane);
		}
		else
		{
			Collections.sort(FromTherePlane, new FlightComparatorTwo());
			return new FlightIteratorTwo(FromTherePlane);
		}
	}
	


private class FlightIteratorOne implements Iterator<FlightEntry>
{
	private List<FlightEntry> arriList;
	private int num =0;
	
	
	public FlightIteratorOne(List<FlightEntry> arriList) {
		this.arriList = arriList;
	}
	@Override
	public FlightEntry next() {
		FlightEntry result = arriList.get(num);
		num ++;
		return result;
	}
	
	@Override
	public boolean hasNext() {
		
		return num<arriList.size();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
}

private class FlightIteratorTwo implements Iterator<FlightEntry>
{
	
	private List<FlightEntry> startList;
	private int num =0;
	
	
	public FlightIteratorTwo(List<FlightEntry> startList) {
		this.startList = startList;
	}
	@Override
	public FlightEntry next() {
		FlightEntry result = startList.get(num);
		num ++;
		return result;
	}
	
	@Override
	public boolean hasNext() {
		
		return num<startList.size();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
//抵达航班比较器
 protected class FlightComparatorOne implements Comparator<FlightEntry>
 {
	 @Override
	public int compare(FlightEntry o1, FlightEntry o2) {
		if(o1.getSlot().getEndTime().after(o2.getSlot().getEndTime()))
			return 1;
		else if(o1.getSlot().getEndTime().equals(o2.getSlot().getEndTime()))
			return 0;
		else
			return -1;
			
	}
 }
//起飞航班比较器
 protected class FlightComparatorTwo implements Comparator<FlightEntry>
 {
	 @Override
	public int compare(FlightEntry o1, FlightEntry o2) {
		if(o1.getSlot().getBeginTime().after(o2.getSlot().getBeginTime()))
			return 1;
		else if(o1.getSlot().getBeginTime().equals(o2.getSlot().getBeginTime()))
			return 0;
		else
			return -1;
	}
 }
 
 public static void main(String[] args) throws IOException {
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
		
		String nameOfLocation = "BeiJing";
		double latitude = 0;
		double longitude = 1;
		Airport testOne = new Airport(nameOfLocation, latitude, longitude);//this airport
		
		String nameOfLocation1 = "Tokyo";
		double latitude1 = 1;
		double longitude1 = 2;
		Airport testTwo = new Airport(nameOfLocation1, latitude1, longitude1);
		
		String nameOfLocation2 = "Harbin";
		double latitude2 = 2;
		double longitude2 = 3;
		Airport testThree = new Airport(nameOfLocation2, latitude2, longitude2);
		
		String ID = "testID";
		String type = "Name";
		int seats = 300;
		double age = 2.5;
		Plane plane1 = new Plane(ID, type, seats, age);
		String ID2 = "ID";
		String type2 = "testName";
		int seats2 = 400;
		double age2 = 2.5;
		Plane plane2 = new Plane(ID2, type2, seats2, age2);
		String ID3 = "ID3";
		String type3 = "testName";
		int seats3 = 300;
		double age3 = 2.5;
		Plane plane3 = new Plane(ID3, type3, seats3, age3);
		
		
		
		FlightEntry firstEntry = new FlightEntryFactory().getEntry("NA8901");
		FlightEntry secondEntry = new FlightEntryFactory().getEntry("NB9284");
		FlightEntry thirdEntry = new FlightEntryFactory().getEntry("NA8938");
		FlightEntry fourthEntry = new FlightEntryFactory().getEntry("NA8238");
		FlightEntry fifthEntry = new FlightEntryFactory().getEntry("NA1237");
		FlightEntry sixthEntry = new FlightEntryFactory().getEntry("NA1371");
		FlightEntry seventhEntry = new FlightEntryFactory().getEntry("NA2742");		
		FlightEntry eighthEntry = new FlightEntryFactory().getEntry("NA4224");

		FlightBoard testBoard = new FlightBoard(testCalOne, testOne);
		
		firstEntry.setResource(plane1);
		secondEntry.setResource(plane2);
		thirdEntry.setResource(plane3);
		fourthEntry.setResource(plane1);
		fifthEntry.setResource(plane1);
		sixthEntry.setResource(plane3);
		seventhEntry.setResource(plane2);
		eighthEntry.setResource(plane1);
		
		
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
		
		firstEntry.run();
		
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
		testBoard.iterator();
		testBoard.setToList(testEntry);
		testBoard.setIterToArr();
		testBoard.iterator();
		testBoard.visualize();
 }

}

