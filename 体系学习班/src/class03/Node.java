package class03;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-23 10:47
 * @description 单链表
 */
public class Node<T> {
    public T value;
    public Node<T> next;

    public Node(T value) {
        this.value = value;
    }
}
