package app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import board.TrainBoard;
import factory.TrainEntryFactory;
import location.TrainStation;
import planningEntry.PlanningEntry;
import planningEntry.PlanningEntryAPIs;
import resource.Coach;
import state.CANCELED;
import state.ENDED;
import state.WAITING;
import timeslot.TimeSlot;
import trainSchedule.TrainEntry;

public class TrainScheduleApp {
	
	/**
	 * 在当前所有资源中查找特定的ID的资源
	 * @param ID 要查找的资源的ID
	 * @param resourceList 当前所有资源构成的列表
	 * @return 具有该特定的ID的资源
	 */
	public static Coach findResource(String ID,Set<Coach>resourceList)
	{
		for(Coach thisCoach:resourceList)
		{
			if(thisCoach.getID().equals(ID))
			return thisCoach;
		}
		return null;
	}
	
	/**
	 * 在当前所有位置中查找特定的名称的位置
	 * @param name 位置的名称
	 * @param locationList 所有的现有位置
	 * @return 所要找的位置
	 */
	public static TrainStation findStation(String name,Set<TrainStation>locationList)
	{
		for(TrainStation thisPort : locationList)
		{
			if(thisPort.getName().equals(name))
			return thisPort;
		}
		return null;
	}
	
	/**
	 * 在当前所有的已有计划项中查找名称为name的计划项
	 * @param name 要找的计划项的名称
	 * @param entryList 所有已有计划项构成的list
	 * @return 要找的计划项
	 */
	public static TrainEntry findEntry(String name,List<TrainEntry>entryList)
	{
		for(PlanningEntry<?> thisEntry:entryList)
		{
			if(thisEntry.getName().equals(name))
				return (TrainEntry)thisEntry;
		}
		return null;
	}
	
	
	public static void printHelp()
	{
		System.out.println("********help********");
		System.out.println("输入下列英文字母或单词：");
		System.out.println("a :增设一个车厢");
		System.out.println("b :删除一个车厢");
		System.out.println("c :增加一个火车站");
		System.out.println("d :删除一个火车站");
		System.out.println("e :增加一个列车计划项");
		System.out.println("f :取消一个计划项");
		System.out.println("g :为某一列车分配车厢组");
		System.out.println("h :使列车启动");
		System.out.println("i :阻塞某个列车");
		System.out.println("j :重启某个已阻塞的列车计划项");
		System.out.println("k :使列车到站");
		System.out.println("l :查看某列车状态");
		System.out.println("m :检测资源独占冲突");
		System.out.println("n :对于一个车厢，查看与该车厢有关的所有计划项");
		System.out.println("o :对于一个车厢，查看某个计划项的前序计划项");
		System.out.println("p :显示一个火车站的信息版");
		System.out.println("help :提供帮助信息");
		System.out.println("end :结束");
		System.out.println();
	}

