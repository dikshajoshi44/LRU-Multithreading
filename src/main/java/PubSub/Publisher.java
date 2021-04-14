package PubSub;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Publisher extends Thread{

    private String nname;
    private MessagePB messagePB;

    PubSubService pubSubService = PubSubService.getInstance();

    public Publisher(String name, MessagePB message){
        this.nname = name;
        this.messagePB = message;
    }

    public void run() {
        pubSubService.publishMessage(this.messagePB);
    }



}
