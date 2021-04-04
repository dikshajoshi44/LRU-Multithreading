package TwitterDesign;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Twitter {

    private HashMap<Integer, User> userMap;
    private static Twitter instance;
    private final Integer timestamp;

    private Twitter(){
        this.userMap = new HashMap<>();
        timestamp = 0;

    }

    public static Twitter getInstance(){
        if(instance == null){
            instance = new Twitter();
        }

        return instance;
    }

    public void postTweet(int userId, int tweetId){

        if(!userMap.containsKey(userId)){
            userMap.put(userId, new User(userId));
        }

        userMap.get(userId).addTweet(tweetId, timestamp + 1);
    }

    //userID will follow followeeUserId
    public void follow(int userId, int followeeUserId){
        if(!userMap.containsKey(userId)){
            userMap.put(userId, new User(userId));
        }

        userMap.get(userId).follow(followeeUserId);
    }

    //userID will unfollow followeeUserId
    public void unFollow(int userId, int followeeUserId){
        if(!userMap.containsKey(userId)){
            System.out.println("User does not exists, cant do this step");
            return;
        }

        userMap.get(userId).unFollow(followeeUserId);
    }

    public LinkedList getHomeNewsFeed(int id){
        if(!userMap.containsKey(id)){
            System.out.println("No user with this id exists");
        }else{
            return userMap.get(id).getRecentTweet();
        }
        return new LinkedList();
    }

    public List<Integer> getPublicNewsFeed(int id){
        if(!userMap.containsKey(id)){
            userMap.put(id, new User(id));
        }

        PriorityQueue<Tweet> recentTweetsMinHeap = new PriorityQueue<Tweet>((a, b) -> (a.timestamp - b.timestamp));

        //add All recent Tweets of this user
        addTweetsInMinHeap(recentTweetsMinHeap, userMap.get(id).getRecentTweet());

        for(int userId: userMap.get(id).getFollowingIds()){
            addTweetsInMinHeap(recentTweetsMinHeap, userMap.get(userId).getRecentTweet());
        }


        List<Integer> finalTweets = new LinkedList<>();

        while(!recentTweetsMinHeap.isEmpty()){
            finalTweets.add(0, recentTweetsMinHeap.poll().id);
        }

        return finalTweets;
    }

    private void addTweetsInMinHeap(PriorityQueue<Tweet> minHeap, LinkedList<Tweet> tweets){
        for(Tweet tweet: tweets){
            minHeap.add(tweet);

            if(minHeap.size() > 10){
                minHeap.poll();
            }
        }
    }


}
