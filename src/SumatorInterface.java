import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface SumatorInterface {
    void run(String file);
    void createFiboCollectionAndSumIndex(List<BigInteger> fibonacciNumbers, List<BigInteger> fileNumbers);
    Map<Integer,BigInteger> generateSpecialFibonacciCollection(BigInteger max);
}
