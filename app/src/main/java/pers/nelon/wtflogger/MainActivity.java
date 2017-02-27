package pers.nelon.wtflogger;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Map<String, String> map = new ArrayMap<>();
        map.put("map key1", "1");
        map.put("map key2", "2");
        map.put("map key3", "3");
        map.put("map key4", "4");

        String json = "{\"glossary\":{\"title\":\"example glossary\",\"GlossDiv\":{\"title\":\"S\",\"GlossList\":{\"GlossEntry\":{\"ID\":\"SGML\",\"SortAs\":\"SGML\",\"GlossTerm\":\"Standard Generalized Markup Language\",\"Acronym\":\"SGML\",\"Abbrev\":\"ISO 8879:1986\",\"GlossDef\":{\"para\":\"A meta-markup language, used to create markup languages such as DocBook.\",\"GlossSeeAlso\":[\"GML\",\"XML\"]},\"GlossSee\":\"markup\"}}}}}";

        User user = new User();
        user.username = "nelon";
        user.password = "123456";

        WtfLog.d.tmpTag("demo")
                .title("title")
                .msg("这是一条很长很长的消息\n这是一条很长很长的消息\n这是一条很长很长的消息")
                .date()
                .threadInfo()
                .stackTrace()
                .bean(user)
                .key("key").stringVal("val")
                .key("map").mapVal(map)
                .key("list").listVal(Arrays.asList("a", "b", "c", "d", "e", "f"))
                .json("APP黑名单", json)
                .json("json2", json)
                .print();
    }


    public class User {
        String username;
        String password;
    }

}
