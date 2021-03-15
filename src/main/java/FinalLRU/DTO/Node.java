package FinalLRU.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {

    private String key;
    private String value;

    private Node prev;
    private Node next;

    public Node(String key, String value){
        this.key = key;
        this.value = value;
    }
}
