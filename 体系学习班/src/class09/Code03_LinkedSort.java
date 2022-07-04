package class09;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-16 18:48
 * @description 链表partition
 */
public class Code03_LinkedSort {
    /**
     * 链表节点
     */
    private static class Node {
        private int value;
        private Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static void print(Node head) {
        while (head != null) {
            System.out.print(head.value + "\t");
            head = head.next;
        }
        System.out.println();
    }

    /**
     * 给定一个链表，根据value将链表调整为 小于区域，-> 等于区域 -> 大于区域，返回小于区域的头
     *
     * @param head
     * @param value
     * @return
     */
    public static Node sort(Node head, int value) {
        if (head == null || head.next == null) {
            return head;
        }

        //分为3个区域，记录三个区域的头尾节点
        Node smallHead = null;
        Node smallTail = null;
        Node equalHead = null;
        Node equalTail = null;
        Node bigHead = null;
        Node bigTail = null;
        Node next = null;
        Node cur = head;
        while (cur != null) {
            next = cur.next;
            cur.next = null;
            if (cur.value < value) {
                if (smallHead == null) {
                    smallHead = cur;
                    smallTail = cur;
                } else {
                    smallTail.next = cur;
                    smallTail = cur;
                }
            } else if (cur.value > value) {
                if (bigHead == null) {
                    bigHead = cur;
                    bigTail = cur;
                } else {
                    bigTail.next = cur;
                    bigTail = cur;
                }
            } else {
                if (equalHead == null) {
                    equalHead = cur;
                    equalTail = cur;
                } else {
                    equalTail.next = cur;
                    equalTail = cur;
                }
            }
            cur = next;
        }

        //接下来把3部分连接起来
        //1.有小于区域
        if (smallTail != null) {
            smallTail.next = equalHead;
            equalHead = equalTail == null ? bigHead : equalHead;
        }

        //2.有等于区域
        if (equalTail != null) {
            equalTail.next = bigHead;
        }


        return smallHead != null ? smallHead : equalHead != null ? equalHead : bigHead;
    }

    public static void main(String[] args) {

        Node node1 = new Node(1);
        Node node2 = new Node(5);
        Node node3 = new Node(8);
        Node node4 = new Node(4);
        Node node5 = new Node(3);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        print(node1);
        Node sort = sort(node1, 3);
        print(sort);
    }
}
