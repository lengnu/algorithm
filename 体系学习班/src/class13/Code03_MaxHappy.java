package class13;

import java.util.List;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-07-03 11:24
 * @description TODO
 */
public class Code03_MaxHappy {
    private static class Employee {
        public int happy; // 这名员工可以带来的快乐值
        public List<Employee> subordinates; // 这名员工有哪些直接下级
    }
    private static class Info{
        //来和不来情况下的最大收益
        public int no;
        public int yes;

        public Info(int no, int yes) {
            this.no = no;
            this.yes = yes;
        }
    }

    public static int maxHappy(Employee boss){
        if (boss == null){
            return 0;
        }
        Info info = process(boss);
        return Math.max(info.no, info.yes);
    }

    private static Info process(Employee employ){
        if (employ == null){
            return new Info(0,0);
        }
        int no = 0;
        int yes = employ.happy;

        for (Employee subordinate : employ.subordinates) {
            Info info = process(subordinate);
            no += Math.max(info.no, info.yes);
            yes += info.no;
        }
        return new Info(no,yes);
    }



}
