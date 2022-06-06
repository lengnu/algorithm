## 二、异或

- 异或：相同为0，不相同为1
- 可以理解为无进位相加
-  0 ^ N = N, N ^ N = 0,  a ^ b = b ^ a,  a ^ b ^  c = a ^ (b ^ c)

### 1、不用额外变量交换两个数

- a = a ^ b
- b = a ^ b = (a ^ b) ^ b = a
- a = a ^ b = (a ^ b) ^ (a) = b  
- 连续异或三次即可（注意数组相同位置不能采用这种方式交换元素，如果位置相同第一次异或就会把原位置变为0，后续结果全为0）
- 异或运算效率较高

```java
/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-22 20:23
 * @description 不使用额外变量交换两个数，要保证数组交换元素时上游调用不是同一位置
 */
public class Swap {
    public static void main(String[] args) {
        int a = 10;
        int b = 20;
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.format("a = %d,b = %d",a,b);
    }
}

```

### 2、找出数组中出现了奇数次的元素（其余都偶数次）,时间复杂度$$O(N)$$，空间复杂度$$O(1)$$

- 全部异或即可，偶数次的元素会两两异或为0

```java
/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-22 20:31
 * @description 找出数组中出现了奇数次的元素（其余都偶数次）
 */
public class Code01_EvenTimesOddTimes {

    /**
     * 打印数组中出现次数为奇数次的元素，数组为null返回系统最大
     * @param arr
     * @return
     */
    public static int printOddTimesNum(int[] arr){
        if (arr == null || arr.length == 0){
            return Integer.MAX_VALUE;
        }

        int eor = 0;
        for (int num : arr){
            eor ^= num;
        }
        return eor;
    }
}
```

### 3、一个数组中两个数出现了奇数次，其余出现了偶数次，找到这两个数，时间复杂度$$O(N)$$，空间复杂度$$O(1)$$

- 首先将全部元素异或一次，即结果为a ^ b
- 找到a ^ b 结果中最右侧的1，即a 或 b在这一位不同（只要a 和 b在该位不同即可，拿哪一位的1就行）
- 用这个1将所有元素分为两类，即该位为1和该位不为1
- 然后将分成的两类分别异或就可得到结果

```java
/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-22 20:54
 * @description 一个数组中两个数出现了奇数次，其余出现了偶数次，找到这两个数
 */
public class Code02_EvenTimesTwoOddTimes {
    public static int[] printTwoOddTimesNum(int[] arr){
        int eor = 0;
        for (int num : arr){
            eor ^= num;
        }

        //找到分割不同数最右边的1
        int right = eor & (~eor + 1);

        int one = 0;
        for (int num : arr){
            if ((num & right) != 0){
                one ^= num;
            }
        }

        int two = one ^ eor;
        return new int[]{one,two};
    }
}
```

### 4、一个数组中一种数出现了K次，其他都出现了M次，其中$$K < M且 M > 1$$，要求额外空间复杂度$$O(1)$$，即不能使用Hash表，时间复杂度$$O(N)$$

- int类型可以用32位比特表式，准备一个32长度的数组，分别储存所有数字第i位1出现次数
- 那么数组中元素最终只有3种结果，0、p * M（p位一个常数）、p * M + K
- 遍历数组对每个元素取余，不为0的话就说明出现K次的数在该位为1，记录下来即可

```java
/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-22 20:44
 * @description TODO
 */
public class Code03_KM {
    /**
     * 找出数组种出现k次的那个元素,采用int[]32位数组实现，空间复杂度O(1)
     * @param arr
     * @param k
     * @param m
     * @return
     */
    public static int onlyKTimes(int[] arr, int k, int m) {
        int[] counts = new int[32];

        //统计所有数字对应bit位的次数
        for (int num : arr) {
            for (int i = 0; i < 32; i++) {
                counts[i] += (num >> i) & 1;
            }
        }

        //遍历数组找到出现k次的元素
        int result = 0;
        for (int i = 0; i < 32; i++) {
            if ((counts[i] % m) != 0) {
                result |= (1 << i);
            }
        }

        return result;
    }

    /**
     * 采用HashMap找到出现k次的数
     * @param arr
     * @param k
     * @param m
     * @return
     */
    public static int onlyKTimesByHashMap(int[] arr,int k,int m){
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : arr){
            if (!map.containsKey(num)){
                map.put(num,0);
            }
            map.put(num,map.get(num) + 1);
        }

        for (int num : map.keySet()){
            if (map.get(num) == k){
                return num;
            }
        }
        return 0;

    }

    /**
     * 返回满足要求的数组
     *
     * @param maxKinds 一共多少种数的范围
     * @param range    数字的范围
     * @param k        出现k次
     * @param m        出现m次
     * @return
     */
    public static int[] randomArray(int maxKinds, int range, int k, int m) {
        //1.出现k次的数字
        int kTimesNum = randomNumber(range);

        //2.一共多少种数,至少需要两种
        int numKinds = (int) ((Math.random() * maxKinds) + 2);

        //3.数组的长度
        int[] arr = new int[k + m * (numKinds - 1)];

        //4.填充出现k次的数
        int index = 0;
        for (; index < k; index++) {
            arr[index] = kTimesNum;
        }

        //5.填充剩余的数字
        numKinds--;
        HashSet<Integer> set = new HashSet<>();
        set.add(kTimesNum);

        while (numKinds != 0) {
            int curNum = 0;
            //保证随机的数字没有出现过
            do {
                curNum = randomNumber(range);
            } while (set.contains(curNum));
            set.add(curNum);
            //剩余待填的种类的数减1
            numKinds--;

            //填充剩余的数字
            for (int i = 0; i < m; i++) {
                arr[index++] = curNum;
            }
        }
        return arr;

    }

    /**
     * 返回[-range,range]范围内的随机整数
     *
     * @param range
     * @return
     */
    public static int randomNumber(int range) {
        return (int) (Math.random() * (range + 1)) - (int) (Math.random() * (range + 1));
    }

    public static void main(String[] args) {
        int kinds = 50;
        int range = 300;
        int testTimes = 10000;
        int max = 9;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            int a = (int) (Math.random() * max) + 1;  //1 ~ 9
            int b = (int) (Math.random() * max) + 1;  //1 ~ 9
            int k = Math.min(a, b);
            int m = Math.max(a, b);
            if (k == m) {
                m++;
            }
            int[] arr = randomArray(kinds,range,k,m);
            int resultOne = onlyKTimes(arr, k, m);
            int resultTwo = onlyKTimesByHashMap(arr, k, m);
            if (resultOne != resultTwo){
                System.out.println("出错了");
                return;
            }
        }
        System.out.println("测试结束");
    }
}
```

## 三、一些基础数据结构（链表、Master公式、堆、栈、哈希表）

### 1、反转单链表和双链表，空间复杂度$$O(1)$$

- 使用容器，空间复杂度$$O(N)$$，效率太低
- 使用额外几个变量，完成链表反转。反转时候只关注当前节点

