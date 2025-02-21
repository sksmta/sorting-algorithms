import java.util.Arrays;

public class MinHeap {
    private int[] heap;
    private int size;
    private int maxSize;

    public MinHeap(int maxSize)
    {
        this.maxSize = maxSize;
        this.size = 0;
        heap = new int[this.maxSize];
    }


    private int parent(int pos) { return (pos - 1) / 2; }
    private int leftChild(int pos) { return (2 * pos) + 1; }
    private int rightChild(int pos) { return (2 * pos) + 2; }

    public void insert(int element) {
        if (size < maxSize) {
            heap[size] = element;
            heapifyUp(size);
            size++;
        } else if (element > heap[0]) {
            heap[0] = element;
            heapifyDown(0);
        }
    }
    

    public int removeMin()
    {
        if (size == 0) throw new IllegalStateException("Head is empty");
        int min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        return min;
    }

    private void heapifyUp(int index)
    {
        while (index > 0 && heap[parent(index)] > heap[index])
        {
            swap(heap, index, parent(index));
            index = parent(index);
        }
    }

    private void heapifyDown(int index)
    {
        int smallest = index;
        int left = leftChild(smallest);
        int right = rightChild(smallest);
        if (left < size && heap[left] < heap[smallest])
        {
            smallest = left;
        }
        if (right < size && heap[right] < heap[smallest])
        {
            smallest = right;
        }
        if (smallest != index)
        {
            swap(heap, index, smallest);
            heapifyDown(smallest);
        }
    }

    public void swap(int[] arr, int i, int j)
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public int[] getHeapElements()
    {
        return Arrays.copyOf(heap, size);
    }
}