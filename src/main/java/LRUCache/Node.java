package LRUCache;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Node {

    private String key;
    private String value;

    Node prev = null;
    Node next = null;

    public Node(String key, String value){
        this.key = key;
        this.value = value;
    }

}
