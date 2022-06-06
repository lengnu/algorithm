package class07;

import java.util.*;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-30 21:46
 * @description TODO
 */
public class Code02_EveryStepShowBoss {
    /**
     * 抽象出来的顾客类
     */
    private static class Customer {
        private int id;
        private int buy;
        private int enterTime;

        public Customer(int id, int buy, int enterTime) {
            this.id = id;
            this.buy = buy;
            this.enterTime = enterTime;
        }
    }

    /**
     * 得到每步的候选区元素
     *
     * @param arr
     * @param op
     * @param k
     * @return
     */
    public static List<List<Integer>> topK(int[] arr, boolean[] op, int k) {
        if (arr == null || op == null || arr.length == 0 || op.length == 0 || k <= 0) {
            return null;
        }
        //得到操作的次数
        int N = arr.length;
        //存储id - customer表
        Map<Integer, Customer> customerMap = new HashMap<>();
        //候选区列表
        List<Customer> cands = new ArrayList<>();
        //得奖区列表
        List<Customer> daddy = new ArrayList<>();
        //每一步的得奖区名单
        List<List<Integer>> result = new ArrayList<>();
        //开始遍历每一个操作
        for (int i = 0; i < N; i++) {
            //得到当前用户的id
            int id = arr[i];
            boolean buyOrRefund = op[i];
            //当前用户购买数为且发生退货时间，忽略
            if (!customerMap.containsKey(id) && !buyOrRefund) {
                if (i == 0){
                    result.add(new ArrayList<>());
                }else{
                    result.add(result.get(result.size() - 1));
                }
                continue;
            }
            //1. 用户购买数为0发生买货
            if (!customerMap.containsKey(id)) {
                customerMap.put(id, new Customer(id, 0, 0));
            }
            //2.用户购买数不为0发生退货
            //3.用户购买书不为0发生买货
            Customer customer = customerMap.get(id);
            if (buyOrRefund) {
                customer.buy++;
            } else {
                customer.buy--;
            }
            //如果用户当前购买为0了，直接移除用户
            if (customer.buy == 0) {
                customerMap.remove(id);
            }

            //用户刚进入系统进行买货
            if (!cands.contains(customer) && !daddy.contains(customer)) {
                //得奖区不满则直接进得奖区
                if (daddy.size() < k) {
                    customer.enterTime = i;
                    daddy.add(customer);
                } else {
                    //满了则进候选区
                    customer.enterTime = i;
                    cands.add(customer);
                }
            }

            //接下来清除两个区中buy为0的用户
            clearZeroBuy(daddy);
            clearZeroBuy(cands);
            //对两个区进行排序
            daddy.sort((customer1, customer2) -> customer1.buy != customer2.buy ? customer1.buy - customer2.buy : customer1.enterTime - customer2.enterTime);
            cands.sort((customer1, customer2) -> customer1.buy != customer2.buy ? customer2.buy - customer1.buy : customer1.enterTime - customer2.enterTime);
            //接下来整合两个区
            move(cands, daddy, k, i);
            result.add(getDaddyId(daddy));
        }
        return result;
    }

    //整合两个区的元素
    private static void move(List<Customer> cands, List<Customer> daddy, int k, int time) {
        //候选区没人直接退出
        if (cands.isEmpty()) {
            return;
        }

        //等奖区不为null
        if (daddy.size() < k) {
            Customer customer = cands.get(0);
            customer.enterTime = time;
            cands.remove(0);
            daddy.add(customer);
        } else {
            //得奖区满了，且候选区不为null
            if (cands.get(0).buy > daddy.get(0).buy) {
                Customer oldDaddy = daddy.get(0);
                daddy.remove(0);
                oldDaddy.enterTime = time;
                Customer newDaddy = cands.get(0);
                cands.remove(0);
                newDaddy.enterTime = time;
                cands.add(oldDaddy);
                daddy.add(newDaddy);
            }
        }
    }

    private static void clearZeroBuy(List<Customer> arr) {
        List<Customer> noZero = new ArrayList<>();
        for (Customer customer : arr) {
            if (customer.buy != 0) {
                noZero.add(customer);
            }
        }
        arr.clear();
        for (Customer customer : noZero) {
            arr.add(customer);
        }
    }

    public static List<Integer> getDaddyId(List<Customer> daddy) {
        List<Integer> ids = new ArrayList<>();
        for (Customer customer : daddy) {
            ids.add(customer.id);
        }
        return ids;
    }

