package pers.nelon.wtflogger;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by 李冰锋 on 2016/9/7 16:35.
 * E-mail:libf@ppfuns.com
 * Package: com.ppfuns.wtflogger
 */
class WtfLog {
    private final static String TAG = WtfLog.class.getSimpleName();
    private static String mTag;

    private static ArrayList<String> filterList;

    /**
     * 表示是否反向过滤，默认false
     */
    private static boolean reverseFilter;

    final static Logger v = new Logger("v");
    final static Logger i = new Logger("i");
    final static Logger d = new Logger("d");
    final static Logger w = new Logger("w");
    final static Logger e = new Logger("e");
    final static Logger wtf = new Logger("wtf");

    static {
        if (filterList == null) {
            filterList = new ArrayList<>();
        } else {
            filterList.clear();
        }
        mTag = WtfLog.class.getSimpleName();

        reverseFilter = false;
    }

    public static void setTag(String tag) {
        mTag = tag;
    }

    public static void filter(String tag) {
        filterList.add(tag);
    }

    public static void reverseFilter(boolean reverse) {
        reverseFilter = reverse;
    }

    static final class Logger {
        private StringBuilder mStringBuilder;

        private String type;
        private String mTmpTag;

        /**
         * 打印用
         */
        private String title;
        private String threadInfo;
        private String stackTrace;
        private String kv;
        private String json;
        private String xml;
        private String bean;
        private KVPrinter mKvPrinter;
        private BeanPrinter mBeanPrinter;
        private final static int DEFAULT_STACKTRACE_LINE = 10;

        private Logger(String type) {
            this.type = type;
            this.mStringBuilder = new StringBuilder();

            this.title = "";
            this.threadInfo = "";
            this.stackTrace = "";
            this.kv = "";
            this.json = "";
            this.xml = "";
            this.bean = "";
        }

        public Logger title(String title) {
            this.title = "\t" + title;
            return this;
        }

        Logger tmpTag(String tag) {
            this.mTmpTag = tag;
            return this;
        }

        Logger threadInfo() {
            Thread thread = Thread.currentThread();
            threadInfo += "\t> thread_id : " + thread.getId() + "\n";
            threadInfo += "\t> thread_name : " + thread.getName();
            return this;
        }

        Logger stackTrace(int line) {
            StackTraceElement[] stackElements = new Throwable().getStackTrace();
            if (stackElements != null) {
                int length = stackElements.length > line ? line : stackElements.length;
                for (int i = 0; i < length; i++) {
                    stackTrace += "\t> " + stackElements[i] + "\n";
                }
                stackTrace += "\t> " + "....";
            }
            return this;
        }

        public Logger stackTrace() {
            return stackTrace(DEFAULT_STACKTRACE_LINE);
        }


        /**
         * K-V形式的打印
         *
         * @param key 要打印的key
         * @return
         */
        KVPrinter key(String key) {
            if (mKvPrinter == null) {
                synchronized (WtfLog.class) {
                    if (mKvPrinter == null) {
                        mKvPrinter = new KVPrinter(this, key);
                    }
                }
            } else {
                mKvPrinter.reset(key);
            }
            return mKvPrinter;
        }

        /**
         * 以对具有getter的bean进行打印
         *
         * @param bean
         * @return
         */
        public Logger bean(Object bean) {
            if (mBeanPrinter == null) {
                mBeanPrinter = new BeanPrinter(this, bean);
            } else {
                mBeanPrinter.reset(bean);
            }
            return mBeanPrinter.parse();
        }

        public void print() {
            String tmp = mTmpTag;

            if (tmp == null) {
                tmp = mTag;
            }

            if (tmp != null && !isFiltered(tmp)) {

                if (!TextUtils.isEmpty(title)) {
                    if (title.endsWith("\n")) {
                        title = title.substring(0, title.length() - 1);
                    }
                    mStringBuilder.append("[Title]:\n").append(title).append("\n");
                }

                if (!TextUtils.isEmpty(threadInfo)) {
                    if (threadInfo.endsWith("\n")) {
                        threadInfo = threadInfo.substring(0, threadInfo.length() - 1);
                    }
                    mStringBuilder.append("[ThreadInfo]:\n").append(threadInfo).append("\n");
                }

                if (!TextUtils.isEmpty(stackTrace)) {
                    if (stackTrace.endsWith("\n")) {
                        stackTrace = stackTrace.substring(0, stackTrace.length() - 1);
                    }
                    mStringBuilder.append("[StackInfo]:\n").append(stackTrace).append("\n");
                }

                if (!TextUtils.isEmpty(kv)) {
                    if (kv.endsWith("\n")) {
                        kv = kv.substring(0, kv.length() - 1);
                    }
                    mStringBuilder.append("[KV-Print]:\n").append(kv).append("\n");
                }

                if (!TextUtils.isEmpty(bean)) {
                    if (bean.endsWith("\n")) {
                        bean = bean.substring(0, bean.length() - 1);
                    }
                    mStringBuilder.append("[Bean-Print]:\n").append(bean).append("\n");
                }

                if (!TextUtils.isEmpty(json)) {
                    if (json.endsWith("\n")) {
                        json = json.substring(0, json.length() - 1);
                    }
                    mStringBuilder.append("[Json-Print]:\n").append(json).append("\n");
                }

                if (!TextUtils.isEmpty(xml)) {
                    if (xml.endsWith("\n")) {
                        xml = xml.substring(0, xml.length() - 1);
                    }
                    mStringBuilder.append("[Xml-Print]:\n").append(xml).append("\n");
                }

                /**
                 * 打印log
                 */
                synchronized (WtfLog.class) {
                    printLog(type, tmp, mStringBuilder.toString());
                    clear();
                }
            }
        }

        private void clear() {
            title = "";
            threadInfo = "";
            stackTrace = "";
            kv = "";
            json = "";
            xml = "";
            bean = "";
            mStringBuilder = new StringBuilder();
            mTmpTag = "";
        }

        /**
         * 判断tag过滤
         *
         * @param tmpTag 被判断的tag
         * @return true-过滤，false-不过滤
         */
        private boolean isFiltered(String tmpTag) {
            if (filterList != null) {
                for (String tag : filterList) {
                    if (tag.equalsIgnoreCase(tmpTag)) {
                        return !reverseFilter;
                    }
                }
            }
            return reverseFilter;
        }

        private void printLog(@NonNull String type, String tag, String msg) {
            msg += "\n";
            switch (type) {
                case "v":
                    Log.v(tag, msg);
                    break;
                case "i":
                    Log.i(tag, msg);
                    break;
                case "d":
                    Log.d(tag, msg);
                    break;
                case "w":
                    Log.w(tag, msg);
                    break;
                case "e":
                    Log.e(tag, msg);
                    break;
                case "wtf":
                    Log.wtf(tag, msg);
                    break;
            }
        }

        void addKvStr(String kvStr) {
            kv += kvStr;
        }

        void addJsonStr(String jsonStr) {
            json += jsonStr;
        }

        void addXmlStr(String xmlStr) {
            xml += xmlStr;
        }

        void addBeanStr(String beanStr) {
            bean += beanStr;
        }
    }


}

