//import org.junit.Test;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//
//import java.util.List;
//
//public class RedisTest {
//    // 单机版
//    @Test
//    public void tsetJedis() {
//        // 创建Jedis对象
//        Jedis jedis = new Jedis("127.0.0.1", 6379);
//        //操作数据库
//        jedis.set("key1", "123");
//        String val = jedis.get("key1");
//        System.out.println(val);
//        //关闭连接
//        jedis.close();
//    }
//    // 单机版 使用连接池
//    @Test
//    public void  testJedis2() {
//        // 创建Jedis的连接池
//        JedisPool jedisPool = new JedisPool("127.0.0.1",6379);
//        // 从连接池中取Jedis对象
//        Jedis jedis = jedisPool.getResource();
//        // 操作数据库
//        jedis.lpush("list1","1","2","3");
//        List<String> result = jedis.lrange("list1", 0, -1);
//        System.out.println(result);
//        // 关闭连接池
//        jedis.close();
//    }

    // 集群版
//    @Test
//    public void testJedis3() {
//        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
//        nodes.add(new HostAndPort("127.0.0.1",7001));
//        nodes.add(new HostAndPort("127.0.0.1",7002));
//        nodes.add(new HostAndPort("127.0.0.1",7003));
//        nodes.add(new HostAndPort("127.0.0.1",7004));
//        nodes.add(new HostAndPort("127.0.0.1",7005));
//        nodes.add(new HostAndPort("127.0.0.1",7006));
//        //创建Jedis集群对象
//        JedisCluster jedisCluster = new JedisCluster(nodes);
//        // 操作数据库
//        jedisCluster.set("key1","12");
//        System.out.println(jedisCluster.get("key1"));
//        //关闭连接
//        jedisCluster.close();
//    }
//}
