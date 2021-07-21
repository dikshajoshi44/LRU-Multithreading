package BestLRU;


import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
class Node{

    private String key;
    private String value;
    private Node next;
    private Node prev;

    public Node(String key, String value){
        this.key = key;
        this.value = value;
    }
}

@Getter
@Setter
public class LruCache {

    private static LruCache instance;
    private int capacity;
    private Map<String, Node> hashMapLru;

    private Node head;
    private Node tail;

    private LruCache(int Capacity){
        this.capacity = Capacity;
        this.hashMapLru = new HashMap<>();
        head = new Node("0", "0");
        tail = new Node("0", "0");
        head.setNext(tail);
        tail.setPrev(head);
    }



    public static LruCache getInstance(){
        if(instance == null){
            instance = new LruCache(5);
        }
        return instance;
    }


    public String get(String key){

        Node node = hashMapLru.get(key);

        if(node == null){
            return null;
        }

        deleteNode(node);
        insertNode(node);
        return node.getValue();

    }

    public void put(String key, String value){
        if(hashMapLru.containsKey(key)){
            deleteNode(hashMapLru.get(key));
        }

        if(hashMapLru.size() == capacity){
            deleteNode(tail.getPrev());
        }

        insertNode(new Node(key, value));
    }

    public void deleteNode(Node node){

        hashMapLru.remove(node.getKey());
        if(node.getPrev() != null)
            node.getPrev().setNext(node.getNext());

        if(node.getNext() != null)
            node.getNext().setPrev(node.getPrev());
    }

    public void insertNode(Node node){
        hashMapLru.put(node.getKey(), node);

//        if(head.getNext()!= null)
        head.getNext().setPrev(node);
        node.setNext(head.getNext());
        head.setNext(node);
        node.setPrev(head);

    }
}



@Getter
@Setter
class Driver{
    public static void main(String[] args){

        LruCache cacheType = LruCache.getInstance();

        cacheType.put("1", "100");
        System.out.println(cacheType.get("100"));

        cacheType.put("10", "1000");
        cacheType.put("3", "10000");
        cacheType.put("4", "10000");
        cacheType.put("3", "1000000");
        cacheType.put("1", "99");
        System.out.println(cacheType.get("3"));
        cacheType.put("3", "98");
        System.out.println(cacheType.get("10"));
        System.out.println(cacheType.get("1"));
       // System.out.println(cacheType.get("1"));
        cacheType.put("7", "998");
        cacheType.put("8", "998");
//        System.out.println(cacheType.get("4"));
//        cacheType.put("3", "100");
//        System.out.println(cacheType.get("10"));
//        System.out.println(cacheType.get("3"));

    }
}
