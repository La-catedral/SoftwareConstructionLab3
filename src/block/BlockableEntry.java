package block;


import java.util.Calendar;



public interface BlockableEntry {

	/**
	 * block the entry within a timeSlot
	 * @param atTime,the timeSlot that the entry should be blocked
	 */
	public boolean blockTheEntry(Calendar atTime);
	

	
	/**
	 * restart the entry
	 * @param atThisTime,the time that the entry will be restarted 
	 */
	public boolean restart(Calendar atThisTime);
}
