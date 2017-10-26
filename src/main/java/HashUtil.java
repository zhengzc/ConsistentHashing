/**
 * @auther Created by johnson.zheng
 * @pray Code is far away from bug with the animal alpaca protecting. amen!
 * @date 2017/10/25
 */
public class HashUtil {
    public static int bkdrhash(String str) {
        final int seed = 131;

        int hash = 0;

        for (int i = 0; i < str.length(); i++) {
            hash = hash * seed + (int)str.charAt(i);
        }

        return hash & 0x7FFFFFFF;
    }


    public static int aphash(String str) {
        int hash = 0;

        for (int i = 0; i < str.length(); i++) {
            if ((i & 1) == 0) {
                hash ^= (hash << 7) ^ (str.charAt(i)) ^ (hash >> 3);
            } else {
                hash ^= ~((hash << 11) ^ (str.charAt(i)) ^ (hash >> 5));
            }
        }

        return hash & 0x7FFFFFFF;
    }

    public static int jshash(String str) {
        int hash = 0;

        for (int i = 0; i < str.length(); i++) {
            hash ^= (hash << 5) + (int)str.charAt(i) + (hash >> 2);
        }

        return hash & 0x7FFFFFFF;
    }

    public static int rshash(String str) {
        int hash = 0;

        int a = 63689;
        final int b = 378551;

        for (int i = 0; i < str.length(); i++) {
            hash = hash * a + (int)str.charAt(i);
            a *= b;
        }

        return hash & 0x7FFFFFFF;
    }

    public static int sdbmhash(String str) {
        int hash = 0;

        for (int i = 0; i < str.length(); i++) {
            hash = (int)str.charAt(i) + (hash << 6) + (hash << 16) - hash;
        }

        return hash & 0x7FFFFFFF;
    }

    public static int pjwhash(String str) {
        int BitsInUnignedInt = 32;
        int ThreeQuarters    = 24;
        int OneEighth        = 4;
        int HighBits         = (int)(0xFFFFFFFF) << (BitsInUnignedInt - OneEighth);
        int hash             = 0;
        int test             = 0;

        for (int i = 0; i < str.length(); i++) {
            hash = (hash << OneEighth) + (int)str.charAt(i);
            if ((test = hash & HighBits) != 0)
            {
                hash = ((hash ^ (test >> ThreeQuarters)) & (~HighBits));
            }
        }

        return hash & 0x7FFFFFFF;
    }

    public static int elfhash(String str) {
        int hash = 0;
        int x = 0;

        for (int i = 0; i < str.length(); i++) {
            hash = (hash << 4) + (int)str.charAt(i);

            if ((x & hash & 0xF0000000L) != 0) {
                hash ^= x >> 24;
                hash &= ~x;
            }
        }

        return hash & 0x7FFFFFFF;
    }

    public static int djbhash(String str) {
        int hash = 5381;

        for (int i = 0; i < str.length(); i++) {
            hash += (hash << 5) + (int)str.charAt(i);
        }

        return hash & 0x7FFFFFFF;
    }

    public static int dekhash(String str) {
        int hash = str.length();

        for (int i = 0; i < str.length(); i++) {
            hash = (hash << 5) ^ (hash >> 27) ^ (int)str.charAt(i);
        }

        return hash & 0x7FFFFFFF;
    }

    public static int bphash(String str) {
        int hash = str.length();

        for (int i = 0; i < str.length(); i++) {
            hash = (hash << 7) ^ (int)str.charAt(i);
        }

        return hash & 0x7FFFFFFF;
    }

    public static int fnvhash(String str) {
        int fnvprime = 0x811C9DC5;
        int hash = 0;

        for (int i = 0; i < str.length(); i++) {
            hash *= fnvprime;
            hash ^= (int)str.charAt(i);
        }

        return hash & 0x7FFFFFFF;
    }

    public static int javahash(String str) {
        int hash = 0;

        for (int i = 0; i < str.length(); i++) {
            hash = hash * 31 + (int)str.charAt(i);
        }

        return hash & 0x7FFFFFFF;
    }
}
