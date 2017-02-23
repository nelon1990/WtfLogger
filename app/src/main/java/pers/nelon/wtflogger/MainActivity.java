package pers.nelon.wtflogger;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Arrays;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Map<String, String> mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMap = new ArrayMap<>();
        mMap.put("nelon1", "23333");
        mMap.put("nelon2", "23333");
        mMap.put("nelon3", "23333");
        mMap.put("nelon4", "23333");

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><PARAM><DBID>35</DBID><SEQUENCE>atgtca</SEQUENCE><MAXNS>10</MAXNS><MINIDENTITIES>90</MINIDENTITIES><MAXEVALUE>10</MAXEVALUE><USERNAME>admin</USERNAME><PASSWORD>111111</PASSWORD><TYPE>P</TYPE><RETURN_TYPE>2</RETURN_TYPE></PARAM>";
        String json = "{\n" +
                "    \"glossary\": {\n" +
                "        \"title\": \"example glossary\",\n" +
                "        \"GlossDiv\": {\n" +
                "            \"title\": \"S\",\n" +
                "            \"GlossList\": {\n" +
                "                \"GlossEntry\": {\n" +
                "                    \"ID\": \"SGML\",\n" +
                "                    \"SortAs\": \"SGML\",\n" +
                "                    \"GlossTerm\": \"Standard Generalized Markup Language\",\n" +
                "                    \"Acronym\": \"SGML\",\n" +
                "                    \"Abbrev\": \"ISO 8879:1986\",\n" +
                "                    \"GlossDef\": {\n" +
                "                        \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\",\n" +
                "                        \"GlossSeeAlso1\": [\n" +
                "                            \"GML\",\n" +
                "                            \"XML\"\n" +
                "                        ]\n" +
                "                    },\n" +
                "                    \"GlossSee\": \"markup\"\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";

        WtfLog.d.title("test json")
                .tmpTag("json")
                .json(json)
                .key("asda").intVal(23333)
                .print();
    }

    public void print(View view) {
        WtfLog.d
                .addKvStr("asas");

        WtfLog.d.tmpTag("test")
                .title("test WtfLogger")
                .threadInfo()
                .stackTrace(999)
                .key("aaa").intVal(30)
                .key("list").listVal(Arrays.asList(1, 2, 3, 4, "adasdas"))
                .key("map").mapVal(mMap)
                .key("currentTimeMillis").longVal(System.currentTimeMillis())
                .print();

    }
}
