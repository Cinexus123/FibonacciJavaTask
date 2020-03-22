import java.util.List;
import java.util.Map;

public interface SumatorInterface {
    void run(String file);
    void createFiboCollectionAndSumIndex(List<Long> fibonacciNumbers,List<Long> fileNumbers);
    Map<Integer,Long> generateSpecialFibonacciCollection(Long max);
}
