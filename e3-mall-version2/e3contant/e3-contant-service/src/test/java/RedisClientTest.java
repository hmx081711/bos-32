//import com.hmx.e3_common.jedis.JedisClient;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//public class RedisClientTest {
//    @Test
//    public void test(){
//        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext-redis.xml");
//        JedisClient client = ac.getBean(JedisClient.class);
//        client.set("key1","456");
//        System.out.println(client.get("key1"));
//    }
//}
