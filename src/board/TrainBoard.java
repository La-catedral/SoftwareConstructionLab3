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

import factory.TrainEntryFactory;
import location.Location;
import location.TrainStation;
import planningEntry.PlanningEntry;
import resource.Coach;
import timeslot.TimeSlot;
import trainSchedule.TrainEntry;
/**
 * mutable
 */
public class TrainBoard implements Iterable<TrainEntry>{
	private final Calendar timeNow;	//当前时间
	private final Location thisLocation;//该board所在地点
	private List<TrainEntry> ToThereTrain  = new ArrayList<>();//过去未来一小时抵达该车站的火车
	private List<TrainEntry> FromThereTrain  = new ArrayList<>();//过去未来一小时从该车站出发的火车
	private boolean arrivIterator = true;
	
	//Abstraction function:
	//	AF(timeNow,thisLocation,ToThereTrain,FromThereTrain,arrivIterator) = the time now,the location of the board,
	//	the list of the train will start from this locaiton,the list of the train will end in this locaiton,  
	//	the controller of the type of the iterator
	//Representation invariant:
	//	all fields must be non null
	//Safety from rep exposure:
	// 	all fields are private
	//	timeNow and thisLocation are final,ToThereTrain and ToThereTrain and arrivIterator are allowed to be changed
	
	
	//constructor
	public TrainBoard(Calendar timeNow, Location thisLocation) {
		this.timeNow = timeNow;
		this.thisLocation = thisLocation;
		checkRep();
	}
	
	//checkrep
		private void checkRep()
		{
			assert timeNow != null;
			assert thisLocation != null;
			assert FromThereTrain != null;
			assert ToThereTrain != null;
			for(TrainEntry thisCour:FromThereTrain)
			{
				assert thisCour != null;
			}
			for(TrainEntry thisCour:ToThereTrain)
			{
				assert thisCour != null;
			}
		}
	
	/**
	 * 将最终车站为该车站或途径车站列表中含有该车站的抵达时间与当前时间差小于一小时的对象加入抵达列表
	 * @param totalEntrys 所有计划项构成的列表
	 */
	public void setToList(List<? extends PlanningEntry<?>> totalEntrys)
	{
		for(PlanningEntry<?> thisTrain:totalEntrys)
		{			
			List<? extends Location> thisList = ((TrainEntry) thisTrain).getLocationList();
			if (!thisList.get(0).equals(thisLocation) && thisList.contains(thisLocation)) // 如果thisLocation在列表中 且非起始车站
			{
				if (thisList.indexOf(thisLocation) == thisList.size() - 1) // 若为终点站
				{
					Calendar thisTime = ((TrainEntry) thisTrain).getSlot().getEndTime();
					if (timeWithin(60, timeNow, thisTime))
						ToThereTrain.add((TrainEntry) thisTrain);
				} else {
					Calendar thisTime = ((TrainEntry) thisTrain).getSlotList().get(thisList.indexOf(thisLocation) - 1)
							.getBeginTime();
					if (timeWithin(60, timeNow, thisTime))
						ToThereTrain.add((TrainEntry) thisTrain);
				}
			}	
		}
	}
	
	/**
	 * 将起始车站为该车站或途径车站列表中含有该车站的抵达时间与当前时间差小于一小时的对象加入抵达列表
	 * @param totalEntrys 所有计划项构成的列表
	 */
	public void setFromList(List<? extends PlanningEntry<?>> totalEntrys)
	{
		for(PlanningEntry<?> thisTrain:totalEntrys)
		{			
			List<? extends Location> thisList = ((TrainEntry) thisTrain).getLocationList();
			if (!thisList.get(thisList.size()-1).equals(thisLocation) && thisList.contains(thisLocation)) // 如果thisLocation在列表中 且非起始车站
			{
				if (thisList.indexOf(thisLocation) == 0) // 若为终点站
				{
					Calendar thisTime = ((TrainEntry) thisTrain).getSlot().getBeginTime();
					if (timeWithin(60, timeNow, thisTime))
						FromThereTrain.add((TrainEntry) thisTrain);
				} else {
					Calendar thisTime = ((TrainEntry) thisTrain).getSlotList().get(thisList.indexOf(thisLocation) - 1)
							.getEndTime();
					if (timeWithin(60, timeNow, thisTime))
						FromThereTrain.add((TrainEntry) thisTrain);
				}
			}	
		}
	}
	
