package class10;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-22 22:30
 * @description 二叉树的层序遍历
 */
public class Code03_LevelTraversalBinaryTree {
    /**
     * 二叉树的层序遍历
     *
     * @param root
     */
    public static void levelTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            System.out.print(cur.value + "\t");
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.right = new TreeNode(3);
        root.right = new TreeNode(4);
        root.right.left = new TreeNode(5);
        root.right.left.right = new TreeNode(6);
        levelTraversal(root);
    }

}

