package class13;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-07-03 11:10
 * @description 返回最低公共祖先
 */
public class Code02_LowestAncestor {
    public static class TreeNode{
        public int value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }
    }

    public static class Info{
       public boolean findA;
       public boolean findB;
       public TreeNode ans;

        public Info(boolean findA, boolean findB, TreeNode ans) {
            this.findA = findA;
            this.findB = findB;
            this.ans = ans;
        }
    }

    public static TreeNode lowestAncestor(TreeNode root,TreeNode a,TreeNode b){
        return precess(root,a,b).ans;
    }

    public static Info precess(TreeNode x,TreeNode a,TreeNode b){
        if (x == null) {
            return new Info(false,false,null);
        }
        Info leftInfo = precess(x.left,a,b);
        Info rightInfo = precess(x.right,a,b);
        boolean findA = leftInfo.findA || rightInfo.findA || (x == a);
        boolean findB = leftInfo.findB || rightInfo.findB || (x == b);
        TreeNode ans = null;
        if (leftInfo.ans != null){
            ans = leftInfo.ans;
        }else if (rightInfo.ans != null){
            ans = rightInfo.ans;
        }else{
            //左树和右树都没有发现答案
            if (findA && findB){
                ans = x;
            }
        }
        return new Info(findA,findB,ans);
    }

}

