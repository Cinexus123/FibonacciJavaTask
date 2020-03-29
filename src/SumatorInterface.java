import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface SumatorInterface {
    void run(String file);
    void createFiboCollectionAndSumIndex(List<String> fibonacciNumbers, List<String> indexSum, String max);
    List<String> generateSpecialFibonacciCollection(String max);
    String addStringNumbers(String number1,String number2);
}
