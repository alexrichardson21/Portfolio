package tree.binary;

import java.util.ArrayList;

class BinaryHeap {

	ArrayList<Integer> heap;

	BinaryHeap () {
		heap = new ArrayList<Integer>();
	}

	/*
	 * Continues to bubble values up the tree until we
	 * find a node that is greater than it
	 */
	public void bubbleUp (int index) {
		if (index == 0) {return;}

		int parent = getParent(index);

		if (heap.get(parent) < heap.get(index)) {
			Integer temp = heap.get(index);
			heap.set(index, heap.get(parent));
			heap.set(parent, temp);
			bubbleUp(parent);
		}

	}

	public void insert (Integer toInsert) {
		heap.add(toInsert);
		bubbleUp(heap.size() - 1);
	}

	// Traversal helpers
	public int getParent (int index) {
		return (index - 1) / 2;
	}

	public int getChild (int index, char child) {
		int result = (index * 2) + 1;
		if (child == 'R') {
			result++;
		}
		return result;
	}

	public void print () {
		for (int i = 0; i < heap.size(); i++) {
			System.out.println(heap.get(i));
		}
	}

	public ArrayList<Integer> getSortedElements () {
		ArrayList<Integer> temp = new ArrayList<Integer>(heap);
		for (int i = temp.size() - 1; i >= 0; i--) {
			int j = i;
			while (j != 0) {
				if (temp.get(i) > temp.get(getParent(i))) {
					Integer tempInt = temp.get(i);
					temp.set(i, temp.get(parent));
					temp.set(parent, tempInt);
					bubbleUp(parent);
					}

				}
			}
		}
		return temp;
	}

}