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
        Subscriber sub2 = new Subscriber("python1");
        Subscriber sub3 = new Subscriber("python2");

        sub1.addSubscriber("JAVA");
        sub2.addSubscriber("PYTHON");
        sub3.addSubscriber("PYTHON");

        ExecutorService pool = Executors.newFixedThreadPool(2);

        pool.submit(sub1);
        pool.submit(sub2);
        pool.submit(sub3);

        for(int i = 0; i < 2; i++) {

            MessagePB message1 = new MessagePB("JAVA", "This is a java message_______" +i);
            MessagePB message2 = new MessagePB("PYTHON", "This is a python message________" + i);

//            pool.submit(sub1);
//            pool.submit(sub2);
//            pool.submit(sub3);

            pool.submit((new Publisher("javaPub", message1)));
            pool.submit((new Publisher("javaPub2", message1)));
            pool.submit((new Publisher("javaPub3", message1)));
            pool.submit((new Publisher("javaPub4", message1)));
            pool.submit(new Publisher("pythonPub", message2));
        }

        pool.shutdown();

    }
}
