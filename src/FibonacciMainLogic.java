import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class FibonacciMainLogic implements SumatorInterface  {

    private long startTime;
    private long lineCounter = 0;
    private long correctLine = 0;
    private long processed = 0;

    public static void main(String[] args) {
        FibonacciMainLogic fibonacci = new FibonacciMainLogic();
        fibonacci.run("src/liczby3.txt");
    }

    @Override
    public void run(String file) {
        try {
            int counter = 0;
            String line;
            String[] rowValue = null;
            List<BigInteger> indexSum = new ArrayList<>();
            List<BigInteger> fibonacciNumbers = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(file));

            startTime = System.nanoTime();
            while ((line = br.readLine()) != null) {
                rowValue = line.split(";");
                for (String part : rowValue)
                {
                    if(counter == 0 || counter == 1) { // if value from one or second column
                        fibonacciNumbers.add(new BigInteger(part));
                        counter++;
                    }
                    else // if value from last column
                        indexSum.add(new BigInteger(part));
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
    public void createFiboCollectionAndSumIndex(List<BigInteger> fibonacciNumbers,List<BigInteger> indexSum) {
        int listCounter = 1;
        int indexSumCounter = 0;
        int sum =0;
        BigInteger sum1 = BigInteger.valueOf(sum);
        int doubleElements = 0;

        BigInteger max = Collections.max(fibonacciNumbers);
        Map<Integer,BigInteger> uniqueCollection = generateSpecialFibonacciCollection(max);// set key and value collection to map
        Set<Map.Entry<Integer,BigInteger>> entrySet = uniqueCollection.entrySet();
            for(BigInteger element : fibonacciNumbers)
            {
                for(Map.Entry<Integer, BigInteger> entry: entrySet) {
                    if(element.equals(entry.getValue()))
                    {
                        doubleElements++;
                        sum1 = sum1.add(new BigInteger(String.valueOf(entry.getKey())));
                        if(listCounter % 2 == 0)
                        {
                            if(indexSum.get(indexSumCounter).equals(sum1) && doubleElements == 2) {
                                correctLine++;
                            }
                            break;
                        }
                    }
                }
                listCounter++;
                processed++;
                if(processed % 2 == 0)
                {
                    sum1 = BigInteger.valueOf(0);
                    indexSumCounter++;
                    doubleElements = 0;
                }
            }
    }

    public Map<Integer,BigInteger> generateSpecialFibonacciCollection(BigInteger max) {
        String first = "0";
        BigInteger value = new BigInteger(String.valueOf(first));
        String second = "1";
        BigInteger value1 = new BigInteger(String.valueOf(second));
        String third = "2";
        BigInteger value2 = new BigInteger(String.valueOf(third));
        String temp = "0";
        BigInteger temp1 = new BigInteger(String.valueOf(temp));
        int counter = 3;

        Map<Integer,BigInteger> collection = new TreeMap<>();
        collection.put(0,value);
        collection.put(1,value1);
        collection.put(2,value2);

            while(temp1.compareTo(max) == -1 )
            {
              temp1 = value1.add(value2);
              value1 = value2;
              value2 = temp1;
              collection.put(counter,temp1);
              counter++;
            }
        return collection;
    }
}
