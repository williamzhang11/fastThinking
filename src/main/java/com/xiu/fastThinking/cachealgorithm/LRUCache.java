package com.xiu.fastThinking.cachealgorithm;

import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.print.attribute.Size2DSyntax;

public class LRUCache extends LinkedHashMap<String, String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int CACHE_SIZE;
	
	public LRUCache(int cacheSize) {
		
		//true表示让linkedhashmap 按照访问顺序进行排序，最近访问的放在头部，最老访问的放在尾部
		
		super(16, 0.75f, true);
		this.CACHE_SIZE = cacheSize;
	}

	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<String, String> eldest) {
		//当map中数量大于指定缓存个数时，自动删除最老数据
		return this.size() >CACHE_SIZE;
	}
	
	
	public static void main(String[] args) {
		
		LRUCache lruCache = new LRUCache(10);
		
		for(int i=0;i<20;i++) {
			
			lruCache.put("key_"+i, "value_"+i);
			System.out.println(lruCache);
		}
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
