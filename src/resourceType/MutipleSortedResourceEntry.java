package resourceType;

import java.util.List;

import resource.Resource;

public interface MutipleSortedResourceEntry {

	/**
	 * 设置被该对象管理的资源序列
	 * @param thisresource ,被管理的资源序列
	 */
	public boolean setResource(List<? extends Resource> thisresource);
	
	/**
	 * 获得被该对象管理的资源序列
	 * @return 被管理的资源序列
	 */
	public List<? extends Resource> getResource();
}
