package FinalLRU;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverClass {

    public static void main(String[] args){

        LRUCache cache = LRUCache.getInstance();

        cache.putKey("1", "100");
        cache.putKey("10", "1000");
        cache.putKey("3", "10000");
        cache.putKey("4", "10000");
        cache.putKey("3", "1000000");
        cache.putKey("10", "99");
        cache.putKey("1", "98");
//        cache.putKey("9", "97");


        System.out.println(cache.getKey("1"));
        System.out.println(cache.getKey("10"));
        System.out.println(cache.getKey("3"));






    }

}
