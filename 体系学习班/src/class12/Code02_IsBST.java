package class12;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-28 20:40
 * @description 判断一棵树是否为搜索二叉树
 */
public class Code02_IsBST {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    //记录二叉树递归过程中左子树和右子树的信息
    private static class Info{
        public int max;
        public int min;
        public boolean isCBT;

        public Info(int max, int min, boolean isCBT) {
            this.max = max;
            this.min = min;
            this.isCBT = isCBT;
        }
    }

    /**
     * 判断一棵树是否为搜索二叉树
     * @param root
     * @return
     */
    public static boolean isCBT(Node root){
        if (root == null){
            return true;
        }
        return process(root).isCBT;
    }

    private static Info process(Node x){
        if (x == null){
            return null;
        }
        //拿到左子树和右子树的信息
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);
        int min = x.value;
        int max = x.value;
        if (leftInfo != null){
            min = Math.min(min,leftInfo.min);
            max = Math.max(max,leftInfo.max);
        }
        if (rightInfo != null){
            min = Math.min(min,rightInfo.min);
            max = Math.max(max,rightInfo.max);
        }
        boolean isCBT = true;
        //找出不满足搜索二叉树的条件
        if (leftInfo != null && !leftInfo.isCBT){
            isCBT = false;
        }
        if (rightInfo != null && !rightInfo.isCBT){
            isCBT = false;
        }
        if (leftInfo != null && !(leftInfo.max < x.value)){
            isCBT = false;
        }
        if (rightInfo != null && !(rightInfo.min > x.value)){
            isCBT = false;
        }
        return new Info(max,min,isCBT);
    }

}
