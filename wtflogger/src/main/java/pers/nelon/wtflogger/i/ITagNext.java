package pers.nelon.wtflogger.i;

/**
 * Created by 李冰锋 on 2017/2/28 18:02.
 * E-mail:libf@ppfuns.com
 * Package: pers.nelon.wtflogger.i
 */

public interface ITagNext extends ILogFinal {
    ITagNext msg(String pMsg);

    ITagNext json(String pJson);

    ITagNext xml(String pXml);

    IKvNext key(String pKey);

    ITagNext stackTrace();

    ITagNext threadInfo();

    ITagNext date();
}
