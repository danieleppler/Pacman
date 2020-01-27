package Data_Structure;

public class HeapMin {

    double _positiveInfinity = Double.POSITIVE_INFINITY;
    final int INIT_SIZE = 10;
    public Vertex _a[];
    private int _size;

    public HeapMin(Vertex arr[]) {
        _size = arr.length;
        _a = new Vertex[_size];
        for (int i = 0; i < _size; i++) {
            _a[i] = arr[i];
        }
    }

    public HeapMin() {
        _a = new Vertex[0];
    }

    /**
     * returns the heap size
     */
    public int getSize() {
        return _size;
    }

    /**
     * returns the heap array
     */
    public Vertex[] getA() {
        return _a;
    }

    /**
     * parent returns the parent of tag  i
     */
    private int parent(int i) {
        return (i - 1) / 2;
    }

    /**
     * leftChild returns the left child of tag  i
     */
    private int leftChild(int i) {
        return 2 * i + 1;
    }

    /**
     * rightChild returns the right child of tag  i
     */
    private int rightChild(int i) {
        return 2 * i + 2;
    }

    /**
     * returns the heap minimum
     */
    public Vertex heapMinimum() {
        return _a[0];
    }

    /**
     * returns true if the heap is empty, otherwise false
     */
    public boolean isEmpty() {
        boolean ans = false;
        if (_size == 0) ans = true;
        return ans;
    }

    /**
     * the minHeapfy function maintains the min-heap property
     */
    private void minHeapify(int v, int heapSize) {
        int smallest;
        int left = leftChild(v);
        int right = rightChild(v);
        if (left < heapSize && _a[left].minDistance < _a[v].minDistance) {
            smallest = left;
        } else {
            smallest = v;
        }
        if (right < heapSize && _a[right].minDistance < _a[smallest].minDistance) {
            smallest = right;
        }
        if (smallest != v) {
            exchange(v, smallest);
            minHeapify(smallest, heapSize);
        }
    }

    /**
     * the heap minimum element extraction
     */
    public Vertex heapExtractMin() {
        double min = _positiveInfinity;
        Vertex v = null;
        if (!isEmpty()) {
            v = _a[0];
            //min = v.minDistance;
            _a[0] = _a[_size - 1];
            _size = _size - 1;
            minHeapify(0, _size);
        }
        return v;
    }

    /**
     * the heapDecreaseKey implements the Decrease Key operation
     */
    public void heapDecreaseKey(Vertex node) {
        int v = node.tag;
        int i = 0;
        while (i < _size && v != _a[i].tag) i++;
        if (node.minDistance < _a[i].minDistance) {
            _a[i] = node;
            while (i > 0 && _a[parent(i)].minDistance > _a[i].minDistance) {
                exchange(i, parent(i));
                i = parent(i);
            }
        }
    }

    /**
     * minHeapInsert function implements the Insert-Key operation
     */
    public void minHeapInsert(Vertex node) {
        resize(1);
        Vertex v=new Vertex(node);
        _a[_size - 1] = v;
        _a[_size - 1].minDistance = _positiveInfinity;
        heapDecreaseKey(v);
    }

    /**
     * increment an array
     */
    public void resize(int increment) {
        Vertex temp[] = new Vertex[_size + increment];
        for (int i = 0; i < _size; i++) {
            temp[i] = _a[i];
        }
        _a = temp;
        _size = _size + increment;
    }

    /**
     * exchange two array elements
     */
    private void exchange(int i, int j) {
        Vertex t = _a[i];
        _a[i] = _a[j];
        _a[j] = t;
    }
    /*	*/

    /**
     * print a heap array
     **//*
	public void print(){
		for (int i=0; i<_size; i++){
			System.out.print(_a[i]+"; ");
		}
		System.out.println();
	}*/
    public boolean contains(int tag) {
        boolean ans = false;
        for (int i = 0; !ans && i < _size; i++) {
            if (_a[i].tag == tag) ans = true;
        }
        return ans;
    }
}
