package timeslot;

public class SingleSlotimple implements SingleSlot{
//mutable
	private TimeSlot singleSlot;

	// Abstraction function:
    // 	AF(singleSlot) = the timeSlot by the object  
    // Representation invariant:
    //	the field must be non-null
    // Safety from rep exposure:
    //  the field is private 
	//	the Observer return an immtable object
	
	//checkrep
	private void checkRep()
	{
		assert singleSlot != null;
	}
	
	@Override
	public void setSlot(TimeSlot timeSlot)
	{
		this.singleSlot = timeSlot;
		checkRep();
	}
	
	@Override
		public TimeSlot getSlot() {
			return singleSlot;
		}
	
	
}
