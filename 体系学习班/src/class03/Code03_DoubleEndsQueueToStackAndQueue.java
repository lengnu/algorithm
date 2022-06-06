package class03;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-23 12:43
 * @description 双向链表实现队列和栈
 */
public class Code03_DoubleEndsQueueToStackAndQueue {
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

}
