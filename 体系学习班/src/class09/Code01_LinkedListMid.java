package class09;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-06 23:49
 * @description 快慢指针
 */
public class Code01_LinkedListMid {
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
     * 链表长度奇数时返回中点，偶数时返回上中点
     * 慢指针先走就是下中点，快指针先走就是上中点
     * @param head
     * @return
     */
    public static Node midOrDownMidNode(Node head){
        if (head == null || head.next == null || head.next.next == null){
            return head;
        }

        Node fast = head.next.next;
        Node slow = head.next;

        while (fast != null && fast.next != null){
            slow = slow.next;
            //fast永远在奇数位置，fast不满足时两种情况
            //1.fast == null 奇数个节点，直接返回slow
            //2.fast.next == null ，偶数个节点，每次slow在fast之前走，所有返回时slow已经是下中点了
            fast = fast.next.next;
        }

        return slow;
    }

    /**
     * 链表长度奇数时返回中点，偶数时返回下中点
     * 慢指针先走就是下中点，快指针先走就是上中点
     * @param head
     * @return
     */
    public static Node midOrUpMidNode(Node head){
        if (head == null || head.next == null || head.next.next == null){
            return head;
        }

        Node fast = head.next.next;
        Node slow = head.next;

        while (fast != null && fast.next != null){
            fast = fast.next.next;
            //快指针永远是奇数位置，当快指针位为null时说明链表偶数个节点
            //这时候慢指针不用走了，就直接是上中点
            if (fast == null){
                break;
            }
            slow = slow.next;

        }
        return slow;
    }


    /**
     * 链表长度奇数时返回中点的前一个，偶数时返回上中点前一个
     * 借助前面的midOrUpMidNode，在用一个变量记录slow的pre
     *  midOrUpMidNode可知 奇数 返回中点，偶数返回上中点
     *  那么slow的pre，奇数 返回中点前一个，偶数返回上中点的前一个
     * @param head
     * @return
     */
    public static Node midPreAndUpPre(Node head){
        if (head == null || head.next == null || head.next.next == null){
            return head;
        }

        Node fast = head.next.next;
        Node slow = head.next;
        Node pre = head;

        while (fast != null && fast.next != null){
            fast = fast.next.next;
            if (fast == null){
                break;
            }
            pre = slow;
            slow = slow.next;
        }
        return pre;
    }

    /**
     * 链表长度奇数时返回中点的前一个，偶数时返回下中点的前一个
     * 借助前面的midOrDownMidNode，在用一个变量记录slow的pre
     *  midOrDownMidNode可知 奇数 返回中点，偶数返回下中点
     *  那么slow的pre，奇数 返回中点前一个，偶数返回下中点的前一个
     * @param head
     * @return
     */
    public static Node midUpAndDownPre(Node head){
        if (head == null || head.next == null || head.next.next == null){
            return head;
        }

        Node fast = head.next.next;
        Node slow = head.next;
        Node pre = head;

        while (fast != null && fast.next != null){
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        return pre;
    }

    public static void main(String[] args) {
        Node test = null;
        test = new Node(0);
        test.next = new Node(1);
        test.next.next = new Node(2);
        test.next.next.next = new Node(3);
        test.next.next.next.next = new Node(4);
        test.next.next.next.next.next = new Node(5);
        test.next.next.next.next.next.next = new Node(6);
        test.next.next.next.next.next.next.next = new Node(7);
        //test.next.next.next.next.next.next.next.next = new Node(8);

        System.out.println(midOrDownMidNode(test).value);
        System.out.println(midOrUpMidNode(test).value);
        System.out.println(midPreAndUpPre(test).value);
        System.out.println(midUpAndDownPre(test).value);
    }
}
