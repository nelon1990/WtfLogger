package pers.nelon.wtflogger;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import pers.nelon.wtflogger.WtfLog.Logger;

/**
 * Created by 李冰锋 on 2016/9/8 12:16.
 * E-mail:libf@ppfuns.com
 * Package: com.ppfuns.wtflogger
 */
class JsonPrinter extends AbstractPrinter<String> {
    public final static String TAG = JsonPrinter.class.getSimpleName();

    private static final int DEFAULT = 2;
    private String jsonStr;
    private StringBuilder mStringBuilder;
    private int mIndentNum;

    public JsonPrinter(Logger logger, String json) {
        super(logger);
        jsonStr = json;
        mStringBuilder = new StringBuilder();
        mIndentNum = DEFAULT;
    }

    @Override
    void reset(String pS) {
        jsonStr = pS;
        mIndentNum = DEFAULT;
        mStringBuilder = new StringBuilder();
    }

    public void parse() {
        if (jsonStr == null) {
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            mStringBuilder.append(createIndent(mIndentNum - 1));
            recursiveParseObject(mIndentNum++, jsonObject);
        } catch (JSONException pE) {
            pE.printStackTrace();
            try {
                JSONArray jsonArray = new JSONArray(jsonStr);
                mStringBuilder.append(createIndent(mIndentNum - 1));
                recursiveParseArray(mIndentNum++, jsonArray);
            } catch (JSONException pE1) {
                pE1.printStackTrace();
            }
        }

        getLogger().addJsonStr(mStringBuilder.append("\n").toString());
    }

    private void recursiveParseArray(int pIndentNum, JSONArray pJsonArray) {
        mStringBuilder.append("[")
                .append("\n")
                .append(createIndent(pIndentNum));
        Log.d(TAG, "start: " + pIndentNum);

        for (int i = 0; i < pJsonArray.length(); i++) {
            try {
                String endFix;

                if (i == pJsonArray.length() - 1) {
                    endFix = "\n";
                } else {
                    endFix = "," + "\n" + createIndent(pIndentNum);
                }

                Object o = pJsonArray.get(i);
                if (o instanceof JSONArray) {
                    // FIXME: 2017/2/23 直接++，在数组有多个元素时，会有问题
                    recursiveParseArray(mIndentNum++, (JSONArray) o);
                    mStringBuilder.append(endFix);
                } else if (o instanceof JSONObject) {
                    // FIXME: 2017/2/23 直接++，在数组有多个元素时，会有问题
                    recursiveParseObject(mIndentNum++, (JSONObject) o);
                    mStringBuilder.append(endFix);
                } else {
                    parseSingle(o);
                    mStringBuilder.append(endFix);
                }
            } catch (JSONException pE) {
                pE.printStackTrace();
            }
        }
        mStringBuilder.append(createIndent(pIndentNum - 1))
                .append("]");
        Log.d(TAG, "end: " + pIndentNum);
    }

    private void recursiveParseObject(int pIndentNum, JSONObject pJsonObject) {
        mStringBuilder.append("{")
                .append("\n")
                .append(createIndent(pIndentNum));
        Log.d(TAG, "start: " + pIndentNum);

        Iterator<String> keys = pJsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            mStringBuilder.append("\"" + key + "\":");
            String endFix;

            if (keys.hasNext()) {
                endFix = "," + "\n" + createIndent(pIndentNum);
            } else {
                endFix = "\n";
            }

            try {
                Object o = pJsonObject.get(key);
                if (o instanceof JSONArray) {
                    // FIXME: 2017/2/23 直接++，在数组有多个元素时，会有问题
                    recursiveParseArray(mIndentNum++, (JSONArray) o);
                    mStringBuilder.append(endFix);
                } else if (o instanceof JSONObject) {
                    // FIXME: 2017/2/23 直接++，在数组有多个元素时，会有问题
                    recursiveParseObject(mIndentNum++, (JSONObject) o);
                    mStringBuilder.append(endFix);
                } else {
                    parseSingle(o);
                    mStringBuilder.append(endFix);
                }
            } catch (JSONException pE) {
                pE.printStackTrace();
            }
        }

        mStringBuilder.append(createIndent(pIndentNum - 1))
                .append("}");
        Log.d(TAG, "end: " + pIndentNum);
    }


    private void parseSingle(Object pO) {
        if (pO instanceof String) {
            pO = "\"" + pO + "\"";
        }
        mStringBuilder.append(pO);
    }

    private String createIndent(int pIndentNum) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < pIndentNum; i++) {
            stringBuffer.append("\t");
        }
        return stringBuffer.toString();
    }
}
