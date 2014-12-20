package com.lokesh.airline.core;

/**
 * 
 * A flight queue class supporting sundry operations needed for Dijkstra's
 * algorithm.
 * 
 * @author Lokesh Lavangale
 * 
 * @param <T>
 */
public class FlightQueue<T> {

	// Record holds a key and value for each record in the queue
	private Record<T>[] queue;

	// size is the size of the queue (including the null element at queue[0]
	private int size;

	public FlightQueue() {
		// create an array of size 1
		queue = new Record[1];
		size = 1;
	}

	/**
	 * Return true if the queue is empty.
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		// if size is 1, the array contains only the null element at queue[0]
		if (size == 1)
			return true;
		else
			return false;
	}

	/**
	 * Insert a pair (key, value) into the queue, and return a Handle to this
	 * pair so that we can find it later in constant time.
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Handle insert(int key, T value) {
		// if Q is full, double its size
		if (queue.length == size) {
			Record<T>[] temp = queue;
			queue = new Record[size * 2];
			for (int j = 0; j < size; j++) {
				queue[j] = temp[j];
			}
		}

		// Insertion starts at the bottom of the heap and works its way up,
		// trying to find the right place to insert the new key k.
		int i = size;
		Handle h = new Handle(i);
		queue[i] = new Record<T>(key, value, h);
		int halfIndx = (int) Math.floor(i / 2);
		if (halfIndx <= 1)
			halfIndx = 1;
		// If the key queue[i] is smaller than the new key k, move queue[i]
		// below k.
		while (i > 1 && key < queue[halfIndx].getKey()) {
			swap(i, halfIndx);
			i = (int) Math.floor(i / 2);
			halfIndx = (int) Math.floor(i / 2);
			if (halfIndx <= 1)
				halfIndx = 1;
		}

		// the queue is one element larger so increase its size
		size++;

		return h;
	}

	/**
	 * Return the smallest key in the queue.
	 * 
	 * @return
	 */
	public int min() {
		// queue[1] will always have the smallest key
		if (isEmpty())
			return 0;
		else
			return queue[1].getKey();
	}

	/**
	 * Extract the (key, value) pair associated with the smallest key in the
	 * queue and return its "value" object.
	 * 
	 * @return
	 */
	public T extractMin() {
		if (!isEmpty()) {
			// queue[1] has the smallest key
			T value = queue[1].getValue();
			// since you're removing it set its handle to point to the null
			// element
			queue[1].getHandle().setIndex(0);

			// take the last element in the queue, put it at the top, and call
			// heapify starting at top
			queue[1] = queue[size - 1];
			queue[1].getHandle().setIndex(1);
			size--;
			heapify(1);
			return value;
		} else
			return null;
	}

	/**
	 * 
	 * @param i
	 */
	private void heapify(int i) {
		// if i is not a leaf
		if (i <= (int) Math.floor(size / 2)) {
			// find the child of queue[i] with the smallest key
			int j;
			if (size < 2 * i + 1 || queue[2 * i].getKey() < queue[2 * i + 1].getKey()) {
				j = 2 * i;
			} else {
				j = 2 * i + 1;
			}
			// if queue[i].key is larger than either of its childs' keys swap
			// queue[i]
			// with its smallest child and heapify from that point
			if (queue[j].getKey() < queue[i].getKey()) {
				swap(i, j);
				heapify(j);
			}
		}
	}

	/**
	 * Look at the (key, value) pair referenced by Handle h. If that pair is no
	 * longer in the queue, or its key is <= newkey, do nothing and return
	 * false. Otherwise, replace "key" by "newkey", fixup the queue, and return
	 * true.
	 * 
	 * @param h
	 * @param newkey
	 * @return
	 */
	public boolean decreaseKey(Handle h, int newkey) {
		// check that h doesn't refer to a removed key

		if (queue[h.getIndex()] == null)
			return false;
		if (h.getIndex() > size)
			return false;
		// check that the newkey is smaller than the old key
		if (newkey > queue[h.getIndex()].getKey())
			return false;

		// decrease works like insert key except rather than starting at the
		// bottom of the queue, it start at h's index
		int i = h.getIndex();
		queue[i].setKey(newkey);

		int halfIndx = (int) Math.floor(i / 2);

		while (i > 1 && newkey < queue[halfIndx].getKey()) {
			swap(i, halfIndx);
			i = (int) Math.floor(i / 2);
			halfIndx = (int) Math.floor(i / 2);
			if (halfIndx <= 1)
				halfIndx = 1;
		}

		h.setIndex(i);

		if (newkey == queue[h.getIndex()].getKey())
			return true;
		return false;
	}

	/**
	 * 
	 * Get the key of the (key, value) pair associated with a given Handle.
	 * (This result is undefined if the handle no longer refers to a pair in the
	 * queue.)
	 * 
	 * 
	 * @param h
	 * @return
	 */
	public int handleGetKey(Handle h) {
		return queue[h.getIndex()].getKey();
	}

	/**
	 * 
	 * Get the value object of the (key, value) pair associated with a given
	 * Handle. (This result is undefined if the handle no longer refers to a
	 * pair in the queue.)
	 * 
	 * 
	 * @param h
	 * @return
	 */
	public T handleGetValue(Handle h) {
		if (queue[h.getIndex()] == null)
			return null;
		return queue[h.getIndex()].getValue();
	}

	/**
	 * Print every element of the queue in the order in which it appears in the
	 * implementation (i.e. the array representing the heap).
	 */
	public String toString() {
		String str = "";
		for (int i = 1; i < size; i++) {
			str = str + " " + queue[i].getValue();
		}
		return str;
	}

	/**
	 * the swap method swaps the two queue elements at the given indices and
	 * updates their handles
	 * 
	 * @param i1
	 * @param i2
	 */
	private void swap(int i1, int i2) {
		Record<T> temp = queue[i1];
		queue[i1] = queue[i2];
		queue[i2] = temp;
		queue[i1].getHandle().setIndex(i1);
		queue[i2].getHandle().setIndex(i2);
	}
}
