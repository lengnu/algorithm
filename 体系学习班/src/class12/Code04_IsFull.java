package class12;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-28 21:06
 * @description 判断一棵二叉树是否为满树
 */
public class Code04_IsFull {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    private static class Info{
        public int size;
        public int height;

        public Info(int size, int height) {
            this.size = size;
            this.height = height;
        }
    }

    /**
     * 判断一棵树是否为满树
     * @param root
     * @return
     */
    public static boolean isFull(Node root){
        if (root == null){
            return true;
        }
        Info info = process(root);
        return info.size == (1 << info.height - 1);
    }

    private static Info process(Node x){
        if (x == null){
            return new Info(0,0);
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);
        return new Info(leftInfo.size + rightInfo.size + 1,1 + Math.max(leftInfo.height ,rightInfo.height));
    }
}
