package TwitterDesign;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@Setter
@Getter
public class User {
    int id;
    Set<Integer> followingIds = new HashSet<>();
    LinkedList<Tweet> recentTweet = new LinkedList<>();

    public User(int id){
        this.setId(id);
    }

    public void follow(int id){
        if(!followingIds.contains(id)){
            followingIds.add(id);
        }else{
            System.out.println("Already following this user with id" + id);
        }
        return;
    }

    public void unFollow(int id){
        if(followingIds.contains(id)){
            followingIds.remove(id);
        }else{
            System.out.println("Already not following this user" + id);
        }
        return;
    }

    public void addTweet(int tweetId, Integer timestamp){
        if(recentTweet.size() > 10){
            recentTweet.removeFirst();
        }else{
            recentTweet.add(new Tweet(tweetId, timestamp));
        }
        return;
    }

}
