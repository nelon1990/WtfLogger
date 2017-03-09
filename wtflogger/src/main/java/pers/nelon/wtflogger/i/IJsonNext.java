package pers.nelon.wtflogger.i;

/**
 * Created by 李冰锋 on 2017/2/28 18:10.
 * E-mail:libf@ppfuns.com
 * Package: pers.nelon.wtflogger.i
 */

public interface IJsonNext {
    ITagNext jsonString(String pJson);

    ITagNext jsonObject(Object pObj);
}
