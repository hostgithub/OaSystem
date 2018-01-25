package com.gdtc.oasystem.word;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 下载网络PDF 保存在本地 完了调用WPS
 */
public class PdfWpsActivity extends BaseActivity {

    private ProgressDialog mProgressDialog;

    // 下载失败
    public static final int DOWNLOAD_ERROR = 2;
    // 下载成功
    public static final int DOWNLOAD_SUCCESS = 1;
    private File file1;

    private String pdfUrl= "http://file.chmsp.com.cn/colligate/file/00100000224821.pdf";
    private String s;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wps;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        init();
    }


    private void init() {
        // TODO Auto-generated method stub
//        Intent intent = getIntent();
//        final String Strname = intent.getStringExtra("url");
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        //截取最后14位 作为文件名
        s = pdfUrl.substring(pdfUrl.length()-14);
        Log.e("------------>文件名",s);
        //文件存储
        file1 = new File(Environment.getExternalStorageDirectory(), getFileName(s));
        Log.e("------------>文件路径",file1.toString());
        Log.e("------------>文件名",getFileName(s));
        new Thread() {
            public void run() {

                File haha = new File( file1.getAbsolutePath());
                Log.e("------------路径>",haha.toString());
                //判断是否有此文件
                if (haha.exists()) {
                    //有缓存文件,拿到路径 直接打开
                    Message msg = Message.obtain();
                    msg.obj = haha;
                    msg.what = DOWNLOAD_SUCCESS;
                    handler.sendMessage(msg);
                    mProgressDialog.dismiss();
                    return;
                }
//              本地没有此文件 则从网上下载打开
                File downloadfile = downLoad(pdfUrl, file1.getAbsolutePath(), mProgressDialog);
//                Log.e("------------>",file1.getAbsolutePath());
                Log.e("------------>",downloadfile.toString());
//              Log.i("Log",file1.getAbsolutePath());
                Message msg = Message.obtain();
                if (downloadfile != null) {  //downloadfile 为空
                    // 下载成功,安装....
                    msg.obj = downloadfile;
                    msg.what = DOWNLOAD_SUCCESS;
                } else {
                    // 提示用户下载失败.
                    msg.what = DOWNLOAD_ERROR;
                }
                handler.sendMessage(msg);
                mProgressDialog.dismiss();
            };
        }.start();
    }



    /**
     * 传入文件 url  文件路径  和 弹出的dialog  进行 下载文档
     */
    public File downLoad(String serverpath, String savedfilepath, ProgressDialog pd) {
        try {
            URL url = new URL(serverpath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            if (conn.getResponseCode() == 200) {
                int max = conn.getContentLength();
                pd.setMax(max);
                InputStream is = conn.getInputStream();
                File file = new File(savedfilepath);
                Log.e("------------>下载的文件",file.toString());
                FileOutputStream fos = new FileOutputStream(file);
                int len = 0;
                byte[] buffer = new byte[1024];
                int total = 0;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    total += len;
                    pd.setProgress(total);
                }
                fos.flush();
                fos.close();
                is.close();
                return file;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String getFileName(String serverurl) {
        return serverurl.substring(serverurl.lastIndexOf("/") + 1);
    }

    /**
     * 下载完成后  直接打开文件
     */
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case DOWNLOAD_SUCCESS:
                    File file = (File) msg.obj;
                    Log.e("------------成功接收文件>",file.toString());
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.addCategory("android.intent.category.DEFAULT");
                    Uri data;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
                        data = FileProvider.getUriForFile(PdfWpsActivity.this, "com.gdtc.oasystem.fileprovider", file);
                        // 给目标应用一个临时授权
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } else {
                        data = Uri.fromFile(file);
                    }
                    intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setDataAndType (data, "application/pdf");
                    startActivity(Intent.createChooser(intent, "标题"));
                    /**
                     * 弹出选择框   把本activity销毁
                     */
                    finish();
                    break;
                case DOWNLOAD_ERROR:
                    Toast.makeText(PdfWpsActivity.this, "文件加载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
