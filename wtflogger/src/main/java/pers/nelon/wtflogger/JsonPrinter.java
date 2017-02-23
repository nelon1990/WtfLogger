package pers.nelon.wtflogger;

import pers.nelon.wtflogger.WtfLog.Logger;

/**
 * Created by 李冰锋 on 2016/9/8 12:16.
 * E-mail:libf@ppfuns.com
 * Package: com.ppfuns.wtflogger
 */
class JsonPrinter extends AbstractPrinter<String> {
    public final static String TAG = JsonPrinter.class.getSimpleName();

    private String jsonStr;

    public JsonPrinter(Logger logger, String json) {
        super(logger);
        jsonStr = json;
    }

    public Logger stringFormat() {
        return getLogger();
    }

    @Override
    void reset(String pS) {

    }
}
