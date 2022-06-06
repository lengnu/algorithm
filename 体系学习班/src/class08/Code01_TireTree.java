package class08;

import java.util.Currency;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-02 11:07
 * @description TODO
 */
public class Code01_TireTree {
    private static class Node {
        //有多少字符串经过该节点
        private int pass;
        //有多少字符串以该节点结尾
        private int end;
        //路径
        private Node[] nexts;

        public Node() {
            //只存储小写字符
            nexts = new Node[26];
            pass = 0;
            end = 0;
        }
    }

    //前缀树头节点
    public Node root;

    public Code01_TireTree() {
        root = new Node();
    }

    /**
     * 数上增加一个字符串
     *
     * @param word
     */
    public void insert(String word) {
        if (word == null) {
            return;
        }
        char[] str = word.toCharArray();
        //代表第i条路径
        int path = 0;
        Node cur = root;
        for (int i = 0; i < str.length; i++) {
            path = str[i] - 'a';
            if (cur.nexts[path] == null) {
                cur.nexts[path] = new Node();
            }
            cur = cur.nexts[path];
            cur.pass++;
        }
        cur.end++;
    }

    /**
     * 查找执行字符串在前缀树出现了几次
     *
     * @param word
     * @return
     */
    public int search(String word) {
        if (word == null) {
            return 0;
        }
        char[] str = word.toCharArray();
        int path = 0;
        Node cur = root;
        cur.pass++;
        for (int i = 0; i < str.length; i++) {
            path = str[i] - 'a';
            if (cur.nexts[path] == null) {
                return 0;
            }
            cur = cur.nexts[path];
        }
        return cur.end;
    }

    /**
     * 返回有几个以指定字符串为开头的字符
     * @param word
     * @return
     */
    public int prefixNumber(String word){
        if (word == null) {
            return 0;
        }
        char[] str = word.toCharArray();
        int path = 0;
        Node cur = root;
        for (int i = 0; i < str.length; i++) {
            path = str[i] - 'a';
            if (cur.nexts[path] == null) {
                return 0;
            }
            cur = cur.nexts[path];
        }
        return cur.pass;
    }

    /**
     * 删除前缀树上指定单词
     * @param word
     * @return
     */
    public void delete(String word){
        if (search(word) == 0){
            return;
        }

        char[] str = word.toCharArray();
        Node cur = root;
        cur.pass--;
        int path = 0;
        for (int i = 0;i < str.length;i++){
            path = str[i] - 'a';
            if (--cur.nexts[path].pass == 0){
                cur.nexts[path] = null;
                return;
            }
            cur = cur.nexts[path];
        }
        cur.end--;
    }

}
