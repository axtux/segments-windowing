package data;

import java.util.ArrayList;

public class Heap {
	/**
	 * Heapify array with maximum on top.
	 * @param array Original array that will be modified to become a heap.
	 */
	public static <E extends Comparable<E>> void heapify(ArrayList<E> array) {
		heapify(array, true);
	}
	/**
	 * Heapify array.
	 * @param array Original array that will be modified to become a heap.
	 * @param useMax If true, maximum will be on top. If false, minimum will be.
	 */
	public static <E extends Comparable<E>> void heapify(ArrayList<E> array, boolean useMax) {
		int size = array.size();
		for(int i = Father(size-1); i >= 0; --i) {
			heapify(array, i, size, useMax);
		}
	}
	/**
	 * Make size-sized heap from array considering array is already a heap except for element i. 
	 * @param array Heap array.
	 * @param i Element that could be at wrong place in the heap.
	 * @param size Size of the heap (array size won't be used here).
	 * @param useMax If true, maximum will be on top. If false, minimum will be.
	 */
	private static <E extends Comparable<E>> void heapify(ArrayList<E> array, int i, int size, boolean useMax) {
		int max = i;
		int left = Left(i);
		int right = Right(i);
		// m change comparison result
		int m = useMax ? 1 : -1;
		
		if(left < size && array.get(left).compareTo(array.get(max))*m > 0) {
			max = left;
		}
		if(right < size && array.get(right).compareTo(array.get(max))*m > 0) {
			max = right;
		}
		
		if(max != i) {
			E tmp = array.get(i);
			array.set(i, array.get(max));
			array.set(max, tmp);
			heapify(array, max, size, useMax);
		}
	}
	/**
	 * Sort array using heap sort in ascending order.
	 * @param array Array to sort.
	 */
	public static <E extends Comparable<E>> void sortArray(ArrayList<E> array) {
		sortArray(array, true);
	}
	/**
	 * Sort array using heap sort.
	 * @param array Array to sort.
	 * @param asc_order If true, ascending order will be use. If false, descending order will be.
	 */
	public static <E extends Comparable<E>> void sortArray(ArrayList<E> array, boolean asc_order) {
		heapify(array, asc_order);
		
		E last;
		for(int i = array.size()-1; i > 0; --i) {
			last = array.get(i);
			// set maximum (minimum if asc_order is false) at the end of array
			array.set(i, array.get(0));
			// replace first by last and heapify
			array.set(0, last);
			heapify(array, 0, i, asc_order);
		}
		
	}
	/**
	 * Considering an 0-indexed array as a tree, get father index of element i.
	 * @param i Element index.
	 * @return Index of father element.
	 */
	private static int Father(int i) {
		return (i+1)/2 -1;
	}
	/**
	 * Considering an 0-indexed array as a tree, get left child index of element i.
	 * @param i Element index.
	 * @return Index of left child element.
	 */
	private static int Left(int i) {
		return 2*(i+1) -1;
	}
	/**
	 * Considering an 0-indexed array as a tree, get right child index of element i.
	 * @param i Element index.
	 * @return Index of right child element.
	 */
	private static int Right(int i) {
		return 2*(i+1)+1 -1;
	}
}
