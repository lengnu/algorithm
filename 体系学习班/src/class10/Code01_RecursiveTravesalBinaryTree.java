package class10;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-22 21:13
 * @description 二叉树的递归三种遍历方式
 */
public class Code01_RecursiveTravesalBinaryTree {
    /**
     * 先序遍历
     *
     * @param root
     */
    public static void pre(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.print(root.value + "\t");
        pre(root.left);
        pre(root.right);
    }

    /**
     * 中序遍历
     *
     * @param root
     */
    public static void in(TreeNode root) {
        if (root == null) {
            return;
        }
        in(root.left);
        System.out.print(root.value + "\t");
        in(root.right);
    }

    /**
     * 后序遍历
     *
     * @param root
     */
    public static void pos(TreeNode root) {
        if (root == null) {
            return;
        }
        pos(root.left);
        pos(root.right);
        System.out.print(root.value + "\t");
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.right = new TreeNode(3);
        root.right = new TreeNode(4);
        root.right.left = new TreeNode(5);
        root.right.left.right = new TreeNode(6);
        pre(root);
        System.out.println();
        in(root);
        System.out.println();
        pos(root);
    }
}
