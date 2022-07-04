package class13;

import java.lang.invoke.VolatileCallSite;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-07-03 10:43
 * @description 判断一棵二叉树是否为完全二叉树
 */
public class Code01_IsCBT {
    public static class TreeNode{
        public int value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }
    }

    public static class Info{
        public int height;
        public boolean isCBT;
        public boolean isFull;

        public Info(int height, boolean isCBT, boolean isFull) {
            this.height = height;
            this.isCBT = isCBT;
            this.isFull = isFull;
        }
    }

    public static boolean isCBT(TreeNode root){
        if (root == null){
            return true;
        }
        return process(root).isCBT;
    }

    public static Info process(TreeNode x){
        if (x == null){
            return new Info(0,true,true);
        }

        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);
        int height = Math.max(leftInfo.height,rightInfo.height) + 1;
        boolean isFull = false;
        boolean isCBT = false;
        if (leftInfo.isFull && rightInfo.isFull && (rightInfo.height == leftInfo.height)){
            isFull = true;
            isCBT = true;
        }

        if (leftInfo.isFull && rightInfo.isFull && ((rightInfo.height + 1 )== leftInfo.height)){
            isCBT = true;
        }

        if (leftInfo.isCBT && rightInfo.isFull && ((rightInfo.height + 1 )== leftInfo.height)){
            isCBT = true;
        }

        if (leftInfo.isFull && rightInfo.isCBT && (rightInfo.height == leftInfo.height)){
            isCBT = true;
        }
        return new Info(height,isCBT,isFull);
    }

    public static boolean isCBT1(TreeNode root){
        if (root == null){
            return true;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        //记录是否遇到孩子不全的节点
        boolean flag = false;
        queue.add(root);
        while (!queue.isEmpty()){
            TreeNode cur = queue.poll();
            TreeNode left = cur.left;
            TreeNode right = cur.right;
            if (left == null && right != null){
                return false;
            }
            if (flag && (left != null || right != null)) {
                return  false;
            }

            if (left != null){
                queue.add(left);
            }
            if (right != null){
                queue.add(right);
            }
            if (left == null || right == null) {
                flag = true;
            }
        }
        return true;
    }

    public static TreeNode generateTree(int level){
        return generate(1,level);
    }

    public static TreeNode generate(int cur,int level){
        if (cur >= level || Math.random() < 0.5){
            return null;
        }
        TreeNode root = new TreeNode((int) (Math.random() * 1000));
        root.left = generate(cur + 1,level);
        root.right = generate(cur + 1,level);
        return root;
    }

    public static void main(String[] args) {
        int times = 10;
        int level = 5;
        System.out.println("测试开始...");
        for (int i = 0; i < times; i++) {
            TreeNode root = generateTree(level);
            if (isCBT(root) != isCBT1(root)){
                System.out.println("出错了...");
                System.exit(1);
            }
        }
        System.out.println("测试结束...");
    }

}
