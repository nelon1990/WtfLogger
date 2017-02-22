package pers.nelon.wtflogger;

/**
 * Created by 李冰锋 on 2016/9/8 12:05.
 * E-mail:libf@ppfuns.com
 * Package: com.ppfuns.wtflogger
 */
public abstract class AbstractPrinter {
    public final static String TAG = AbstractPrinter.class.getSimpleName();

    private WtfLog.Logger mLogger;

    public AbstractPrinter(WtfLog.Logger logger) {
        mLogger = logger;
    }

    protected WtfLog.Logger getLogger() {
        WtfLog.Logger logger = mLogger;
        mLogger = null;
        return logger;
    }

}
