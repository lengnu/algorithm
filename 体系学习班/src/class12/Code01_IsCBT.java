package class12;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-28 12:22
 * @description 判断一棵树是否为完全二叉树
 */
public class Code01_IsCBT {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 判断是否为完全二叉树
     *
     * @param root
     * @return
     */
    public static boolean isCBT(Node root) {
        if (root == null) {
            return true;
        }
        //进行层序遍历
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        //记录是否已经存在两个孩子不安的节点
        boolean flag = false;
        while (!queue.isEmpty()) {
            root = queue.poll();
            Node left = root.left;
            Node right = root.right;
            if (
                //有右孩子灭有左孩子，不是完全二叉树
                    (left == null && right != null)
                            ||
                            //已经存在孩子不全的节点且 目前遍历节点存在孩子，不是完全二叉树
                            (flag && (left != null || right != null))
            ) {
                return false;
            }
            if (left != null) {
                queue.add(left);
            }
            if (right != null) {
                queue.add(right);
            }
            if (left == null || right == null){
                flag = true;
            }
        }
        return true;
    }

}
