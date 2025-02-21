import java.util.function.Function;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class TestSortingAlgorithms
{
    public static final Map<String, Function<int[], int[]>> funcs = new LinkedHashMap<>();
    public static final Map<String, int[]> textfiles = new LinkedHashMap<>();
 static {
        funcs.put("Insertion sort", InsertionSort::sort); 
        funcs.put("Selection sort", SelectionSort::sort); 
        funcs.put("Shell sort", ShellSort::sort); 
        funcs.put("Merge sort", MergeSort::sort); 
        funcs.put("Quick sort", QuickSort::sort);

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

    public static void main(String[] args)
    {
        for (String funcName : funcs.keySet())
        {
            testAlgorithmTimesWithAllTextFiles(funcName, 10);

            saveAlgorithmTimesToCSV(funcName, 10);
        }
    }

    public static boolean testAlgorithms(int[] input)
    {
        for (String funcName : funcs.keySet())
        {
            System.out.println(funcName);
            boolean isSorted = testSortingAlgorithm(funcs.get(funcName), input.clone(), false);
            System.out.println("Is sorted: " + isSorted);
            System.out.println("____________________________________________________________________\n");
            if (!isSorted) return false;
        }
        return true;
    }

    public static void testTimes(Map<String, int[]> textfiles)
    {
        for (String textfile : textfiles.keySet())
        {
            System.out.println("Testing for " + textfile + "\n");
            for (String funcName : funcs.keySet())
            {
                long algDuration = timeAlgorithm(funcs.get(funcName), textfiles.get(textfile).clone(), false);
                System.out.println(funcName + " took " + algDuration + "ns or " + algDuration / 1_000_000 + "ms to sort");
            }
            System.out.println("\n____________________________________________________________________\n\n");
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

    private static boolean isSorted(int[] input, int[] result) {
        Arrays.sort(input);
        try
        {
            for (int i = 0; i < input.length - 1; i++) {
                if (input[i] != result[i]) {
                    return false;
                }
            }
        } catch(Exception e) { return false; }
        return true;
    }

    public static boolean testSortingAlgorithm(Function<int[], int[]> func, int[] input, boolean print)
    {
        int[] result = func.apply(Arrays.copyOf(input, input.length));
        if (print)
        {
            System.out.println("Input:  " + Arrays.toString(input));
            System.out.println("Result: " + Arrays.toString(result));
        }
        return isSorted(input, result);
    }

    public static long timeAlgorithm(Function<int[], int[]> func, int[] input, boolean print)
    {
        long startTime = System.nanoTime();
        if (print) { System.out.println("Start Time: " + startTime); }
        func.apply(Arrays.copyOf(input, input.length));
        long endTime = System.nanoTime();
        if (print) { System.out.println("End Time: " + endTime); }
        return endTime - startTime;
    }

    public static void swap(int[] input, int i, int j)
    {
        int temp = input[i];
        input[i] = input[j];
        input[j] = temp;
    }

    public static long testAlgorithmTimesWithTextFile(String funcName, String filename, int repeats)
    {
        int[] input = textfiles.get(filename);
        List<Long> times = new ArrayList<Long>();
        long totalTime = 0;
        for (int i = 0; i < repeats + 2; i++)
        {
            if (i > 1)
            {
                System.out.println("Test " + (i - 1) + " for " + filename);
            }
            long timeTaken = timeAlgorithm(funcs.get(funcName), input, false);
            if (i > 1)
            {
                System.out.println("Time taken: " + timeTaken + "ns or " + timeTaken / 1_000_000 + "ms");
                times.add(timeTaken);
                totalTime += timeTaken;
            }
        }
        saveTimesToFile("times/" + funcName.replace(" ", "_") + "_" + filename + ".txt", times, funcName);
        System.out.println("Average time taken: " + totalTime / repeats + "ns or " + totalTime / repeats / 1_000_000 + "ms");
        System.out.println("\n____________________________________________________________________\n\n");
        return totalTime / repeats;
    }

    public static void testAllAlgorithmTimesWithTextFile(String filename, int repeats)
    {
        List<Long> times = new ArrayList<Long>();
        for (String funcName : funcs.keySet())
        {
            System.out.println("Testing for " + funcName);
            times.add(testAlgorithmTimesWithTextFile(funcName, filename, repeats));
        }
        System.out.println("Average times for " + filename + " with " + repeats + " repeats");
        for (int i = 0; i < times.size(); i++)
        {
            System.out.println(funcs.keySet().toArray()[i] + " took " + times.get(i) + "ns or " + times.get(i) / 1_000_000 + "ms to sort");
        }
    }

    public static void testAlgorithmTimesWithAllTextFiles(String funcName, int repeats)
    {
        List<Long> times = new ArrayList<Long>();
        for (String textfile : textfiles.keySet())
        {
            System.out.println("Testing for " + textfile);
            times.add(testAlgorithmTimesWithTextFile(funcName, textfile, repeats));
        }
        System.out.println("Average times for " + funcName + " with " + repeats + " repeats");
        for (int i = 0; i < times.size(); i++)
        {
            System.out.println(textfiles.keySet().toArray()[i] + " took " + times.get(i) + "ns or " + times.get(i) / 1_000_000 + "ms to sort");
        }
        System.out.println("\n____________________________________________________________________\n\n");
    }

    private static void saveTimesToFile(String filename, List<Long> input, String funcName)
{
    try {
        File directory = new File("times");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(filename))
        {

            writer.println("Algorithm, Dataset, Time(ns), Time(ms)");

            for (int i = 0; i < input.size(); i++)
            {
                String dataset = textfiles.keySet().toArray()[i].toString();
                long timeNs = input.get(i);
                long timeMs = timeNs / 1_000_000;
                writer.println(funcName + "," + dataset + "," + timeNs + "," + timeMs); 
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static void saveAlgorithmTimesToCSV(String funcName, int repeats)
{
    List<Long> times = new ArrayList<Long>();

    for (String textfile : textfiles.keySet())
    {
        System.out.println("Testing for " + textfile);
        long avgTime = testAlgorithmTimesWithTextFile(funcName, textfile, repeats);
        times.add(avgTime);
    }
    
    saveTimesToFile("times/" + funcName.replace(" ", "_") + "_times.csv", times, funcName);
    System.out.println("Finished saving CSV for " + funcName);
}

}