package PracticeLRU.Service;

import PracticeLRU.LLNode.Node;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
public class LRUCache implements LRUCacheInterface {

    private static LRUCache instance;
    private  final Map<String, Node> lruMap;
    private  Node tail = null;
    private  Node head = null;
    private int capacity = 0;
    private final int CAPACITY = 7;

    private LRUCache(){
        this.lruMap = new HashMap<>();
    }

    public static LRUCache getInstance(){
        if(instance == null){
            instance = new LRUCache();
        }
        return instance;
    }

    public void setCapacity(int capacity) {
        capacity = CAPACITY;
    }

    @Override
    public void putkey(String key, String value){

        if(!lruMap.containsKey(key)){
            Node newNode = new Node(key, value);
            lruMap.put(key, newNode);
            moveNodeToHead(newNode);
        }else{

            if(lruMap.size() >= capacity){
                lruMap.remove(tail.getKey());
                deleteNodeFromList(tail);
            }
            Node newNode = new Node(key, value);
            lruMap.put(key, newNode);
            moveNodeToHead(newNode);
        }
    }

    @Override
    public String getKey(String key){
        if(!lruMap.containsKey(key)){
            return null;
        }

        Node node = lruMap.get(key);
        moveNodeToHead(node);
        return node.getValue();
    }

    public void moveNodeToHead(Node node){

        if(head == null){
            head = node;
            head.setNext(null);
            head.setPrev(null);
        }else{
            node.setNext(head);
            head.setPrev(node);
            node.setPrev(null);
        }

        if(tail == null){
            tail = head;
        }
    }

    public void deleteNodeFromList(Node node){

        if(node.getPrev() != null) {
            Node prevNode = node.getPrev();
            prevNode.setNext(null);
        }
    }


}
