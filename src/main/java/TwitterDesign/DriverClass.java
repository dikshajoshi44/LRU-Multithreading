package TwitterDesign;

import java.util.LinkedList;
import java.util.List;

public class DriverClass {

    public static void main(String args[]){
        Twitter twitterInstance = Twitter.getInstance();

//        twitterInstance.postTweet(1, 1);
//        twitterInstance.postTweet(1, 2);
//        twitterInstance.postTweet(2, 1);
//        twitterInstance.postTweet(2, 2);
//        twitterInstance.follow(1,2);

        List<Integer> newsFeed;
        twitterInstance.postTweet(1, 5); // User 1 posts a new tweet (id = 5).
        newsFeed = twitterInstance.getPublicNewsFeed(1);  // User 1's news feed should return a list with 1 tweet id -> [5]. return [5]

        System.out.println("newsFeed" + newsFeed);

        twitterInstance.follow(1, 2);    // User 1 follows user 2.
        twitterInstance.postTweet(2, 6); // User 2 posts a new tweet (id = 6).
        newsFeed = twitterInstance.getPublicNewsFeed(1);  // User 1's news feed should return a list with 2 tweet ids -> [6, 5]. Tweet id 6 should precede tweet id 5 because it is posted after tweet id 5.
        System.out.println("newsFeed.." + newsFeed);

        twitterInstance.unFollow(1, 2);  // User 1 unfollows user 2.
        newsFeed = twitterInstance.getPublicNewsFeed(1);
        System.out.println("newsFeed" + newsFeed);
    }
}
