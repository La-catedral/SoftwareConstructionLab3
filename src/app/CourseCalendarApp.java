package app;

import java.util.ArrayList;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import board.ClassBoard;
import classSchedule.CourseEntry;
import factory.CourseEntryFactory;
import location.ClassRoom;
import location.Location;
import planningEntry.CheckLocationConForceImp;
import planningEntry.PlanningEntry;
import planningEntry.PlanningEntryAPIs;
import resource.Teacher;
import state.CANCELED;
import state.ENDED;
import state.WAITING;
import timeslot.TimeSlot;

public class CourseCalendarApp {

	/**
	 * 在当前所有资源中查找特定的ID的资源
	 * @param ID 要查找的资源的ID
	 * @param resourceList 当前所有资源构成的列表
	 * @return 具有该特定的ID的资源
	 */
	public static Teacher findResource(String ID,Set<Teacher>resourceList)
	{
		for(Teacher thisTeacher:resourceList)
		{
			if(thisTeacher.getID().equals(ID))
			return thisTeacher;
		}
		
		return null;
	}
	
	/**
	 * 在当前所有位置中查找特定的名称的位置
	 * @param name 位置的名称
	 * @param locationList 所有的现有位置
	 * @return 所要找的位置
	 */
	public static ClassRoom findClassRoom(String name,Set<ClassRoom>locationList)
	{
		for(ClassRoom thisRoom : locationList)
		{
			if(thisRoom.getName().equals(name))
			return thisRoom;
		}
		return null;
	}
	
	/**
	 * 在当前所有的已有计划项中查找名称为name的计划项
	 * @param name 要找的计划项的名称
	 * @param entryList 所有已有计划项构成的list
	 * @return 要找的计划项
	 */
	public static CourseEntry findEntry(String name,List<CourseEntry>entryList)
	{
		for(PlanningEntry<?> thisEntry:entryList)
		{
			if(thisEntry.getName().equals(name))
				return (CourseEntry)thisEntry;
		}
		return null;
	}
	public static void printHelp()
	{
		System.out.println("********help********");
		System.out.println("输入下列英文字母或单词：");
		System.out.println("a :加入一位教师");
		System.out.println("b :减少一位教师");
		System.out.println("c :增加一个教室");
		System.out.println("d :删除一个教室");
		System.out.println("e :增加一个课程计划项");
		System.out.println("f :取消一个计划项");
		System.out.println("g :为某一课程分配教师");
		System.out.println("h :开始上课");
		System.out.println("i :变更某个已存在的计划项的位置:");
		System.out.println("j :下课");
		System.out.println("k :查看某课程状态");
		System.out.println("l :检测资源独占冲突");
		System.out.println("m :检测位置独占冲突");
		System.out.println("n :对于一位老师，查看与该老师有关的所有计划项");
		System.out.println("o :对于一位老师，查看某个计划项的前序计划项");
		System.out.println("p :显示一个教室的信息版");
		System.out.println("help :提供帮助信息");
		System.out.println("end :结束");
		System.out.println();
	}
	
