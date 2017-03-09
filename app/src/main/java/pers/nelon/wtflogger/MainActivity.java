package pers.nelon.wtflogger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String json = "{\"glossary\":{\"title\":\"example glossary\",\"GlossDiv\":{\"title\":\"S\",\"GlossList\":{\"GlossEntry\":{\"ID\":\"SGML\",\"SortAs\":\"SGML\",\"GlossTerm\":\"Standard Generalized Markup Language\",\"Acronym\":\"SGML\",\"Abbrev\":\"ISO 8879:1986\",\"GlossDef\":{\"para\":\"A meta-markup language, used to create markup languages such as DocBook.\",\"GlossSeeAlso\":[\"GML\",\"XML\"]},\"GlossSee\":\"markup\"}}}}}";
        String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" +
                "<!--  Copyright w3school.com.cn -->\n" +
                "<note>\n" +
                "\t<to>George</to>\n" +
                "\t<from>John</from>\n" +
                "\t<heading>Reminder</heading>\n" +
                "\t<body>Don't forget the meeting!</body>\n" +
                "</note> \n";

        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put(1, 45);
        objectObjectHashMap.put("asdas", this);
        objectObjectHashMap.put("va", "avad");

        WtfLog.i.tag("test")
                .msg("他们用自己喜欢的方式,带着喜欢的人,用喜欢的节奏,和一群喜欢摩托旅行的大家庭开始了这次的海南环岛之旅。")
                .stackTrace()
                .date()
                .json(json)
                .threadInfo()
                .stackTrace()
                .key("int key").intVal(233)
                .key("float").floatVal(1.654545f)
                .key("hex").hexVal(45)
                .key("bin").binVal(878)
                .key("array").arrayVal(new Integer[]{11, 2, 5, 3, 8, 4})
                .key("list").listVal(Arrays.asList(124, "qweqw,", 124, "afaf"))
                .xml(xml)
                .key("map").mapVal(objectObjectHashMap)
                .print();
    }


    public class User {
        String username;
        String password;
        private int age;
    }

}
