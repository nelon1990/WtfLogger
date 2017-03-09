package pers.nelon.wtflogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by 李冰锋 on 2017/3/1 16:53.
 * E-mail:libf@ppfuns.com
 * Package: pers.nelon.wtflogger.refactor
 */
class JsonFormatter {
    public final static String TAG = JsonFormatter.class.getSimpleName();

    private static final int INDENT = 1;
    private static final String PREFIX = "";

    static String format(String unFormatted) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            JSONObject jsonObject = new JSONObject(unFormatted);
            stringBuilder.append(createIndent(INDENT - 1));
            recursiveParseObject(INDENT + 1, jsonObject, stringBuilder);
        } catch (JSONException pE) {
            pE.printStackTrace();
            try {
                JSONArray jsonArray = new JSONArray(unFormatted);
                stringBuilder.append(createIndent(INDENT - 1));
                recursiveParseArray(INDENT + 1, jsonArray, stringBuilder);
            } catch (JSONException pE1) {
                pE1.printStackTrace();
                stringBuilder = new StringBuilder("INVAIL JSON");
            }
        }
        return createIndent(INDENT == 1 ? 1 : INDENT - 1) + stringBuilder.toString();
    }

    private static void recursiveParseArray(int pIndentNum, JSONArray pJsonArray, StringBuilder pStringBuilder) {
        pStringBuilder.append("[")
                .append("\n")
                .append(PREFIX)
                .append(createIndent(pIndentNum));

        for (int i = 0; i < pJsonArray.length(); i++) {
            try {
                String endFix;

                if (i == pJsonArray.length() - 1) {
                    endFix = "\n" + PREFIX;
                } else {
                    endFix = "," + "\n" + PREFIX + createIndent(pIndentNum);
                }

                Object o = pJsonArray.get(i);
                if (o instanceof JSONArray) {
                    recursiveParseArray(pIndentNum + 1, (JSONArray) o, pStringBuilder);
                    pStringBuilder.append(endFix);
                } else if (o instanceof JSONObject) {
                    recursiveParseObject(pIndentNum + 1, (JSONObject) o, pStringBuilder);
                    pStringBuilder.append(endFix);
                } else {
                    parseSingle(o, pStringBuilder);
                    pStringBuilder.append(endFix);
                }
            } catch (JSONException pE) {
                pE.printStackTrace();
            }
        }
        pStringBuilder.append(createIndent(pIndentNum - 1))
                .append("]");
    }

    private static void recursiveParseObject(int pIndentNum, JSONObject pJsonObject, StringBuilder pStringBuilder) {
        pStringBuilder.append("{")
                .append("\n")
                .append(PREFIX)
                .append(createIndent(pIndentNum));

        Iterator<String> keys = pJsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            pStringBuilder.append("\"").append(key).append("\":");
            String endFix;

            if (keys.hasNext()) {
                endFix = "," + "\n" + PREFIX + createIndent(pIndentNum);
            } else {
                endFix = "\n" + PREFIX;
            }

            try {
                Object o = pJsonObject.get(key);
                if (o instanceof JSONArray) {
                    recursiveParseArray(pIndentNum + 1, (JSONArray) o, pStringBuilder);
                    pStringBuilder.append(endFix);
                } else if (o instanceof JSONObject) {
                    recursiveParseObject(pIndentNum + 1, (JSONObject) o, pStringBuilder);
                    pStringBuilder.append(endFix);
                } else {
                    parseSingle(o, pStringBuilder);
                    pStringBuilder.append(endFix);
                }
            } catch (JSONException pE) {
                pE.printStackTrace();
            }
        }

        pStringBuilder.append(createIndent(pIndentNum - 1))
                .append("}");
    }


    private static void parseSingle(Object pO, StringBuilder pStringBuilder) {
        if (pO instanceof String) {
            pO = "\"" + pO + "\"";
        }
        pStringBuilder.append(pO);
    }

    private static String createIndent(int pIndentNum) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < pIndentNum; i++) {
            stringBuilder.append("\t");
        }
        return stringBuilder.toString();
    }
}