	public static void main(String[] args) {

		Set<TrainStation> locationList = new HashSet<>();//现有位置列表
		Set<Coach> resourceList = new HashSet<>();//现有资源列表
		List<TrainEntry> entryList = new ArrayList<>();//创立的所有计划项 无论状态 都要加入这里
		
		printHelp();
		Scanner in = new Scanner(System.in);
		while(true)
		{
			System.out.println("请输入选项：");
			String choice = in.nextLine();
			ALL:
			switch (choice) {
			case "a": {//增设一个车厢
				try {
					System.out.println("请输入要增设车厢的编号、车厢类型、座位数、出厂年份，参数间空格分开，如\"G381 商务 100 2018\"：");
					String[] param = in.nextLine().split(" ");
					Coach newCoach = new Coach(param[0], param[1], Integer.valueOf(param[2]),
							Integer.valueOf(param[3]));
					resourceList.add(newCoach);
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("不符合要求的格式！");
				}
				break;
			}
			case "b" ://删除一个车厢
			{
				System.out.println("请输入要删除车厢的编号：");
				String ID = in.nextLine();
				Coach coachToDel = findResource(ID, resourceList);
				OUT: if (coachToDel == null) {
					System.out.println(" 不存在此车厢");
				} else {
					for (TrainEntry thisEntry : entryList) {
						if (thisEntry.getResource().contains(coachToDel)) {
							if (!(thisEntry.getState().equals(CANCELED.instance)
									|| thisEntry.getState().equals(ENDED.instance))) {
								System.out.println("有未结束的计划项正在占用该资源");
								break OUT;
							}
						}
					}
					resourceList.remove(coachToDel);
				}
				break;
			}
			case "c" ://增加一个火车站
			{
				try {
					System.out.println("请输入要增加车站的名字、经度、纬度，参数间空格分开，如\"Harbin -30 155\"：");
					String[] paramOfPort = in.nextLine().split(" ");
					TrainStation newLocation = new TrainStation(paramOfPort[0], Double.valueOf(paramOfPort[1]),
							Double.valueOf(paramOfPort[2]));
					locationList.add(newLocation);
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("不符合要求的格式！");
				}
				break;
			}
			case "d" ://删除一个火车站
			{
				System.out.println("请输入要删除车站的名字");
				String nameOfPort = in.nextLine();
				TrainStation portToDel = findStation(nameOfPort, locationList);
				OUT: if (portToDel == null) {
					System.out.println(" 不存在此车站");
				} else {
					for (TrainEntry thisEntry : entryList) {
						if (thisEntry.getLocationList().contains(portToDel)) {
							if (!(thisEntry.getState().equals(CANCELED.instance)
									|| thisEntry.getState().equals(ENDED.instance))) {
								System.out.println("有未结束的计划项正在占用该位置");
								break OUT;
							}
						}
					}
					locationList.remove(portToDel);
				}
				break;
			}
			case "e" ://增加一个列车计划项
			{
				System.out.println("请输入列车计划项的编号如\"G381\"：");
				try {
					String zeroParam = in.nextLine();
					TrainEntry newEntry = new TrainEntryFactory().getEntry(zeroParam);
					System.out.println("请输入列车途经车站，参数间空格分开，如\"Harbin TianJin ShiJiazhuang\"：");
					String[] firstParam = in.nextLine().split(" ");
					List<TrainStation> stations = new ArrayList<>();
					for (String s : firstParam) {
						TrainStation thisStation = findStation(s, locationList);
						if (thisStation != null) {
							stations.add(thisStation);
						} else {
							System.out.println(s + "车站不存在");
							break ALL;
						}
					}
					newEntry.setLocationList(stations);
					System.out.println("请输入发车时间，以yyyy MM dd HH mm的格式，例如\"2020 1 1 13 27\"代表2020年1月1日13点27分：");
					String[] secondParam = in.nextLine().split(" ");
					Calendar from = Calendar.getInstance();
					from.set(Integer.valueOf(secondParam[0]), Integer.valueOf(secondParam[1]) - 1,
							Integer.valueOf(secondParam[2]), Integer.valueOf(secondParam[3]),
							Integer.valueOf(secondParam[4]));
					System.out.println("请输入到重点站时间，以yyyy MM dd HH mm的格式，例如\"2020 1 1 13 27\"代表2020年1月1日13点27分：");
					String[] thirdParam = in.nextLine().split(" ");
					Calendar to = Calendar.getInstance();
					to.set(Integer.valueOf(thirdParam[0]), Integer.valueOf(thirdParam[1]) - 1,
							Integer.valueOf(thirdParam[2]), Integer.valueOf(thirdParam[3]),
							Integer.valueOf(thirdParam[4]));
					newEntry.setSlot(new TimeSlot(from, to));
					List<TimeSlot> slotList = new ArrayList<>();
					for (int i = 1; i < stations.size() - 1; i++) {
						System.out.println("请输入第" + i
								+ "个途经站的到达时间，以yyyy MM dd HH mm的格式，例如\\\"2020 1 1 13 27\\\"代表2020年1月1日13点27分：");
						String[] fourthParam = in.nextLine().split(" ");
						Calendar arr = Calendar.getInstance();
						arr.set(Integer.valueOf(fourthParam[0]), Integer.valueOf(fourthParam[1]) - 1,
								Integer.valueOf(fourthParam[2]), Integer.valueOf(fourthParam[3]),
								Integer.valueOf(fourthParam[4]));
						System.out.println("请输入从第" + i
								+ "个途经站出发的时间，以yyyy MM dd HH mm的格式，例如\\\"2020 1 1 13 27\\\"代表2020年1月1日13点27分：");
						String[] fifthParam = in.nextLine().split(" ");
						Calendar sta = Calendar.getInstance();
						sta.set(Integer.valueOf(fifthParam[0]), Integer.valueOf(fifthParam[1]) - 1,
								Integer.valueOf(fifthParam[2]), Integer.valueOf(fifthParam[3]),
								Integer.valueOf(fifthParam[4]));
						slotList.add(new TimeSlot(arr, sta));
					}
					newEntry.setSlot(slotList);
					entryList.add(newEntry);
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("不符合要求的格式！");
				}
				break;
			}
			case "f" ://取消一个计划项
			{
				System.out.println("请输入列车计划项的编号:");
				String paramString = in.nextLine();
				TrainEntry entryToDel = findEntry(paramString, entryList);
				if (entryToDel != null) {
					entryToDel.cancel();
				} else {
					System.out.println("没有该计划项！");
				}
				break;
			}
			case "g" ://为某一列车分配车厢组
			{
				System.out.println("请输入要分配资源的列车计划项的编号");
				try {
					String zeroParam = in.nextLine();
					TrainEntry entryToAllo = findEntry(zeroParam, entryList);
					if (entryToAllo != null) {
						System.out.println("请输入要分配的车厢ID序列，参数间空格分开，如\"CA1001 B9802\"：");
						String[] firstParam = in.nextLine().split(" ");
						List<Coach> coachList = new ArrayList<>();
						for (String s : firstParam) {
							Coach thisCoa = findResource(s, resourceList);
							if (thisCoa != null)
								coachList.add(thisCoa);
							else {
								System.out.println("不存在该车厢");
								break ALL;
							}
						}
						entryToAllo.setResource(coachList);
					} else {
						System.out.println("没有此列车计划项");
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("不符合要求的格式！");
				}
				break;
			}
			case "h" ://使列车启动
			{
				System.out.println("请输入要启动的列车计划项的编号:");
				String paramString = in.nextLine();
				TrainEntry entryToDel = findEntry(paramString, entryList);
				if (entryToDel != null) {
					entryToDel.run();
				} else {
					System.out.println("没有该计划项！");
				}
				break;
			}
			case "i" ://阻塞某个列车
			{
				System.out.println("请输入要阻塞的列车计划项的编号:");
				try {
					String paramString = in.nextLine();
					TrainEntry entryToDel = findEntry(paramString, entryList);
					if(entryToDel != null) {
					System.out.println("请输入阻塞时间，以yyyy MM dd HH mm的格式，例如\"2020 1 1 13 27\"代表2020年1月1日13点27分：");
					String[] thirdParam = in.nextLine().split(" ");
					Calendar to = Calendar.getInstance();
					to.set(Integer.valueOf(thirdParam[0]), Integer.valueOf(thirdParam[1]) - 1,
							Integer.valueOf(thirdParam[2]), Integer.valueOf(thirdParam[3]),
							Integer.valueOf(thirdParam[4]));
					entryToDel.blockTheEntry(to);
					}
					else {
						System.out.println("不存在该计划项");
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("不符合要求的格式！");
				}
				break;
			}
			case "j" ://重启某个已阻塞的列车计划项
			{
				System.out.println("请输入要重启的列车计划项的编号:");
				try {
					String paramString = in.nextLine();
					TrainEntry entryToDel = findEntry(paramString, entryList);
					if(entryToDel != null) {
					System.out.println("请输入重启时间，以yyyy MM dd HH mm的格式，例如\"2020 1 1 13 27\"代表2020年1月1日13点27分：");
					String[] thirdParam = in.nextLine().split(" ");
					Calendar to = Calendar.getInstance();
					to.set(Integer.valueOf(thirdParam[0]), Integer.valueOf(thirdParam[1]) - 1,
							Integer.valueOf(thirdParam[2]), Integer.valueOf(thirdParam[3]),
							Integer.valueOf(thirdParam[4]));
					entryToDel.restart(to);
					}else {
						System.out.println("不存在该计划项");
					}
					
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("不符合要求的格式！");
				}
				break;
			}
			case "k" ://使列车到站
			{
				System.out.println("请输入要结束的列车计划项的编号:");
				String paramString = in.nextLine();
				TrainEntry entryToDel = findEntry(paramString, entryList);
				if (entryToDel != null) {
					entryToDel.end();
				} else {
					System.out.println("没有该计划项！");
				}
				break;
			}
			case "l" ://查看某列车状态
			{
				System.out.println("请输入查询状态的列车计划项的编号:");
				String paramString = in.nextLine();
				TrainEntry entryToDel = findEntry(paramString, entryList);
				if (entryToDel != null) {
					System.out.println(entryToDel.getState());
				} else {
					System.out.println("没有该计划项！");
				}
				break;
			}
			case "m" ://检测资源独占冲突
			{
				System.out.println("请稍等");
				PlanningEntryAPIs<?> thisOne = new PlanningEntryAPIs<>();
				System.out.println("是否存在资源独占冲突："+thisOne.checkResourceExclusiveConflict(entryList));
				break;
			}
			case "n" ://对于一个车厢，查看与该车厢有关的所有计划项
			{
				System.out.println("请输入需要查询的车厢的ID:");
				String paramString = in.nextLine();
				Coach planeToSear = findResource(paramString, resourceList);
				if(planeToSear != null)
				{
					for(TrainEntry thisEntry: entryList)
					{
						if (!thisEntry.getState().equals(WAITING.instance)) {
							if (thisEntry.getResource().contains(planeToSear))
								System.out.println(thisEntry.getName());
						}
					}
				}
				else {
					System.out.println("不存在该车厢");
				}
				break;
			}
			case "o" ://对于一个车厢，查看某个计划项的前序计划项
			{
				System.out.println("请输入列车计划项的编号、以及有关车厢的ID，参数间空格分开，如\"CA1001 B9802\"：");
				PlanningEntryAPIs<Coach> thisOne = new PlanningEntryAPIs<>();
				String[] firstParam = in.nextLine().split(" ");
				PlanningEntry<Coach> checkedOne = findEntry(firstParam[0], entryList);
				Coach thisResource = findResource(firstParam[1], resourceList);
				if (checkedOne != null && thisResource != null) {
					PlanningEntry<Coach> formerOne = thisOne.findPreEntryPerResource(thisResource, checkedOne,
							entryList);
					if (formerOne != null)
						System.out.println(formerOne.getName());
					else
						System.out.println("没有更早的计划项");
				}
				break;
			}
			case "p" ://显示一个火车站的信息版
			{
				System.out.println("请输入需要查询车站的名字:");
				String paramString = in.nextLine();
				TrainStation portToBoard = findStation(paramString, locationList);
				System.out.println("请输入当前时间，以yyyy MM dd HH mm的格式，例如\"2020 1 1 13 27\"代表2020年1月1日13点27分：");
				String[] secondParam = in.nextLine().split(" ");
				Calendar timeNow = Calendar.getInstance();
				timeNow.set(Integer.valueOf(secondParam[0]), Integer.valueOf(secondParam[1]) - 1,
						Integer.valueOf(secondParam[2]), Integer.valueOf(secondParam[3]),
						Integer.valueOf(secondParam[4]));
				if(portToBoard != null)
				{
					TrainBoard newBoard = new TrainBoard(timeNow, portToBoard);
					newBoard.setFromList(entryList);
					newBoard.setToList(entryList);
					newBoard.setIterToArr();
					newBoard.iterator();
					newBoard.setIterToFro();
					newBoard.iterator();
					newBoard.visualize();
				}
				else {
					System.out.println("不存在该车站");
				}
				break;
			}
			case "help" ://提供帮助信息
			{
				printHelp();
				break;
			}
			case "end":
				in.close();
				System.exit(0);
			default:
				System.out.println("非法选项！");
			}
		}
	}
}
