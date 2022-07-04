package class35;


/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-29 17:26
 * @description AVL树
 */
public class Code01_AVLTreeMap {
    /**
     * AVL树的节点，Key必须可排序
     *
     * @param <K>
     * @param <V>
     */
    private static class AVLNode<K extends Comparable<K>, V> {
        private K key;
        private V value;
        private AVLNode<K, V> left;
        private AVLNode<K, V> right;
        private int height;

        public AVLNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.height = 1;
        }

        public AVLNode(K key, V value, int height) {
            this.key = key;
            this.value = value;
            this.height = height;
        }
    }

    /**
     * AVL树实现有序表
     *
     * @param <K>
     * @param <V>
     */
    public static class AVLTreeMap<K extends Comparable<K>, V> {
        private AVLNode<K, V> root;
        private int size;

        public AVLTreeMap() {
            this.root = null;
            this.size = 0;
        }

        /**
         * 在以cur为头的树上加入节点，加完之后把整棵树头节点返回
         *
         * @param cur
         * @param key
         * @param value
         * @return
         */
        private AVLNode<K, V> add(AVLNode<K, V> cur, K key, V value) {
            //从cur开始判断Key加在哪
            if (cur == null) {
                return new AVLNode<>(key, value);
            } else {
                if (key.compareTo(cur.key) < 0) {
                    cur.left = add(cur.left, key, value);
                } else {
                    cur.right = add(cur.right, key, value);
                }
                cur.height = 1 + Math.max((cur.left != null ? cur.left.height : 0), (cur.right != null ? cur.right.height : 0));
            }
            return maintain(cur);
        }

        /**
         * 在以cur为头的树上把树调平并返回头节点
         *
         * @param cur
         * @return
         */
        private AVLNode<K, V> maintain(AVLNode<K, V> cur) {
            if (cur == null) {
                return null;
            }
            int leftHeight = cur.left != null ? cur.left.height : 0;
            int rightHeight = cur.right != null ? cur.right.height : 0;
            //左树右树高度大于1，需要调整
            if (Math.abs(leftHeight - rightHeight) > 1) {
                //左树高于右树
                if (leftHeight > rightHeight) {
                    int leftLeftHeight = cur.left != null && cur.left.left != null ? cur.left.left.height : 0;
                    int leftRightHeight = cur.left != null && cur.left.right != null ? cur.left.right.height : 0;
                    //LL型
                    if (leftLeftHeight >= leftRightHeight) {
                        cur = rightRotate(cur);
                    } else {
                        //LR型
                        cur.left = leftRotate(cur.left);
                        cur = rightRotate(cur);
                    }
                } else {
                    //右子树高于左子树
                    int rightLeftHeight = cur.right != null && cur.right.left != null ? cur.right.left.height : 0;
                    int rightRightHeight = cur.right != null && cur.right.right != null ? cur.right.right.height : 0;
                    //RR型
                    if (rightRightHeight >= rightLeftHeight) {
                        cur = leftRotate(cur);
                    } else {
                        //RL型
                        cur.right = rightRotate(cur.right);
                        cur = leftRotate(cur);
                    }
                }
            }
            return cur;
        }

        /**
         * 在以cur为头的树上删除key对于的节点
         *
         * @param cur
         * @param key
         * @return
         */
        private AVLNode<K, V> delete(AVLNode<K, V> cur, K key) {
            if (key.compareTo(cur.key) > 0) {
                cur.right = delete(cur.right, key);
            } else if (key.compareTo(cur.key) < 0) {
                cur.left = delete(cur.left, key);
            } else {
                //cur正好是要删除的节点
                if (cur.left == null && cur.right == null) {
                    cur = null;
                } else if (cur.left == null && cur.right != null) {
                    cur = cur.right;
                } else if (cur.left != null && cur.right == null) {
                    cur = cur.left;
                } else {
                    AVLNode<K, V> des = cur.right;
                    while (des.left != null) {
                        des = des.left;
                    }
                    cur.right = delete(cur.right, des.key);
                    des.left = cur.left;
                    des.right = cur.right;
                    cur = des;
                }
            }
            if (cur != null) {
                cur.height = 1 + Math.max((cur.left != null ? cur.left.height : 0), (cur.right != null ? cur.right.height : 0));
            }
            return maintain(cur);
        }

        /**
         * 右旋
         *
         * @param cur
         * @return
         */
        private AVLNode<K, V> rightRotate(AVLNode<K, V> cur) {
            AVLNode<K, V> left = cur.left;
            //左孩子的右树挂载当cur左边
            cur.left = left.right;
            //左孩子的右树接管cur
            left.right = cur;
            cur.height = 1 + Math.max((cur.left != null ? cur.left.height : 0), (cur.right != null ? cur.right.height : 0));
            left.height = 1 + Math.max((left.left != null ? left.left.height : 0), (left.right != null ? left.right.height : 0));
            return left;
        }

        /**
         * 左旋
         *
         * @param cur
         * @return
         */
        private AVLNode<K, V> leftRotate(AVLNode<K, V> cur) {
            AVLNode<K, V> right = cur.right;
            cur.right = right.left;
            right.left = cur;
            cur.height = 1 + Math.max((cur.left != null ? cur.left.height : 0), (cur.right != null ? cur.right.height : 0));
            right.height = 1 + Math.max((right.left != null ? right.left.height : 0), (right.right != null ? right.right.height : 0));
            return right;
        }

        /**
         * 返回树的大小
         *
         * @return
         */
        public int size() {
            return this.size;
        }

        /**
         * 找到最接近key的节点
         *
         * @param key
         * @return
         */
        private AVLNode<K, V> findLastIndex(K key) {
            AVLNode<K, V> pre = root;
            AVLNode<K, V> cur = root;
            while (cur != null) {
                pre = cur;
                if (key.compareTo(cur.key) == 0) {
                    break;
                } else if (key.compareTo(cur.key) < 0) {
                    cur = cur.left;
                } else {
                    cur = cur.right;
                }
            }
            return pre;
        }

        /**
         * 找到大于等于key的最接近的节点
         *
         * @param key
         * @return
         */
        private AVLNode<K, V> findLastNoSmallIndex(K key) {
            AVLNode<K, V> ans = null;
            AVLNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.key) == 0) {
                    ans = cur;
                    break;
                } else if (key.compareTo(cur.key) < 0) {
                    ans = cur;
                    cur = cur.left;
                } else {
                    cur = cur.right;
                }
            }
            return ans;
        }

        /**
         * 找到小于等于key的最接近的节点
         *
         * @param key
         * @return
         */
        private AVLNode<K, V> findLastNoBigIndex(K key) {
            AVLNode<K, V> ans = null;
            AVLNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.key) == 0) {
                    ans = cur;
                    break;
                } else if (key.compareTo(cur.key) < 0) {
                    cur = cur.left;
                } else {
                    ans = cur;
                    cur = cur.right;
                }
            }
            return ans;
        }


        /**
         * 树上是否包含某个key
         *
         * @param key
         * @return
         */
        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }
            AVLNode<K, V> lastNode = findLastIndex(key);
            return lastNode != null && lastNode.key.compareTo(key) == 0;
        }

        /**
         * 加入一个K - V
         *
         * @param key
         * @param value
         */
        public void put(K key, V value) {
            if (key == null) {
                return;
            }
            AVLNode<K, V> lastNode = findLastIndex(key);
            //需要覆盖
            if (lastNode != null && lastNode.key.compareTo(key) == 0) {
                lastNode.value = value;
            } else {
                size++;
                root = add(root, key, value);
            }
        }

        /**
         * 删除一个K - V
         *
         * @param key
         */
        public void remove(K key) {
            if (key == null) {
                return;
            }
            if (containsKey(key)) {
                this.size--;
                root = delete(root, key);
            }
        }

        /**
         * 获取对应节点的值
         *
         * @param key
         * @return
         */
        public V get(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.key) == 0) {
                return lastNode.value;
            }
            return null;
        }

        /**
         * 获取树上最小的key
         *
         * @return
         */
        public K firstKey() {
            if (root == null) {
                return null;
            }
            AVLNode<K, V> cur = root;
            while (cur.left != null) {
                cur = cur.left;
            }
            return cur.key;
        }

        /**
         * 获取树上最大的key
         *
         * @return
         */
        public K lastKey() {
            if (root == null) {
                return null;
            }
            AVLNode<K, V> cur = root;
            while (cur.right != null) {
                cur = cur.right;
            }
            return cur.key;
        }

        /**
         * 获取小于等于key且最接近的key
         *
         * @param key
         * @return
         */
        public K floorKey(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> lastNoBigNode = findLastNoBigIndex(key);
            return lastNoBigNode == null ? null : lastNoBigNode.key;
        }

        /**
         * 获取大于等于key且最接近的key
         *
         * @param key
         * @return
         */
        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> lastNoSmallNode = findLastNoSmallIndex(key);
            return lastNoSmallNode == null ? null : lastNoSmallNode.key;
        }
    }
}
