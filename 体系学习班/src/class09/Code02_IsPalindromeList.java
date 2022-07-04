package class09;

import java.util.Stack;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-07 0:21
 * @description TODO
 */
public class Code02_IsPalindromeList {
    /**
     * 链表节点
     */
    private static class Node{
        private int value;
        private Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 通过栈判断链表是否为回文
     * @param head
     * @return
     */
    public static boolean isPalindromeByStack(Node head){
        if (head == null || head.next == null){
            return true;
        }
        Stack<Integer> stack = new Stack<>();
        Node cur = head;
        while (cur != null){
            stack.push(cur.value);
            cur = cur.next;
        }

        cur = head;
        while (cur != null){
            if (stack.pop() != cur.value){
                return false;
            }
            cur = cur.next;
        }
        return true;
    }

    /**
     * 通过反转链表的方式找到是否为回文
     * @param head
     * @return
     */
    public static boolean isPalindromeByReverseList(Node head){
        if (head == null || head.next == null){
            return true;
        }
        //1.首先找到链表的上中点
        Node fast = head.next;
        Node slow = head;
        while (fast != null && fast.next != null){
            fast = fast.next.next;
            if (fast == null){
                break;
            }
            slow = slow.next;
        }
        //记录左边的最后一个节点
        Node leftLast = slow;

        //2.slow为奇数下的中点，偶数下的上中点，把后面的部分逆序
        //右边部分的第一个节点
        Node rightFirst = slow.next;
        //前面第一部分和第二部分断开
        slow.next = null;
        //开始逆序
        Node pre = null;
        Node next = null;
        while (rightFirst != null){
            next = rightFirst.next;
            rightFirst.next = pre;
            pre = rightFirst;
            rightFirst = next;
        }
        //现在pre为后半部分逆序完成之后的头节点
        //用fast记录逆序完成之后链表的头节点，防止找不到
        fast = pre;
        //用slow记录左边部分的头节点
        slow = head;
        boolean res = true;
        while (fast != null && slow != null){
            if (fast.value != slow.value){
                //不能直接退出，需要将原链表调整正确
                res = false;
                break;
            }
            fast = fast.next;
            slow = slow.next;
        }

        //3.开始恢复链表

        slow = null;
        next = pre;
        while (pre != null){
            next = pre.next;
            pre.next = slow;
            slow = pre;
            pre = next;
        }
        //现在slow为翻转过后的头节点
        leftLast.next = slow;
        return res;
    }

    public static void printNode(Node head){
        while (head != null){
            System.out.print(head.value + "\t");
            head = head.next;
        }
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(2);
        head.next.next.next.next = new Node(1);
        System.out.println(isPalindromeByStack(head));
        printNode(head);
        System.out.println(isPalindromeByReverseList(head));
        printNode(head);

    }
}