```java
/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-23 10:47
 * @description 单链表
 */
public class Node<T> {
    public T value;
    public Node<T> next;

    public Node(T value) {
        this.value = value;
    }
}

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-23 10:50
 * @description 双链表
 */
public class DoubleNode<T> {
    public T value;
    public DoubleNode<T> last;
    public DoubleNode<T> next;

    public DoubleNode(T value) {
        this.value = value;
    }
}


/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-23 10:52
 * @description 反转链表
 */
public class Code01_ReverseList {

    /**
     * 打印单链表
     * @param head
     * @param <T>
     */
    public static <T> void printLinkedList(Node<T> head){
        while (head != null){
            System.out.print(head.value + " ->");
            head = head.next;
        }
        System.out.println();
    }


    /**
     * 打印双链表
     * @param head
     * @param <T>
     */
    public static <T> void printDoubleList(DoubleNode<T> head){
        while (head != null){
            System.out.print(head.value + " ->");
            head = head.next;
        }
        System.out.println();
    }

    /**
     * 反转单链表,迭代方式，循环遍历链表节点,不用额外空间
     * @param head  原链表头节点
     * @param <T>   泛型
     * @return      反转过后的头节点
     */
    public static <T> Node<T> reverseLinkedList(Node<T> head){
        if (head == null){
            return head;
        }
        //记录反转链表的头节点
        Node<T> pre = null;
        //当前节点的下一个节点
        Node<T> next = null;
        Node<T> cur = head;
        while (cur != null){
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    /**
     * 反转单链表,迭代方式，循环遍历链表节点,不用额外空间
     * head在迭代中为当前节点，只需要考虑当前节点的调整情况
     * @param head  原链表头节点
     * @param <T>   泛型
     * @return      反转过后的头节点
     */
    public static <T> DoubleNode<T> reverseDoubleList(DoubleNode<T> head){
        if (head == null){
            return head;
        }

        DoubleNode<T> pre = null;
        DoubleNode<T> next = null;
        DoubleNode<T> cur = head;
        while (cur != null){
            next = cur.next;
            cur.next = pre;
            cur.last = next;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    /**
     * 采用容器方法反转单链表
     * @param head
     * @param <T>
     * @return
     */
    public static <T>  Node<T> reverseLinkedListByArray(Node<T> head){
        if (head == null){
            return head;
        }
        List<Node<T>> nodes = new ArrayList<>();
        while (head != null){
            nodes.add(head);
            head = head.next;
            System.out.println("haha");
        }
        System.out.println(nodes.size());
        for (int i = nodes.size() - 1;i > 0;i--){
            nodes.get(i).next = nodes.get(i - 1);
        }
        System.out.println(nodes.size());
        nodes.get(0).next = null;
        return nodes.get(nodes.size() - 1);
    }

    /**
     * 随机生成单链表
     * @param len   链表的最大长度
     * @param value 链表的最大值[1,value]
     * @return
     */
    public static  Node<Integer> generateRandomLinkedList(int len,int value){
        int size = (int) (Math.random() * (len + 1));
        Node<Integer> head = new Node((int)(Math.random()*(value + 1)));
        Node<Integer> pre = head;
        size--;
        while (size-- > 0){
            Node<Integer> cur = new Node((int)(Math.random()*(value + 1)));
            pre.next = cur;
            pre = cur;
        }
        return head;
    }

    /**
     * 随机生成双链表
     * @param len   链表的最大长度
     * @param value 链表的最大值[1,value]
     * @return
     */
    public static  DoubleNode<Integer> generateRandomDoubleList(int len,int value){
        int size = (int) (Math.random() * (len + 1));
        DoubleNode<Integer> head = new DoubleNode((int)(Math.random()*(value + 1)));
        DoubleNode<Integer> pre = head;
        size--;
        while (size-- > 0){
            DoubleNode<Integer> cur = new DoubleNode(((int)(Math.random()*(value + 1))));
            pre.next = cur;
            cur.last = pre;
            pre = cur;
        }
        return head;
    }

    public static void main(String[] args) {
        Node<Integer> randomLinkedList = generateRandomLinkedList(20, 100);
        DoubleNode<Integer> generateRandomDoubleList = generateRandomDoubleList(20, 100);

        printLinkedList(randomLinkedList);
        Node<Integer> integerNode = reverseLinkedList(randomLinkedList);
        printLinkedList(integerNode);

        printDoubleList(generateRandomDoubleList);
        DoubleNode<Integer> doubleNode = reverseDoubleList(generateRandomDoubleList);
        printDoubleList(doubleNode);
    }
}

```

### 2、删除链表给定的元素(有一定编码技巧)

- 函数必须有一个返回值，返回删除后新的头部，因为有可能删除头节点

- 首先找到第一个不需要删除的节点

```java
public class Code02_DeleteGivenValue {
    /**
     * 删除链表给定的值
     * @param head 
     * @param value
     * @param <T>
     * @return
     */
    public static <T> Node<T> removeValue(Node head,T value){
        //head来到第一个不需要删除的位置
        while (head != null){
            if (head.value != value){
               break;
            }
            head = head.next;
        }
        
        Node<T> pre = head;
        Node<T> cur = head;
        while (cur != null){
            if (cur.value == value){
                pre.next = cur.next;
            }else {
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }
}

```

### 3、数组实现队列、栈，由双向链表实现双端队列

```java
/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-23 12:43
 * @description 双向链表实现队列和栈
 */
public class DoubleEndsQueueToStackAndQueue {
    /**
     * 由双向链表实现双端队列
     * @param <T>
     */
    public static class DoubleEndsQueue<T> {
        public DoubleNode<T> head;
        public DoubleNode<T> tail;

        /**
         * 从队列头加入元素
         *
         * @param value
         */
        public void addFromHead(T value) {
            DoubleNode<T> cur = new DoubleNode<>(value);
            if (head == null) {
                head = cur;
                tail = cur;
            } else {
                cur.next = head;
                head.last = cur;
                head = cur;
            }
        }

        /**
         * 从队列尾部加入元素
         *
         * @param value
         */
        public void addFromTail(T value) {
            DoubleNode<T> cur = new DoubleNode<>(value);
            if (tail == null) {
                head = cur;
                tail = cur;
            } else {
                cur.last = tail;
                tail.next = cur;
                tail = cur;
            }
        }

        /**
         * 从队列头部弹出元素，没有元素抛出异常
         * @return
         */
        public T popFromHead() throws Exception {
            if (head == null) {
                throw new Exception("链表为空");
            }

            DoubleNode<T> cur = head;
           if (head == tail){
               head = null;
               tail = null;
           }else {
               head = head.next;
               cur.next = null;
               head.last = null;
           }
           return cur.value;
        }

        /**
         * 从队列尾部弹出元素，没有元素抛出异常
         * @return
         */
        public T popFromTail() throws Exception {
            if (tail == null){
                throw new Exception("链表为空");
            }

            DoubleNode<T> cur = tail;
            if (tail == head){
                tail = null;
                head = null;
            }else {
                tail = tail.last;
                tail.next = null;
                cur.last = null;
            }
            return cur.value;
        }

        /**
         * 判断双双端队列是否为null
         * @return
         */
        public boolean isEmpty(){
            return head ==  null;
        }
    }

    /**
     * 由数组实现栈
     */
    public static class ArrayToStack{
        public final int limit;
        public int top;
        public int[] data;

        public ArrayToStack(int limit) {
            this.limit = limit;
            top = 0;
            data = new int[limit];
        }

        /**
         * 元素入栈，满抛出异常
         * @param value
         */
        public void push(int value) {
            if (top == limit){
                throw new RuntimeException("当前栈已满");
            }
            data[top++] = value;
        }

        /**
         * 元素出栈，栈空则抛出异常
         * @return
         */
        public int pop()   {
            if (top == 0){
                throw new RuntimeException("当前栈为空");
            }
            return data[--top];
        }

        /**
         * 当前栈是否为空
         * @return
         */
        public boolean isEmpty(){
            return top == 0;
        }

        /**
         * 返回当前栈元素个数
         * @return
         */
        public int size(){
            return top;
        }
    }

    /**
     * 循环数组数组实现队列
     */
    public static class ArrayToQueue{
        public final int limit;
        public int pushIndex;
        public int popIndex;
        public int[] data;
        public int size;

        public ArrayToQueue(int limit) {
            this.limit = limit;
            popIndex = 0;
            pushIndex = 0;
            size = 0;
            data = new int[limit];
        }


        /**
         * 元素入队列
         * @param value
         */
        public void add(int value){
            if (isFull()){
                throw new RuntimeException("队列已满");
            }
            data[pushIndex++] = value;
            pushIndex %= limit;
            size++;
        }

        /**
         * 元素出队列
         * @return
         */
        public int offer(){
            if (isEmpty()){
                throw new RuntimeException("队列为空");
            }
            int value = data[popIndex++];
            popIndex %= limit;
            size--;
            return value;
        }

        /**
         * 判断队列是否为空
         * @return
         */
        public boolean isEmpty(){
            return size == 0;
        }

        /**
         * 判断队列是否已满
         * @return
         */
        public boolean isFull(){
            return size == limit;
        }
    }

}

```

### 4、最小栈,getMin()方法时间复杂度$$O(1)$$

- 实现一个特殊的栈，在基本功能的基础上，再实现返回栈中最小元素的操作，要求

1. pop、push、getMin、操作的时间复杂度都是$$O(1)$$

2. 设置栈类型可以选用已有的栈结构



- 做法：使用两个栈，一个正常存取元素，一个只存储当前时刻栈的最小元素

