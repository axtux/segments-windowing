package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Comparator;

import org.junit.Before;
import org.junit.Test;

import data.Heap;

public class HeapTests {
	ArrayList<Integer> ordered_asc;
	ArrayList<Integer> ordered_desc;
	Comparator<Integer> comparator;
	
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
		
		comparator = Integer::compare;
	}
	
	@Test
	public void reversedTest() {
		ArrayList<Integer> array = new ArrayList<Integer>(ordered_desc);
		testArray(array);
	}
	
	@Test
	public void orderedTest() {
		ArrayList<Integer> array = new ArrayList<Integer>(ordered_asc);
		testArray(array);
	}
	
	@Test
	public void unorderedTest() {
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
		
		testArray(array);
	}
	
	public void testArray(ArrayList<Integer> original) {
		ArrayList<Integer> copy;
		
		copy = new ArrayList<Integer>(original);
		Heap.heapify(copy, comparator);
		assertEquals("Max on top of heap", 10, copy.get(0).intValue());
		
		copy = new ArrayList<Integer>(original);
		Heap.heapify(copy, comparator.reversed());
		assertEquals("Min on top of reversed heap", 0, copy.get(0).intValue());
		
		copy = new ArrayList<Integer>(original);
		Heap.sortArray(copy);
		assertEquals("Sorted array (ascending)", ordered_asc, copy);
		
		copy = new ArrayList<Integer>(original);
		Heap.sortArray(copy, true);
		assertEquals("Sorted array (descending)", ordered_desc, copy);
		
		copy = new ArrayList<Integer>(original);
		Heap.sortArray(copy, comparator);
		assertEquals("Sorted array (ascending)", ordered_asc, copy);
		
		copy = new ArrayList<Integer>(original);
		Heap.sortArray(copy, comparator.reversed());
		assertEquals("Sorted array (descending)", ordered_desc, copy);
	}
	
	
}
