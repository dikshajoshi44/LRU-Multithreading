package PracticeLRU.Driver;

import PracticeLRU.Service.LRUCache;

public class Driver {

    public static void main(String args[]){

        LRUCache cache = LRUCache.getInstance();
        String eta = "1";
        String unit = "1".equalsIgnoreCase(eta) ? "min" : "mins";
        System.out.println(unit);


        cache.putkey("1", "100");
        cache.putkey("10", "1000");
        cache.putkey("3", "10000");
        cache.putkey("4", "10000");
        cache.putkey("3", "1000000");
        cache.putkey("10", "99");
        cache.putkey("1", "98");
        cache.putkey("1", "100");
        cache.putkey("10", "100");
        cache.putkey("3", "100");

        System.out.println(cache.getKey("1"));
        System.out.println(cache.getKey("10"));
        System.out.println(cache.getKey("3"));
    }
}