```java
/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-23 22:13
 * @description 最小栈
 */
public class Code05_MinStack {
    public static class MinStack{
        private Stack<Integer> stack;
        private Stack<Integer> minStack;

        public MinStack() {
            stack = new Stack<>();
            minStack = new Stack<>();
        }

        /**
         *返回当前栈的最小元素
         * @return
         */
        public int getMin(){
            return minStack.peek();
        }

        public void push(int value){
            if (stack.empty()){
                minStack.push(value);
            }else {
                if (value < getMin()){
                    minStack.push(value);
                }else {
                    minStack.push(getMin());
                }
            }
            stack.push(value);
        }

        /**
         * 弹出栈当前元素
         * @return
         */
        public int pop(){
            minStack.pop();
            return stack.pop();
        }

        /**
         * 得到当前栈的大小
         * @return
         */
        public int size(){
            return stack.size();
        }
    }
}

```

### 5、使用两个队列实现栈并且使用两个栈实现队列

- 注意一些编码技巧

```java
package class03;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-23 22:20
 * @description TODO
 */
public class Code06_QueueToStackAndStackToQueue {
    /**
     * 使用两个队列实现一个栈
     */
    public static class QueueToStack {
        private Queue<Integer> queue;
        private Queue<Integer> help;

        public QueueToStack() {
            queue = new LinkedList<>();
            help = new LinkedList<>();
        }

        /**
         * 元素出栈
         */
        public int pop() {
            return queue.poll();
        }

        /**
         * 元素入栈
         * @param value
         */
        public void push(int value) {
            help.add(value);
            while (!queue.isEmpty()) {
                help.add(queue.poll());
            }
            Queue<Integer> temp = help;
            help = queue;
            queue = temp;
        }
    }

    
    /**
     * 使用两个栈实现一个队列
     */
    public static class StackToQueue{
        private Stack<Integer> pushStack;
        private Stack<Integer> popStack;

        public StackToQueue() {
            pushStack = new Stack<>();
            popStack = new Stack<>();
        }

        /**
         * 元素出队列操作
         * @return
         */
        public int poll(){
            if (popStack.isEmpty()){
                while (!pushStack.isEmpty()){
                    popStack.push(pushStack.pop());
                }
            }
            return popStack.pop();
        }

        /**
         * 元素入队列操作
         * @param value
         */
        public void add(int value){
            pushStack.add(value);
        }
    }
}

```

### 6、Master公式（分析递归函数时间复杂度,子问题规模必须一样）

- 假设递归可以写成$$T(N) = a*T(\frac {N} b)+O(N^d)$$（其中$$a、b、d$$都为常数）

1. 若$$log_ba < d$$，复杂度为$$O(N^d)$$
2. 若$$log_ba > d$$，复杂度为$$O(N*log_ba)$$
3. 若$$log_ba = d$$，复杂度为$$O(N^d*logN)$$

### 7、哈希表和有序表（使用时需要注意的点）

1. Hash表（HashMap）

- HashMap增删改查都是$$O(1)$$常数时间的，不过这个常数时间有点大
- Hash表内部的存储空间：如果为常数类型（包括String）则开辟空间为对应数据类型的空间大小；如果为引用，则存的是引用地址（8个字节）
- HashMap对于String和包装类型在Hash表中按值传递（equals方法）

2. 有序表（TreeMap、包括红黑树[Java有序表的实现]、AVL树、SB树）

- TreeMap增删改查都是$$O(logN)$$的
- 不会村同样的Key，Key值按一定的规则进行排序

## 四、归并排序时间$$O(N*logN)$$，空间$$O(N)$$

### 1、归并排序递归和迭代版本（需要注意一些边界条件，理解好迭代版本代码）

```java
package class04;

import java.util.Arrays;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-24 21:01
 * @description 归并排序
 */
public class Code01_MergeSort {

    /**
     * 归并排序递归版本
     *
     * @param arr
     */
    public static void sort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        mergeSort(arr, 0, arr.length - 1);
    }

    /**
     * 递归归并排序
     *
     * @param arr
     * @param L
     * @param R
     */
    private static void mergeSort(int[] arr, int L, int R) {
        //注意，如果写L==R数组长度不能为0
        //否则，就要写成L >= R
        if (L == R) {
            return;
        }
        int mid = L + ((R - L) >> 1);
        //对左边排序
        mergeSort(arr, L, mid);
        //对右边排序
        mergeSort(arr, mid + 1, R);
        //归并
        merge(arr, L, mid, R);
    }

    /**
     * 归并过程，不是递归
     *
     * @param arr
     * @param L
     * @param R
     * @param mid
     */
    private static void merge(int[] arr, int L, int mid, int R) {
        int[] help = new int[R - L + 1];
        //存储新数组的下标
        int k = 0;
        //mid是左边结束的位置
        int l = L;
        int r = mid + 1;
        while (l <= mid && r <= R) {
            if (arr[l] <= arr[r]) {
                help[k++] = arr[l++];
            } else {
                help[k++] = arr[r++];
            }
        }
        //左边没有放完
        while (l <= mid) {
            help[k++] = arr[l++];
        }
        //右边没有放完
        while (r <= R) {
            help[k++] = arr[r++];
        }
        //将排序好的数组返回原数组
        for (int i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
    }

    /**
     * 生成随机长度数组
     *
     * @param maxVale
     * @param maxLen
     * @return
     */
    private static int[] generateRandomArray(int maxVale, int maxLen) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = randomNumber(maxVale);
        }
        return arr;
    }

    /**
     * 返回[-max,max]中随机一个元素
     *
     * @param maxValue
     * @return
     */
    private static int randomNumber(int maxValue) {
        return (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
    }

    /**
     * 迭代版本归并排序
     * 每次原则一个步长，从1开始(步长代表一个左组或右组的长度)
     */
    public static void sortNonRecursive(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        int mergeSize = 1;
        while (mergeSize < N) {
            //代表每每次调整补偿左边最开始的位置
            int L = 0;
            while (L < N) {
                //左组为[L,M]
                int M = L + mergeSize - 1;
                //左组都不够了，直接跳出本次merge，开始下一轮，必要的一行代码
                if (M > N - 1){
                    break;
                }
                //右组边界
                int R = Math.min(N - 1, M + mergeSize);
                //归并
                merge(arr, L, M, R);
                //下一个左组长度
                L = R + 1;
            }
            //防止Integer.MAX_VALUE溢出，不影响结果
            if (mergeSize > N / 2) {
                break;
            }
            //每次步长加倍
            mergeSize <<= 1;
        }
    }

    public static void main(String[] args) {
        int maxValue = 30;
        int maxLen = 10;
        int times = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < times; i++) {
            int[] arrOriginal = generateRandomArray(maxValue, maxLen);
            int[] arrCopyOne = new int[arrOriginal.length];
            int[] arrCopyTwo = new int[arrOriginal.length];
            System.arraycopy(arrOriginal, 0, arrCopyOne, 0, arrOriginal.length);
            System.arraycopy(arrOriginal, 0, arrCopyTwo, 0, arrOriginal.length);
            Arrays.sort(arrOriginal);
            sort(arrCopyOne);
            sortNonRecursive(arrCopyTwo);
            if (!Arrays.equals(arrCopyOne, arrOriginal) || !Arrays.equals(arrCopyTwo,arrOriginal)) {
                System.out.println("出错了...");
                System.exit(1);
            }
        }
        System.out.println("测试结束");
    }

}

```

### 2、小和问题$$O(N*logN)$$

1. 问题描述：数组中所有元素左边比它小的元素个数累加起来叫做数组的小和
2. 问题求解：

- 常规二次循环即可,$$O(N^2)$$

