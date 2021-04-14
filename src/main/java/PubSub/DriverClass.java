package PubSub;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DriverClass {

    public static void main(String[] args) {


        PubSubService service = PubSubService.getInstance();

//        MessagePB message1 = new MessagePB("JAVA", "This is a java message");
////        MessagePB message2 = new MessagePB("JAVA", "This is a java message");
//
//        MessagePB message2 = new MessagePB("PYTHON", "This is a python message");
//        MessagePB message4 = new MessagePB("PYTHON", "This is a python message");

        Subscriber sub1 = new Subscriber("javaSub");
        Subscriber sub2 = new Subscriber("pythonSub");

        sub1.addSubscriber("JAVA");
        sub2.addSubscriber("PYTHON");

        ExecutorService pool = Executors.newFixedThreadPool(2);


        for(int i = 0; i < 5; i++) {

            MessagePB message1 = new MessagePB("JAVA", "This is a java message_______" +i);
            MessagePB message2 = new MessagePB("PYTHON", "This is a python message________" + i);

            pool.submit((new Publisher("javaPub", message1)));
            pool.submit(new Publisher("pythonPub", message2));

            pool.submit(sub1);
            pool.submit(sub2);
        }

        pool.shutdown();

    }
}
