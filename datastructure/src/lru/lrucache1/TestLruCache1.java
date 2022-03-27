package lru.lrucache1;

import lru.lrucache.LruCache;

/**
 * @author lilibo
 * @create 2021-09-04 5:22 PM
 */
public class TestLruCache1 {

    public static void main(String[] args) {
        LruCache cache = new LruCache(10);
        for(int i = 1; i <= 30; i++){
            String key = "key" + i;
            String value = "value" + i;
            cache.put(key, value);
        }
        cache.print();
        System.out.println("------------");
        for(int i = 30; i >= 20; i--) {
            String key = "key" + i;
            String value = "value" + i;
            System.out.println("get(" + key + ") = " + cache.get(key));
        }
        cache.print();
        System.out.println("------------");
        for(int i = 31; i <= 35; i++){
            String key = "key" + i;
            String value = "value" + i;
            cache.put(key, value);
        }
        cache.print();
    }

}
