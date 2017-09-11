package cn.xwj.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.xwj.easy.E;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        E.view().transparentStatusBar(this);
    }
}
