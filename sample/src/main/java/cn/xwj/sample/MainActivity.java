package cn.xwj.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bncggle.sample.R;

import cn.xwj.easyutil.util.ViewUtil;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtil.setTransparentStatusBar(this);
    }
}
