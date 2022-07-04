package class14;

/**
 * @BelongsProject: Algorithm-System
 * @BelongsPackage: class14
 * @Author: duwei
 * @Date: 2022/7/4 19:05
 * @Description: 点亮等的最小代价
 */
public class Code01_Light {
    public static int minCountLights1(String road){
        if (road == null || road.length() == 0){
            return 0;
        }
        char[] str = road.toCharArray();
        int index = 0;
        int count = 0;
        while (index < str.length){
            if (str[index] == 'X'){
                index++;
            }else {
                count++;
                if (index + 1 == str.length){
                    break;
                }
                if (str[index + 1] == 'X'){
                    index += 2;
                }else {
                    index += 3;
                }
            }
        }
        return count;
    }

    public static int minCountLights2(String road){
        char[] str = road.toCharArray();
        int i = 0;
        int light = 0;
        while (i < str.length) {
            if (str[i] == 'X') {
                i++;
            } else {
                light++;
                if (i + 1 == str.length) {
                    break;
                } else { // 有i位置 i+ 1 X .
                    if (str[i + 1] == 'X') {
                        i = i + 2;
                    } else {
                        i = i + 3;
                    }
                }
            }
        }
        return light;
    }

    public static String generateRoad(int maxLength){
        int len = (int) (Math.random() * maxLength) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            if (Math.random() < 0.5){
                builder.append("X");
            }else {
                builder.append(".");
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        int times = 10000;
        int maxLength = 100;
        System.out.println("start...");
        for (int i = 0; i < times; i++) {
            String road = generateRoad(maxLength);
            if (minCountLights1(road) != minCountLights2(road)){
                System.out.println("出错了");
                System.exit(1);
            }
        }
        System.out.println("finish...");
    }



}
