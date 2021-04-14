package PubSub;

import javax.swing.text.html.HTMLDocument;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PubSubService {

    private static volatile PubSubService instance ;

//    private BlockingQueue<MessagePB> blockingQueue = new ArrayBlockingQueue<MessagePB>(100);
    Lock lock = new ReentrantLock();
    private Map<String, Set<Subscriber>> topicWiseSubscribers = new HashMap<>();
    private BlockingQueue<MessagePB> blockingQueue =  new ArrayBlockingQueue<>(10);
//    private Map<String, BlockingQueue<MessagePB>> topicWiseBlockingQueue = new HashMap<>();

    private PubSubService(){}

    public static PubSubService getInstance(){

        synchronized(PubSubService.class) {
            if (instance == null) {
                instance = new PubSubService();
            }
        }
        return instance;

    }

    public void publishMessage(MessagePB messagePB){

        this.blockingQueue.add(messagePB);

//        lock.lock();
//        String key = messagePB.getTopic();
//        String value = messagePB.getMessage();
//
//        if(this.topicWiseBlockingQueue.containsKey(key)){
//            BlockingQueue queue = this.topicWiseBlockingQueue.get(key);
//            queue.add(messagePB.getMessage());
//        }else{
//            BlockingQueue queue = new ArrayBlockingQueue(10);
//            queue.add(value);
//            topicWiseBlockingQueue.put(key, queue);
//        }
//        lock.lock();
    }

    public void addSubscriber(String topic, Subscriber sub){

        if(this.topicWiseSubscribers.containsKey(topic)){
            this.topicWiseSubscribers.get(topic).add(sub);

        }else{
            Set<Subscriber> subs = new HashSet<>();
            subs.add(sub);
            this.topicWiseSubscribers.put(topic, subs);
        }

        sub.getSubscriberTopics().add(topic);
    }

    public void subscribeMessage(Subscriber sub){

        if(this.blockingQueue.isEmpty() || this.blockingQueue == null){
            System.out.println("No messages from publishers to display ");
        }else{
            while(!this.blockingQueue.isEmpty()){
                MessagePB message = this.blockingQueue.remove();
                String topic = message.getTopic();

                Set<Subscriber> subscribersOfTopic = this.topicWiseSubscribers.get(topic);
                if(!subscribersOfTopic.isEmpty()) {
                    for (Subscriber elementSub : subscribersOfTopic) {
                        System.out.println("Subscriber eating a message " + message.getMessage() + " sub name " + elementSub.getNname());
                    }
                }else{
                    System.out.println(sub + "dont have any topic to eat, please send next ");
                }
            }
        }
//        lock.lock();
//            if(this.topicWiseBlockingQueue == null || this.topicWiseBlockingQueue.isEmpty()){
//                System.out.println("No messages from publishers to display");
//            }else{
//                while(!this.topicWiseBlockingQueue.isEmpty()){
//
//                    for(String topic : sub.getSubscriberTopics()){
//                        if(this.topicWiseBlockingQueue.containsKey(topic)){
//                            MessagePB message = this.topicWiseBlockingQueue.get(topic).remove();
//                            System.out.println("Message taken by sub is " + message.getMessage() + "by subscriber name " + sub.getName());
//                        }
//                    }
////                    Iterator hmIterator = this.topicWiseBlockingQueue.entrySet().iterator();
////                    while(hmIterator.hasNext()){
////                        Map.Entry element = (Map.Entry) hmIterator.next();
////                    }
//                }
//            }
//        lock.unlock();
    }
}
