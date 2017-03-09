package pers.nelon.wtflogger;

import android.util.Log;

import pers.nelon.wtflogger.i.ILogFrist;

/**
 * Created by 李冰锋 on 2017/2/28 15:04.
 * E-mail:libf@ppfuns.com
 * Package: pers.nelon.wtflogger.refactor
 */
public class WtfLog {
    public final static String TAG = WtfLog.class.getSimpleName();

    public final static ILogFrist v = new WtfLogger(Log.VERBOSE);
    public final static ILogFrist d = new WtfLogger(Log.DEBUG);
    public final static ILogFrist i = new WtfLogger(Log.INFO);
    public final static ILogFrist w = new WtfLogger(Log.WARN);
    public final static ILogFrist e = new WtfLogger(Log.ERROR);
    public final static ILogFrist wtf = new WtfLogger(Log.ERROR);
}
