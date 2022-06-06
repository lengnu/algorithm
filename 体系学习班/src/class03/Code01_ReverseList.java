package class03;

import java.util.ArrayList;
import java.util.List;

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
