package cn.xwj.sample;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.xwj.easy.E;
import cn.xwj.easy.helper.EPermission;
import cn.xwj.easy.util.LogUtil;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        E.view(this).transparentStatusBar();
    }
}
