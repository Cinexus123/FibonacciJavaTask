import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FibonacciMainLogic implements SumatorInterface  {

    private long startTime;
    private long lineCounter = 0;
    private long correctLine = 0;
    private long processed = 0;
    private List<Integer> sizeIncomingValue = new ArrayList<>();
    private List<Integer> sizeGenerateValue = new ArrayList<>();

    public static void main(String[] args) {
        FibonacciMainLogic fibonacci = new FibonacciMainLogic();
        fibonacci.run("src/nowy.txt");
    }

    @Override
    public void run(String file) {
        try {
            int counter = 0;
            String line;
            String[] rowValue = null;
            List<String> indexSum = new ArrayList<>();
            List<String> fibonacciNumbers = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(file));

            startTime = System.nanoTime();
            while ((line = br.readLine()) != null) {
                rowValue = line.split(";");
                for (String part : rowValue)
                {
                    if(counter == 0 || counter == 1) { // if value from one or second column
                        fibonacciNumbers.add(part);
                        sizeIncomingValue.add(part.length());
                        counter++;
                    }
                    else // if value from last column
                        indexSum.add(part);
                }
                counter = 0;
                lineCounter++;
            }
            br.close();

            String max = Collections.max(fibonacciNumbers);
            createFiboCollectionAndSumIndex(fibonacciNumbers, indexSum, max);

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
    public void createFiboCollectionAndSumIndex(List<String> fibonacciNumbers, List<String> indexSum, String max) {
        int findIndexElementInGenerateList = 0;
        int counterIncomingValueElement = 0;
        int listCounter = 1;
        int indexSumCounter = 0;
        String sum = "0";
        int doubleElements = 0;

        List<String> uniqueCollection = generateSpecialFibonacciCollection(max);// set key and value collection to map

        for(Integer incomingNumbers : sizeIncomingValue)
        {
            doubleElements++;
            if(sizeGenerateValue.contains(incomingNumbers))
            {
                for(Integer generateNumbers : sizeGenerateValue)
                {
                    if(incomingNumbers.equals(generateNumbers))
                    {
                        if(fibonacciNumbers.get(counterIncomingValueElement).equals(uniqueCollection.get(findIndexElementInGenerateList)))
                        {
                            sum = addStringNumbers(sum,String.valueOf(findIndexElementInGenerateList));
                            if(listCounter % 2 == 0)
                            {
                                if(indexSum.get(indexSumCounter).equals(sum) && doubleElements == 2) {
                                    correctLine++;
                                }
                                break;
                            }
                            break;
                        }
                    }
                    findIndexElementInGenerateList++;
                }
                findIndexElementInGenerateList = 0;
            }
            listCounter++;
            processed++;
            counterIncomingValueElement++;

            if(processed % 2 == 0)
            {
                sum = "0";
                indexSumCounter++;
                doubleElements = 0;
            }
        }
    }

    public List<String> generateSpecialFibonacciCollection(String max) {

        List<String> collection = new ArrayList<>();
        String value = "0";
        String value1 = "1";
        String value2 = "2";
        String temp1 = "0";

        collection.add(value);
        collection.add(value1);
        collection.add(value2);
        sizeGenerateValue.add(value.length());
        sizeGenerateValue.add(value1.length());
        sizeGenerateValue.add(value2.length());

        while(temp1.length() <= max.length()) {
            temp1 = addStringNumbers(value1,value2);
            value1 = value2;
            value2 = temp1;
            collection.add(temp1);
            sizeGenerateValue.add(temp1.length());
        }

        return collection;
    }

    public String addStringNumbers(String numberValue1, String numberValue2) {

        int i = numberValue1.length();
        int j = numberValue2.length();
        int biggerValue = Math.max(i, j) + 1;
        char[] signTable = new char[biggerValue];

        for (int digit = 0; biggerValue > 0; digit /= 10) {
            if (i > 0)
                digit += numberValue1.charAt(--i) - '0';
            if (j > 0)
                digit += numberValue2.charAt(--j) - '0';

            signTable[--biggerValue] = (char) ('0' + digit % 10);
        }
        for (biggerValue = 0; biggerValue < signTable.length - 1 && signTable[biggerValue] == '0'; biggerValue++) {} // remove unecessery 0 front

        return new String(signTable, biggerValue, signTable.length - biggerValue);
    }
}
