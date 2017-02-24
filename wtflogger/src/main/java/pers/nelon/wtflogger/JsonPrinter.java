package pers.nelon.wtflogger;

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
    private final String s = createIndent(DEFAULT - 1) + ">";
    private String jsonStr;
    private StringBuilder mStringBuilder;
    private int mIndentNum;

    public JsonPrinter(Logger logger, String json) {
        super(logger);
        jsonStr = json;
        mStringBuilder = new StringBuilder();
        mStringBuilder.append(s);
        mIndentNum = DEFAULT;
    }

    @Override
    void reset(String pS) {
        jsonStr = pS;
        mIndentNum = DEFAULT;
        mStringBuilder = new StringBuilder();
        mStringBuilder.append(s);
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
                .append(s)
                .append(createIndent(pIndentNum));

        for (int i = 0; i < pJsonArray.length(); i++) {
            try {
                String endFix;

                if (i == pJsonArray.length() - 1) {
                    endFix = "\n" + s;
                } else {
                    endFix = "," + "\n" + s + createIndent(pIndentNum);
                }

                Object o = pJsonArray.get(i);
                if (o instanceof JSONArray) {
                    recursiveParseArray(pIndentNum + 1, (JSONArray) o);
                    mStringBuilder.append(endFix);
                } else if (o instanceof JSONObject) {
                    recursiveParseObject(pIndentNum + 1, (JSONObject) o);
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
    }

    private void recursiveParseObject(int pIndentNum, JSONObject pJsonObject) {
        mStringBuilder.append("{")
                .append("\n")
                .append(s)
                .append(createIndent(pIndentNum));

        Iterator<String> keys = pJsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            mStringBuilder.append("\"" + key + "\":");
            String endFix;

            if (keys.hasNext()) {
                endFix = "," + "\n" + s + createIndent(pIndentNum);
            } else {
                endFix = "\n" + s;
            }

            try {
                Object o = pJsonObject.get(key);
                if (o instanceof JSONArray) {
                    recursiveParseArray(pIndentNum + 1, (JSONArray) o);
                    mStringBuilder.append(endFix);
                } else if (o instanceof JSONObject) {
                    recursiveParseObject(pIndentNum + 1, (JSONObject) o);
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
