package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import data.Heap;

public class HeapTests {
	ArrayList<Integer> ordered_asc;
	ArrayList<Integer> ordered_desc;
	
	@Before
	public void initArrays() {
		ordered_asc = new ArrayList<Integer>();
		ordered_asc.add(new Integer(0));
		ordered_asc.add(new Integer(1));
		ordered_asc.add(new Integer(2));
		ordered_asc.add(new Integer(3));
		ordered_asc.add(new Integer(4));
		ordered_asc.add(new Integer(5));
		ordered_asc.add(new Integer(6));
		ordered_asc.add(new Integer(7));
		ordered_asc.add(new Integer(8));
		ordered_asc.add(new Integer(9));
		ordered_asc.add(new Integer(10));
		
		ordered_desc = new ArrayList<Integer>();
		ordered_desc.add(new Integer(10));
		ordered_desc.add(new Integer(9));
		ordered_desc.add(new Integer(8));
		ordered_desc.add(new Integer(7));
		ordered_desc.add(new Integer(6));
		ordered_desc.add(new Integer(5));
		ordered_desc.add(new Integer(4));
		ordered_desc.add(new Integer(3));
		ordered_desc.add(new Integer(2));
		ordered_desc.add(new Integer(1));
		ordered_desc.add(new Integer(0));
	}
	
	@Test
	public void reversedTest() {
		ArrayList<Integer> array = new ArrayList<Integer>();
		array.add(new Integer(10));
		array.add(new Integer(9));
		array.add(new Integer(8));
		array.add(new Integer(7));
		array.add(new Integer(6));
		array.add(new Integer(5));
		array.add(new Integer(4));
		array.add(new Integer(3));
		array.add(new Integer(2));
		array.add(new Integer(1));
		array.add(new Integer(0));
		
		ArrayList<Integer> heapMax = new ArrayList<Integer>(array);
		Heap.heapify(heapMax);
		assertEquals("Max on top", 10, heapMax.get(0).intValue());
		
		ArrayList<Integer> heapMin = new ArrayList<Integer>(array);
		Heap.heapify(heapMin, false);
		assertEquals("Min on top", 0, heapMin.get(0).intValue());
		
		ArrayList<Integer> asc = new ArrayList<Integer>(array);
		Heap.sortArray(asc);
		assertEquals("Sorted array (ascending)", ordered_asc, asc);
		
		ArrayList<Integer> desc = new ArrayList<Integer>(array);
		Heap.sortArray(desc, false);
		assertEquals("Sorted array (descending)", ordered_desc, desc);
	}
	
	@Test
	public void orderedTest() {
		ArrayList<Integer> array = new ArrayList<Integer>();
		array.add(new Integer(0));
		array.add(new Integer(1));
		array.add(new Integer(2));
		array.add(new Integer(3));
		array.add(new Integer(4));
		array.add(new Integer(5));
		array.add(new Integer(6));
		array.add(new Integer(7));
		array.add(new Integer(8));
		array.add(new Integer(9));
		array.add(new Integer(10));
		
		ArrayList<Integer> heapMax = new ArrayList<Integer>(array);
		Heap.heapify(heapMax);
		assertEquals("Max on top", 10, heapMax.get(0).intValue());
		
		ArrayList<Integer> heapMin = new ArrayList<Integer>(array);
		Heap.heapify(heapMin, false);
		assertEquals("Min on top", 0, heapMin.get(0).intValue());
		
		ArrayList<Integer> asc = new ArrayList<Integer>(array);
		Heap.sortArray(asc);
		assertEquals("Sorted array (ascending)", ordered_asc, asc);
		
		ArrayList<Integer> desc = new ArrayList<Integer>(array);
		Heap.sortArray(desc, false);
		assertEquals("Sorted array (descending)", ordered_desc, desc);
	}
	
	@Test
	public void randomTest() {
		ArrayList<Integer> array = new ArrayList<Integer>();
		array.add(new Integer(2));
		array.add(new Integer(4));
		array.add(new Integer(6));
		array.add(new Integer(8));
		array.add(new Integer(0));
		array.add(new Integer(7));
		array.add(new Integer(10));
		array.add(new Integer(5));
		array.add(new Integer(3));
		array.add(new Integer(1));
		array.add(new Integer(9));
		
		ArrayList<Integer> heapMax = new ArrayList<Integer>(array);
		Heap.heapify(heapMax);
		assertEquals("Max on top", 10, heapMax.get(0).intValue());
		
		ArrayList<Integer> heapMin = new ArrayList<Integer>(array);
		Heap.heapify(heapMin, false);
		assertEquals("Min on top", 0, heapMin.get(0).intValue());
		
		ArrayList<Integer> asc = new ArrayList<Integer>(array);
		Heap.sortArray(asc);
		assertEquals("Sorted array (ascending)", ordered_asc, asc);
		
		ArrayList<Integer> desc = new ArrayList<Integer>(array);
		Heap.sortArray(desc, false);
		assertEquals("Sorted array (descending)", ordered_desc, desc);
	}
}
