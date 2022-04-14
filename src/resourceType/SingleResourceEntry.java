package resourceType;

import resource.Resource;

public interface SingleResourceEntry {

	

	/**
	 * 将对象管理的resource设为该resource
	 * @param thisresource,被管理的对象
	 */
	public boolean setResource(Resource thisresource);
	
	/**
	 * 返回对象管理的resource
	 * @return 被管理的对象
	 */
	public Resource getResource();
}
