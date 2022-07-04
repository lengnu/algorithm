package class12;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-28 20:55
 * @description 判断一棵树是否为平衡二叉树
 */
public class Code03_IsBalanced {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    private static class Info{
        public int height;
        public boolean isBalanced;

        public Info(int height, boolean isBalanced) {
            this.height = height;
            this.isBalanced = isBalanced;
        }
    }

    public static boolean isBalanced(Node root){
        if (root == null){
            return true;
        }
        return process(root).isBalanced;
    }

    private static Info process(Node x){
        if (x == null){
            return new Info(0,true);
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);
        int height = Math.max(leftInfo.height,rightInfo.height) + 1;
        boolean isBalanced =true;
        if (!leftInfo.isBalanced || !rightInfo.isBalanced || Math.abs(leftInfo.height - rightInfo.height) > 1){
            isBalanced = false;
        }
        return new Info(height,isBalanced);
    }
}
