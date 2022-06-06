package class03;

import java.util.Stack;

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
