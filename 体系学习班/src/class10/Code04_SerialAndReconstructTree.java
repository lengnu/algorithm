package class10;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-22 22:40
 * @description 二叉树的序列化与反序列化
 */
public class Code04_SerialAndReconstructTree {
    /**
     * 二叉树的先序序列化
     *
     * @param root
     * @return
     */
    public static Queue<String> preSerialize(TreeNode root) {
        Queue<String> result = new LinkedList<>();
        pres(result, root);
        return result;
    }

    public static void pres(Queue<String> result, TreeNode root) {
        if (root == null) {
            result.add("#");
            return;
        }
        result.add(String.valueOf(root.value));
        pres(result, root.left);
        pres(result, root.right);
    }

    /**
     * 二叉树的先序反序列化算法
     *
     * @param preSerializeList
     * @return
     */
    public static TreeNode preReconstruct(Queue<String> preSerializeList) {
        return preb(preSerializeList);
    }

    public static TreeNode preb(Queue<String> preSerializeList) {
        String value = preSerializeList.poll();
        if (value.equals("#")) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(value));
        root.left = preb(preSerializeList);
        root.right = preb(preSerializeList);
        return root;
    }

    /**
     * 二叉树的层序序列化
     *
     * @param root
     * @return
     */
    public static Queue<String> levelSerialize(TreeNode root) {
        Queue<String> ans = new LinkedList<>();
        if (root == null) {
            ans.add("#");
            return ans;
        } else {
            Queue<TreeNode> queue = new LinkedList<>();
            ans.add(String.valueOf(root.value));
            queue.add(root);
            while (!queue.isEmpty()) {
                root = queue.poll();
                if (root.left != null) {
                    ans.add(String.valueOf(root.left.value));
                    queue.add(root.left);
                } else {
                    ans.add("#");
                }
                if (root.right != null) {
                    ans.add(String.valueOf(root.right.value));
                    queue.add(root.right);
                } else {
                    ans.add("#");
                }
            }
            return ans;
        }

    }

    /**
     * 二叉树的层序反序列化
     * @param levelList
     * @return
     */
    public static TreeNode levelReconstruct(Queue<String> levelList) {
        if (levelList == null || levelList.size() == 0) {
            return null;
        }
        String value = levelList.poll();
        TreeNode root = generateNode(value);
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
        }
        TreeNode node = null;
        while (!queue.isEmpty()) {
            node = queue.poll();
            node.left = generateNode(levelList.poll());
            node.right = generateNode(levelList.poll());
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        return root;
    }

    public static TreeNode generateNode(String value) {
        if (value.equals("#")) {
            return null;
        }
        return new TreeNode(Integer.parseInt(value));
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.right = new TreeNode(3);
        root.right = new TreeNode(4);
        root.right.left = new TreeNode(5);
        root.right.right = new TreeNode(6);
        Code02_RecursiveUnTravesalBinaryTree.pre(root);
        Code02_RecursiveUnTravesalBinaryTree.pre(preReconstruct(preSerialize(root)));

        Code03_LevelTraversalBinaryTree.levelTraversal(root);
        Queue<String> queue = levelSerialize(root);
        TreeNode node = levelReconstruct(queue);
        Code03_LevelTraversalBinaryTree.levelTraversal(node);
        System.out.println(Code06_TreeMaxWidth.maxWidth(root));
    }
}
