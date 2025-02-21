public class QuickSort {
    public static int[] sort(int[] input)
    {
        quickSort(input, 0, input.length-1);
        return input;
    }

    public static void quickSort(int[] input, int p, int r)
    {
        if (p < r)
        {
            int q = partition(input, p, r);
            quickSort(input, p, q-1);
            quickSort(input, q+1, r);
        }
    }

    public static int partition(int[] input, int p, int r)
    {
        int x = input[r];
        int i = p - 1;
        for (int j = p; j < r; j++)
        {
            if (input[j] <= x)
            {
                i++;
                swap(input, i, j);
            }
        }
        swap(input, i + 1, r);
        return i + 1;
    }

    public static void swap(int[] input, int i, int j)
    {
        int temp = input[i];
        input[i] = input[j];
        input[j] = temp;
    }
}