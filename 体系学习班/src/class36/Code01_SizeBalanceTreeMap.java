package class36;


/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-30 21:21
 * @description SB树
 */
public class Code01_SizeBalanceTreeMap {
    private static class SizeBalanceTreeNode<K extends Comparable<K>, V> {
        public K key;
        public V value;
        public SizeBalanceTreeNode<K, V> left;
        public SizeBalanceTreeNode<K, V> right;
        public int size;

        public SizeBalanceTreeNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.size = 1;
        }
    }

    public static class SizeBalanceTreeMap<K extends Comparable<K>, V> {
        private SizeBalanceTreeNode<K, V> root;
        private int size;

        public SizeBalanceTreeMap() {
        }

        public int size() {
            return size;
        }

        private SizeBalanceTreeNode<K, V> leftRotate(SizeBalanceTreeNode<K, V> cur) {
            SizeBalanceTreeNode<K, V> right = cur.right;
            cur.right = right.left;
            right.left = cur;
            cur.size = (cur.left != null ? cur.left.size : 0)
                    + (cur.right != null ? cur.right.size : 0)
                    + 1;
            right.size = (right.left != null ? right.left.size : 0)
                    + (right.right != null ? right.size : 0)
                    + 1;
            return right;
        }

        private SizeBalanceTreeNode<K, V> rightRotate(SizeBalanceTreeNode<K, V> cur) {
            SizeBalanceTreeNode<K, V> left = cur.left;
            cur.left = left.right;
            left.right = cur;
            cur.size = (cur.left != null ? cur.left.size : 0)
                    + (cur.right != null ? cur.right.size : 0)
                    + 1;
            left.size = (left.left != null ? left.left.size : 0)
                    + (left.right != null ? left.right.size : 0)
                    + 1;
            return left;
        }

        private SizeBalanceTreeNode<K, V> maintain(SizeBalanceTreeNode<K, V> cur){
            if (cur == null){
                return null;
            }
            int leftSize = cur.left != null ? cur.left.size : 0;
            int rightSize = cur.right != null ? cur.right.size : 0;
            int leftLeftSize = cur.left != null && cur.left.left != null ? cur.left.left.size : 0;
            int leftRightSize = cur.left != null && cur.left.right != null ? cur.left.right.size : 0;
            int rightLeftSize = cur.right != null && cur.right.left != null ? cur.right.left.size : 0;
            int rightRightSize = cur.right != null && cur.right.right != null ? cur.right.right.size : 0;
            //LL型
            if (leftLeftSize > rightSize){
                //右旋
                cur = rightRotate(cur);
                //调整改动过的节点
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            }else if (leftRightSize > rightSize){
                //LR型
                cur.left = leftRotate(cur.left);
                cur = rightRotate(cur);
                cur.left = maintain(cur.left);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            }else if (rightRightSize > leftSize){
                //RR型
                cur = leftRotate(cur);
                cur.left = maintain(cur.left);
                cur = maintain(cur);
            }else if (rightLeftSize > leftSize){
                //RL型
                cur.right = rightRotate(cur.right);
                cur = leftRotate(cur);
                cur.left = maintain(cur.left);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            }
            return cur;
        }

        private SizeBalanceTreeNode<K, V> add(SizeBalanceTreeNode<K, V> cur,K key,V value){
            if (cur == null){
                return new SizeBalanceTreeNode<>(key,value);
            }else {
                cur.size++;
                if (key.compareTo(cur.key) < 0){
                    cur.left = add(cur.left,key,value);
                }else {
                    cur.right = add(cur.right,key,value);
                }
                return maintain(cur);
            }
        }

        //TODO delete最后一步不太懂
        private SizeBalanceTreeNode<K,V> delete(SizeBalanceTreeNode<K,V> cur,K key){
            cur.size--;
            if (key.compareTo(cur.key) > 0){
                cur.right = delete(cur.right,key);
            }else if (key.compareTo(cur.key) < 0){
                cur.left = delete(cur.left,key);
            }else {
                //分4种情况
                if (cur.left == null && cur.right == null){
                    cur = null;
                }else if (cur.left != null && cur.right == null){
                    cur = cur.left;
                }else if (cur.left == null && cur.right != null){
                    cur = cur.right;
                }else {
                    SizeBalanceTreeNode<K, V> pre = null;
                    SizeBalanceTreeNode<K, V> des = cur.right;
                    des.size--;
                    while (des.left != null) {
                        pre = des;
                        des = des.left;
                        des.size--;
                    }
                    if (pre != null) {
                        pre.left = des.right;
                        des.right = cur.right;
                    }
                    des.left = cur.left;
                    des.size = des.left.size + (des.right == null ? 0 : des.right.size) + 1;
                    cur = des;
                }
            }
            return cur;
        }

        private SizeBalanceTreeNode<K,V> findLastIndex(K key){
            SizeBalanceTreeNode<K,V> pre = root;
            SizeBalanceTreeNode<K,V> cur = root;
            while (cur != null){
                pre = cur;
                if (key.compareTo(cur.key) < 0){
                    cur = cur.left;
                }else if (key.compareTo(cur.key) > 0){
                    cur = cur.right;
                }else {
                    break;
                }
            }
            return pre;
        }

        public void put(K key,V value){
            if (key == null){
                return;
            }
            SizeBalanceTreeNode<K, V> lastIndex = findLastIndex(key);
            //存在这个节点
            if (lastIndex != null && lastIndex.key.compareTo(key) == 0){
                lastIndex.value = value;
            }else {
                root = add(root,key,value);
            }
        }

        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }
            SizeBalanceTreeNode<K, V> lastNode = findLastIndex(key);
            return lastNode != null && lastNode.key.compareTo(key) == 0;
        }

        public void remove(K key){
            if (key == null) {
                return;
            }
            if (containsKey(key)) {
                this.size--;
                root = delete(root, key);
            }
        }

        public V get(K key) {
            if (key == null) {
                return null;
            }
            SizeBalanceTreeNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.key) == 0) {
                return lastNode.value;
            }
            return null;
        }

    }
}
