import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @auther Created by johnson.zheng
 * @pray Code is far away from bug with the animal alpaca protecting. amen!
 * @date 2017/10/24
 */

@Slf4j
public class TestConsistentHash {

    @Test
    public void test() {
        ConsistentHash consistentHash = new ConsistentHash(new String[]{"127.0.0.1", "127.0.0.2","127.0.0.3","127.0.0.4"});

        Map<String, Integer> countMap = new HashMap<>();

        for(int i = 0 ; i < 100000 ; i++) {
            String tmp = UUID.randomUUID().toString();
            String key = consistentHash.hash(tmp);
//            log.debug(">>>{}:{}", tmp, key);

            if (countMap.containsKey(key)) {
                countMap.put(key, countMap.get(key) + 1);
            }else{
                countMap.put(key, 1);
            }
        }

        log.debug(">>>{}", JSON.toJSONString(countMap));
    }

    @Test
    public void testAddAndRemoveKey() {
        ConsistentHash consistentHash = new ConsistentHash(new String[]{"10.0.0.1", "10.0.0.2","10.0.0.3","10.0.0.4"});

        hashList(consistentHash);

        consistentHash.addKey("10.0.0.5");
        hashList(consistentHash);

        consistentHash.removeKey("10.0.0.2");
        hashList(consistentHash);
    }

    private void hashList(ConsistentHash consistentHash) {
        Map<String, Integer> countMap = new HashMap<>();
        for(int i = 0 ; i < 10 ; i++) {
            String str = "test" + i;
            String key = consistentHash.hash(str);
            log.debug(">>>{}:{}", str, key);

            if (countMap.containsKey(key)) {
                countMap.put(key, countMap.get(key) + 1);
            }else{
                countMap.put(key, 1);
            }
        }

        log.debug(">>>{}", JSON.toJSONString(countMap));
    }
}
