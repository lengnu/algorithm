package class10;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-27 15:36
 * @description 求树的最大宽度
 */
public class Code06_TreeMaxWidth {
    /**
     * 求二叉树的最大宽度
     * @param root
     * @return
     */
    public static int maxWidth(TreeNode root){
        if (root == null){
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode curEnd = root;
        TreeNode nextEnd = null;
        int count = 0;
        int max = 0;
        while (!queue.isEmpty()){
            count++;
            root = queue.poll();
            if (root.left != null){
                queue.add(root.left);
                nextEnd = root.left;
            }

            if (root.right != null) {
                queue.add(root.right);
                nextEnd = root.right;
            }
            if (root == curEnd){
                max = Math.max(count,max);
                count = 0;
                curEnd = nextEnd;
            }
        }
        return max;
    }
}
