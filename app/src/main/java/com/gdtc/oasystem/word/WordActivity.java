package com.gdtc.oasystem.word;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;

import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseActivity;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class WordActivity extends BaseActivity {

    @BindView(R.id.btn_open)
    Button btn_open;
    @BindView(R.id.btn_wps)
    Button btn_wps;
    @BindView(R.id.btn_file_list)
    Button btn_file_list;

    // 创建生成的文件地址
    private static final String newPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/doc/test.doc";
    private static final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/doc";//创建目录

    @Override
    protected int getLayoutId() {
        return R.layout.activity_word;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Save();

        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WordActivity.this, WebViewActivity.class));
//                doOpenWord();//报空指针异常
            }
        });

        btn_wps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doOpenWord(newPath);// 清单文件中配置的authorities android 版本高打开文件方式就会不同
            }
        });

        btn_file_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WordActivity.this,ViewFile.class);
                intent.putExtra("name",newPath);
                startActivity(intent);
            }
        });
    }

    /**
     * 调用手机中安装的可打开word的软件
     */
    private void doOpenWord(String path){

//        String path = Environment.getExternalStorageDirectory().getPath().concat("/").concat("myDoc").concat("/").concat("a.doc");
        File docFile = new File(path);
        Intent in = new Intent("android.intent.action.VIEW");
        in.addCategory("android.intent.category.DEFAULT");
        Uri data;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
            data = FileProvider.getUriForFile(WordActivity.this, "com.gdtc.oasystem.fileprovider", docFile);
            // 给目标应用一个临时授权
            in.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(docFile);
        }
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        in.setDataAndType(data, "application/msword");
        startActivity(in);
//        try{
//            WordActivity.this.startActivity(intent);
//        } catch(ActivityNotFoundException e) {
//            //检测到系统尚未安装OliveOffice的apk程序
//            Toast.makeText(WordActivity.this, "未找到软件", Toast.LENGTH_LONG).show();
//            //请先到www.olivephone.com/e.apk下载并安装
//        }catch (NullPointerException e){
//            startActivity(new Intent(WordActivity.this, WebViewActivity.class));
//        }
    }
    /**
     * newFile 生成文件
     * map 要填充的数据
     */
    public void writeDoc(InputStream in, File newFile, Map<String, String> map) {
        try {

            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }

            HWPFDocument hdt = new HWPFDocument(in);
            // Fields fields = hdt.getFields();
            // 读取word文本内容
            Range range = hdt.getRange();
            // System.out.println(range.text());

            // 替换文本内容
            for (Map.Entry<String, String> entry : map.entrySet()) {
                range.replaceText(entry.getKey(), entry.getValue());
            }
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            FileOutputStream out = new FileOutputStream(newFile, true);
            hdt.write(ostream);
            // 输出字节流
            out.write(ostream.toByteArray());
            out.close();
            ostream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void Save() {
        try {
            //从assets读取我们的Word模板
            InputStream is = getAssets().open("Name.doc");
            //创建生成的文件
            File newFile = new File(newPath);
            Map<String, String> map = new HashMap<String, String>();
            map.put("$Name$", "张磊");
            map.put("$Sex$", "男");
            map.put("$Address$", "上海市杨浦区xx路xx号");
            writeDoc(is, newFile, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
