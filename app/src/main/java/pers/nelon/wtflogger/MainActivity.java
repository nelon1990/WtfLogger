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

        String json = "{\"employees\":[{\"firstName\":\"Bill\",\"lastName\":\"Gates\"},{\"firstName\":\"George\",\"lastName\":\"Bush\"},{\"firstName\":\"Thomas\",\"lastName\":\"Carter\"}]}";

        WtfLog.d.title("示例")
                .tmpTag(TAG)
                .json(json)
                .print();

        WtfLog.d.title("示例")
                .tmpTag(TAG)
                .key("key1").intVal(1)
                .key("key2").stringVal("string1")
                .key("key2").floatVal(3.5f)
                .key("map").mapVal(map)
                .key("list").listVal(Arrays.asList(1, 2, 3, 4))
                .print();

        User user = new User();
        user.username = "nelon";
        user.password = "123456";

        WtfLog.d.title("示例")
                .tmpTag(TAG)
                .bean(user)
                .print();

        WtfLog.d.title("示例")
                .tmpTag(TAG)
                .stackTrace()
                .threadInfo()
                .print();

    }


    public class User {
        String username;
        String password;
    }

}
