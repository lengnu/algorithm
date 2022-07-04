package class14;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-07-03 17:41
 * @description 最多的会议场次
 */
public class Code04_BestArrange {
    public static int bestArrange(int[][] program){
        if (program == null || program.length == 0){
            return 0;
        }
        //当前来到时间点
        int timeLine = 0;
        int count = 0;
        for (int i = 0; i < program.length; i++) {
            if (program[i][0] >= timeLine){
                count++;
                timeLine = program[i][1];
            }
        }
        return count;
    }
}
