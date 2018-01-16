package com.example.zcm.outputxml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class OutputXml {
    public static void main(String args[]) {
        // Ҫ�������ļ���λ��
        String pathHdp = "C:/Users/administrator/Desktop/javaxml/values-hdpi/";
        String pathXHdp = "C:/Users/administrator/Desktop/javaxml/values-xhdpi/";
        String pathXXHdp = "C:/Users/administrator/Desktop/javaxml/values-xxhdpi/";
        String pathXXXHdp = "C:/Users/administrator/Desktop/javaxml/values-xxxhdpi/";
        File file = getFile(pathHdp);
        outputDimens(file, 751, 2.0 / 1.0);
        file = getFile(pathXHdp);
        outputDimens(file, 751, 1.5 / 1.0);
        file = getFile(pathXXHdp);
        outputDimens(file, 751, 1.0);
        file = getFile(pathXXXHdp);
        outputDimens(file, 751, 1.0 / 1.5);
    }

    private static File getFile(String path) {
        //�����ļ���
        File newdirpath = new File(path);
        if (!newdirpath.exists()) {//�ж��Ƿ��Ѿ����ڣ������ڼ�����
            newdirpath.mkdirs();
        } else {//���ڱ�ɾ����Ȼ���ٴ���
            newdirpath.delete();
            System.out.println("File Delete success.");
            newdirpath.mkdirs();
            System.out.println("File Create Success.");
        }
        //����txt�ļ���������
        File file = new File(path + "dimens.xml");
        if (file.exists()) {
            file.delete();
            System.out.println("���ļ���ɾ��");
//            System.exit(0);
        }
        return file;
    }

    private static void outputDimens(File file, int px, double multiple) {
        try {
            file.createNewFile();
            System.out.println("��ʼ�������ļ�");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            DecimalFormat df = new DecimalFormat("######0.00");
            bw.write("<resources>");
            bw.newLine();
            for (int i = 0; i < px; i++) {
                bw.write("<dimen name=\"size_" + i + "px\">" + df.format(i / multiple) + "px</dimen>");
                bw.newLine();
            }
            bw.newLine();
            bw.write("<dimen name=\"size_" + "40_px\">-" + df.format(40 / multiple) + "px</dimen>");
            bw.newLine();
            bw.write("<dimen name=\"size_" + "6_px\">-" + df.format(6 / multiple) + "px</dimen>");
            bw.newLine();
            bw.write("</resources>");
            bw.close();
            System.out.println("���ļ��������!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