- 每次在归并的过程中，只要左边当前位置的元素的比右边前位置的元素小，那么左边的这个数就会对右边部分 当前位置向右的所有数产生小和，那么记录右边数组到右边当前的位置之差乘上左边部分当前的值，就是在这次归并中左边该数产生的小和。因为每次都是局部归并，小和每次归并只对右边部分产生，但是归并会两两合并，小归并到大归并，那么在往顶部的归并中产生的所有小和都会被统计起来。因为每次只比较左右两部分，所有在前一次归并中右边部分的小和不会再被记录。$$O(N*logN)$$

  ```java
  /**
   * @author duwei
   * @version 1.0.0
   * @create 2022-05-25 21:39
   * @description 求小和
   */
  public class Code02_SmallSum {
  
      /**
       * 两层for循环来求小和
       *
       * @param arr
       * @return
       */
      public static int smallSumByFor(int[] arr) {
          if (arr == null || arr.length < 2) {
              return 0;
          }
          int sum = 0;
          int N = arr.length;
          for (int i = 1; i < N; i++) {
              for (int j = 0; j < i; j++) {
                  if (arr[j] < arr[i]) {
                      sum += arr[j];
                  }
              }
          }
          return sum;
      }
  
      /**
       * 求数组的小和
       *
       * @param arr
       * @return
       */
      public static int smallSum(int[] arr) {
          if (arr == null || arr.length < 2) {
              return 0;
          }
          return mergeSort(arr, 0, arr.length - 1);
      }
  
      private static int mergeSort(int[] arr, int L, int R) {
          if (L == R) {
              return 0;
          }
          int mid = L + ((R - L) >> 1);
          return mergeSort(arr, L, mid) +
                  mergeSort(arr, mid + 1, R) +
                  merge(arr, L, mid, R);
      }
  
      private static int merge(int[] arr, int L, int M, int R) {
          int[] help = new int[R - L + 1];
          int index = 0;
          int sum = 0;
          int l = L;
          int r = M + 1;
          while (l <= M && r <= R) {
              //相等时先放右边，因为左边还有对右边下一个位置产生小和
              //因为相等不知道右边几个数比左边大
              sum += arr[l] < arr[r] ? (arr[l] * (R - r + 1)) : 0;
              help[index++] = arr[l] < arr[r] ? arr[l++] : arr[r++];
          }
  
          while (l <= M) {
              help[index++] = arr[l++];
          }
  
          while (r <= R) {
              help[index++] = arr[r++];
          }
  
          for (int i = 0; i < help.length; i++) {
              arr[L + i] = help[i];
          }
          return sum;
      }
  
      /**
       * 生成随机长度数组
       *
       * @param maxVale
       * @param maxLen
       * @return
       */
      private static int[] generateRandomArray(int maxVale, int maxLen) {
          int len = (int) (Math.random() * (maxLen + 1));
          int[] arr = new int[len];
          for (int i = 0; i < len; i++) {
              arr[i] = randomNumber(maxVale);
          }
          return arr;
      }
  
      /**
       * 返回[-max,max]中随机一个元素
       *
       * @param maxValue
       * @return
       */
      private static int randomNumber(int maxValue) {
          return (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
      }
  
      public static void main(String[] args) {
          int maxValue = 30;
          int maxLen = 10;
          int times = 10000;
          System.out.println("测试开始");
          for (int i = 0; i < times; i++) {
              int[] arrOriginal = generateRandomArray(maxValue, maxLen);
              int[] arrCopyOne = new int[arrOriginal.length];
              System.arraycopy(arrOriginal, 0, arrCopyOne, 0, arrOriginal.length);
              if (smallSum(arrOriginal) != smallSumByFor(arrCopyOne)) {
                  System.out.println("出错了...");
                  System.exit(1);
              }
          }
          System.out.println("测试结束");
      }
  }
  
  ```

### 3、逆序对$$O(N*logN)$$

1. 问题描述：对于一个数组，如果$$i<j,arr[i] > arr[j]$$则构成一个逆序对，求数组中所有逆序对个数
2. 问题思路：

- 两层for循环暴力求解
- 同上题一样，可以用归并排序。在归并排序中每次统计逆序对，最后结果加起来就是结果。如果左边的当前位置和右边当前位置为逆序对，那么左边当前位置往后的所有位置都与右边当前位置是逆序对，因为数组两部分都是有序的。这里不在详细解释，思路都差不多，只不过统计方式需要改变，这种局部到全局的统计思路都大体相同。

```java
/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-25 22:20
 * @description 求逆序对多少个
 */
public class Code03_ReversePair {

    /**
     * 两次for循环统计逆序对个数
     * @param arr
     * @return
     */
    public static int reversePairByFor(int[] arr){
        if (arr == null || arr.length < 2){
            return 0;
        }
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]){
                    sum++;
                }
            }
        }
        return sum;
    }

    /**
     * 归并排序求小和
     * @param arr
     * @return
     */
    public static int reversePair(int[] arr){
        if (arr == null || arr.length < 2){
            return 0;
        }

        return mergeSort(arr,0,arr.length - 1);
    }

    private static int mergeSort(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        int mid = L + ((R - L) >> 1);
        return mergeSort(arr, L, mid) +
                mergeSort(arr, mid + 1, R) +
                merge(arr, L, mid, R);
    }

    private static int merge(int[] arr, int L, int M, int R) {
        int[] help = new int[R - L + 1];
        int index = 0;
        int sum = 0;
        int l = L;
        int r = M + 1;
        while (l <= M && r <= R) {
            //相等时先放左边，因为左边还有对右边下一个位置产生逆序
            sum += arr[l] > arr[r] ? (M - l + 1) : 0;
            help[index++] = arr[l] <= arr[r] ? arr[l++] : arr[r++];
        }

        while (l <= M) {
            help[index++] = arr[l++];
        }

        while (r <= R) {
            help[index++] = arr[r++];
        }

        for (int i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
        return sum;
    }

    /**
     * 生成随机长度数组
     *
     * @param maxVale
     * @param maxLen
     * @return
     */
    private static int[] generateRandomArray(int maxVale, int maxLen) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = randomNumber(maxVale);
        }
        return arr;
    }

    /**
     * 返回[-max,max]中随机一个元素
     *
     * @param maxValue
     * @return
     */
    private static int randomNumber(int maxValue) {
        return (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
    }

    public static void main(String[] args) {
        int maxValue = 30;
        int maxLen = 10;
        int times = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < times; i++) {
            int[] arrOriginal = generateRandomArray(maxValue, maxLen);
            int[] arrCopyOne = new int[arrOriginal.length];
            System.arraycopy(arrOriginal, 0, arrCopyOne, 0, arrOriginal.length);
            if (reversePair(arrOriginal) != reversePairByFor(arrCopyOne)) {
                System.out.println("出错了...");
                System.exit(1);
            }
        }
        System.out.println("测试结束");
    }
}

```

### 4、新逆序对$$O(N*logN)$$

1. 问题描述：对于一个数组，如果$$i<j,arr[i] > 2 * arr[j]$$则构成一个逆序对，求数组中所有逆序对个数
2. 问题思路：

- 两层for循环暴力求解
- 同上面逆序对一样，不过这里的统计过程和归并过程需要分开，因为统计和归并的条件不同。这也是一个新的启示。

3. 启示：当遇见数组左边比右边怎样，或者右边比左边怎样时，都可以采用归并中处理的思路。如果判断条件和归并条件不同，可以分开处理，先统计再归并。

```java
/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-25 22:46
 * @description 新的逆序对定义
 * 一种新的统计和归并分开方法
 */
public class Code04_BigThanRightTwice {
    /**
     * 两次for循环统计逆序对个数
     * @param arr
     * @return
     */
    public static int bigThanRightTwiceFor(int[] arr){
        if (arr == null || arr.length < 2){
            return 0;
        }
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > 2 * arr[j]){
                    sum++;
                }
            }
        }
        return sum;
    }

    /**
     * 归并排序求小和
     * @param arr
     * @return
     */
    public static int bigThanRightTwice(int[] arr){
        if (arr == null || arr.length < 2){
            return 0;
        }

        return mergeSort(arr,0,arr.length - 1);
    }

    private static int mergeSort(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        int mid = L + ((R - L) >> 1);
        return mergeSort(arr, L, mid) +
                mergeSort(arr, mid + 1, R) +
                merge(arr, L, mid, R);
    }

    private static int merge(int[] arr, int L, int M, int R) {
        int[] help = new int[R - L + 1];
        int index = 0;
        int sum = 0;
        int l = L;
        int r = M + 1;
        while (l <= M && r <= R) {
            //相等时先放左边，因为左边还有对右边下一个位置产生逆序
            if (arr[l] > 2 * arr[r]){
                sum += (M - l + 1);
                l++;
            }else {
                r++;
            }

        }

        //两个下标复原，开始归并过程
        l = L;
        r = M + 1;
        while (l <= M && r <= R) {
            help[index++] = arr[l] <= arr[r] ? arr[l++] : arr[r++];
        }

        while (l <= M) {
            help[index++] = arr[l++];
        }

        while (r <= R) {
            help[index++] = arr[r++];
        }

        for (int i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
        return sum;
    }

    /**
     * 生成随机长度数组
     *
     * @param maxVale
     * @param maxLen
     * @return
     */
    private static int[] generateRandomArray(int maxVale, int maxLen) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = randomNumber(maxVale);
        }
        return arr;
    }

    /**
     * 返回[-max,max]中随机一个元素
     *
     * @param maxValue
     * @return
     */
    private static int randomNumber(int maxValue) {
        return (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
    }

    public static void main(String[] args) {
        int maxValue = 30;
        int maxLen = 10;
        int times = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < times; i++) {
            int[] arrOriginal = generateRandomArray(maxValue, maxLen);
            int[] arrCopyOne = new int[arrOriginal.length];
            System.arraycopy(arrOriginal, 0, arrCopyOne, 0, arrOriginal.length);
            if (bigThanRightTwiceFor(arrOriginal) != bigThanRightTwice(arrCopyOne)) {
                System.out.println("出错了...");
                System.exit(1);
            }
        }
        System.out.println("测试结束");
    }
}

```

