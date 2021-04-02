package FinalLRU.Service;

import FinalLRU.DTO.Node;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class LRUCache implements CachingService {

    private static LRUCache instance;
    private final Map<String, Node> map;
    private static int capacity = 7;
    private Node head = null;
    private Node tail = null;

    private LRUCache(){
        this.map = new HashMap<String, Node>();
    }

    public static LRUCache getInstance(){
        if (instance == null){
            instance = new LRUCache();
        }

        return instance;
    }

    public String getKey(String key){
        if(!map.containsKey(key)){
            return null;
        }

        Node node = map.get(key);
        moveNodeToHead(node);
        return node.getValue();
    }

    public void putKey(String key, String value){

        if(map.containsKey(key)){

            Node newNode = new Node(key, value);
            map.put(key, newNode);
            moveNodeToHead(newNode);

        }else{

            if(map.size() >= capacity){
                map.remove(tail.getKey());
                deleteNodeFromList(tail);
            }

            Node node = new Node(key, value);
            map.put(key, node);
            moveNodeToHead(node);
        }
    }

    public void moveNodeToHead(Node node){
        node.setPrev(null);
        node.setNext(head);

        if(head != null){
            head.setPrev(node);
        }

        // handle the case for the very first element
        head = node;
        if(tail == null)
            tail = head;
    }

    public void deleteNodeFromList(Node node){
        if(node.getPrev() == null){
            if(node.getNext()!= null){
                node.getNext().setPrev(null);
            }
        }else if(node.getNext() == null){
            node.getPrev().setNext(null);
        }else{
            node.getPrev().setNext(node.getNext());
        }
    }



}