	/**
	 * 已知该列车计划项会抵达该车站 获得抵达时间
	 * @param thisEntry 对应的计划项
	 * @return 抵达时间
	 */
	public Calendar getToTime(TrainEntry thisEntry)
	{
		if (ToThereTrain.contains(thisEntry)) {
			List<? extends Location> thisList = thisEntry.getLocationList();
			Calendar thisTime;
			if (thisList.indexOf(thisLocation) == thisList.size() - 1) // 若为终点站
			{
				thisTime = thisEntry.getSlot().getEndTime();
			} else {
				thisTime = thisEntry.getSlotList().get(thisList.indexOf(thisLocation) - 1).getBeginTime();
			}
			return thisTime;
		} else {
			System.out.println("该计划项不符合抵达该车站的要求");
			return null;
		}
	}
	
	/**
	 * 已知该列车计划项会从该车站发车 获得出发时间
	 * @param thisEntry
	 * @return
	 */
		public Calendar getFromTime(TrainEntry thisEntry)
		{
		if (FromThereTrain.contains(thisEntry)) {
			List<? extends Location> thisList = thisEntry.getLocationList();
			Calendar thisTime;
			if (thisList.indexOf(thisLocation) == 0) // 若为终点站
			{
				thisTime = thisEntry.getSlot().getBeginTime();
			} else {
				thisTime = thisEntry.getSlotList().get(thisList.indexOf(thisLocation) - 1).getEndTime();
			}
			return thisTime;
		} else {
			System.out.println("该计划项不符合从该车站发车的要求");
			return null;
		}
		}
	
	
	/**
	 * 已知一个计划项会抵达这个地点 获取它的当前状态
	 * @param thisEntry 上述的计划项
	 * @return 文字描述的状态
	 */
	public String getStateOfTo(TrainEntry thisEntry)
	{
		if (ToThereTrain.contains(thisEntry)) {
			if (thisEntry.getState().toString().equals("已取消"))
				return "已取消";
			else {
				if (getToTime(thisEntry).after(timeNow))
					return "即将抵达";
				else
					return "已到达";
			}
		} else {
			System.out.println("不在该board相应位置上的计划项");
			return null;
		}
	}
	
	/**
	 * 已知一个任务项会从这个地点出发 获取它的当前状态
	 * @param thisEntry 上述的计划项
	 * @return 文字描述的状态
	 */
	public String getStateOfFrom(TrainEntry thisEntry)
	{
		if (FromThereTrain.contains(thisEntry)) {
			if (thisEntry.getState().toString().equals("已取消"))
				return "已取消";
			else {
				if (getFromTime(thisEntry).after(timeNow))
					return "即将出发";
				else
					return "已出发";
			}
		} else {
			System.out.println("不在该board相应位置上的计划项");
			return null;
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
	
	public void visualize()
	{
		JFrame arriveBoard = new JFrame();
		Object [][] thisInfo = new Object [ToThereTrain.size()][4];
		
		int i = 0;
		for(TrainEntry thisTrain :ToThereTrain)
		{
			SimpleDateFormat form = new SimpleDateFormat("HH:mm");
			String calOneStr = form.format(getToTime(thisTrain).getTime());
			thisInfo[i][0] = calOneStr;//获取抵达时间 Calendar
			thisInfo[i][1] = thisTrain.getName();//列车名字
			thisInfo[i][2] = thisTrain.getLocationList().get(0).getName() +"-"+ thisTrain.getLocationList().get(thisTrain.getLocationList().size()-1).getName();
			thisInfo[i++][3] = getStateOfTo(thisTrain);//上一行获取航班方向，改行获取航班状态
		}
	    // 创建表格中的横标题
	    String[] Names = { "抵达时间" , "列车号", "方向", "状态"};
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
	    arriveBoard.setTitle(timeNowStr + " (当前时间)，"+thisLocation.getName()+"抵达车次");
	    arriveBoard.pack();
	    arriveBoard.setBounds(750, 200, 550, 150);
	    arriveBoard.setVisible(true);
	    arriveBoard.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	        	arriveBoard.dispose();
	        }
	    });
	    
	    
	    JFrame startBoard = new JFrame();
	    Object [][] anotherInfo = new Object [FromThereTrain.size()][4];
	    int j = 0;
		

		for(TrainEntry thisTrain :FromThereTrain)
		{
			SimpleDateFormat form = new SimpleDateFormat("HH:mm");
			String calTwoStr = form.format(getFromTime(thisTrain).getTime());
			anotherInfo[j][0] = calTwoStr;//获取抵达时间 Calendar
			anotherInfo[j][1] = thisTrain.getName();//飞机的名字
			anotherInfo[j][2] = thisTrain.getLocationList().get(0).getName()+"-" + thisTrain.getLocationList().get(thisTrain.getLocationList().size()-1).getName();
			anotherInfo[j++][3] = getStateOfFrom(thisTrain);//上一行获取航班方向，改行获取航班状态
		}

