package com.example.zcm.outputtxt.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class OutputXml {

    public static boolean getXML(Activity mAct, File xmlPath, int px, double multiple) {
        if (!xmlPath.exists()) {// 判断是否已经存在，不存在即创建
            xmlPath.mkdirs();
        } else {// 存在便删除，然后再创建
            xmlPath.delete();
            Log.i("删除文件夹", "旧文件夹删除成功");
            xmlPath.mkdirs();
            Log.i("创建文件夹", "重新创建文件夹成功");
        }
        File file = new File(xmlPath + "/dimens.xml");
        if (file.exists()) {
            file.delete();
            Log.i("删除文件", "旧文件删除成功");
//            System.exit(0);
        }
        return outputDimens(mAct, file, px, multiple);
    }

    private static boolean outputDimens(Activity mAct, File file, int px, double multiple) {
        try {
            file.createNewFile();
            Log.i("创建新文件", "开始创建新文件");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            DecimalFormat df = new DecimalFormat("######0.00");
            bw.write("<resources>");
            bw.newLine();
            for (int i = 0; i <= px; i++) {
                bw.write("<dimen name=\"size_" + i + "px\">" + df.format(i * multiple) + "px</dimen>");
                bw.newLine();
            }
            bw.newLine();
            bw.write("<dimen name=\"size_" + "40_px\">-" + df.format(40 * multiple) + "px</dimen>");
            bw.newLine();
            bw.write("<dimen name=\"size_" + "6_px\">-" + df.format(6 * multiple) + "px</dimen>");
            bw.newLine();
            bw.write("</resources>");
            bw.close();
            Log.i("创建新文件", "新文件创建完毕");
//            Toast.makeText(mAct, "请到手机储存中查找你的dimens文件", Toast.LENGTH_LONG).show();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mAct, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return false;
    }
}
