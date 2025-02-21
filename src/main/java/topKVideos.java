import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class topKVideos
{
    public static final Map<String, int[]> textfiles = new LinkedHashMap<>();

    static {

        textfiles.put("int10", getInputFromText("txtFiles/int10.txt")); 
        textfiles.put("int50", getInputFromText("txtFiles/int50.txt")); 
        textfiles.put("int100", getInputFromText("txtFiles/int100.txt")); 
        textfiles.put("int1000", getInputFromText("txtFiles/int1000.txt")); 
        textfiles.put("bad", getInputFromText("txtFiles/bad.txt")); 
        textfiles.put("int20k", getInputFromText("txtFiles/int20k.txt")); 
        textfiles.put("dutch", getInputFromText("txtFiles/dutch.txt")); 
        textfiles.put("int500k", getInputFromText("txtFiles/int500k.txt")); 
        textfiles.put("intBig", getInputFromText("txtFiles/intBig.txt")); 
    }
    public static void main(String[] arg)
    {
        int k = 10; // Number of top views to get
        MinHeap minHeap = new MinHeap(k);
        for (int viewCount : textfiles.get("int10")) {
            System.out.println(viewCount);
            minHeap.insert(viewCount);
        }
        int[] topKViews = new int[k];
        System.out.println("Heap contents before removal: " + Arrays.toString(minHeap.getHeapElements()));

if (minHeap.getHeapElements().length < k) {
    throw new IllegalStateException("Not enough elements in heap to retrieve top " + k);
}
        System.out.println("Top " + k + " most viewed videos:");
        for (int i  = 0; i < k; i++) {     
            topKViews[i] = minHeap.removeMin();
            System.out.println(topKViews[i]);
        }
    }

        public static int[] getInputFromText(String filename)
    {
        List<Integer> list = new ArrayList<Integer>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(Integer.parseInt(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int[] input = new int[list.size()];
        for (int i = 0; i < input.length; i++)
        {
            input[i] = list.get(i);
        }
        return input;
    }
}