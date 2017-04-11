package data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class Array<E> extends ArrayList<E> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Keep all ArraList constructors
	 */
	public Array() {
		super();
	}
	public Array(Collection<? extends E> c) {
		super(c);
	}
	public Array(int initialCapacity) {
		super(initialCapacity);
	}
	/**
	 * Return a sub array of this array from inclusive fromIndex to exclusive toIndex.
	 * @param fromIndex Inclusive starting index.
	 * @param toIndex Exclusive ending index.
	 * @return Sub array.
	 */
	public Array<E> subArray(int fromIndex, int toIndex) {
		if(fromIndex < 0) fromIndex = 0;
		if(toIndex > size()) toIndex = size();
		if(fromIndex >= toIndex) return new Array<E>(0);
		
		Array<E> sub = new Array<E>(toIndex-fromIndex);
		for(int i = fromIndex; i < toIndex; ++i) {
			sub.add(get(i));
		}
		return sub;
	}
	/**
	 * Copy array.
	 * @return Newly create copy of this array.
	 */
	public Array<E> copy() {
		return subArray(0, size());
	}
	/**
	 * Sort array using heap sort.
	 */
	public void sort(Comparator<? super E> comparator) {
		Heap.sortArray(this, comparator);
	}
}
