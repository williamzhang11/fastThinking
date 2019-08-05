package com.xiu.fastThinking.cachepenetrate;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;

import junit.framework.Assert;

public class BloomFilterTest {

	public static void main(String[] args) {
		
		long star = System.currentTimeMillis();
		BloomFilter<Integer> filter = BloomFilter.create(
				Funnels.integerFunnel(),
				10000000, 0.01);
		for(int i=0;i<10000000;i++) {
			filter.put(i);
		}
		
		System.out.println(filter.mightContain(1));
		System.out.println(filter.mightContain(100));
		System.out.println(filter.mightContain(999999219));
		
		long end = System.currentTimeMillis();
	    System.out.println("执行时间：" + (end - star));
		
	}
}
