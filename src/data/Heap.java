package data;

import java.util.ArrayList;
import java.util.Comparator;
/**
 * Create and manage heap into an ArrayList.
 * Heap nodes are stores into an array using breadth-first search.
 * Counting indexes from 1, left child index is 2i, right child index is 2i+1 and father index is i/2 using integer division.
 */
public class Heap {
	/**
	 * Make a heap into given array.
	 * @param array Original array which will be reordered to become a heap.
	 * @param comparator Comparator used to define elements order.
	 * @param <E> Class contained into array and comparable using comparator.
	 */
	public static <E> void heapify(ArrayList<E> array, Comparator<E> comparator) {
		int size = array.size();
		for(int i = father(size-1); i >= 0; --i) {
			heapify(array, comparator, i, size);
		}
	}
	/**
	 * Make size-sized heap into array considering array is already a heap except for element i. 
	 * @param array Heap array.
	 * @param comparator Comparator used to define elements order.
	 * @param i Element which could be at wrong place in the heap.
	 * @param size Size of the heap (array size won't be used here).
	 * @param <E> Class contained into array and comparable using comparator.
	 */
	private static <E> void heapify(ArrayList<E> array, Comparator<E> comparator, int i, int size) {
		int max = i;
		int left = left(i);
		int right = right(i);
		
		if(left < size && comparator.compare(array.get(left), array.get(max)) > 0) {
			max = left;
		}
		if(right < size && comparator.compare(array.get(right), array.get(max)) > 0) {
			max = right;
		}
		
		if(max != i) {
			E tmp = array.get(i);
			array.set(i, array.get(max));
			array.set(max, tmp);
			heapify(array, comparator, max, size);
		}
	}
	/**
	 * Sort array using heap sort in ascending order.
	 * @param array Array to sort.
	 * @param <E> Comparable class.
	 */
	public static <E extends Comparable<E>> void sortArray(ArrayList<E> array) {
		sortArray(array, false);
	}
	/**
	 * Sort array using heap sort.
	 * @param array Array to sort.
	 * @param reverse If false, sort using ascending order. If true, sort using descending order.
	 * @param <E> Comparable class.
	 */
	public static <E extends Comparable<E>> void sortArray(ArrayList<E> array, boolean reverse) {
		Comparator<E> comparator = E::compareTo;
		if(reverse) {
			comparator = comparator.reversed();
		}
		sortArray(array, comparator);
	}
	/**
	 * Sort array using heap sort.
	 * @param array Array to sort.
	 * @param comparator Comparator used to define elements order.
	 * @param <E> Class contained into array and comparable using comparator.
	 */
	public static <E> void sortArray(ArrayList<E> array, Comparator<E> comparator) {
		heapify(array, comparator);
		
		E last;
		for(int i = array.size()-1; i > 0; --i) {
			last = array.get(i);
			// set maximum (minimum if asc_order is false) at the end of array
			array.set(i, array.get(0));
			// replace first by last and heapify
			array.set(0, last);
			heapify(array, comparator, 0, i);
		}
		
	}
	/**
	 * Considering an 0-indexed array as a binary tree, get father index of element i.
	 * @param i Element index.
	 * @return Index of father element.
	 */
	public static int father(int i) {
		return (i+1)/2 -1;
	}
	/**
	 * Considering an 0-indexed array as a binary tree, get left child index of element i.
	 * @param i Element index.
	 * @return Index of left child element.
	 */
	public static int left(int i) {
		return 2*(i+1) -1;
	}
	/**
	 * Considering an 0-indexed array as a binary tree, get right child index of element i.
	 * @param i Element index.
	 * @return Index of right child element.
	 */
	public static int right(int i) {
		return 2*(i+1)+1 -1;
	}
}