## 五、归并排序附加题及快排

### 1、数组指定范围累加和$$O(N*logN)$$------比较难

1. 问题描述：给定一个数组arr，两个整数lower和upper，返回arr中有多少个子数组的累加和在[lower,upper]范围上
2. 问题思路：

- 三层for循环暴力求解
- 生成一个前缀和，优化到$$O(N^2)$$
- 归并排序+前缀和数组$$O(N*logN)$$：求以任何一个位置结尾数组达标的个数，累加起来就是最终结果。如果以j结尾的子数组累加和满足要求，那么就相当于求j之前的有多少前缀和落在[sum(j) - upper,sum(j) - lower]上。现在把解决对象变为前缀和数组，在merge过程中对于右组的每一个元素，在左组找满足要求的元素。

```java
/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-26 21:53
 * @description TODO
 */
public class Code01_CountOfRangeSum {
    /**
     * 三层for循环求解
     *
     * @param arr
     * @param lower
     * @param upper
     * @return
     */
    public static int countRangeSum1(int[] arr, int lower, int upper) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int count = 0;
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j < N; j++) {
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    sum += arr[k];
                }
                if (sum >= lower && sum <= upper) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 两层for循环，前缀和
     *
     * @param arr
     * @param lower
     * @param upper
     * @return
     */
    public static int countRangeSum2(int[] arr, int lower, int upper) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int count = 0;
        int N = arr.length;
        int[] prefixSum = new int[N];
        prefixSum[0] = arr[0];
        for (int i = 1; i < N; i++) {
            prefixSum[i] = prefixSum[i - 1] + arr[i];
        }

        for (int i = 0; i < N; i++) {
            for (int j = i; j < N; j++) {
                int sum = prefixSum[j] - prefixSum[i] + arr[i];
                if (sum >= lower && sum <= upper) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 归并排序求解
     *
     * @param arr
     * @param lower
     * @param upper
     * @return
     */
    public static int countRangeSum3(int[] arr, int lower, int upper) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int N = arr.length;
        int[] prefixSum = new int[N];
        prefixSum[0] = arr[0];
        for (int i = 1; i < N; i++) {
            prefixSum[i] = prefixSum[i - 1] + arr[i];
        }
        return count(prefixSum, 0, arr.length - 1, lower, upper);
    }

    /**
     * 求原始数组L-R范围内多少累加和在lower-upper范围内
     * 换种思路：求解prefixSum中每一个位置包括自己左边达标的数量
     *
     * @param prefixSum
     * @param L
     * @param R
     * @param lower
     * @param upper
     * @return
     */
    public static int count(int[] prefixSum, int L, int R, int lower, int upper) {
        if (L == R) {
            return (prefixSum[L] <= upper && prefixSum[L] >= lower) ? 1 : 0;
        }
        int mid = L + ((R - L) >> 1);
        return count(prefixSum, L, mid, lower, upper) +
                count(prefixSum, mid + 1, R, lower, upper) +
                merge(prefixSum, L, mid, R, lower, upper);
    }

    public static int merge(int[] prefixSum, int L, int M, int R, int lower, int upper) {
        int[] help = new int[R - L + 1];
        int count = 0;
        int index = 0;
        int windowL = L;
        int windowR = L;
        //merge过程左边窗口不会退
        //对于右边每一个位置求左组
        for (int i = M + 1; i <= R; i++) {
            long min = prefixSum[i] - upper;
            long max = prefixSum[i] - lower;
            while (windowR <= M && prefixSum[windowR] <= max) {
                windowR++;
            }
            while (windowL <= M && prefixSum[windowL] < min) {
                windowL++;
            }
            count += windowR - windowL;
        }

        int l = L;
        int r = M + 1;
        while (l <= M && r <= R) {
            if (prefixSum[l] <= prefixSum[r]) {
                help[index++] = prefixSum[l++];
            } else {
                help[index++] = prefixSum[r++];
            }
        }
        //左边没有放完
        while (l <= M) {
            help[index++] = prefixSum[l++];
        }
        //右边没有放完
        while (r <= R) {
            help[index++] = prefixSum[r++];
        }
        //将排序好的数组返回原数组
        for (int i = 0; i < help.length; i++) {
            prefixSum[L + i] = help[i];
        }
        return count;
    }

    /**
     * 生成随机长度数组
     *
     * @param maxVale
     * @param maxLen
     * @return
     */
    private static int[] generateRandomArray(int maxVale, int maxLen) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = randomNumber(maxVale);
        }
        return arr;
    }

    /**
     * 返回[-max,max]中随机一个元素
     *
     * @param maxValue
     * @return
     */
    private static int randomNumber(int maxValue) {
        return (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
    }

    public static void main(String[] args) {
        int maxValue = 30;
        int maxLen = 10;
        int times = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < times; i++) {
            int[] arrOriginal = generateRandomArray(maxValue, maxLen);
            int[] arrCopyOne = new int[arrOriginal.length];
            System.arraycopy(arrOriginal, 0, arrCopyOne, 0, arrOriginal.length);
            if (countRangeSum3(arrOriginal,0,100) != countRangeSum2(arrCopyOne,0,100)) {
                System.out.println("出错了...");
                System.exit(1);
            }
        }
        System.out.println("测试结束");
    }
}

```

### 2、快速排序时间$$O(N*logN)$$，空间$$O(logN)$$

1. 给定一个数组arr，和一个整数num。请把小于等于num的数放在数组的左边，大于num的数放在数组的右边，Partirion过程。

2. 荷兰国旗问题：小于num的数放在数组的左边，等于放中间，大于num的数放在数组的右边。最差时间复杂度$$O(N^2)$$,如果数组有序的话。即1.0和2.0版本。
3. 快排3.0版本，随机选择一个数字和最右边的数进行交换，防止打偏

```java
package class05;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-26 22:51
 * @description TODO
 */
public class Code02_PartitionAndQuickSort {

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * L-R以R为目标，小于等于arr[R]的放左边，大于放右边，返回R应该划分的位置
     *
     * @param arr
     * @param L
     * @param R
     * @return
     */
    public static int partition(int[] arr, int L, int R) {
        if (L > R) {
            return -1;
        }
        if (L == R) {
            return L;
        }

        int lessEqual = L - 1;
        int index = L;
        while (index < R) {
            if (arr[index] <= arr[R]) {
                swap(arr, ++lessEqual, index++);
            } else {
                index++;
            }
        }
        //把目标和大于区域的第一个值进行交换，返回目标值的位置
        swap(arr, ++lessEqual, R);
        return lessEqual;
    }

    /**
     * 荷兰国旗问题
     * L-R以R为目标，小于arr[R]的放左边，等于放中间，大于放右边
     * 返回等于区域的范围
     *
     * @param arr
     * @param L
     * @param R
     * @return
     */
    public static int[] netherLandsFlag(int[] arr, int L, int R) {
        if (L > R) {
            return new int[]{-1, -1};
        }
        if (L == R) {
            return new int[]{L, R};
        }

        int lessLeft = L - 1;
        //最后一个数先不参与
        int rightBig = R;
        int index = L;
        while (index < rightBig) {
            if (arr[index] < arr[R]) {
                swap(arr, index++, ++lessLeft);
            } else if (arr[index] > arr[R]) {
                swap(arr, index, --rightBig);
            } else {
                index++;
            }
        }
        //把目标和大于区域的第一个值进行交换，返回目标值的位置
        swap(arr, rightBig, R);
        return new int[]{lessLeft + 1, rightBig};
    }

    /**
     * 快排1.0,只划分小于等于  大于
     * @param arr
     */
    public static void sort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        quickSort1(arr, 0, arr.length - 1);
    }

    /**
     * 小于等于放左，大于放右边
     *
     * @param arr
     * @param L
     * @param R
     */
    public static void quickSort1(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        int index = partition(arr,0,R);
        quickSort1(arr, L, index - 1);
        quickSort1(arr, index + 1, R);
    }

    /**
     * 快排2.0 划分小于、等于、大于区域
     * @param arr
     */
    public static void sort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        quickSort2(arr, 0, arr.length - 1);
    }

    /**
     * 小于放左，等于放中间，大于放右边
     *
     * @param arr
     * @param L
     * @param R
     */
    public static void quickSort2(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        int[] equalArea = netherLandsFlag(arr, L, R);
        quickSort2(arr, L, equalArea[0] - 1);
        quickSort2(arr, equalArea[0] + 1, R);
    }

    /**
     * 快排3.0 划分小于、等于、大于区域,并且随机选择一个数和最右位置进行交换
     * @param arr
     */
    public static void sort3(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        quickSort3(arr, 0, arr.length - 1);
    }

    /**
     * 3.0 小于放左，等于放中间，大于放右边
     *
     * @param arr
     * @param L
     * @param R
     */
    public static void quickSort3(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        //随机选择一个位置和最右边交换在进行partition过程
        swap(arr, (int) ((Math.random() * (R - L + 1)) + L),R );
        int[] equalArea = netherLandsFlag(arr, L, R);
        quickSort3(arr, L, equalArea[0] - 1);
        quickSort3(arr, equalArea[0] + 1, R);
    }

    /**
     * 生成随机长度数组
     *
     * @param maxVale
     * @param maxLen
     * @return
     */
    private static int[] generateRandomArray(int maxVale, int maxLen) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = randomNumber(maxVale);
        }
        return arr;
    }

    /**
     * 返回[-max,max]中随机一个元素
     *
     * @param maxValue
     * @return
     */
    private static int randomNumber(int maxValue) {
        return (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
    }

    public static void main(String[] args) {
        int maxValue = 30;
        int maxLen = 10;
        int times = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < times; i++) {
            int[] arrOriginal = generateRandomArray(maxValue, maxLen);
            int[] arrCopyOne = new int[arrOriginal.length];
            System.arraycopy(arrOriginal, 0, arrCopyOne, 0, arrOriginal.length);
            Arrays.sort(arrCopyOne);
            sort4(arrOriginal);
            if (!Arrays.equals(arrCopyOne,arrOriginal)) {
                System.out.println("出错了...");
                System.exit(1);
            }
        }
        System.out.println("测试结束");
    }
}

```

