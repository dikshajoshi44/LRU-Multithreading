package FinalLRU.Service;

public interface CachingService {
    public String getKey(String key);
    public void putKey(String key, String value);
}