	public static void main(String[] args) {
			Set<ClassRoom> locationList = new HashSet<>();//现有位置列表
			Set<Teacher> resourceList = new HashSet<>();//现有资源列表
			List<CourseEntry> entryList = new ArrayList<>();//创立的所有计划项 无论状态 都要加入这里
			
			printHelp();
			Scanner in = new Scanner(System.in);
		while (true) {
			System.out.println("请输入选项：");
			String choice = in.nextLine();
			switch (choice) {
			case "a": {// 加入一位教师");
				System.out.println(
						"请输入要增加教师的身份证号、姓名、性别（male/female）、职称，参数间空格分开，如\"110100198001010001 ZhangSan male professor\"：");
				try {
					String[] param = in.nextLine().split(" ");
					Teacher newTeacher = new Teacher(param[0], param[1], param[2], param[3]);
					resourceList.add(newTeacher);
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("不符合要求的格式！");
				}
				break;
			}
			case "b":// 减少一位教师
			{
				System.out.println("请输入要离开的教师的身份证号：");
				String ID = in.nextLine();
				Teacher TeacherToDel = findResource(ID, resourceList);
				OUT: if (TeacherToDel == null) {
					System.out.println(" 不存在此教师");
				} else {
					for (CourseEntry thisEntry : entryList) {
						if (thisEntry.getResource().equals(TeacherToDel)) {
							if (!(thisEntry.getState().equals(CANCELED.instance)
									|| thisEntry.getState().equals(ENDED.instance))) {
								System.out.println("该教师有未结束的计划项");
								break OUT;
							}
						}
					}
					resourceList.remove(TeacherToDel);
				}
				break;
			}
			case "c":// 增加一个教室
			{
				System.out.println("请输入要增加教室的名字、经度、纬度，参数间空格分开，如\"正心11 -30 155\"：");
				try {
					String[] paramOfPort = in.nextLine().split(" ");
					ClassRoom newLocation = new ClassRoom(paramOfPort[0], Double.valueOf(paramOfPort[1]),
							Double.valueOf(paramOfPort[2]));
					locationList.add(newLocation);
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("不符合要求的格式！");
				}
				break;
			}
			case "d":// 删除一个教室
			{
				System.out.println("请输入要删除教室的名字");
				String nameOfPort = in.nextLine();
				ClassRoom portToDel = findClassRoom(nameOfPort, locationList);
				OUT: if (portToDel == null) {
					System.out.println(" 不存在此教室");
				} else {
					for (CourseEntry thisEntry : entryList) {
						if (thisEntry.getLocation().equals(portToDel)) {
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
			case "e":// 增加一个课程计划项
			{
				System.out.println("请输入课程计划项的名称、上课教室名，参数间空格分开，如\"软件构造 正心21\"：");
				try {
					String[] firstParam = in.nextLine().split(" ");
					CourseEntry newEntry = new CourseEntryFactory().getEntry(firstParam[0]);
					Location fromLocation = findClassRoom(firstParam[1], locationList);
					if (fromLocation != null) {
						System.out.println("请输入课程开始时间，以yyyy MM dd HH mm的格式，例如\"2020 1 1 13 27\"代表2020年1月1日13点27分：");
						String[] secondParam = in.nextLine().split(" ");
						Calendar from = Calendar.getInstance();
						from.set(Integer.valueOf(secondParam[0]), Integer.valueOf(secondParam[1]) - 1,
								Integer.valueOf(secondParam[2]), Integer.valueOf(secondParam[3]),
								Integer.valueOf(secondParam[4]));
						System.out.println("请输入课程结束时间，以yyyy MM dd HH mm的格式，例如\"2020 1 1 13 27\"代表2020年1月1日13点27分：");
						String[] thirdParam = in.nextLine().split(" ");
						Calendar to = Calendar.getInstance();
						to.set(Integer.valueOf(thirdParam[0]), Integer.valueOf(thirdParam[1]) - 1,
								Integer.valueOf(thirdParam[2]), Integer.valueOf(thirdParam[3]),
								Integer.valueOf(thirdParam[4]));
						newEntry.setSlot(new TimeSlot(from, to));
						newEntry.setLocation(fromLocation);
						entryList.add(newEntry);
					} else {
						System.out.println("不存在的教室");
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("不符合要求的格式！");
				}
				break;
			}
			case "f":// 取消一个计划项
			{
				System.out.println("请输入课程计划项的名称:");
				String paramString = in.nextLine();
				CourseEntry entryToDel = findEntry(paramString, entryList);
				if (entryToDel != null) {
					entryToDel.cancel();
				} else {
					System.out.println("没有该计划项！");
				}
				break;
			}
			case "g":// 为某一课程分配教师
			{
				System.out.println("请输入要分配资源的课程计划项的名称、教师的ID，参数间空格分开，如\"软件构造 100101198001010001\"：");
				String[] firstParam = in.nextLine().split(" ");
				CourseEntry entryToAllo = findEntry(firstParam[0], entryList);
				Teacher planeToAllo = findResource(firstParam[1], resourceList);
				if (entryToAllo != null && planeToAllo != null) {
					entryToAllo.setResource(planeToAllo);
				} else {
					System.out.println("请检查您的输入！");
				}
				break;
			}
			case "h":// 开始上课
			{
				System.out.println("请输入要上课的课程名称:");
				String paramString = in.nextLine();
				CourseEntry entryToDel = findEntry(paramString, entryList);
				if (entryToDel != null) {
					entryToDel.run();
				} else {
					System.out.println("没有该计划项！");
				}
				break;
			}
			case "i":// 变更某个已存在的计划项的位置
			{
				System.out.println("请输入要上课的课程名称、要变更到哪个教室，参数间空格分开，如\"软件构造 正心31\":");
				String[] paramString = in.nextLine().split(" ");
				CourseEntry entryToDel = findEntry(paramString[0], entryList);
				ClassRoom changeToRoom = findClassRoom(paramString[1], locationList);
				if (entryToDel != null && changeToRoom != null) {
					entryToDel.changeSingleLocation(changeToRoom);
				} else {
					System.out.println("没有该计划项或教室！");
				}
				break;
			}
			case "j":// 下课
			{
				System.out.println("请输入要下课的课程名称:");
				String paramString = in.nextLine();
				CourseEntry entryToDel = findEntry(paramString, entryList);
				if (entryToDel != null) {
					entryToDel.end();
				} else {
					System.out.println("没有该计划项！");
				}
				break;
			}
			case "k":// 查看某课程状态
			{
				System.out.println("请输入查询状态的课程计划项的名称:");
				String paramString = in.nextLine();
				CourseEntry entryToDel = findEntry(paramString, entryList);
				if (entryToDel != null) {
					System.out.println(entryToDel.getState());
				} else {
					System.out.println("没有该计划项！");
				}
				break;
			}
			case "l":// 检测资源独占冲突
			{
				System.out.println("请稍等");
				PlanningEntryAPIs<?> thisOne = new PlanningEntryAPIs<>();
				System.out.println("是否存在资源独占冲突：" + thisOne.checkResourceExclusiveConflict(entryList));
				break;
			}
			case "m":// 检测位置独占冲突
			{
				System.out.println("请稍等");
				PlanningEntryAPIs<?> thisOne = new PlanningEntryAPIs<>();
				System.out.println(
						"是否存在位置独占冲突：" + thisOne.checkLocationConflict(new CheckLocationConForceImp(), entryList));
				break;
			}
			case "n":// 对于一位老师，查看与该老师有关的所有计划项
			{
				System.out.println("请输入需要查询的教师的ID:");
				String paramString = in.nextLine();
				Teacher teacherToSear = findResource(paramString, resourceList);
				if (teacherToSear != null) {
					for (CourseEntry thisEntry : entryList) {
						if(!thisEntry.getState().equals(WAITING.instance))
						{if (thisEntry.getResource().equals(teacherToSear))
							System.out.println(thisEntry.getName());
						}
					}
				} else {
					System.out.println("不存在该教师");
				}
				break;
			}
			case "o":// 对于一位老师，查看某个计划项的前序计划项
			{
				System.out.println("请输入课程计划项名称、以及有关教师的ID，参数间空格分开，如\"软件构造 100101198001010001\"：");
				PlanningEntryAPIs<Teacher> thisOne = new PlanningEntryAPIs<>();
				String[] firstParam = in.nextLine().split(" ");
				PlanningEntry<Teacher> checkedOne = findEntry(firstParam[0], entryList);
				Teacher thisResource = findResource(firstParam[1], resourceList);
				if (checkedOne != null && thisResource != null) {
					PlanningEntry<Teacher> formerOne = thisOne.findPreEntryPerResource(thisResource, checkedOne,
							entryList);
					if (formerOne != null)
						System.out.println(formerOne.getName());
					else
						System.out.println("没有更早的计划项");
				}
				break;
			}
			case "p":// 显示一个教室的信息版
			{
				System.out.println("请输入需要查询的教室的名字:");
				String paramString = in.nextLine();
				ClassRoom portToBoard = findClassRoom(paramString, locationList);
				System.out.println("请输入当前时间，以yyyy MM dd HH mm的格式，例如\"2020 1 1 13 27\"代表2020年1月1日13点27分：");
				String[] secondParam = in.nextLine().split(" ");
				Calendar timeNow = Calendar.getInstance();
				timeNow.set(Integer.valueOf(secondParam[0]), Integer.valueOf(secondParam[1]) - 1,
						Integer.valueOf(secondParam[2]), Integer.valueOf(secondParam[3]),
						Integer.valueOf(secondParam[4]));
				if (portToBoard != null) {
					ClassBoard newBoard = new ClassBoard(timeNow, portToBoard);
					newBoard.setClasses(entryList);
					newBoard.iterator();
					newBoard.visualize();
				} else {
					System.out.println("不存在该教室");
				}
				break;
			}
			case "help":// 提供帮助信息
			{
				printHelp();
				break;
			}
			case "end":
				in.close();
				System.exit(0);
			default:
				System.out.println("非法选项");
			}
		}
	}
}