### 3、快速排序迭代实现

- 自己压栈即可

```java
import java.util.Stack;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-27 22:51
 * @description TODO
 */
public class Code03_QuickNonRecursive {
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 荷兰国旗问题
     * L-R以R为目标，小于arr[R]的放左边，等于放中间，大于放右边
     * 返回等于区域的范围
     *
     * @param arr
     * @param L
     * @param R
     * @return
     */
    public static int[] netherLandsFlag(int[] arr, int L, int R) {
        if (L > R) {
            return new int[]{-1, -1};
        }
        if (L == R) {
            return new int[]{L, R};
        }

        int lessLeft = L - 1;
        //最后一个数先不参与
        int rightBig = R;
        int index = L;
        while (index < rightBig) {
            if (arr[index] < arr[R]) {
                swap(arr, index++, ++lessLeft);
            } else if (arr[index] > arr[R]) {
                swap(arr, index, --rightBig);
            } else {
                index++;
            }
        }
        //把目标和大于区域的第一个值进行交换，返回目标值的位置
        swap(arr, rightBig, R);
        return new int[]{lessLeft + 1, rightBig};
    }

    /**
     * 快排3.0迭代版本
     * @param arr
     */
    public static void sort4(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }

        swap(arr,arr.length - 1,(int) (Math.random() * (arr.length)));
        int[] quickEquals = netherLandsFlag(arr, 0, arr.length - 1);
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{0,quickEquals[0] -1});
        stack.push(new int[]{quickEquals[1] + 1,arr.length - 1});
        while (!stack.isEmpty()){
            int[] curEquals = stack.pop();
            if (curEquals[0] < curEquals[1]){
                int L = curEquals[0];
                int R = curEquals[1];
                swap(arr,R,(int) (Math.random() * (R - L + 1) + L));
                quickEquals = netherLandsFlag(arr, L, R);
                stack.push(new int[]{L,quickEquals[0] - 1});
                stack.push(new int[]{quickEquals[1] + 1,R});
            }
        }
    }
}

```

## 六、堆和堆排序

### 1、堆结构

- 堆是一个完全二叉树，且每一颗子树的根节点是当前树上的最大值（大根堆）或最小值（小根堆）。
- 完全二叉树：完全二叉树要么是一颗满树，要么除了最后一层不满且节点依次从左到右排列。

```java
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-27 22:57
 * @description 大根堆
 */
public class Code01_Heap<T> {
    private T[] data;
    private int limit;
    private int heapSize;
    private Comparator<T> comparator;

    public Code01_Heap(int limit, Comparator<T> comparator) {
        this.limit = limit;
        heapSize = 0;
        data = (T[]) new Object[limit];
        this.comparator = comparator;
    }

    /**
     * 堆上浮操作
     *
     * @param index 上浮的开始位置
     */
    private void heapInsert(int index) {
        while (comparator.compare(data[index], data[(index - 1) / 2]) > 0) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    /**
     * 堆下沉操作
     *
     * @param index
     * @param heapSize
     */
    private void heapify(int index, int heapSize) {
        //左孩子
        int left = 2 * index + 1;
        while (left < heapSize) {
            //左右孩子较大的
            int largest = left + 1 < heapSize && comparator.compare(data[left + 1], data[(left)]) > 0 ?
                    left + 1 : left;
            //孩子较大的与父亲比较
            largest = comparator.compare(data[largest], data[index]) > 0 ? largest : index;
            if (largest == index) {
                break;
            }
            swap(largest, index);
            index = largest;
            left = 2 * index + 1;
        }
    }

    /**
     * 给堆中添加一个元素
     *
     * @param value
     */
    public void add(T value) {
        if (heapSize == limit) {
            throw new RuntimeException("当前堆已满");
        }
        data[heapSize] = value;
        heapInsert(heapSize++);
    }

    /**
     * 堆中弹出一个元素
     */
    public T pop() {
        if (heapSize == 0) {
            throw new RuntimeException("当前堆为空");
        }
        T result = data[0];
        swap(0, --heapSize);
        heapify(0, heapSize);
        return result;
    }

    /**
     * 交换数组中两个位置
     *
     * @param i
     * @param j
     */
    private void swap(int i, int j) {
        T temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public static void main(String[] args) {
        Code01_Heap<Integer> code01Heap = new Code01_Heap<>(10000, Comparator.comparingInt(o -> o));
        //默认小根堆，需要传入自定义参数
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt((o) -> -o));
        System.out.println("测试开始");
        for (int i = 0;i < 10000;i++){
            int num = (int) (Math.random() * 10000);
            code01Heap.add(num);
            queue.add(num);
        }
        for (int i = 0; i < 10000; i++) {
            if (!code01Heap.pop().equals(queue.poll())){
                System.out.println("出错了");
                System.exit(1);
            }
        }
        System.out.println("测试结束");
    }
}

```

### 2、堆排序$$O(N*logN)$$

```java
import java.util.Arrays;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-28 20:29
 * @description 堆排序
 */
public class Code02_HeapSort {
    /**
     * 堆排序
     * @param arr
     */
    public static void sort(int[] arr){
        heapSort(arr);
    }

    private static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int heapSize = arr.length;
        //建大根堆
        for (int i = 1; i < heapSize; i++) {
            //heapInsert过程 O(logN)
           heapInsert(arr,i);
        }

        //开始逐次将最大值放在数组末尾
        while (heapSize > 1) {
            swap(arr, 0, --heapSize);
            heapify(arr,0,heapSize);
        }
    }

    /**
     * heapInsert过程,O(logN)
     */
    private static void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[(index - 1) / 2]) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    /**
     * heapify过程,O(logN)
     *
     * @param arr
     * @param index
     * @param heapSize
     */
    private static void heapify(int[] arr, int index, int heapSize) {
        int left = 2 * index + 1;
        while (left < heapSize) {
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ?
                    left + 1 : left;
            largest = arr[largest] > arr[index] ? largest : index;
            if (largest == index) {
                break;
            }
            swap(arr, largest, index);
            index = largest;
            left = 2 * index + 1;
        }
    }

    /**
     * 交换数组中两个元素
     * @param arr
     * @param i
     * @param j
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 生成随机长度数组
     *
     * @param maxVale
     * @param maxLen
     * @return
     */
    private static int[] generateRandomArray(int maxVale, int maxLen) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = randomNumber(maxVale);
        }
        return arr;
    }

    /**
     * 返回[-max,max]中随机一个元素
     *
     * @param maxValue
     * @return
     */
    private static int randomNumber(int maxValue) {
        return (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
    }

    public static void main(String[] args) {
        int maxValue = 30;
        int maxLen = 10;
        int times = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < times; i++) {
            int[] arrOriginal = generateRandomArray(maxValue, maxLen);
            int[] arrCopyOne = new int[arrOriginal.length];
            System.arraycopy(arrOriginal, 0, arrCopyOne, 0, arrOriginal.length);
            Arrays.sort(arrCopyOne);
            sort(arrOriginal);
            if (!Arrays.equals(arrCopyOne,arrOriginal)) {
                System.out.println("出错了...");
                System.exit(1);
            }
        }
        System.out.println("测试结束");
    }
}
```

