package class10;

import java.util.Stack;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-22 22:17
 * @description 二叉树的非递归三种遍历方式
 */
public class Code02_RecursiveUnTravesalBinaryTree {

    /**
     * 二叉树的非递归先序遍历
     *
     * @param root
     */
    public static void pre(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode cur = stack.pop();
            System.out.print(cur.value + "\t");
            if (cur.right != null) {
                stack.push(cur.right);
            }
            if (cur.left != null) {
                stack.push(cur.left);
            }
        }
        System.out.println();
    }

    /**
     * 二叉树的非递归中序遍历
     * @param root
     */
    public static void in(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (!stack.isEmpty() || cur != null) {
            if (cur != null){
                stack.push(cur);
                cur = cur.left;
            }else {
                cur = stack.pop();
                System.out.print(cur.value + "\t");
                cur = cur.right;
            }
        }
        System.out.println();
    }

    /**
     * 二叉树的非递归后序遍历
     *
     * @param root
     */
    public static void pos(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> help = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode cur = stack.pop();
            help.push(cur);
            if (cur.left != null) {
                stack.push(cur.left);
            }
            if (cur.right != null) {
                stack.push(cur.right);
            }
        }
        while (!help.isEmpty()){
            System.out.print(help.pop().value + "\t");
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
        pre(root);
        System.out.println();
        in(root);
        System.out.println();
        pos(root);
    }
}
