//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.jms.core.MessageCreator;
//
//import javax.jms.*;
//
//public class TestActiveMqSpring {
//
//    @Test
//    public void sendMessage() {
//        //初始化spring容器
//        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext-activemq.xml");
//        JmsTemplate jmsTemplate = ac.getBean(JmsTemplate.class);
//        Destination destination = (Destination) ac.getBean("destination");
//        jmsTemplate.send(destination,new MessageCreator() {
//            @Override
//            public Message createMessage(Session session) throws JMSException {
//                TextMessage message = session.createTextMessage("send-activemqMessage");
//                return message;
//            }
//        });
//    }
//}
