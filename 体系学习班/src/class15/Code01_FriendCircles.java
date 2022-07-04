package class15;

/**
 * @BelongsProject: Algorithm-System
 * @BelongsPackage: class15
 * @Author: duwei
 * @Date: 2022/7/4 19:54
 * @Description: 朋友圈
 */
public class Code01_FriendCircles {
    public static int findCircleNum(int[][] isConnected) {
        UnionFind unionFind = new UnionFind(isConnected.length);
        int N = isConnected.length;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (isConnected[i][j] == 1){
                    unionFind.union(i,j);
                }
            }
        }
        return unionFind.sets;
    }
    private static class UnionFind{
        //以下表代表第i个元素
        private int[] parents;
        private int[] help;
        private int[] size;
        private int sets;

        public UnionFind(int N) {
            parents = new int[N];
            help = new int[N];
            size = new int[N];
            for (int i = 0; i < N; i++) {
                size[i] = 1;
                parents[i] = i;
            }
            sets = N;
        }

        public int  findFather(int i){
            int index = 0;
            while (i != parents[i]){
                help[index++] = i;
                i = parents[i];
            }
            for (int j = 0; j < index; j++) {
                parents[help[j]] = i;
            }
            return i;
        }

        public void union(int i,int j){
            int iHead = findFather(i);
            int jHead = findFather(j);
            if (iHead != jHead){
                if (size[iHead] >= size[jHead]){
                    size[iHead] += size[jHead];
                    parents[jHead] = iHead;
                }else {
                    size[jHead] += size[iHead];
                    size[iHead] = jHead;
                }
                sets--;
            }
        }

        public int sets(){
            return this.sets;
        }
    }
}
