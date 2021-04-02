package FinalLRU.Service;

import FinalLRU.Enum.CacheTypes;

public class CachingRuleFactory {

    private static CachingRuleFactory instance;
    private LRUCache lruCache;

    private CachingRuleFactory(){
        this.lruCache = LRUCache.getInstance();
    }

    public static CachingRuleFactory getInstance(){
        if(instance == null){
            instance = new CachingRuleFactory();
        }

        return instance;
    }

    public CachingService getCachingTechniqueType(CacheTypes type){
        if(type.equals(CacheTypes.LRU)){
            return lruCache;
        }else{
            return lruCache;
        }
    }
}
