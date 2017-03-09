package pers.nelon.wtflogger.i;

import java.util.List;
import java.util.Map;

/**
 * Created by 李冰锋 on 2017/2/28 18:09.
 * E-mail:libf@ppfuns.com
 * Package: pers.nelon.wtflogger.i
 */

public interface IKvNext {
    ITagNext listVal(List pList);

    ITagNext mapVal(Map pMap);

    ITagNext arrayVal(Object[] pArray);

    ITagNext stringVal(String pString);

    ITagNext intVal(int pInt);

    ITagNext doubleVal(double pDouble);

    ITagNext longVal(long pLong);

    ITagNext floatVal(float pFloat);

    ITagNext charVal(char pChar);

    ITagNext byteVal(byte pByte);

    ITagNext hexVal(int pHex);

    ITagNext binVal(int pBin);
}
