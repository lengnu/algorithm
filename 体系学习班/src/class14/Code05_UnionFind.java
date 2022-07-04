package class14;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @BelongsProject: Algorithm-System
 * @BelongsPackage: class14
 * @Author: duwei
 * @Date: 2022/7/4 19:30
 * @Description: 基于hashMap的并查集
 */
public class Code05_UnionFind {
    //每个值都是一个节点，包装了一下，因此可以放基础类型
    private static class Node<V>{
        private V value;

        public Node(V value) {
            this.value = value;
        }
    }
    public static class UnionFind<V>{
        //根据值V找到对于的节点
        private Map<V,Node<V>> nodes;
        //记录父亲节点
        private Map<Node<V>,Node<V>> parents;
        //记录集合大小，只记录每个代表节点的
        private Map<Node<V>,Integer> sizeMap;

        public UnionFind(V[] values){
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (int i = 0; i < values.length; i++) {
                Node<V> node = new Node<>(values[i]);
                nodes.put(values[i],node);
                parents.put(node,node);
                sizeMap.put(node,1);
            }
        }

        /**
         * 判断两个元素x和y是否在同一集合
         * @param x
         * @param y
         * @return
         */
        public  boolean isSameSet(V x,V y){
            return find(x) == find(y);
        }

        private Node<V> find(V x){
            Node<V> node = nodes.get(x);
            Stack<Node> stack = new Stack<>();
            while (node != parents.get(node)){
                stack.push(node);
                node = parents.get(node);
            }
            //优化，将沿途所有节点都挂在代表节点上
            while (!stack.isEmpty()){
                parents.put(stack.pop(),node);
            }
            return node;
        }

        public void union(V a,V b){
            Node<V> aHead = find(a);
            Node<V> bHead = find(b);
            //不在一个集合
            if (aHead != bHead){
                int aSetSize = sizeMap.get(aHead);
                int bSetSize = sizeMap.get(bHead);
                //集合size较大的赋予xNode
                Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
                Node<V> small = aSetSize >= bSetSize ? bHead : aHead;
                parents.put(small,big);
                sizeMap.put(big,aSetSize + bSetSize);
                sizeMap.remove(small);
            }
        }

        public int sets(){
            return sizeMap.size();
        }
    }


}
