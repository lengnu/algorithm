package class09;

import java.util.HashMap;
import java.util.Map;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-16 19:42
 * @description 复制链表
 */
public class Code04_LinkedListRandomCopy {
    public static class RandomNode {
        public int value;
        public RandomNode next;
        public RandomNode random;

        public RandomNode(int value) {
            this.value = value;
            next = null;
            random = null;
        }
    }

    /**
     * 生成指定长度的随机链表
     * @param number
     * @return
     */
    public static RandomNode generateRandomNode(int number) {
        if (number <= 0) {
            return null;
        }
        RandomNode head = new RandomNode((int) (Math.random() * 100));
        Map<Integer, RandomNode> map = new HashMap<>();
        map.put(0, head);
        RandomNode cur = head;
        for (int i = 1; i < number; i++) {
            cur.next = new RandomNode((int) (Math.random() * 100));
            cur = cur.next;
            map.put(i, cur);
        }

        for (int i = 0; i < number; i++) {
            map.get(i).random = map.get((int)(Math.random() * number));
        }
        return head;
    }


    /**
     * 通过Map的方式进行链表的复制
     * @param head
     * @return
     */
    public static RandomNode  copyLinkedListRandomByMap(RandomNode head){
        if (head == null){
            return null;
        }
        RandomNode cur = head;
        Map<RandomNode,RandomNode> map = new HashMap<>();
        while (cur != null){
            map.put(cur,new RandomNode(cur.value));
            cur = cur.next;
        }

        cur = head;
        while (cur != null){
            map.get(cur).next = map.get(cur.next);
            map.get(cur).random = map.get(cur.random);
            cur = cur.next;
        }
        return map.get(head);
    }

    /**
     * 通过在原链表每个节点后面添加一个复制节点来进行链表的复制
     * @param head
     * @return
     */
    public static RandomNode  copyLinkedListRandom(RandomNode head){
        if (head == null){
            return null;
        }
       RandomNode cur = head;
        while (cur != null){
            RandomNode copyRandomNode = new RandomNode(cur.value);
            copyRandomNode.next = cur.next;
            cur.next = copyRandomNode;
            cur = copyRandomNode.next;
        }

        cur = head;
        while (cur != null){
            cur.next.random = cur.random.next;
            cur = cur.next.next;
        }
        cur = head;
        RandomNode copyHead = cur.next;
        RandomNode copyCur = copyHead;
        while (cur != null ){
            cur.next = copyCur.next;
            if (copyCur.next == null){
                break;
            }
            cur = cur.next;
            copyCur.next = cur.next;
            copyCur = copyCur.next;
        }
        return copyHead;
    }

    public static void print(RandomNode head){
        while (head != null){
           System.out.print("cur = " + head.value + " random = " + head.random.value + ";\t");
            head = head.next;
        }
        System.out.println();
    }
    public static void main(String[] args) {
        RandomNode node = generateRandomNode(10);
        print(node);
        RandomNode randomNode = copyLinkedListRandomByMap(node);
        print(randomNode);
        RandomNode randomNode1 = copyLinkedListRandom(node);
        print(randomNode1);
    }
}
