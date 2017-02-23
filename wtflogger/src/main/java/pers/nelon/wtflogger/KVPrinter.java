package pers.nelon.wtflogger;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import pers.nelon.wtflogger.WtfLog.Logger;

/**
 * Created by 李冰锋 on 2016/9/8 9:28.
 * E-mail:libf@ppfuns.com
 * Package: com.ppfuns.wtflogger
 */
class KVPrinter extends AbstractPrinter<String> {
    public final static String TAG = KVPrinter.class.getSimpleName();

    private String str;

    KVPrinter(Logger logger, String key) {
        super(logger);
        str = "\t>  " + key + " : ";
    }

    public Logger intVal(int val) {
        Logger logger = getLogger();
        str += val + "\n";
        logger.addKvStr(str);
        return logger;
    }

    public Logger longVal(long val) {
        Logger logger = getLogger();
        str += val + "\n";
        logger.addKvStr(str);
        return logger;
    }

    public Logger floatVal(float val) {
        Logger logger = getLogger();
        str += val + "\n";
        logger.addKvStr(str);
        return logger;
    }

    public Logger stringVal(String val) {
        Logger logger = getLogger();
        str += val + "\n";
        logger.addKvStr(str);
        return logger;
    }

    public Logger listVal(List val) {
        Logger logger = getLogger();
        str += "\n";
        for (Object o : val) {
            str += "\t\t> " + val.indexOf(o) + ": " + o + "\n";
        }
        logger.addKvStr(str);
        return logger;
    }

    public Logger stringArray(String[] strArr) {
        List<String> stringList = Arrays.asList(strArr);
        return listVal(stringList);
    }

    public Logger intArray(int[] intArr) {
        return listVal(Arrays.asList(intArr));
    }

    public Logger floatArray(float[] floatArr) {
        return listVal(Arrays.asList(floatArr));
    }

    public Logger mapVal(Map val) {
        Logger logger = getLogger();

        str += "\n";
        for (Object o : val.keySet()) {
            str += "\t\t> " + o + ": " + val.get(o) + "\n";
        }

        logger.addKvStr(str);
        return logger;
    }

    @Override
    protected void reset(String pT) {
        str = "\t>  " + pT + " : ";
    }

}
