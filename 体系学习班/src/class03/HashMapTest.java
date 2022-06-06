package class03;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-23 23:56
 * @description Hash表一些基本知识
 */
public class HashMapTest {
    public static class People{
        String name;

        public People(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            People people = (People) o;
            return Objects.equals(name, people.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
    public static void main(String[] args) {
        People people1 = new People("a");
        People people2 = new People("a");
        HashMap<People,Integer> map = new HashMap<>();
        map.put(people1,2);
        System.out.println(map.containsKey(people2));
    }
}
