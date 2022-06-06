package class03;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-23 12:33
 * @description TODO
 */
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
