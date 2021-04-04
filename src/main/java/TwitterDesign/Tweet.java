package TwitterDesign;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class Tweet {
    Integer id;
    int timestamp;

    public Tweet(int id, int timestamp){
        this.setId(id);
        this.setTimestamp(timestamp);
    }
}