	    String[] newNames = { "发车时间" , "列车号", "方向", "状态"};
	    JTable newTable = new JTable(anotherInfo, newNames);
	    newTable.setPreferredScrollableViewportSize(new Dimension(550, 100));
	    JScrollPane newScrollPane = new JScrollPane(newTable);
	    startBoard.getContentPane().add(newScrollPane, BorderLayout.CENTER);
	    
	    SimpleDateFormat form2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String timeNowStr2 = form2.format(timeNow.getTime());
	    startBoard.setTitle(timeNowStr2+" (当前时间)，"+thisLocation.getName()+"出发车次");
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
	 * 返回Iterator的类型是否为arriveList
	 * @return true如果是 否则false
	 */
	public boolean IteArrOrNot()
	{
		return arrivIterator;
	}
	
	@Override
	public Iterator<TrainEntry> iterator() {
		if(arrivIterator == true)
		{
			Collections.sort(ToThereTrain, new TrainComparatorOne());
			return new TrainIteratorOne();
		}
		else
		{
			Collections.sort(FromThereTrain, new TrainComparatorTwo());
			return new TrainIteratorTwo();
		}
	}
	
	//抵达火车Iterator
	private class TrainIteratorOne implements Iterator<TrainEntry>
	{
		private int num =0;
		
		@Override
		public TrainEntry next() {
			TrainEntry result = ToThereTrain.get(num);
			num ++;
			return result;
		}
		
		@Override
		public boolean hasNext() {
			
			return num<ToThereTrain.size();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
	// 出发火车Iterator
	private class TrainIteratorTwo implements Iterator<TrainEntry> {
		private int num = 0;

		@Override
		public TrainEntry next() {
			TrainEntry result = FromThereTrain.get(num);
			num++;
			return result;
		}

		@Override
		public boolean hasNext() {

			return num < FromThereTrain.size();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}
	
	//抵达航班比较器
	 protected class TrainComparatorOne implements Comparator<TrainEntry>
	 {
		 @Override
		public int compare(TrainEntry o1, TrainEntry o2) {
			if(getToTime(o1).after(getToTime(o2)))
				return 1;
			else if(getToTime(o1).equals(getToTime(o2)))
				return 0;
			else
				return -1;
		}
	 }
	 
	//起飞航班比较器
	 protected class TrainComparatorTwo implements Comparator<TrainEntry>
	 {
		 @Override
		public int compare(TrainEntry o1, TrainEntry o2) {
			if(getFromTime(o1).after(getFromTime(o2)))
				return 1;
			else if(getFromTime(o1).equals(getFromTime(o2)))
				return 0;
			else
				return -1;
		}
	 }
	
	 public static void main(String[] args) throws IOException {
		 
		 String nameOfLocation = "BeiJing";
			double latitude = 0;
			double longitude = 1;
			TrainStation testOne = new TrainStation(nameOfLocation, latitude, longitude);//this station
			
			String nameOfLocation1 = "Harbin";
			double latitude1 = 1;
			double longitude1 = 2;
			TrainStation testTwo = new TrainStation(nameOfLocation1, latitude1, longitude1);
			
			String nameOfLocation2 = "ShiJiazhuang";
			double latitude2 = 2;
			double longitude2 = 3;
			TrainStation testThree = new TrainStation(nameOfLocation2, latitude2, longitude2);
			
			TrainEntry firstEntry = new TrainEntryFactory().getEntry("G1266");
			TrainEntry secondEntry = new TrainEntryFactory().getEntry("G1129");
			TrainEntry thirdEntry = new TrainEntryFactory().getEntry("G1436");
			TrainEntry fourthEntry = new TrainEntryFactory().getEntry("G1352");
			
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
			
			String ID = "testID";
			String type = "testName";
			int seats = 300;
			int birthYear = 2010;
			Coach coach1 = new Coach(ID, type, seats, birthYear);
			List<Coach> coachList1 = new ArrayList<>();
			coachList1.add(coach1);
			
			String ID2 = "ID";
			String type2 = "Name";
			int seats2 = 300;
			int birthYear2 = 2010;
			Coach coach2 = new Coach(ID2, type2, seats2, birthYear2);
			List<Coach> coachList2 = new ArrayList<>();
			coachList2.add(coach2);
			
			String ID3 = "testID";
			String type3 = "testName";
			int seats3 = 300;
			int birthYear3 = 2010;
			Coach coach3 = new Coach(ID3, type3, seats3, birthYear3);
			List<Coach> coachList3 = new ArrayList<>();
			coachList3.add(coach3);
			
			firstEntry.setResource(coachList1);
			secondEntry.setResource(coachList2);
			thirdEntry.setResource(coachList3);
			fourthEntry.setResource(coachList1);
			
			
			
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
			testBoard.setIterToArr();
			testBoard.iterator();
			testBoard.setIterToFro();
			testBoard.iterator();
			
			testBoard.visualize();
		 
	 }
	 
	 
}
