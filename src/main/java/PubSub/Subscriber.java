package PubSub;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Subscriber extends Thread {

    private String nname;
    public Subscriber(String name){
        this.nname = name;
    }

    PubSubService service = PubSubService.getInstance();
    List<String> subscriberTopics = new ArrayList<>();

    public void addSubscriber(String topic){
        service.addSubscriber(topic, this);
    }

    public void run(){
        try{
            service.subscribeMessage(this);
        }catch(Exception ex){ ex.printStackTrace();}
    }


}
