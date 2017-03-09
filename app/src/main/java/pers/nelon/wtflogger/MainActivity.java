package pers.nelon.wtflogger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String json = "{\"glossary\":{\"title\":\"example glossary\",\"GlossDiv\":{\"title\":\"S\",\"GlossList\":{\"GlossEntry\":{\"ID\":\"SGML\",\"SortAs\":\"SGML\",\"GlossTerm\":\"Standard Generalized Markup Language\",\"Acronym\":\"SGML\",\"Abbrev\":\"ISO 8879:1986\",\"GlossDef\":{\"para\":\"A meta-markup language, used to create markup languages such as DocBook.\",\"GlossSeeAlso\":[\"GML\",\"XML\"]},\"GlossSee\":\"markup\"}}}}}";
        final String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" +
                "<!--  Copyright w3school.com.cn -->\n" +
                "<note>\n" +
                "\t<to>George</to>\n" +
                "\t<from>John</from>\n" +
                "\t<heading>Reminder</heading>\n" +
                "\t<body>Don't forget the meeting!</body>\n" +
                "</note> \n";

        HashMap map = new HashMap<>();
        map.put("name", "nelon");
        map.put("age", 18);

        List list = Arrays.asList(1, 1f, "item 3");

        WtfLog.i.tag("WtfLog")
                .msg("Easy log with WtfLog !")
                .date()
                .threadInfo()
                .stackTrace()
                .json(json)
                .xml(xml)
                .key("int").intVal(255)
                .key("hex int").hexVal(255)
                .key("hex int").binVal(255)
                .key("map").mapVal(map)
                .key("list").listVal(list)
                .print();

    }


    public class User {
        String username;
        String password;
        private int age;
    }

}
