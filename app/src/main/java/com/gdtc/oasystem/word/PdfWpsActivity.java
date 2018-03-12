package com.gdtc.oasystem.word;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gdtc.oasystem.Config;
import com.gdtc.oasystem.MyApplication;
import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseActivity;
import com.gdtc.oasystem.bean.EventUtil;
import com.gdtc.oasystem.service.Api;
import com.gdtc.oasystem.utils.SharePreferenceTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

//    private String pdfUrl= "http://file.chmsp.com.cn/colligate/file/00100000224821.pdf";
//    private String pdfUrl= "http://192.168.0.105:8080/html/2222.doc";
    private String pdfUrl= "http://192.168.0.113:8080/02122018124541220PM8452.doc";
    private String s;
    private MyBroadCastReciver receiver;

    @BindView(R.id.btn_check)
    Button btn_check;
    private SharePreferenceTools sp;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wps;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        sp = new SharePreferenceTools(MyApplication.getContext());

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });
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
//        s = pdfUrl.substring(pdfUrl.length()-14);
        s = pdfUrl.substring(pdfUrl.length()-14);
        Log.e("------------>将地址截取14位",s);
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
                    Bundle bundle = new Bundle();
                    bundle.putString(WpsModel.OPEN_MODE, WpsModel.ENTER_REVISE_MODE); // 打开模式
                    bundle.putString(WpsModel.USER_NAME, sp.getString(Config.USERNAME)); // 批注人
                    bundle.putBoolean(WpsModel.SEND_CLOSE_BROAD, true); // 关闭时是否发送广播
                    bundle.putBoolean(WpsModel.SEND_SAVE_BROAD, true); // 保存时是否发送广播
                    bundle.putString(WpsModel.THIRD_PACKAGE, getPackageName()); // 第三方应用的包名，用于对改应用合法性的验证
                    bundle.putString(WpsModel.SAVE_PATH, file1.getAbsolutePath()); // 保存路径
                    bundle.putBoolean(WpsModel.CLEAR_TRACE, true);// 清除打开记录
                     bundle.putBoolean(WpsModel.CLEAR_FILE, true); //关闭后删除打开文件
                     bundle.putBoolean(WpsModel.CLEAR_BUFFER, true); //关闭后删除临时文件
                     bundle.putBoolean(WpsModel.CACHE_FILE_INVISIBLE, true); //Wps生成的缓存文件外部是否可见
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setAction(android.content.Intent.ACTION_VIEW);
                    intent.setClassName(WpsModel.PackageName.NORMAL, WpsModel.ClassName.NORMAL);

                    intent.addCategory("android.intent.category.DEFAULT");
                    Uri data;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
                        data = FileProvider.getUriForFile(PdfWpsActivity.this, "com.gdtc.oasystem.fileprovider", file);
                        // 给目标应用一个临时授权
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } else {
                        data = Uri.fromFile(file);
                        intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    intent.setDataAndType (data, "application/doc");
//                    intent.setDataAndType (data, "application/msword");
                    intent.putExtras(bundle);
                    startActivity(Intent.createChooser(intent, "标题"));
                    /**
                     * 弹出选择框   把本activity销毁
                     */
//                    finish();
                    break;
                case DOWNLOAD_ERROR:
                    Toast.makeText(PdfWpsActivity.this, "文件加载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    // 接收函数二
    @Subscribe
    public void onEventBackgroundThread(EventUtil event){
        String msglog = "----onEventBackground收到了消息："+event.getMsg();
        Log.e("EventBus",msglog);

        AlertDialog.Builder builder=new AlertDialog.Builder(PdfWpsActivity.this);
//        builder.setTitle("通知详情");//设置对话框的标题
        builder.setMessage("请将您批注的信息进行痕迹保留！");//设置对话框的内容
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //这个是设置确定按钮

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(PdfWpsActivity.this,"保存",Toast.LENGTH_SHORT).show();
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("http://192.168.12.101:8080/happy/")
                        .build();
                Api service = retrofit.create(Api.class);
                File file = new File(file1.getAbsolutePath());//访问手机端的文件资源，保证手机端sdcdrd中必须有这个文件
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file);

                MultipartBody.Part body = MultipartBody.Part.createFormData("aFile", file.getName(), requestFile);

                String descriptionString = "This is a description";
                RequestBody description =
                        RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);

                Call<ResponseBody> call = service.upload(description, body);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,
                                           Response<ResponseBody> response) {
                        System.out.println("success");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }
        });
        AlertDialog b=builder.create();
        b.show();  //必须show一下才能看到对话框，跟Toast一样的道理
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
