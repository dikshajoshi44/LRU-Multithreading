package PracticeLRU.Service;

import PracticeLRU.LLNode.Node;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public interface LRUCacheInterface {
    public void putkey(String key, String value);
    public String getKey(String key);
}
