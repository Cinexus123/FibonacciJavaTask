import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FibonacciMainLogic implements SumatorInterface  {

    private long startTime;
    private long lineCounter = 0;
    private long correctLine = 0;
    private long processed = 0;
    public static void main(String[] args) {
        FibonacciMainLogic fibonacci = new FibonacciMainLogic();
        fibonacci.run("src/liczby1.txt");
    }

    @Override
    public void run(String file) {
        try {
            int counter = 0;
            String line;
            String[] rowValue = null;
            List<Long> indexSum = new ArrayList<>();
            List<Long> fibonacciNumbers = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(file));

            startTime = System.nanoTime();
            while ((line = br.readLine()) != null) {
                rowValue = line.split(";");
                for (String part : rowValue)
                {
                    if(counter == 0 || counter == 1) { // if value from one or second column
                        fibonacciNumbers.add(Long.parseLong(part));
                        counter++;
                    }
                    else // if value from last column
                        indexSum.add(Long.parseLong(part));
                }
                counter = 0;
                lineCounter++;
            }
            br.close();

            createFiboCollectionAndSumIndex(fibonacciNumbers,indexSum);

            long endTime = System.nanoTime();
            long generalTime = endTime - startTime;
            double totalTimeInSecond = (double) generalTime / 1_000_000_000;

            System.out.println("Seconds: " + totalTimeInSecond);
            System.out.println("Processed line: " + processed / 2);
            System.out.println("Correct line: " + correctLine);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void createFiboCollectionAndSumIndex(List<Long> fibonacciNumbers,List<Long> indexSum) {
        int listCounter = 1;
        int indexFibonacciNumbersList = 0;
        int indexSumCounter = 0;
        long sum = 0;
        long lineCountersValue = lineCounter * 2; //multiply *2 because in one row are two values

        Long max = Collections.max(fibonacciNumbers);
        Map<Integer,Long> uniqueCollection = generateSpecialFibonacciCollection(max);// set key and value collection to map
        Set<Map.Entry<Integer,Long>> entrySet = uniqueCollection.entrySet();

        while(listCounter <= lineCountersValue)
        {
            for(Map.Entry<Integer, Long> entry: entrySet) {
                if(fibonacciNumbers.get(indexFibonacciNumbersList).equals(entry.getValue()))
                {
                    sum += entry.getKey();
                    if(listCounter % 2 == 0)
                    {
                        if(indexSum.get(indexSumCounter).equals(sum)) {
                            correctLine++;
                        }
                        break;
                    }
                }
            }
            indexFibonacciNumbersList++;
            listCounter++;
            processed++;
            if(processed % 2 == 0)
            {
                sum = 0;
                indexSumCounter++;
            }
        }
    }

    public Map<Integer,Long> generateSpecialFibonacciCollection(Long max) {
        long first = 0;
        long second = 1;
        long third = 2;
        long temp = 0;
        int counter = 3;
        Map<Integer,Long> collection = new TreeMap<>();
        collection.put(0,first);
        collection.put(1,second);
        collection.put(2,third);
            while(temp < max)
            {
              temp = second + third;
              second = third;
              third = temp;
              collection.put(counter,temp);
              counter++;
            }
        return collection;
    }
}
