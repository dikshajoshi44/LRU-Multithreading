package FinalLRU;

import FinalLRU.Enum.CacheTypes;
import FinalLRU.Service.CachingRuleFactory;
import FinalLRU.Service.CachingService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverClass {

    public static void main(String[] args){

        CachingRuleFactory ruleFactory = CachingRuleFactory.getInstance();
        CachingService cacheType = ruleFactory.getCachingTechniqueType(CacheTypes.LRU);

        cacheType.putKey("1", "100");
        cacheType.putKey("10", "1000");
        cacheType.putKey("3", "10000");
        cacheType.putKey("4", "10000");
        cacheType.putKey("3", "1000000");
        cacheType.putKey("10", "99");
        cacheType.putKey("1", "98");

        System.out.println(cacheType.getKey("1"));
        System.out.println(cacheType.getKey("10"));
        System.out.println(cacheType.getKey("3"));






    }

}
