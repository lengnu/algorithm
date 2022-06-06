package class01;

import java.time.Instant;
import java.util.OptionalLong;
import java.util.stream.LongStream;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-28 21:01
 * @description TODO
 */
public class Test {
    public static void main(String[] args) {
        long start = Instant.now().toEpochMilli();
        long sum = 0;
        for (long i = 1;i <= 30000000000L;i++){
            sum += i;
        }
        long end = Instant.now().toEpochMilli();
        System.out.println(sum);
        System.out.println("for循环累加耗时\t" + (end - start) + "ms");

        start = Instant.now().toEpochMilli();
        OptionalLong reduce = LongStream.rangeClosed(0, 30000000000L).parallel().reduce(Long::sum);
        end = Instant.now().toEpochMilli();
        System.out.println(reduce.getAsLong());
        System.out.println("流累加耗时\t" + (end - start) + "ms");

    }
}
