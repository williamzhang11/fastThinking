package com.xiu.fastThinking.cachealgorithm;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FIFOCache {

	private Map<String, String> cache;
	
	private Integer size;
	
	private Deque<String> keyQueue;
	
	public FIFOCache(Integer size) {
		cache = new ConcurrentHashMap<String, String>(size);
		this.size = size;
		keyQueue = new LinkedList<String>();
	}
	
	
	public void putValue(String key,String value) {
		cycleKeyQueue(key);
		cache.putIfAbsent(key, value);
	}
	
	public void delValue(String key,String value) {
		
		cache.remove(key, value);
		keyQueue.remove(key);
	}
	
	public void getValue(String key) {
		cache.get(key);
	}
	
	private void cycleKeyQueue(String key) {
		
		keyQueue.addLast(key);
		
		if(keyQueue.size() > size) {
			
			String first = keyQueue.removeFirst();
			cache.remove(first);
		}
	}
	
	public String keyQueue() {
		
		return String.join(",", keyQueue);
	}
	
	public static void main(String[] args) {
		FIFOCache fifoCache = new FIFOCache(10);
		
		for(int i=0;i<20;i++) {
			fifoCache.putValue("key_"+i, "value_"+i);
			System.out.println(fifoCache.keyQueue());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
