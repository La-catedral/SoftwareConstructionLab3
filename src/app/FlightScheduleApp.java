package app;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import board.FlightBoard;
import factory.FlightEntryFactory;
import flightSchedule.FlightEntry;
import location.Airport;
import location.Location;
import planningEntry.PlanningEntry;
import planningEntry.PlanningEntryAPIs;
import resource.Plane;
import state.CANCELED;
import state.ENDED;
import state.WAITING;
import timeslot.TimeSlot;
public class FlightScheduleApp {

	/**
	 * 在当前所有资源中查找特定的ID的资源
	 * @param ID 要查找的资源的ID
	 * @param resourceList 当前所有资源构成的列表
	 * @return 具有该特定的ID的资源
	 */
	public static Plane findResource(String ID,Set<Plane>resourceList)
	{
		for(Plane thisPlane:resourceList)
		{
			if(thisPlane.getID().equals(ID))
			return thisPlane;
		}
		
		return null;
	}
	
	/**
	 * 在当前所有位置中查找特定的名称的位置
	 * @param name 位置的名称
	 * @param locationList 所有的现有位置
	 * @return 所要找的位置
	 */
	public static Airport findAirport(String name,Set<Airport>locationList)
	{
		for(Airport thisPort : locationList)
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
	public static FlightEntry findEntry(String name,List<FlightEntry>entryList)
	{
		for(PlanningEntry<?> thisEntry:entryList)
		{
			if(thisEntry.getName().equals(name))
				return (FlightEntry)thisEntry;
		}
		return null;
	}
	public static void printHelp()
	{
		System.out.println("********help********");
		System.out.println("输入下列英文字母或单词：");
		System.out.println("a :增设一架飞机");
		System.out.println("b :删除一架飞机");
		System.out.println("c :增加一座机场");
		System.out.println("d :删除一座机场");
		System.out.println("e :增加一个航班计划项");
		System.out.println("f :取消一个计划项");
		System.out.println("g :为某一航班分配飞机");
		System.out.println("h :航班起飞");
		System.out.println("i :航班降落");
		System.out.println("j :查看某航班状态");
		System.out.println("k :检测资源独占冲突");
		System.out.println("l :对于一架飞机，查看与该飞机有关的所有计划项");
		System.out.println("m :对于一架飞机，查看某个计划项的前序计划项");
		System.out.println("n :显示一个机场的信息版");
		System.out.println("help :提供帮助信息");
		System.out.println("parser :从外部合法文件中读取计划项信息并生成计划项");
		System.out.println("end :结束");
		System.out.println();

	}
	
	
	
	public static void main(String[] args) throws FileNotFoundException {

		Set<Airport> locationList = new HashSet<>();//现有位置列表
		Set<Plane> resourceList = new HashSet<>();//现有资源列表
		List<FlightEntry> entryList = new ArrayList<>();//创立的所有计划项 无论状态 都要加入这里
		
		
		//运行app
		printHelp();
		Scanner in = new Scanner(System.in);
		while(true)
		{
			System.out.println("请输入选项：");
			String choice = in.nextLine();
			switch (choice) {

			case "a":
			{
				try{System.out.println("请输入要增加飞机的编号、即兴、座位数、机龄，参数间空格分开，如\"B9802 A350 300 2.5\"：");
				String[] param = in.nextLine().split(" ");
				Plane newPlane = new Plane(param[0], param[1], Integer.valueOf(param[2]), Double.valueOf(param[3]));
				resourceList.add(newPlane);
				}catch(ArrayIndexOutOfBoundsException e)
				{
					System.out.println("不符合要求的格式！");
				}
				break;
			}
			case "b":
			{
				System.out.println("请输入要删除飞机的编号：");
				String ID = in.nextLine();
				Plane planeToDel = findResource(ID, resourceList);
				OUT: if (planeToDel == null) {
					System.out.println(" 不存在此飞机");
				} else {
					for (FlightEntry thisEntry : entryList) {
						if (thisEntry.getResource().equals(planeToDel)) {
							if (!(thisEntry.getState().equals(CANCELED.instance)
									|| thisEntry.getState().equals(ENDED.instance))) {
								System.out.println("有未结束的计划项正在占用该资源");
								break OUT;
							}
						}
					}
					resourceList.remove(planeToDel);
				}
				break;
			}
			case "c"://增加一座机场
			{
				try {
				System.out.println("请输入要增加机场的名字、经度、纬度，参数间空格分开，如\"Harbin -30 155\"：");
				String[] paramOfPort = in.nextLine().split(" ");
				Airport newLocation = new Airport(paramOfPort[0], Double.valueOf(paramOfPort[1]),
						Double.valueOf(paramOfPort[2]));
				locationList.add(newLocation);
				}catch(ArrayIndexOutOfBoundsException e)
				{
					System.out.println("不符合要求的格式！");
				}
				break;
			}
			case"d" ://删除一座机场
			{
				System.out.println("请输入要删除机场的名字");
				String nameOfPort = in.nextLine();
				Airport portToDel = findAirport(nameOfPort, locationList);
				OUT: if (portToDel == null) {
					System.out.println(" 不存在此机场");
				} else {
					for (FlightEntry thisEntry : entryList) {
						if (thisEntry.getfromLocation().equals(portToDel) || thisEntry.getToLocation().equals(portToDel)) {
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
			case "e" ://增加一个航班计划项");
			{
				System.out.println("请输入航班计划项的编号、起始地点、终止地点，参数间空格分开，如\"CA1001 Harbin BeiJing\"：");
				try {
					String[] firstParam = in.nextLine().split(" ");
					FlightEntry newEntry = new FlightEntryFactory().getEntry(firstParam[0]);
					Location fromLocation = findAirport(firstParam[1], locationList);
					Location toLocation = findAirport(firstParam[2], locationList);
					if (fromLocation != null && toLocation != null) {
						newEntry.setLocation(fromLocation, toLocation);
						System.out.println("请输入起飞时间，以yyyy MM dd HH mm的格式，例如\"2020 1 1 13 27\"代表2020年1月1日13点27分：");
						String[] secondParam = in.nextLine().split(" ");
						Calendar from = Calendar.getInstance();
						from.set(Integer.valueOf(secondParam[0]), Integer.valueOf(secondParam[1]) - 1,
								Integer.valueOf(secondParam[2]), Integer.valueOf(secondParam[3]),
								Integer.valueOf(secondParam[4]));
						System.out.println("请输入降落时间，以yyyy MM dd HH mm的格式，例如\"2020 1 1 13 27\"代表2020年1月1日13点27分：");
						String[] thirdParam = in.nextLine().split(" ");
						Calendar to = Calendar.getInstance();
						to.set(Integer.valueOf(thirdParam[0]), Integer.valueOf(thirdParam[1]) - 1,
								Integer.valueOf(thirdParam[2]), Integer.valueOf(thirdParam[3]),
								Integer.valueOf(thirdParam[4]));
						newEntry.setSlot(new TimeSlot(from, to));
						entryList.add(newEntry);
					} else {
						System.out.println("不存在的机场");
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("不符合要求的格式！");
				}
				break;
			}
			case "f" ://取消一个计划项")
			{
				System.out.println("请输入航班计划项的编号:");
				String paramString = in.nextLine();
				FlightEntry entryToDel = findEntry(paramString, entryList);
				if (entryToDel != null) {
					entryToDel.cancel();
				} else {
					System.out.println("没有该计划项！");
				}
				break;
			}
			case"g" ://为某个计划项分配资源
			{
				System.out.println("请输入要分配资源的航班计划项的编号、飞机ID，参数间空格分开，如\"CA1001 B9802\"：");
				String[] firstParam = in.nextLine().split(" ");
				FlightEntry entryToAllo = findEntry(firstParam[0], entryList);
				Plane planeToAllo = findResource(firstParam[1], resourceList);
				if(entryToAllo !=null && planeToAllo!= null)
				{
					entryToAllo.setResource(planeToAllo);
				}
				else {
					System.out.println("请检查您的输入！");
				}
				break;
			}
			case "h" ://启动某一计划项
			{
				System.out.println("请输入要启动的航班计划项的编号:");
				String paramString = in.nextLine();
				FlightEntry entryToDel = findEntry(paramString, entryList);
				if (entryToDel != null) {
					entryToDel.run();
				} else {
					System.out.println("没有该计划项！");
				}
				break;
			}
			case "i" ://结束某个计划项
			{
				System.out.println("请输入要结束的航班计划项的编号:");
				String paramString = in.nextLine();
				FlightEntry entryToDel = findEntry(paramString, entryList);
				if (entryToDel != null) {
					entryToDel.end();
				} else {
					System.out.println("没有该计划项！");
				}
				break;
			}
			case "j" ://用户选定一个计划项，查看它的当前状态
			{
				System.out.println("请输入查询状态的航班计划项的编号:");
				String paramString = in.nextLine();
				FlightEntry entryToDel = findEntry(paramString, entryList);
				if (entryToDel != null) {
					System.out.println(entryToDel.getState());
				} else {
					System.out.println("没有该计划项！");
				}
				break;
			}
			case "k" ://检测当前的计划项集合中可能存在的资源独占冲突
			{
				System.out.println("请稍等");
				PlanningEntryAPIs<?> thisOne = new PlanningEntryAPIs<>();
				System.out.println("是否存在资源独占冲突："+thisOne.checkResourceExclusiveConflict(entryList));
				break;
			}
				
			case "l" ://针对用户选定的某个资源，列出使用该资源的所有计划项
			{
				System.out.println("请输入需要查询的飞机的ID:");
				String paramString = in.nextLine();
				Plane planeToSear = findResource(paramString, resourceList);
				if(planeToSear != null)
				{
					for(FlightEntry thisEntry: entryList)
					{
						if (!thisEntry.getState().equals(WAITING.instance)) {
							if (thisEntry.getResource().equals(planeToSear))
								System.out.println(thisEntry.getName());
						}
					}
				}
				else {
					System.out.println("不存在该飞机");
				}
				break;
			}
			case "m"://对于一架飞机，查看某个计划项的前序计划项
			{
				System.out.println("请输入航班计划项的编号、以及有关飞机的ID，参数间空格分开，如\"CA1001 B9802\"：");
				PlanningEntryAPIs<Plane> thisOne = new PlanningEntryAPIs<>();
				String[] firstParam = in.nextLine().split(" ");
				PlanningEntry<Plane> checkedOne = findEntry(firstParam[0], entryList);
				Plane thisResource = findResource(firstParam[1], resourceList);
				if (checkedOne != null && thisResource != null) {
					PlanningEntry<Plane> formerOne = thisOne.findPreEntryPerResource(thisResource, checkedOne,
							entryList);
					if (formerOne != null)
						System.out.println(formerOne.getName());
					else
						System.out.println("没有更早的计划项");
				}
				break;
			}
			case "n"://选定特定位置，可视化展示当前时刻该位置的信息板
			{
				System.out.println("请输入需要查询的机场的名字:");
				String paramString = in.nextLine();
				Airport portToBoard = findAirport(paramString, locationList);
				System.out.println("请输入当前时间，以yyyy MM dd HH mm的格式，例如\"2020 1 1 13 27\"代表2020年1月1日13点27分：");
				String[] secondParam = in.nextLine().split(" ");
				Calendar timeNow = Calendar.getInstance();
				timeNow.set(Integer.valueOf(secondParam[0]), Integer.valueOf(secondParam[1]) - 1,
						Integer.valueOf(secondParam[2]), Integer.valueOf(secondParam[3]),
						Integer.valueOf(secondParam[4]));
				if(portToBoard != null)
				{
					FlightBoard newBoard = new FlightBoard(timeNow, portToBoard);
					newBoard.setFromList(entryList);
					newBoard.setToList(entryList);
					newBoard.setIterToArr();
					newBoard.iterator();
					newBoard.setIterToFro();
					newBoard.iterator();
					newBoard.visualize();
				}
				else {
					System.out.println("不存在该机场");
				}
				break;
			}
			case "help" ://提供帮助信息");	
			{
				printHelp();
				break;
			}
			case "parser":
			{
				System.out.println("请输入文件民，比如src/app/FlightSchedule_1.txt:");
				Scanner fileScan = new Scanner(new File(in.nextLine()));
				while (fileScan.hasNext()) {
					String thisLine;
					String pattern1 = "Flight:(\\d\\d\\d\\d-\\d\\d-\\d\\d),([A-Z][A-Z]\\d{2,4})\\s*";
					Pattern newPar = Pattern.compile(pattern1);
					thisLine = fileScan.nextLine();
//					if (thisLine.equals(""))
//						break;
					Matcher m = newPar.matcher(thisLine);
					if (!m.matches()) {
						System.out.println(thisLine + "：该行不符合规范，停止读入");
						System.exit(0);
					}
					String FlightName = m.group(2);
					String date = m.group(1);

					String pattern2 = "\\{";// 第二行
					newPar = Pattern.compile(pattern2);
					thisLine = fileScan.nextLine();
					m = newPar.matcher(thisLine);
					if (!m.matches()) {
						System.out.println(thisLine + "：该行不符合规范，停止读入");
						System.exit(0);
					}

					String pattern3 = "\\s*DepartureAirport:(\\w+)";
					newPar = Pattern.compile(pattern3);
					thisLine = fileScan.nextLine();
					m = newPar.matcher(thisLine);
					if (!m.matches()) {
						System.out.println(thisLine + "：该行不符合规范，停止读入");
						System.exit(0);
					}
					String departureAir = m.group(1);

					String pattern4 = "\\s*ArrivalAirport:(\\w+)";
					newPar = Pattern.compile(pattern4);
					thisLine = fileScan.nextLine();
					m = newPar.matcher(thisLine);
					if (!m.matches()) {
						System.out.println(thisLine + "：该行不符合规范，停止读入");
						System.exit(0);
					}
					String ArrivalAir = m.group(1);

					String pattern5 = "\\s*DepatureTime:([0-9]{4}-\\d\\d-\\d\\d) (\\d\\d:\\d\\d)\\s*";
					newPar = Pattern.compile(pattern5);
					thisLine = fileScan.nextLine();
					m = newPar.matcher(thisLine);
					if (!m.matches() || !m.group(1).equals(date))// 若不匹配或者该日期和前面的日期不相同//|| !m.group(1).equals(date)
					{
						System.out.println(thisLine + "：该行不符合规范，停止读入");
						System.exit(0);
					}
					String deparDate = m.group(1);
					String deparTime = m.group(2);

					String pattern6 = "\\s*ArrivalTime:(\\d{4}-\\d\\d-\\d\\d) (\\d\\d:\\d\\d)\\s*";
					newPar = Pattern.compile(pattern6);
					thisLine = fileScan.nextLine();
					m = newPar.matcher(thisLine);
					Calendar firstCal = Calendar.getInstance();
					String[] formerTimeString = date.split("-");
					firstCal.set(Integer.valueOf(formerTimeString[0]), Integer.valueOf(formerTimeString[1]) - 1,
							Integer.valueOf(formerTimeString[2]));
					if (!m.matches() || !m.group(1).equals(date))// 若不匹配或者该日期和前面的日期不相同
					{
						Calendar newCal = Calendar.getInstance();
						String[] timeString = m.group(1).split("-");
						newCal.set(Integer.valueOf(timeString[0]), Integer.valueOf(timeString[1]) - 1,
								Integer.valueOf(timeString[2]));
						
						if (!(firstCal.get(Calendar.DAY_OF_YEAR) == newCal.get(Calendar.DAY_OF_YEAR) - 1)) {
							System.out.println(thisLine + "：该行不符合规范，停止读入");
							System.exit(0);
						}
					}
					String arriDate = m.group(1);
					String arriTime = m.group(2);

					String pattern7 = "\\s*Plane:((B|N)\\d{4})\\s*";
					newPar = Pattern.compile(pattern7);
					thisLine = fileScan.nextLine();
					m = newPar.matcher(thisLine);
					if (!m.matches()) {
						System.out.println(thisLine + "：该行不符合规范，停止读入");
						System.exit(0);
					}
					String PlaneID = m.group(1);

					String pattern8 = "\\{";
					newPar = Pattern.compile(pattern8);
					thisLine = fileScan.nextLine();
					m = newPar.matcher(thisLine);
					if (!m.matches()) {
						System.out.println(thisLine + "：该行不符合规范，停止读入");
						System.exit(0);
					}

					String pattern9 = "\\s*Type:([\\da-zA-Z]+)\\s*";
					newPar = Pattern.compile(pattern9);
					thisLine = fileScan.nextLine();
					m = newPar.matcher(thisLine);
					if (!m.matches()) {
						System.out.println(thisLine + "：该行不符合规范，停止读入");
						System.exit(0);
					}
					String type = m.group(1);

					String pattern10 = "\\s*Seats:(\\d+)\\s*";
					newPar = Pattern.compile(pattern10);
					thisLine = fileScan.nextLine();
					m = newPar.matcher(thisLine);
					if (!m.matches() || Integer.valueOf(m.group(1)) < 50 || Integer.valueOf(m.group(1)) > 600) {
						System.out.println(thisLine + "：该行不符合规范，停止读入");
						System.exit(0);
					}
					String seats = m.group(1);

					String pattern11 = "\\s*Age:((([1-9]?\\d)|0)(\\.\\d)?)\\s*";
					newPar = Pattern.compile(pattern11);
					thisLine = fileScan.nextLine();
					m = newPar.matcher(thisLine);
					if (!m.matches() || Double.valueOf(m.group(1)) < 0 || Double.valueOf(m.group(1)) > 30)// ||Double.valueOf(m.group(1))<0||Integer.valueOf(m.group(1))>30
					{
						System.out.println(thisLine + "：该行不符合规范，停止读入");
						System.exit(0);
					}
					String age = m.group(1);

					String pattern12 = "\\s*\\}\\s*";
					newPar = Pattern.compile(pattern12);
					thisLine = fileScan.nextLine();
					m = newPar.matcher(thisLine);
					if (!m.matches()) {
						System.out.println(thisLine + "：该行不符合规范，停止读入");
						System.exit(0);
					}

					String pattern13 = "\\s*\\}\\s*";
					newPar = Pattern.compile(pattern13);
					thisLine = fileScan.nextLine();
					m = newPar.matcher(thisLine);
					if (!m.matches()) {
						System.out.println(thisLine + "：该行不符合规范，停止读入");
						System.exit(0);
					}
					
					{
						Plane newPlane = new Plane(PlaneID, type, Integer.valueOf(seats), Double.valueOf(age));
						resourceList.add(newPlane);
						
						Airport newLocationOne = new Airport(departureAir, 0,0);
						locationList.add(newLocationOne);
						
						Airport newLocationTwo = new Airport(ArrivalAir, 0,0);
						locationList.add(newLocationTwo);
					}
					{
						FlightEntry newEn = findEntry(FlightName, entryList);
						if(newEn!=null)
						{
							if (newEn.getSlot().getBeginTime().get(Calendar.DAY_OF_YEAR) == firstCal
									.get(Calendar.DAY_OF_YEAR)) {//若航班编号相同 并且日期也相同 则为非法
								System.out.println("航班不能在同一天飞两次！");
								System.exit(0);
							} else {//若是不同日期的同一编号的航班
								String[] depar = deparTime.split(":");
								String[] arri= arriTime.split(":");
								if (!(Integer.valueOf(depar[0]) == newEn.getSlot().getBeginTime()//起飞时间应相同
										.get(Calendar.HOUR_OF_DAY)
										&& Integer.valueOf(depar[1]) == newEn.getSlot().getBeginTime()
												.get(Calendar.MINUTE)
										&& Integer.valueOf(arri[0]) == newEn.getSlot().getEndTime()//抵达时间应相同
												.get(Calendar.HOUR_OF_DAY)
										&& Integer.valueOf(arri[1]) == newEn.getSlot().getEndTime().get(Calendar.MINUTE)
										&& departureAir.equals(newEn.getfromLocation().getName())//起飞机场应相同
										&& ArrivalAir.equals(newEn.getToLocation().getName()))) {//降落机场应相同
									System.out.println("同一航班每天往返的机场和时间应相同！");
									System.exit(0);
								}
								else {//满足条件 就新建一个计划项
									FlightEntry newEntry = new FlightEntryFactory().getEntry(FlightName);
									Location fromLocation = findAirport(departureAir, locationList);
									Location toLocation = findAirport(ArrivalAir, locationList);
									if (fromLocation != null && toLocation != null) {
										newEntry.setLocation(fromLocation, toLocation);

										String[] deparDay = deparDate.split("-");
										String[] deparNew = deparTime.split(":");
										String[] arrDay = arriDate.split("-");
										String[] arr = arriTime.split(":");

										Calendar from = Calendar.getInstance();
										from.set(Integer.valueOf(deparDay[0]), Integer.valueOf(deparDay[1]) - 1,
												Integer.valueOf(deparDay[2]), Integer.valueOf(deparNew[0]),
												Integer.valueOf(deparNew[1]));
										Calendar to = Calendar.getInstance();
										to.set(Integer.valueOf(arrDay[0]), Integer.valueOf(arrDay[1]) - 1,
												Integer.valueOf(arrDay[2]), Integer.valueOf(arr[0]), Integer.valueOf(arr[1]));
										newEntry.setSlot(new TimeSlot(from, to));
										newEntry.setResource(findResource(PlaneID, resourceList));
										entryList.add(newEntry);
									}
									
								}
							}
						}
						else {// 若没有此编号的航班 创建一个
							FlightEntry newEntry = new FlightEntryFactory().getEntry(FlightName);
							Location fromLocation = findAirport(departureAir, locationList);
							Location toLocation = findAirport(ArrivalAir, locationList);
							if (fromLocation != null && toLocation != null) {
								newEntry.setLocation(fromLocation, toLocation);

								String[] deparDay = deparDate.split("-");
								String[] depar = deparTime.split(":");
								String[] arrDay = arriDate.split("-");
								String[] arr = arriTime.split(":");

								Calendar from = Calendar.getInstance();
								from.set(Integer.valueOf(deparDay[0]), Integer.valueOf(deparDay[1]) - 1,
										Integer.valueOf(deparDay[2]), Integer.valueOf(depar[0]),
										Integer.valueOf(depar[1]));
								Calendar to = Calendar.getInstance();
								to.set(Integer.valueOf(arrDay[0]), Integer.valueOf(arrDay[1]) - 1,
										Integer.valueOf(arrDay[2]), Integer.valueOf(arr[0]), Integer.valueOf(arr[1]));
								newEntry.setSlot(new TimeSlot(from, to));
								newEntry.setResource(findResource(PlaneID, resourceList));
								entryList.add(newEntry);
							}
						}
					}
				}
				fileScan.close();
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
