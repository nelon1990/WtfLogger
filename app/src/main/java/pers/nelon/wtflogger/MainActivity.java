package pers.nelon.wtflogger;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Arrays;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


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
