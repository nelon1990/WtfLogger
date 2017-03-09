package pers.nelon.wtflogger;

import android.util.Log;
import android.util.SparseArray;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import pers.nelon.wtflogger.i.IKvNext;
import pers.nelon.wtflogger.i.ILogFrist;
import pers.nelon.wtflogger.i.ITagNext;

/**
 * Created by 李冰锋 on 2017/2/28 16:07.
 * E-mail:libf@ppfuns.com
 * Package: pers.nelon.wtflogger.refactor
 */
public class WtfLogger implements
        ILogFrist,
        ITagNext,
        IKvNext {
    public final static String TAG = WtfLogger.class.getSimpleName();

    private static final int ORDER_MSG = 0;
    private static final int ORDER_DATE = 1;
    private static final int ORDER_THREAD = 2;
    private static final int ORDER_STACKTRACE = 3;
    private static final int ORDER_KV = 4;
    private static final int ORDER_JSON = 5;
    private static final int ORDER_XML = 6;

    private SparseArray<String> mLabelMap = makeLabelArray(
            ORDER_MSG, "Msg",
            ORDER_DATE, "Date",
            ORDER_THREAD, "Thread",
            ORDER_STACKTRACE, "Stacktrace",
            ORDER_KV, "Key-Value",
            ORDER_XML, "Xml",
            ORDER_JSON, "Json");

    private ThreadLocal<String> mTagLocal = new ThreadLocal<>();
    private ThreadLocal<String> mKeyLocal = new ThreadLocal<>();
    private ThreadLocal<SparseArray<String>> mLogArrayLocal = new ThreadLocal<SparseArray<String>>() {
        @Override
        protected SparseArray<String> initialValue() {
            return new SparseArray<>();
        }
    };
    private volatile int mPriority;

    WtfLogger(int pPriority) {
        mPriority = pPriority;
    }

    @Override
    public ITagNext tag(String pTag) {
        mTagLocal.set(pTag);
        return this;
    }

    @Override
    public ITagNext json(String pJson) {
        String format = JsonFormatter.format(pJson);
        String decorate = decorate(ORDER_JSON, format);
        mLogArrayLocal.get()
                .put(ORDER_JSON, decorate);
        return this;
    }

    @Override
    public ITagNext listVal(List pList) {
        String val = KvFormatter.listVal(mKeyLocal.get(), pList);
        kvInternal(val);
        return this;
    }

    @Override
    public ITagNext mapVal(Map pMap) {
        String val = KvFormatter.mapVal(mKeyLocal.get(), pMap);
        kvInternal(val);
        return this;
    }

    @Override
    public ITagNext arrayVal(Object[] pArray) {
        String val = KvFormatter.arrayVal(mKeyLocal.get(), pArray);
        kvInternal(val);
        return this;
    }

    @Override
    public ITagNext stringVal(String pString) {
        String val = KvFormatter.simpleVal(mKeyLocal.get(), pString);
        kvInternal(val);
        return this;
    }

    @Override
    public ITagNext intVal(int pInt) {
        String val = KvFormatter.simpleVal(mKeyLocal.get(), pInt);
        kvInternal(val);
        return this;
    }

    @Override
    public ITagNext doubleVal(double pDouble) {
        String val = KvFormatter.simpleVal(mKeyLocal.get(), pDouble);
        kvInternal(val);
        return this;
    }

    @Override
    public ITagNext longVal(long pLong) {
        String val = KvFormatter.simpleVal(mKeyLocal.get(), pLong);
        kvInternal(val);
        return this;
    }

    @Override
    public ITagNext floatVal(float pFloat) {
        String val = KvFormatter.simpleVal(mKeyLocal.get(), pFloat);
        kvInternal(val);
        return this;
    }

    @Override
    public ITagNext charVal(char pChar) {
        String val = KvFormatter.simpleVal(mKeyLocal.get(), pChar);
        kvInternal(val);
        return this;
    }

    @Override
    public ITagNext byteVal(byte pByte) {
        String val = KvFormatter.simpleVal(mKeyLocal.get(), pByte);
        kvInternal(val);
        return this;
    }

    @Override
    public ITagNext hexVal(int pHex) {
        String val = KvFormatter.simpleVal(mKeyLocal.get(), Integer.toHexString(pHex));
        kvInternal(val);
        return this;
    }

    @Override
    public ITagNext binVal(int pBin) {
        String val = KvFormatter.simpleVal(mKeyLocal.get(), Integer.toBinaryString(pBin));
        kvInternal(val);
        return this;
    }

    private void kvInternal(Object singleKv) {
        String kvStr = mLogArrayLocal.get().get(ORDER_KV);
        if (kvStr == null) {
            kvStr = "";
        }
        String afterDecorate = decorate(ORDER_KV, kvStr + singleKv);
        mLogArrayLocal.get()
                .put(ORDER_KV, afterDecorate);
    }

    @Override
    public ITagNext msg(String pMsg) {
        String tmpMsg = "\t" + pMsg;
        String afterDecorate = decorate(ORDER_MSG, tmpMsg);
        mLogArrayLocal.get()
                .put(ORDER_MSG, afterDecorate);
        return this;
    }

    @Override
    public ITagNext xml(String pXml) {
        String format = XmlFormatter.format(pXml);
        String decorate = decorate(ORDER_XML, format);
        mLogArrayLocal.get()
                .put(ORDER_XML, decorate);
        return this;
    }

    @Override
    public IKvNext key(String pKey) {
        mKeyLocal.set(pKey);
        return this;
    }

    @Override
    public ITagNext stackTrace() {
        String stacktrace = LogHelper.getStacktrace(10, LogHelper.class, WtfLogger.class);
        String decorate = decorate(ORDER_STACKTRACE, stacktrace);
        mLogArrayLocal.get()
                .put(ORDER_STACKTRACE, decorate);
        return this;
    }

    @Override
    public ITagNext threadInfo() {
        String threadInfo = LogHelper.getThreadInfo();
        String decorate = decorate(ORDER_THREAD, threadInfo);
        mLogArrayLocal.get()
                .put(ORDER_THREAD, decorate);
        return this;
    }

    @Override
    public ITagNext date() {
        String date = LogHelper.getDate();
        String afterDecorate = decorate(ORDER_DATE, date);
        mLogArrayLocal.get()
                .put(ORDER_DATE, afterDecorate);
        return this;
    }

    @Override
    public void print() {
        StringBuilder msg = new StringBuilder();
        Set<LogHolder> holderSet = new TreeSet<>();

        SparseArray<String> array = mLogArrayLocal.get();
        for (int i = 0; i < array.size(); i++) {
            LogHolder logHolder = new LogHolder(array.keyAt(i), array.valueAt(i));
            holderSet.add(logHolder);
        }

        for (LogHolder logHolder : holderSet) {
            msg.append(logHolder.msg);
        }

        Log.println(mPriority, mTagLocal.get(), msg.toString());

        mLogArrayLocal.remove();
        mTagLocal.remove();
    }

    private String decorate(int pOrder, String pLog) {
        StringBuilder builder = new StringBuilder();
        String label = mLabelMap.get(pOrder);
        builder.append("[").append(label).append("]").append(":");

        if (pLog.startsWith(builder.toString())) {
            pLog = pLog.substring(builder.length(), pLog.length());
        }
        if (pLog.startsWith("\n")) {
            pLog = pLog.substring(1, pLog.length());
        }
        if (pLog.endsWith("\n")) {
            pLog = pLog.substring(0, pLog.length() - 1);
        }

        builder.append("\n")
                .append(pLog).append("\n");
        return builder.toString();
    }


    private <T> SparseArray<T> makeLabelArray(Object... pObjects) {
        SparseArray<T> map = new SparseArray<>();
        int key = 0;
        T val;
        try {
            for (int i = 0; i < pObjects.length; i++) {
                if (i % 2 == 0) {
                    key = (int) pObjects[i];
                } else {
                    val = (T) pObjects[i];
                    map.put(key, val);
                }
            }
        } catch (Exception ignored) {
            map.clear();
        }

        return map;
    }

    private class LogHolder implements Comparable<LogHolder> {
        int order;
        String msg;

        public LogHolder(int pOrder, String pMsg) {
            order = pOrder;
            msg = pMsg;
        }

        @Override
        public int compareTo(LogHolder o) {
            return this.order - o.order;
        }
    }

}
