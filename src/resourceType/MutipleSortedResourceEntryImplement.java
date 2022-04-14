package resourceType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import resource.Resource;

public class MutipleSortedResourceEntryImplement implements MutipleSortedResourceEntry{
//mutable
	private List<? extends Resource> thisResource;
	
	// Abstraction function:
    // 	AF(thisResource) = the list of the resources being controlled by this object 
    // Representation invariant:
    //	the field must be non-null,and all the objects in thisResource must be non-null
    // Safety from rep exposure:
    //  the field is private 
	//	the Observer return an unmodifiable collection

	//checkrep
	private void checkRep()
	{
		assert thisResource != null;
		for(Resource thisRes:thisResource)
		{
			assert thisRes != null;
		}
	}
	
	@Override
	public boolean  setResource(List<? extends Resource> thisresource) {
		thisResource =new ArrayList<>(thisresource);
		checkRep();
		return true;
	}
	
	@Override
	public List<? extends Resource> getResource() {
		return Collections.unmodifiableList(thisResource);
	}
}
