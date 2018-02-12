package com.gdtc.oasystem.word;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;
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
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class OpenWordFromWpsAndInsideActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{

    @BindView(R.id.btn_open)
    Button btn_open;
    @BindView(R.id.btn_wps)
    Button btn_wps;
    @BindView(R.id.btn_file_list)
    Button btn_file_list;
    public static final int PERMISSION = 100;

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
            public void onClick(View v) { //手机内部打开 webview显示word
                startActivity(new Intent(OpenWordFromWpsAndInsideActivity.this, WebViewActivity.class));
//                doOpenWord();//报空指针异常
            }
        });

        btn_wps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//调用WPS
                doOpenWord(newPath);// 清单文件中配置的authorities android 版本高打开文件方式就会不同
            }
        });

        btn_file_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //打开网络制定PDF
//                Intent intent=new Intent(OpenWordFromWpsAndInsideActivity.this,ViewFile.class);
//                intent.putExtra("name",newPath);
//                startActivity(intent);
                /**
                 * 6.0系统动态权限申请需要
                 */
                String[] params = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
                if (EasyPermissions.hasPermissions(OpenWordFromWpsAndInsideActivity.this, params)) {
                    startActivity(new Intent(OpenWordFromWpsAndInsideActivity.this,PdfWpsActivity.class));
                } else {
                    EasyPermissions.requestPermissions(OpenWordFromWpsAndInsideActivity.this, "应用需要权限才能安全运行", PERMISSION, params);
                }
            }
        });

        if(getIntent()!=null){
            if(getIntent().getStringExtra("filepath")!=null){
                File file = new File(getIntent().getStringExtra("filepath"));
                Log.e("------------成功接收文件>",file.toString());

                Intent intent = new Intent("android.intent.action.VIEW");
                Bundle bundle = new Bundle();
                bundle.putString(WpsModel.OPEN_MODE, WpsModel.OpenMode.NORMAL); // 打开模式
                bundle.putBoolean(WpsModel.SEND_CLOSE_BROAD, true); // 关闭时是否发送广播
                bundle.putBoolean(WpsModel.SEND_SAVE_BROAD, true); // 保存时是否发送广播
                bundle.putString(WpsModel.THIRD_PACKAGE, getPackageName()); // 第三方应用的包名，用于对改应用合法性的验证
                bundle.putBoolean(WpsModel.CLEAR_TRACE, true);// 清除打开记录
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setClassName(WpsModel.PackageName.NORMAL, WpsModel.ClassName.NORMAL);

                intent.addCategory("android.intent.category.DEFAULT");
                Uri data;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
                    data = FileProvider.getUriForFile(OpenWordFromWpsAndInsideActivity.this, "com.gdtc.oasystem.fileprovider", file);
                    // 给目标应用一个临时授权
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } else {
                    data = Uri.fromFile(file);
                    intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                intent.setDataAndType (data, "application/doc");
                intent.putExtras(bundle);
                startActivity(Intent.createChooser(intent, "标题"));
            }

        }
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
            data = FileProvider.getUriForFile(OpenWordFromWpsAndInsideActivity.this, "com.gdtc.oasystem.fileprovider", docFile);
            // 给目标应用一个临时授权
            in.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(docFile);
        }
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        in.setDataAndType(data, "application/msword");
        startActivity(in);
//        try{
//            OpenWordFromWpsAndInsideActivity.this.startActivity(intent);
//        } catch(ActivityNotFoundException e) {
//            //检测到系统尚未安装OliveOffice的apk程序
//            Toast.makeText(OpenWordFromWpsAndInsideActivity.this, "未找到软件", Toast.LENGTH_LONG).show();
//            //请先到www.olivephone.com/e.apk下载并安装
//        }catch (NullPointerException e){
//            startActivity(new Intent(OpenWordFromWpsAndInsideActivity.this, WebViewActivity.class));
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

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        startActivity(new Intent(OpenWordFromWpsAndInsideActivity.this,PdfWpsActivity.class));
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        switch (requestCode) {
            case PERMISSION:
                //引导用户跳转到设置界面
                new AppSettingsDialog.Builder(OpenWordFromWpsAndInsideActivity.this, "希望您通过权限")
                        .setTitle("权限设置")
                        .setPositiveButton("设置")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setRequestCode(PERMISSION)
                        .build()
                        .show();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
