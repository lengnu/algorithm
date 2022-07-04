//package class13;
//
///**
// * @author duwei
// * @version 1.0.0
// * @create 2022-07-03 11:48
// * @description 最大二叉搜索子树的头节点
// */
//public class Code04_MaxSubSBTRoot {
//    public static class TreeNode {
//        public int value;
//        public TreeNode left;
//        public TreeNode right;
//
//        public TreeNode(int value) {
//            this.value = value;
//        }
//    }
//
//    public static class Info {
//        public int max;
//        public int min;
//        public int maxSubSBTSize;
//        public boolean isSBT;
//
//        public Info(int max, int min, int maxSubSBTSize, boolean isSBT) {
//            this.max = max;
//            this.min = min;
//            this.maxSubSBTSize = maxSubSBTSize;
//            this.isSBT = isSBT;
//        }
//    }
//
//    public static TreeNode maxSubSBTRoot(TreeNode root) {
//        if (root == null) {
//            return root;
//        }
//    }
//
//    public static Info process(TreeNode x) {
//        if (x == null) {
//            return null;
//        }
//        int max = x.value;
//        int min = x.value;
//        Info leftInfo = process(x.left);
//        Info rightInfo = process(x.right);
//        if (leftInfo != null){
//            max = Math.max(leftInfo.max,max);
//            min = Math.min(leftInfo.min,min);
//        }
//        if (rightInfo != null){
//            max = Math.max(rightInfo.max, max);
//            min = Math.min(rightInfo.min, min);
//        }
//        TreeNode maxSubSBTRoot = null;
//        if (leftInfo != null && leftInfo.isSBT){
//            maxSubSBTRoot =
//        }
//        boolean leftIsBST = leftInfo == null || (leftInfo != null && leftInfo.isSBT);
//        boolean rightIsBST = rightInfo == null || (rightInfo != null && rightInfo.isSBT);
//        boolean curIsBST = false;
//        if (leftIsBST && rightIsBST) {
//            if (
//                    (leftInfo == null || (leftInfo.max < x.value))
//                            && (rightInfo == null || (rightInfo.min > x.value))) {
//                curIsBST = true;
//            }
//        }
//
//        if (curIsBST){
//            maxSubSBTRoot = x;
//        }
//
//
//    }
//}
