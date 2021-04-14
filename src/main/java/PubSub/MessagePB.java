package PubSub;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessagePB {
    private String topic;
    private String message;

    public MessagePB(String topic, String payload){
        this.topic = topic;
        this.message = payload;
    }

}
