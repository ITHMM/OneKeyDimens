package com.example.zcm.outputtxt;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zcm.outputtxt.utils.OutputXml;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    private CheckBox cb01;
    private CheckBox cb02;
    private EditText etInputPx;
    private TextView tvRemind;
    private static final String WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cb01 = findViewById(R.id.cb01);
        cb02 = findViewById(R.id.cb02);
        etInputPx = findViewById(R.id.et_inputpx);
        tvRemind = findViewById(R.id.tv_remind);
        AssetManager mgr = getAssets();//得到AssetManager
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/Roboto-LightItalic.ttf");//根据路径得到Typeface
//        etInputPx.setTypeface(tf);
        tvRemind.setTypeface(tf);//设置字体
        etInputPx.getPaint().setTypeface(tf);
        if (android.os.Build.VERSION.SDK_INT > 23)
            getPersimmions();
    }

    @TargetApi(23)
    public void getPersimmions() {
//        addPermission("android.permission.CAMERA");
//        addPermission("android.permission.RECORD_AUDIO");
//        addPermission("android.permission.READ_EXTERNAL_STORAGE");
//        addPermission("android.permission.MOUNT_UNMOUNT_FILESYSTEMS");
        if (checkSelfPermission(WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {// 第一次申请权限(勾选永久拒绝)
            requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 127);
        }
        if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {// 没有勾选永久拒绝
            requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 127);
        } else {
            if (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                Toast.makeText(this, "请确保该应用已经获取存储权限", Toast.LENGTH_LONG).show();
        }
    }

    /*为了帮助查找用户可能需要解释的情形，
    Android 提供了一个实用程序方法，
    即 shouldShowRequestPermissionRationale()。
    如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
    注：如果用户在过去拒绝了权限请求，并在权限请求系统对话框中
    选择了 Don’t ask again 选项，此方法将返回 false。
    如果设备规范禁止应用具有该权限，此方法也会返回 false。*/
    @TargetApi(23)
    public boolean addPermission(String permission) {
        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return shouldShowRequestPermissionRationale(permission);
        }
    }

    public void getXML(View view) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File sdDir = new File(Environment.getExternalStorageDirectory().getPath());
            String sdDirPath = sdDir.getPath();
            String pxStr = etInputPx.getText().toString().trim();
            int px;
            String pathHdp1 = null;
            String pathHdp2 = null;
            String pathXHdp1 = null;
            String pathXHdp2 = null;
            String pathXXHdp1 = null;
            String pathXXHdp2 = null;
            String pathXXXHdp1 = null;
            String pathXXXHdp2 = null;
            if (!TextUtils.isEmpty(pxStr)) {
                px = Integer.parseInt(pxStr);
                boolean success = false;
                if (cb01.isChecked()) {// 基于720设计标准
                    String dimensDir = sdDirPath + "/" + cb01.getText();
                    Log.i("输出目录", dimensDir);
                    pathHdp1 = dimensDir + "/values-hdpi";
                    pathXHdp1 = dimensDir + "/values-xhdpi";
                    pathXXHdp1 = dimensDir + "/values-xxhdpi";
                    pathXXXHdp1 = dimensDir + "/values-xxxhdpi";
                    sdDir = new File(pathHdp1);// 在SD卡根目录下创建文件夹及文件(注意android6.0需要手动开启存储权限)
                    OutputXml.getXML(this, sdDir, px, 1.0 / 1.5);
                    sdDir = new File(pathXHdp1);
                    OutputXml.getXML(this, sdDir, px, 1.0);
                    sdDir = new File(pathXXHdp1);
                    OutputXml.getXML(this, sdDir, px, 1.5 / 1.0);
                    sdDir = new File(pathXXXHdp1);
                    success = OutputXml.getXML(this, sdDir, px, 2.0 / 1.0);
                    if (success) tvRemind.setText("请到储存中查找已生成的下列文件:\n\n"
                            + pathHdp1 + "/dimens.xml\n\n" + pathXHdp1 + "/dimens.xml\n\n"
                            + pathXXHdp1 + "/dimens.xml\n\n" + pathXXXHdp1 + "/dimens.xml\n\n");
                }
                if (cb02.isChecked()) {// 基于1080设计标准
                    String dimensDir = sdDirPath + "/" + cb02.getText();
                    Log.i("输出目录", dimensDir);
                    pathHdp2 = dimensDir + "/values-hdpi";
                    pathXHdp2 = dimensDir + "/values-xhdpi";
                    pathXXHdp2 = dimensDir + "/values-xxhdpi";
                    pathXXXHdp2 = dimensDir + "/values-xxxhdpi";
                    sdDir = new File(pathHdp2);// 在SD卡根目录下创建文件夹及文件(注意android6.0需要手动开启存储权限)
                    OutputXml.getXML(this, sdDir, px, 1.0 / (1.5 * 1.5));
                    sdDir = new File(pathXHdp2);
                    OutputXml.getXML(this, sdDir, px, 1.0 / 1.5);
                    sdDir = new File(pathXXHdp2);
                    OutputXml.getXML(this, sdDir, px, 1.0);
                    sdDir = new File(pathXXXHdp2);
                    success = OutputXml.getXML(this, sdDir, px, 2.0 / 1.5);
                    if (success) tvRemind.setText("请到储存中查找已生成的下列文件:\n\n"
                            + pathHdp2 + "/dimens.xml\n\n" + pathXHdp2 + "/dimens.xml\n\n"
                            + pathXXHdp2 + "/dimens.xml\n\n" + pathXXXHdp2 + "/dimens.xml\n\n");
                }
                if (cb01.isChecked() && cb02.isChecked()) {
                    if (success) tvRemind.setText("请到储存中查找已生成的下列文件:\n\n"
                            + pathHdp1 + "/dimens.xml\n\n" + pathXHdp1 + "/dimens.xml\n\n"
                            + pathXXHdp1 + "/dimens.xml\n\n" + pathXXXHdp1 + "/dimens.xml\n\n"
                            + pathHdp2 + "/dimens.xml\n\n" + pathXHdp2 + "/dimens.xml\n\n"
                            + pathXXHdp2 + "/dimens.xml\n\n" + pathXXXHdp2 + "/dimens.xml\n\n");
                }
                if (!cb01.isChecked() && !cb02.isChecked()) {
                    Toast.makeText(this, "至少选择一种适配方案!", Toast.LENGTH_LONG).show();
                }
                // 在缓存目录下创建文件夹及文件
            /*sdDir = new File(getCacheDir() + "/00xml/javaxml/values-hdpi");
            OutputXml.getXML(this,sdDir, px);
            sdDir = new File(getCacheDir() + "/00xml/javaxml/values-xhdpi");
            OutputXml.getXML(this,sdDir, px);
            sdDir = new File(getCacheDir() + "/00xml/javaxml/values-xxhdpi");
            OutputXml.getXML(this,sdDir, px);
            sdDir = new File(this,getCacheDir() + "/00xml/javaxml/values-xxxhdpi");
            OutputXml.getXML(sdDir, px);*/
            } else {
                Toast.makeText(this, "最大px值不能为空!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "没有监测到SD卡!", Toast.LENGTH_LONG).show();
        }
    }
}
