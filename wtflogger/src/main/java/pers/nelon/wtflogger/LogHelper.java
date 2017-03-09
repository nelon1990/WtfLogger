package pers.nelon.wtflogger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 李冰锋 on 2017/3/1 10:22.
 * E-mail:libf@ppfuns.com
 * Package: pers.nelon.wtflogger.refactor
 */
class LogHelper {
    public final static String TAG = LogHelper.class.getSimpleName();

    static String getStacktrace(int line, Class... filterClzs) {
        List<String> clzNameList = new ArrayList<>();
        for (Class filterClz : filterClzs) {
            clzNameList.add(filterClz.getName());
        }

        String stackTrace = "";
        StackTraceElement[] stackElements = new Throwable().getStackTrace();
        if (stackElements != null) {
            int length = stackElements.length > line ? line : stackElements.length;
            for (int i = 0; i < length; i++) {
                if (clzNameList.contains(stackElements[i].getClassName())) {
                    continue;
                }

                stackTrace += "\t> " + stackElements[i] + "\n";
            }
            stackTrace += "\t> " + "....";
        }
        return stackTrace;
    }

    static String getDate() {
        return "\t" + new Date(System.currentTimeMillis()).toString();
    }

    static String getThreadInfo() {
        Thread thread = Thread.currentThread();
        return "\t> thread_id : " + thread.getId() + "\n" +
                "\t> thread_name : " + thread.getName();
    }

}
