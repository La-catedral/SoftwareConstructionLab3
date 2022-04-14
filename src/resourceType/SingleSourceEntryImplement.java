package resourceType;

import resource.Resource;

public class SingleSourceEntryImplement implements SingleResourceEntry{
//mutable
	private Resource thisResource;

	// Abstraction function:
    // 	AF(thisResource) = the resource being controlled by this object 
    // Representation invariant:
    //	the field must be non-null
    // Safety from rep exposure:
    //  the field is private 
	//	the Observer return an immutable object

	//checkRep
	private void checkRep()
	{
		assert thisResource != null;
	}
	
	@Override
	public boolean setResource(Resource thisresource) {
		this.thisResource = thisresource;	
		checkRep();
		return true;
	}
	
	@Override
	public Resource getResource() {
		return thisResource;
	}
	
}