    public static List<List<Integer>> topKByHead(int[] arr, boolean[] op, int k) {
        if (arr == null || op == null || arr.length == 0 || op.length == 0 || k <= 0) {
            return null;
        }
        //得到操作的次数
        int N = arr.length;
        //存储id - customer表
        Map<Integer, Customer> customerMap = new HashMap<>();
        //记录返回结果
        List<List<Integer>> result = new ArrayList<>();
        HeapGreater<Customer> cands = new HeapGreater<>((customer1, customer2) -> customer1.buy != customer2.buy ? customer1.buy - customer2.buy : customer2.enterTime - customer1.enterTime);
        HeapGreater<Customer> daddy = new HeapGreater<>((customer1, customer2) -> customer1.buy != customer2.buy ? customer2.buy - customer1.buy : customer2.enterTime - customer1.enterTime);
        for (int i = 0; i < N; i++) {
            //得到当前用户的id
            int id = arr[i];
            boolean buyOrRefund = op[i];
            //购买数位0且发生退货
            if (!customerMap.containsKey(id) && !buyOrRefund) {
                if (i == 0){
                    result.add(new ArrayList<>());
                }else{
                    result.add(result.get(result.size() - 1));
                }
                continue;
            }

            //当前候选区和得奖区都没有用户，用户购买数为0发生买货
            if (!customerMap.containsKey(id)) {
                customerMap.put(id, new Customer(id, 0, 0));
            }

            Customer customer = customerMap.get(id);
            if (buyOrRefund) {
                customer.buy++;
            } else {
                customer.buy--;
            }

            if (customer.buy == 0) {
                customerMap.remove(id);
            }

            if (!cands.containsKey(customer) && !daddy.containsKey(customer)) {
                if (daddy.size() < k) {
                    customer.enterTime = i;
                    daddy.add(customer);
                } else {
                    customer.enterTime = i;
                    cands.add(customer);
                }
            } else if (cands.containsKey(customer)) {
                //用户在候选区中
                if (customer.buy == 0) {
                    cands.remove(customer);
                } else {
                    cands.resign(customer);
                }
            } else {
                if (customer.buy == 0) {
                    daddy.remove(customer);
                } else {
                    daddy.resign(customer);
                }
            }

            //开始移动两个区
            if (!cands.isEmpty()) {
                if (daddy.size() < k) {
                    Customer customer1 = cands.poll();
                    customer1.enterTime = i;
                    daddy.add(customer1);
                } else {
                    if (daddy.peek().buy < cands.peek().buy) {
                        Customer oldDaddy = daddy.poll();
                        oldDaddy.enterTime = i;
                        Customer newDaddy = cands.poll();
                        newDaddy.enterTime = i;
                        daddy.add(newDaddy);
                        cands.add(oldDaddy);
                    }
                }
            }

            List<Integer> ids = new ArrayList<>();
            for (Customer cs : daddy.getAllElements()) {
                ids.add(cs.id);
            }
            result.add(ids);

        }
        return result;
    }

    /**
     * 生成随机的用户ID
     * @param len
     * @param maxId
     * @return
     */
    public static int[] generateIds(int len,int maxId){
        int[] result = new int[len];
        for (int i = 0; i < len; i++) {
            result[i] = (int) (Math.random() * maxId) + 1;
        }
        return result;
    }

    /**
     * 随机生成买货推过操作
     * @param len
     * @return
     */
    public static boolean[] generateOperation(int len){
        boolean[] op = new boolean[len];
        for (int i = 0; i < len; i++) {
          op[i] = Math.random() < 0.5 ? true : false;
        }
        return op;
    }

    public static void main(String[] args) {
        int len = 100;
        int maxId = 20;
        int times = 10000;
        int maxK = 5;
        System.out.println("测试开始");
        for (int i = 0; i < times; i++) {
            int[] ids = generateIds(len,maxId);
            boolean[] op = generateOperation(len);
            int k = (int) (Math.random() * maxK) + 1;
            List<List<Integer>> result1 = topK(ids, op, k);
            List<List<Integer>> result2 = topKByHead(ids, op, k);
            for (int j = 0; j < ids.length; j++) {
               List<Integer> ids1 = result1.get(j);
               List<Integer> ids2 = result2.get(j);
               ids1.sort((Comparator.comparingInt(o -> o)));
               ids2.sort((Comparator.comparingInt(o -> o)));
               for (int m = 0;m < ids1.size();m++){
                   if (!ids1.get(m).equals(ids2.get(m))){
                       System.out.println("出错了...");
                       System.exit(1);
                   }
               }
            }
        }
        System.out.println("测试结束");

    }

}
