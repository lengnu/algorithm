package class10;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-27 15:48
 * @description 得到中序遍历下某个节点的后继节点
 */
public class Code07_GetSuccessorNode {
    public static class Node {
        public int value;
        public Node left;
        public Node right;
        public Node parent;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node getSuccessorNode(Node node) {
        if (node == null) {
            return null;
        }
        if (node.right != null) {
            Node cur = node.right;
            while (cur.left != null) {
                cur = cur.left;
            }
            return cur;
        } else {
            Node parent = node.parent;
            //父亲不为null且当前节点是父亲的右孩子就继续寻找
            while (parent != null && parent.right == node) {
                node = parent;
                parent = node.parent;
            }
            return parent;
        }
    }
}
