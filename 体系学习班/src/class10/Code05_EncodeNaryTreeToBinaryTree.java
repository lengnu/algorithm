package class10;

import class07.HeapGreater;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-27 15:19
 * @description 二叉树与多叉树的转换
 */
public class Code05_EncodeNaryTreeToBinaryTree {
    //多叉树
    public static class Node{
        public int val;
        public List<Node> children;

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, List<Node> children) {
            this.val = val;
            this.children = children;
        }
    }

    //二叉树
    public static class TreeNode{
        public int val;
        public TreeNode left;
        public TreeNode right;


        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    //将多叉树转二叉树
    public static TreeNode encode(Node root){
        if(root == null){
            return null;
        }
        TreeNode head = new TreeNode(root.val);
        head.left = en(root.children);
        return head;
    }

    public static TreeNode en(List<Node> children){
        TreeNode head = null;
        TreeNode cur = null;
        for (Node child : children) {
            TreeNode tNode = new TreeNode(child.val);
            if (head == null){
                head = tNode;
            }else {
                cur.right = tNode;
            }
            cur = tNode;
            cur.left = en(child.children);
        }
        return head;
    }

    /**
     * 二叉树转多叉树
     */
    public static Node decode(TreeNode head){
        if (head == null){
            return null;
        }
        return new Node(head.val,de(head.left));
    }

    private static List<Node> de(TreeNode root) {
        List<Node> children = new ArrayList<>();
        while (root != null) {
           Node cur = new Node(root.val,de(root.left));
            children.add(cur);
            root = root.right;
        }
        return children;
    }

}
