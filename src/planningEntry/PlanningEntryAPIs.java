package planningEntry;

import java.util.List;



import classSchedule.CourseEntry;
import flightSchedule.FlightEntry;
import resource.Resource;
import timeslot.TimeSlot;
import trainSchedule.TrainEntry;

public class PlanningEntryAPIs<R> {

	

		/**
		 * 检测一组计划项之间是否存在位置独占冲突:
		 * 如果两个计划项在同一时间点上占用了不可共享的位置，那么就存在了位置冲突。
		 * @param thisCheckStrategy,选择检查的算法
		 * @param entries，要检查的计划项列表
		 * @return 是否存在conflict
		 */
	   public boolean checkLocationConflict(CheckLocationInterface thisCheckStrategy,List<? extends PlanningEntry<?>>   entries)
	   {
		   return thisCheckStrategy.checkLocationConflict(entries);
	   }
	   
	   /**
	    * 检测一组计划项之间是否存在资源独占冲突:
	    * 如果两个计划项在同一时间点上占用了同样的资源，那么就存在了资源冲突
	    * @param entries 计划项列表
	    * @return 是否有冲突
	    */
	   public boolean checkResourceExclusiveConflict(List<? extends PlanningEntry<?>> entries)
	   {
		   Class<?> cla = entries.get(0).getClass();
		   if(cla.equals(CourseEntry.class))
		   {
			   for(int i =0;i<entries.size();i++)
			   {
				   Resource thisResource = ((CourseEntry)entries.get(i)).getResource();
				   TimeSlot thisTime = ((CourseEntry)entries.get(i)).getSlot();
				   for(int j =0;j<entries.size();j++)
				   {
					   if(j != i)
					   {
						   CourseEntry compareEntry = (CourseEntry)(entries.get(j));
						  if(compareEntry.getResource().equals(thisResource) && compareEntry.getSlot().checkCoinOrNot(thisTime))
							  return true;
					   }
				   }
			   }
		   }
		   else if(cla.equals(FlightEntry.class))
		   {
			   for(int i =0;i<entries.size();i++)
			   {
				   Resource thisResource = ((FlightEntry)entries.get(i)).getResource();
				   TimeSlot thisTime = ((FlightEntry)entries.get(i)).getSlot();
				   for(int j =0;j<entries.size();j++)
				   {
					   if(j != i)
					   {
						   FlightEntry compareEntry = (FlightEntry)entries.get(j);
						  if(compareEntry.getResource().equals(thisResource) && compareEntry.getSlot().checkCoinOrNot(thisTime))
							  return true;
					   }
				   }
			   }
		   }
		   else if(cla.equals(TrainEntry.class))
		   {
			   for(int i =0;i<entries.size();i++)
			   {
				   TimeSlot thisTime = ((TrainEntry)entries.get(i)).getSlot();
				   for(Resource thisResource : ((TrainEntry)entries.get(i)).getResource())
				   {
					   for(int j = 0;j<entries.size();j++)
					   {
						   if(j != i)
						   {
							   TrainEntry compareEntry = (TrainEntry)entries.get(j);
							   if(compareEntry.getSlot().checkCoinOrNot(thisTime) && compareEntry.getResource().contains(thisResource))
								   return true;
						   }
					   }
				   }
			   }
		   }
			   
			   return false;
	   }
	   
	   /**
	    * 提取面向特定资源的前序计划项:
	    * 针对某个资源r和使用r的某个计划项 e，从一组计划项中找出e的前序f，
	    * f也使用资源r，f的执行时间在e之 前，且在e和f之间不存在使用资源r的其他计划项。
	    * 若不存在这样的计划项 f，则返回 null。
	    * 如果存在多个这样的 f，返回其中任意一个即可
	    * @param r 相应的资源
	    * @param e 相应的计划项
	    * @param entries 所有计划项的列表
	    * @return	entries中e的关于r的前序计划项
	    */
	   public  PlanningEntry<R> findPreEntryPerResource(R r,
		         PlanningEntry<R> e, List<? extends PlanningEntry<R>> entries)
	   {
		   PlanningEntry<R> resultEntry = null;
		   Class<?> cla = e.getClass();
		   if(cla.equals(CourseEntry.class))
		   {
			   if(!((CourseEntry)e).getResource().equals(r))
			   {
				   System.out.println("该计划项并不含有该资源！");
				   return null;
				   
			   }
			   else
			   {
				   TimeSlot thisTime = ((CourseEntry)e).getSlot();
				   for(PlanningEntry<R> compareEntry:entries)
				   {
					   if(!compareEntry.getName().equals(e.getName()))
					   {
						  Resource compareResource = ((CourseEntry)compareEntry).getResource();
						  TimeSlot compareTime = ((CourseEntry)compareEntry).getSlot();
						   if(compareResource.equals(r) &&  compareTime.getBeginTime().before(thisTime.getBeginTime()))
						   {
							   if(resultEntry == null)
								   resultEntry = compareEntry;
							   else
							   {
								   if(((CourseEntry)resultEntry).getSlot().getBeginTime().before(compareTime.getBeginTime()))
									   resultEntry = compareEntry;
							   }
						   }
					   }
				   }
			   }
		   }
		   else if(cla.equals(FlightEntry.class))
		   {
			   TimeSlot thisTime = ((FlightEntry)e).getSlot();
			   if(!((FlightEntry)e).getResource().equals(r))
			   {
				   System.out.println("该计划项并不含有该资源！");
				   return null;
			   }
			else {
				for (PlanningEntry<R> compareEntry : entries) {
					if (!compareEntry.getName().equals(e.getName())) {
						Resource compareResource = ((FlightEntry) compareEntry).getResource();
						TimeSlot compareTime = ((FlightEntry) compareEntry).getSlot();
						if (compareResource.equals(r)
								&& compareTime.getBeginTime().before(thisTime.getBeginTime())) {
							if (resultEntry == null)
								resultEntry = compareEntry;
							else {
								if (((FlightEntry) resultEntry).getSlot().getBeginTime()
										.before(compareTime.getBeginTime()))
									resultEntry = compareEntry;
							}
						}
					}
				}
			}
		   }
		   else if(cla.equals(TrainEntry.class))
		   {
			   TimeSlot thisTime = ((TrainEntry)e).getSlot();
//			   Resource thisResource = ((TrainEntry)e).get;
			   if(!((TrainEntry)e).getResource().contains(r))
			   {
				   System.out.println("该计划项并不含有该资源！");
				   return null;
			   }
			else {
			
				for (PlanningEntry<R> compareEntry : entries) {
					if (!compareEntry.getName().equals(e.getName())) {
						TimeSlot compareTime = ((TrainEntry) compareEntry).getSlot();
						List<? extends Resource> compareResource = ((TrainEntry) compareEntry).getResource();
						if (compareResource.contains(r) && compareTime.getBeginTime().before(thisTime.getBeginTime())) {
							if (resultEntry == null)
								resultEntry = compareEntry;
							else {
								if (((TrainEntry) resultEntry).getSlot().getBeginTime()
										.before(compareTime.getBeginTime()))
									resultEntry = compareEntry;
							}
						}
					}
				}
				
			}
		   }
		   return resultEntry;
	   }


}
