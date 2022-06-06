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