### 3、已知一个几乎有序的数组。几乎有序是指如果把数组排好序之后，每个元素移动的距离一定不超过k，并且k对于数组长度来说是比较小的。选择一个合适的排序策略对这个数组进行排序$$O(N*logK)$$

- 思路：维护一个K大小的堆。假设把数组0-k-1的元素放到堆中，那么堆中最小的元素一定在0位置。然后出一个元素，把下一位元素加入到小根堆，其中最小的元素一定在1位置，直到堆为空。

```java
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-28 21:14
 * @description TODO
 */
public class Code03_SortArrayDistanceLessK {
    
    /**
     * k < arr.length
     * @param arr
     * @param k
     */
    public static void sortArrayDistanceLessK(int[] arr,int k){
        if (k == 0){
            return;
        }

        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int index = 0;
        for (;index < Math.min(arr.length - 1,k - 1);index++){
            heap.add(arr[index]);
        }

        int i = 0;
        for (;index < arr.length;index++,i++){
            heap.add(arr[index]);
            arr[i] = heap.poll();
        }

        while (!heap.isEmpty()){
            arr[i++] = heap.poll();
        }

        System.out.println(Arrays.toString(arr));

    }

}
```

## 七、加强堆

### 1、最大线段重合问题$$O(N*logN)$$

1. 问题描述：给定很多线段，每个线段都有两个数[start, end]，表示线段开始位置和结束位置，左右都是闭区间

   规定：

   1）线段的开始和结束位置一定都是整数值

   2）线段重合区域的长度必须>=1

   返回线段最多重合区域中，包含了几条线段

2. 分析：采用堆结构，按照start的升序将线段进行排序。然后遍历每条线段，准备一个小根堆，按照end进行排序。对于当前遍历到的线段来说。如果堆为空直接加入堆，如果堆中有元素，就把堆中所有end比当前线段start小的或相等的全部弹出，然后当前线段入栈，记录堆中元素的个数，然后开始遍历下一个线段，在遍历过程中，堆的最大size就是答案。

- 暴力求解$$O(N*(max - min))$$
- 堆求解$$O(N*logN)$$

```java
package class07;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-29 11:49
 * @description 最大线段重合问题
 */
public class Code01_CoverMax {
    /**
     * 暴力循环求解
     * @param lines
     * @return
     */
    public static int maxCover1(int[][] lines){
        if (lines == null || lines.length == 0){
            return 0;
        }

        int startMin = lines[0][0];
        int endMax = lines[0][1];
        int N = lines.length;
        for (int i = 1;i < N;i++){
            startMin = Math.min(startMin,lines[i][0]);
            endMax = Math.max(endMax,lines[i][1]);
        }

        int max = 0;
        for (double point = startMin + 0.5;point < endMax;point += 1){
            int count = 0;
            for (int i = 0;i < N;i++){
                if (lines[i][0] < point && lines[i][1] > point){
                    count++;
                }
            }
            max = Math.max(count,max);
        }
        return max;
    }

    /**
     * 采用堆求解
     * @param lines
     * @return
     */
    public static int maxCover2(int[][] lines){
        if (lines == null || lines.length == 0){
            return 0;
        }

        //按照开始位置排序
        Arrays.sort(lines, Comparator.comparingInt(o -> o[0]));
        //小根堆
      //  HeapGreater<Integer> heapGreater = new HeapGreater<>((o1, o2) -> o2 - o1);
        PriorityQueue<Integer> heapGreater = new PriorityQueue<>();
        int max = 0;
        for (int i = 0;i < lines.length;i++){
            while (!heapGreater.isEmpty() && heapGreater.peek() <= lines[i][0]){
                heapGreater.poll();
            }
            heapGreater.add(lines[i][1]);
            max = Math.max(max,heapGreater.size());
        }
        return max;
    }

    /**
     * 生成N条L---R范围内的随机线段
     * @param N
     * @param L
     * @param R
     * @return
     */
    public static int[][] generateLines(int N,int L,int R){
        int[][] lines = new int[N][];
        for (int i = 0;i < N;i++){
            int[] line = new int[2];
            int start = generateRandomNumber(L,R);
            int end = generateRandomNumber(L,R);
            while (end <= start){
                start = generateRandomNumber(L,R);
                end = generateRandomNumber(L,R);
            }
            line[0] = start;
            line[1] = end;
            lines[i] = line;
        }
        return lines;
    }

    public static int generateRandomNumber(int lower,int upper){
        return (int) (Math.random() * (upper - lower + 1 ) + lower);
    }

    public static void main(String[] args) {
        int N = 10;
        int L = 0;
        int R = 10;
        int times = 10;
        System.out.println("测试开始");
        for (int i = 0; i < times; i++) {
            int[][] lines = generateLines(N,L,R);
            int count1 = maxCover1(lines);
            int count2 = maxCover2(lines);
            if (count1 != count2){
                System.out.println(count1 + "\t" + count2);
                System.out.println("出错了...");
                System.exit(1);
            }
        }
        System.out.println("测试结束");
    }
}

```

### 2、给定一个整型数组，int[] arr；和一个布尔类型数组，boolean[] op，比较考验业务逻辑，两种方式实现，暴力求解和堆求解

两个数组一定等长，假设长度为N，arr[i]表示客户编号，op[i]表示客户操作

