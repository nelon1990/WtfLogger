package pers.nelon.wtflogger;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by 李冰锋 on 2017/3/9 10:46.
 * E-mail:libf@ppfuns.com
 * Package: pers.nelon.wtflogger.refactor
 */

public class KvFormatter {

    public final static String TAG = KvFormatter.class.getSimpleName();

    private static final String PREFIX = "\t>  ";
    private static final String SEPERATE = " : ";
    private static final String NEW_LINE = "\n";


    public static String simpleVal(String pKey, Object val) {
        return PREFIX + pKey + SEPERATE + val;
    }

    public static String listVal(String pKey, List val) {
        String str = PREFIX + pKey + SEPERATE +
                NEW_LINE;
        for (Object o : val) {
            str += "\t" + PREFIX + val.indexOf(o) + SEPERATE + o +
                    NEW_LINE;
        }
        return str;
    }

    public static String arrayVal(String pKey, Object[] val) {
        return listVal(pKey, Arrays.asList(val));
    }

    public static String mapVal(String pKey, Map val) {
        String str = PREFIX + pKey + SEPERATE +
                NEW_LINE;

        for (Object key : val.keySet()) {
            str += "\t" + PREFIX + key + SEPERATE + val.get(key) +
                    NEW_LINE;
        }
        return str;
    }
}
