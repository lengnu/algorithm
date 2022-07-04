package class12;

import java.awt.event.PaintEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-28 21:42
 * @description 求一棵树上的最大距离
 */
public class Code06_MaxDistance {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    private static class Info {
        public int height;
        public int maxDistance;

        public Info(int height, int maxDistance) {
            this.height = height;
            this.maxDistance = maxDistance;
        }
    }

    //求一棵树上的最大距离
    public static int maxDistance(Node root) {
        if (root == null) {
            return 0;
        }
        return process(root).maxDistance;
    }

    private static Info process(Node x) {
        if (x == null) {
            return new Info(0, 0);
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);
        int height = Math.max(leftInfo.height,rightInfo.height) + 1;
        int maxDistance = Math.max(
                leftInfo.height + rightInfo.height + 1,
                Math.max(leftInfo.maxDistance,rightInfo.maxDistance)
        );
        return new Info(height,maxDistance);
    }
}