arr = [ 3  ,  3  ,  1  , 2,   1,   2,   5…

op = [ T  ,  T,   T,   T,   F,   T,    F…

依次表示：3用户购买了一件商品，3用户购买了一件商品，1用户购买了一件商品，2用户购买了一件商品，1用户退货了一件商品，2用户购买了一件商品，5用户退货了一件商品…

一对arr[i]和op[i]就代表一个事件：

用户号为arr[i]，op[i] == T就代表这个用户购买了一件商品

op[i] == F就代表这个用户退货了一件商品

现在你作为电商平台负责人，你想在每一个事件到来的时候，

都给购买次数最多的前K名用户颁奖。

所以每个事件发生后，你都需要一个得奖名单（得奖区）。

得奖系统的规则：

1，如果某个用户购买商品数为0，但是又发生了退货事件，

   则认为该事件无效，得奖名单和上一个事件发生后一致，例子中的5用户

2，某用户发生购买商品事件，购买商品数+1，发生退货事件，购买商品数-1

3，每次都是最多K个用户得奖，K也为传入的参数

   如果根据全部规则，得奖人数确实不够K个，那就以不够的情况输出结果

4，得奖系统分为得奖区和候选区，任何用户只要购买数>0，

   一定在这两个区域中的一个

5，购买数最大的前K名用户进入得奖区，

   在最初时如果得奖区没有到达K个用户，那么新来的用户直接进入得奖区

6，如果购买数不足以进入得奖区的用户，进入候选区

7，如果候选区购买数最多的用户，已经足以进入得奖区，

   该用户就会替换得奖区中购买数最少的用户（大于才能替换），

   如果得奖区中购买数最少的用户有多个，就替换最早进入得奖区的用户

   如果候选区中购买数最多的用户有多个，机会会给最早进入候选区的用户

8，候选区和得奖区是两套时间，

   因用户只会在其中一个区域，所以只会有一个区域的时间，另一个没有

   从得奖区出来进入候选区的用户，得奖区时间删除，

   进入候选区的时间就是当前事件的时间（可以理解为arr[i]和op[i]中的i）

   从候选区出来进入得奖区的用户，候选区时间删除，

   进入得奖区的时间就是当前事件的时间（可以理解为arr[i]和op[i]中的i）

9，如果某用户购买数==0，不管在哪个区域都离开，区域时间删除，

   离开是指彻底离开，哪个区域也不会找到该用户

   如果下次该用户又发生购买行为，产生>0的购买数，

   会再次根据之前规则回到某个区域中，进入区域的时间重记

请遍历arr数组和op数组，遍历每一步输出一个得奖名单

public List<List<Integer>> topK (int[] arr, boolean[] op, int k)

- 巨量代码

```java
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

```

## 八、前缀树、基数排序、计数排序

### 1、前缀树

- 前缀树是一个多叉树，用路径来代表字符。
- 能提供增删改查$$O(M)$$事件复杂度,M为字符串的长度。

```java
/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-02 11:07
 * @description TODO
 */
public class Code01_TireTree {
    private static class Node {
        //有多少字符串经过该节点
        private int pass;
        //有多少字符串以该节点结尾
        private int end;
        //路径
        private Node[] nexts;

        public Node() {
            //只存储小写字符
            nexts = new Node[26];
            pass = 0;
            end = 0;
        }
    }

    //前缀树头节点
    public Node root;

    public Code01_TireTree() {
        root = new Node();
    }

    /**
     * 数上增加一个字符串
     *
     * @param word
     */
    public void insert(String word) {
        if (word == null) {
            return;
        }
        char[] str = word.toCharArray();
        //代表第i条路径
        int path = 0;
        Node cur = root;
        for (int i = 0; i < str.length; i++) {
            path = str[i] - 'a';
            if (cur.nexts[path] == null) {
                cur.nexts[path] = new Node();
            }
            cur = cur.nexts[path];
            cur.pass++;
        }
        cur.end++;
    }

    /**
     * 查找执行字符串在前缀树出现了几次
     *
     * @param word
     * @return
     */
    public int search(String word) {
        if (word == null) {
            return 0;
        }
        char[] str = word.toCharArray();
        int path = 0;
        Node cur = root;
        cur.pass++;
        for (int i = 0; i < str.length; i++) {
            path = str[i] - 'a';
            if (cur.nexts[path] == null) {
                return 0;
            }
            cur = cur.nexts[path];
        }
        return cur.end;
    }

    /**
     * 返回有几个以指定字符串为开头的字符
     * @param word
     * @return
     */
    public int prefixNumber(String word){
        if (word == null) {
            return 0;
        }
        char[] str = word.toCharArray();
        int path = 0;
        Node cur = root;
        for (int i = 0; i < str.length; i++) {
            path = str[i] - 'a';
            if (cur.nexts[path] == null) {
                return 0;
            }
            cur = cur.nexts[path];
        }
        return cur.pass;
    }

    /**
     * 删除前缀树上指定单词
     * @param word
     * @return
     */
    public void delete(String word){
        if (search(word) == 0){
            return;
        }

        char[] str = word.toCharArray();
        Node cur = root;
        cur.pass--;
        int path = 0;
        for (int i = 0;i < str.length;i++){
            path = str[i] - 'a';
            if (--cur.nexts[path].pass == 0){
                cur.nexts[path] = null;
                return;
            }
            cur = cur.nexts[path];
        }
        cur.end--;
    }
}

```

### 2、计数排序-空间时间复杂度都是$$O(n + k)$$

- 一般用于数据范围较小，且无负数的场景

```java
/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-04 22:36
 * @description 计数排序
 */
public class Code02_CountSort {
    /**
     * 计数排序
     *
     * @param arr
     */
    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        countSort(arr);
    }

    private static void countSort(int[] arr) {
        int N = arr.length;
        int[] maxAndMin = findMaxAndMin(arr);
        int[] bucket = new int[maxAndMin[1] - maxAndMin[0] + 1];
        for (int i = 0; i < N; i++) {
            //将负数 转换到 正数的下标
            // 将所有数字限定到 min - max发明为
            bucket[arr[i] - maxAndMin[0]]++;
        }

        //生产累计数组，保证排序稳定性
        for (int i = 1; i < bucket.length; i++) {
            bucket[i] += bucket[i - 1];
        }

        //开始元素倒出桶
        int[] newTable = new int[N];
        for (int i = N - 1; i >= 0; i--) {
            newTable[--bucket[arr[i] - maxAndMin[0]]] = arr[i];
        }

        System.arraycopy(newTable, 0, arr, 0, N);
    }
    
    /**
     * 找到数组的最大值和最小值
     *
     * @param arr
     * @return
     */
    private static int[] findMaxAndMin(int[] arr) {
        int N = arr.length;
        int max = arr[0];
        int min = arr[0];
        for (int i = 1; i < N; i++) {
            max = Math.max(max, arr[i]);
            min = Math.min(min, arr[i]);
        }
        return new int[]{min, max};
    }

    /**
     * 生成随机长度数组
     *
     * @param maxVale
     * @param maxLen
     * @return
     */
    private static int[] generateRandomArray(int maxVale, int maxLen) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = randomNumber(maxVale);
        }
        return arr;
    }

    /**
     * 返回[-max,max]中随机一个元素
     *
     * @param maxValue
     * @return
     */
    private static int randomNumber(int maxValue) {
        return (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
    }

    public static void main(String[] args) {
        int maxValue = 300;
        int maxLen = 100;
        int times = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < times; i++) {
            int[] arrOriginal = generateRandomArray(maxValue, maxLen);
            int[] arrCopyOne = new int[arrOriginal.length];
            System.arraycopy(arrOriginal, 0, arrCopyOne, 0, arrOriginal.length);
            Arrays.sort(arrOriginal);
            sort(arrCopyOne);
            if (!Arrays.equals(arrCopyOne, arrOriginal)) {
                System.out.println("出错了...");
                System.exit(1);
            }
        }
        System.out.println("测试结束");
    }


}

```

### 3、基数排序-空间复杂度$$O(n + k)$$，时间复杂度$$O(k*n)$$

- 遍历最大位数的十进制数，从低位到高位依次进行桶排序

```java
package class08;

import java.util.Arrays;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-04 23:23
 * @description 基数排序, 不能出现负数
 */
public class Code03_RadixSort {
    /**
     * 基数排序
     *
     * @param arr
     */
    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        radixSort(arr);
    }

    public static void radixSort(int[] arr, int base) {
        //10进制的桶
        final int radix = base;
        int N = arr.length;
        int[] help = new int[N];
        int max = Arrays.stream(arr).max().getAsInt();
        System.out.println(max);
        int digit = maxBits(max, radix);
        System.out.println(digit);
        //对每一位进行排序
        for (int d = 1; d <= digit; d++) {
            int[] bucket = new int[radix];
            for (int i = 0; i < arr.length; i++) {
                //获取当前数字 radix进制下 右边第i位的值
                int j = getDigit(arr[i], d, radix);
                bucket[j]++;
            }

            //累加
            for (int i = 1; i < radix; i++) {
                bucket[i] += bucket[i - 1];
            }

            //出桶
            for (int i = N - 1; i >= 0; i--) {
                int j = getDigit(arr[i], d, radix);
                help[--bucket[j]] = arr[i];
            }

            for (int i = 0; i < N; i++) {
                arr[i] = help[i];
            }
        }
    }

    public static void radixSort(int[] arr) {
        radixSort(arr, 10);
    }

    /**
     * 求当前数字base进制的最大位数
     *
     * @param number
     */
    public static int maxBits(int number, int base) {
        int result = 0;
        while (number != 0) {
            result++;
            number /= base;
        }
        return result;
    }

    /**
     * 获取当前数字base进制下右边第d位的值
     *
     * @param number
     * @param d
     * @param base
     * @return
     */
    public static int getDigit(int number, int d, int base) {
        return (number / (int) (Math.pow(base, d - 1))) % base;
    }

    /**
     * 生成随机长度数组
     *
     * @param maxVale
     * @param maxLen
     * @return
     */
    private static int[] generateRandomArray(int maxVale, int maxLen) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * (maxLen + 1));
        }
        return arr;
    }
    
    public static void main(String[] args) {
        int maxValue = 300;
        int maxLen = 100;
        int times = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < times; i++) {
            int[] arrOriginal = generateRandomArray(maxValue, maxLen);
            int[] arrCopyOne = new int[arrOriginal.length];
            System.arraycopy(arrOriginal, 0, arrCopyOne, 0, arrOriginal.length);
            Arrays.sort(arrOriginal);
            sort(arrCopyOne);
            if (!Arrays.equals(arrCopyOne, arrOriginal)) {
                System.out.println("出错了...");
                System.exit(1);
            }
        }
        System.out.println("测试结束");
    }
}

```

