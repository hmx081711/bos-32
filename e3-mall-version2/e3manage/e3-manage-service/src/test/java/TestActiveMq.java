//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.junit.Test;
//
//import javax.jms.*;
//
//public class TestActiveMq {
//
//    @Test
//    public void testQuery() throws Exception {
//        //创建连接工厂对象
//        ConnectionFactory acf = new ActiveMQConnectionFactory("tcp://192.168.208.133:61616");
//        //建立连接
//        Connection connection = acf.createConnection();
//        connection.start();
//        //创建Session
//        //第一个参数是是否开启事务，第二个参数选择应答模式
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        //创建Destinaton对象
//        Queue queue = session.createQueue("test-query");
//        //创建Producter对象
//        MessageProducer producer = session.createProducer(queue);
//        //创建消息对象
//        TextMessage textMessage = session.createTextMessage("今天晚上开会2");
//        //发送消息
//        producer.send(textMessage);
//        //关闭资源
//        producer.close();
//        session.close();
//        connection.close();
//    }
//
//    @Test
//    public void  tsetQueryAcheiveMessage() throws Exception {
//        //创建连接工厂对象
//        ConnectionFactory acf = new ActiveMQConnectionFactory("tcp://192.168.208.133:61616");
//        //建立连接
//        Connection connection = acf.createConnection();
//        connection.start();
//        //创建Session
//        //第一个参数是是否开启事务，第二个参数选择应答模式
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        //创建Destinaton对象
//        Queue queue = session.createQueue("test-query");
//        //创建consumer对象
//        final MessageConsumer consumer = session.createConsumer(queue);
//        //接收消息
//        consumer.setMessageListener(new MessageListener() {
//            @Override
//            public void onMessage(Message message) {
//               TextMessage message1 = (TextMessage) message;
//               String text;
//               try {
//                    text = message1.getText();
//                   System.out.println(text);
//               } catch (JMSException e) {
//                   e.printStackTrace();
//               }
//            }
//        });
//        //等待接收消息
//        //system.in.read();
//        //关闭资源
//        consumer.close();
//        session.close();
//        connection.close();
//    }
//
//    @Test
//    public void testTopic() throws Exception {
//        //创建连接工厂对象
//        ConnectionFactory acf = new ActiveMQConnectionFactory("tcp://192.168.208.133:61616");
//        //建立连接
//        Connection connection = acf.createConnection();
//        connection.start();
//        //创建Session
//        //第一个参数是是否开启事务，第二个参数选择应答模式
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        //创建Destinaton对象
//        Topic queue = session.createTopic("test-query");
//        //创建Producter对象
//        MessageProducer producer = session.createProducer(queue);
//        //创建消息对象
//        TextMessage textMessage = session.createTextMessage("今天晚上开会2");
//        //发送消息
//        producer.send(textMessage);
//        //关闭资源
//        producer.close();
//        session.close();
//        connection.close();
//    }
//
//    @Test
//    public void testAchieveTopic() throws Exception {
//        //创建连接工厂对象
//        ConnectionFactory acf = new ActiveMQConnectionFactory("tcp://192.168.208.133:61616");
//        //建立连接
//        Connection connection = acf.createConnection();
//        connection.start();
//        //创建Session
//        //第一个参数是是否开启事务，第二个参数选择应答模式
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        //创建Destinaton对象
//        Topic queue = session.createTopic("test-query");
//        //创建consumer对象
//        final MessageConsumer consumer = session.createConsumer(queue);
//        //接收消息
//        consumer.setMessageListener(new MessageListener() {
//            @Override
//            public void onMessage(Message message) {
//                TextMessage message1 = (TextMessage) message;
//                String text;
//                try {
//                    text = message1.getText();
//                    System.out.println(text);
//                } catch (JMSException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        //等待接收消息
//        System.in.read();
//        //关闭资源
//        consumer.close();
//        session.close();
//        connection.close();
//    }
//}

