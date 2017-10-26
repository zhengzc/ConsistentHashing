import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @auther Created by johnson.zheng
 * @pray Code is far away from bug with the animal alpaca protecting. amen!
 * @date 2017/10/24
 *
 * 一致哈希算法
 */
@Slf4j
public class ConsistentHash {
    private String[] keys;
    //虚拟节点个数
    private int virtualNodeCount;

    //使用treemap来构建哈希环，提高查找速度
    private TreeMap<Integer,String> hashCircle = new TreeMap<>();

    public ConsistentHash(String[] keys) {
        this(keys, 100);
    }

    public ConsistentHash(String[] keys , int virtualNodeCount) {
        this.keys = keys;
        //创建虚拟节点
        if (virtualNodeCount < 1 || virtualNodeCount > 999) {
            throw new IllegalArgumentException("virtualNodeCount must bettween 1 - 999");
        }
        this.virtualNodeCount = virtualNodeCount;

        //将key放如hash环
        for (String key : keys) {
            addKey(key);
        }
    }

    /**
     * 每个key放入hash环
     * @param key
     */
    public void addKey(String key) {
        List<String> virtualKeys = this.genVirtualNodeKeys(key);
        for(String tmp : virtualKeys) {
            int keyHashCode = _hash(tmp);
//            log.debug("virtual node : {}->{}",tmp,keyHashCode);
            hashCircle.put(keyHashCode, tmp);
        }

    }

    /**
     * 移除key
     * @param key
     */
    public void removeKey(String key) {
        List<String> virtualKeys = this.genVirtualNodeKeys(key);
        for(String tmp : virtualKeys) {
            int keyHashCode = _hash(tmp);
//            log.debug("virtual node : {}->{}",tmp,keyHashCode);
            hashCircle.remove(keyHashCode);
        }
    }

    /**
     * 一致hash结果
     * @param str
     * @return
     */
    public String hash(String str) {
        int argHash = _hash(str);
        SortedMap<Integer,String> subTreeMap = hashCircle.tailMap(argHash);
        int tmpKey = subTreeMap.isEmpty() ? hashCircle.firstKey() : subTreeMap.firstKey();
        return getRealKey(hashCircle.get(tmpKey));
    }

    /**
     * 内部使用的hash算法,为了增强hash算法分布性，增加了md5运算
     * @param key
     * @return
     */
    private int _hash(String key) {
        return md5(key).hashCode();
    }

    /**
     * 生成虚拟node列表
     * @param key
     * @return
     */
    private List<String> genVirtualNodeKeys(String key){
        List<String> keys = new ArrayList<>(virtualNodeCount);
        for(int i = 0 ; i < virtualNodeCount ; i++) {
            StringBuilder sb = new StringBuilder(key);
            sb.append("$V")
                    .append(String.format("%03d",i));

            keys.add(sb.toString());
        }

        return keys;
    }

    /**
     * 将虚拟key转化为实际key
     * @param virtualNodeKey
     * @return
     */
    private String getRealKey(String virtualNodeKey) {
        return virtualNodeKey.substring(0, virtualNodeKey.length() - 5);
    }

    private String md5(String str) {
        String ret = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            BASE64Encoder base64en = new BASE64Encoder();
            ret = base64en.encode(md5.digest(str.getBytes(Charset.forName("UTF-8"))));
            return ret;
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(),e);
        }

        return ret;
    }
}
