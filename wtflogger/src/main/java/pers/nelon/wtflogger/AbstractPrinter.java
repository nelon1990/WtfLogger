package pers.nelon.wtflogger;

/**
 * Created by 李冰锋 on 2016/9/8 12:05.
 * E-mail:libf@ppfuns.com
 * Package: com.ppfuns.wtflogger
 */
abstract class AbstractPrinter<T> {
    public final static String TAG = AbstractPrinter.class.getSimpleName();

    private WtfLog.Logger mLogger;

    AbstractPrinter(WtfLog.Logger logger) {
        mLogger = logger;
    }

    WtfLog.Logger getLogger() {
        return mLogger;
    }

    abstract void reset(T pT);

    protected interface IPrinterLogger {
        void addKvStr(String kv);

        void addJsonStr(String json);

        void addXmlStr(String xmlStr);

        void addBeanStr(String beanStr);
    }
}
