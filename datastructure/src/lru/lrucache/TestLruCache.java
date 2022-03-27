package lru.lrucache;

/**
 * Lru缓存测试
 * @author lilibo
 * @create 2021-09-03 4:22 PM
 */
public class TestLruCache {

    public static void main(String[] args) {
        LruCache lruCache = new LruCache(10);
        for (int i = 0; i < 20; i++) {
            String key = "key-" + i;
            String value = "value" + i;
            lruCache.put(key, value);
        }
        lruCache.print();
        System.out.println("***********");
        for (int i = 19; i >= 10; i--) {
            String key = "key-" + i;
            System.out.println("取出的值为:" + lruCache.get(key));
        }
        lruCache.print();
        System.out.println("***********");
        for (int i = 20; i < 40; i++) {
            String key = "key-" + i;
            String value = "value" + i;
            lruCache.put(key, value);
        }
        lruCache.print();
        System.out.println("***********");

        System.out.println("取出的值为:" + lruCache.get("key-" + 37));
        System.out.println("取出的值为:" + lruCache.get("key-" + 31));
        lruCache.print();
        System.out.println("***********");

    }

}
