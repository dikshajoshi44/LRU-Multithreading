package LRUCache;

import java.util.HashMap;

public class LRUMain {

    private static int capacity;
    private final HashMap<String, Node> map;
    private LRUMain instance;

    private Node head = null;
    private Node tail = null;

    private LRUMain(int capacity){
        this.map = new HashMap<String, Node>();
    }

    public LRUMain getInstance(){
        if (this.instance == null){
            instance = new LRUMain(capacity);
        }

        return instance;
    }

    public void addSizeOfCache(int Capacity){
        capacity = Capacity;
    }

    public String getOperationInCache(String key){

        if(!map.containsKey(key)){
            return null;
        }

        Node node = map.get(key);
        deleteFromList(node);
        setHeadOfList(node);

        return node.getValue();

    }

    public void putOperationInCache(String key, String value){

            if(map.containsKey(key)){
                Node node = map.get(key);
                node.setValue(value);

                deleteFromList(node);
                setHeadOfList(node);

            }else{
                if(map.size() >= capacity){
                    map.remove(tail.getKey());
                    deleteFromList(tail);
                }

                Node newNode = new Node(key, value);
                map.put(key, newNode);
                setHeadOfList(newNode);
            }

    }

    public void deleteFromList(Node node){

        if(node.prev != null){
            node.prev.next = node.next;
        }else{
            head = node.next;
        }

        if(node.next != null) {
            node.next.prev = node.prev;
        }else{
            tail = node.prev;
        }

    }

    public void setHeadOfList(Node node){
            node.prev = null;
            node.next = head;

            if(head != null){
                head.prev = node;
            }

            head = node;
            if(tail == null){
                tail = head;
            }
    }

}
