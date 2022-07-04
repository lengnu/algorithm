package class09;

import java.awt.*;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-22 20:36
 * @description 判断两个链表是否相交
 */
public class Code05_FindFirstIntersectNode {
    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 判断一个链表是否有环，如果没有返回null，有就返回第一个入环节点
     *
     * @param head
     * @return
     */
    public static Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node fast = head.next.next;
        Node slow = head.next;
        while (fast != slow) {
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }

        //快指针回到开头
        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * 判断两个无环链表是否相交，相交返回第一个相交节点，否则返回null
     *
     * @param head1
     * @param head2
     */
    public static Node noLoop(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        int n = 0;
        //统计第一个链表长度
        Node cur1 = head1;
        while (cur1.next != null) {
            cur1 = cur1.next;
            n++;
        }

        //统计第二个链表长度
        Node cur2 = head2;
        while (cur2.next != null) {
            cur2 = cur2.next;
            n--;
        }
        if (cur1 != cur2) {
            return null;
        }
        //让cur1指向长链表的开头
        cur1 = n > 0 ? head1 : head2;
        cur2 = cur1 == head1 ? head2 : head1;
        n = Math.abs(n);

        //长链表先走n步
        while (n-- > 0) {
            cur1 = cur1.next;
        }

        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    /**
     * 判断两个有环链表是否相交
     *
     * @param head1 第一个链表头节点
     * @param loop1 第一个链表入环节点
     * @param head2 第二个链表的头节点
     * @param loop2 第二个链表入环节点
     * @return
     */
    public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
        if (head1 == null || head2 == null || loop1 == null || loop2 == null) {
            return null;
        }
        //1.入环节点相同
        if (loop1 == loop2) {
            int n = 0;
            Node cur1 = head1;
            Node cur2 = head2;
            while (cur1 != loop1) {
                n++;
                cur1 = cur1.next;
            }
            while (cur2 != loop2) {
                n--;
                cur2 = cur2.next;
            }
            //让cur1指向长链表的开头
            cur1 = n > 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 : head1;
            n = Math.abs(n);
            while (n-- > 0) {
                cur1 = cur1.next;
            }
            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        } else {
            //2.入环节点不同
            Node cur = loop1.next;
            while (cur != loop1) {
                if (cur == loop2) {
                    return loop1;
                }
                cur = cur.next;
            }
            return null;

        }
    }

    public static Node getInterectNode(Node head1,Node head2){
        if(head1 == null || head2 == null){
            return null;
        }

        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);

        //1.两个无环链表
        if(loop1 == null && loop2 == null){
            return noLoop(head1,head2);
        }

        //2.两个有环链表
        if(loop1 != null && loop2 != null){
            return bothLoop(head1,loop1,head2,loop2);
        }

        //3.1个有环一个无环不可能相交
        return null;
    }

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        node1.next = node2;
        node2.next = node3;
        node4.next = node5;
        node3.next = node5;
        node5.next = node6;
        System.out.println(getInterectNode(node1, node4).value);
    }
}
