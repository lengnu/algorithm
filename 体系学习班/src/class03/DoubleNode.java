package class03;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-23 10:50
 * @description 双链表
 */
public class DoubleNode<T> {
    public T value;
    public DoubleNode<T> last;
    public DoubleNode<T> next;

    public DoubleNode(T value) {
        this.value = value;
    }
}
