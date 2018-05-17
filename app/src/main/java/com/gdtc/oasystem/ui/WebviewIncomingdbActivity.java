package com.gdtc.oasystem.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gdtc.oasystem.Config;
import com.gdtc.oasystem.MyApplication;
import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseActivity;
import com.gdtc.oasystem.bean.EventUtil;
import com.gdtc.oasystem.bean.HuiZhiBean;
import com.gdtc.oasystem.bean.ShouWenDbDetail;
import com.gdtc.oasystem.bean.XZfujian;
import com.gdtc.oasystem.broadcastreciver.ShouwenDaibanBroadCastReciver;
import com.gdtc.oasystem.service.Api;
import com.gdtc.oasystem.utils.FileUtil;
import com.gdtc.oasystem.utils.MyScrollView;
import com.gdtc.oasystem.utils.NetWorkUtil;
import com.gdtc.oasystem.utils.RetrofitUtils;
import com.gdtc.oasystem.utils.SharePreferenceTools;
import com.gdtc.oasystem.word.WpsModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WebviewIncomingdbActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_content)
    WebView webView;//xml中最好是自适应 不要match
    @BindView(R.id.scrollView)
    MyScrollView scrollView;
    @BindView(R.id.title_center)
    TextView title_center;
    private SharePreferenceTools sp;
    private ShouWenDbDetail.ResultsBean resultsBean;
    @BindView(R.id.edt_content)
    EditText edt_content;

    public static final int PERMISSION = 100;


    private ProgressDialog mProgressDialog;

    // 下载失败
    public static final int DOWNLOAD_ERROR = 2;
    // 下载成功
    public static final int DOWNLOAD_SUCCESS = 1;
    private File file1;
    private String s;
    private ShouwenDaibanBroadCastReciver fawenDaibanBroadCastReciver;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview_dispatchdb;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        title_center.setText("审批详情");
        sp = new SharePreferenceTools(MyApplication.getContext());
        Intent intent = getIntent();

        fawenDaibanBroadCastReciver =new ShouwenDaibanBroadCastReciver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("cn.wps.moffice.file.close");
        registerReceiver(fawenDaibanBroadCastReciver, filter);

//        LinearLayout.LayoutParams mWebViewLP = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.FILL_PARENT,
//                LinearLayout.LayoutParams.FILL_PARENT);
        //mDetailWebView.setLayoutParams(mWebViewLP);
        //mDetailWebView.setInitialScale(100);
        WebSettings webSettings = webView .getSettings();

        //缩放操作
//        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
//        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
//        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        /**
         *  Webview在安卓5.0之前默认允许其加载混合网络协议内容
         *  在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        }

        webSettings.setJavaScriptEnabled(true);//支持js
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//允许js弹出alert
        webView.requestFocusFromTouch();//支持获取手势焦点，输入用户名、密码或其他
        if (NetWorkUtil.isNetworkConnected(this)) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        webSettings.setSupportZoom(true);  //不支持缩放 false
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");
        //是否使用WebView内置的放大机制，貌似设置了这条以后下面那条不用设置了
        webSettings.setBuiltInZoomControls(true);

        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        //辅助处理请求，点击链接在本browser中打开
        webView.setWebViewClient(new WebViewClient() {
            //处理https请求
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //handler.proceed();//等待证书响应
                if(error.getPrimaryError() ==  SslError.SSL_DATE_INVALID
                        || error.getPrimaryError() == SslError.SSL_EXPIRED
                        || error.getPrimaryError() == SslError.SSL_INVALID
                        || error.getPrimaryError() == SslError.SSL_UNTRUSTED) {

                    handler.proceed();

                }else{
                    handler.cancel();
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("weixin://wap/pay?")) {
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(WebviewIncomingdbActivity.this, "没有安装微信，请选择其他支付方式", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }

                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                startProgressDialog();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                if (url != null && url.contains("http://www.jcodecraeer.com")) {
//                    String fun = "javascript:function getClass(parent,sClass) { var aEle=parent.getElementsByTagName('div'); var aResult=[]; var i=0; for(i<0;i<aEle.length;i++) { if(aEle[i].className==sClass) { aResult.push(aEle[i]); } }; return aResult; } ";
//                    view.loadUrl(fun);
//                    String fun2 = "javascript:function hideOther() {getClass(document,'header')[0].style.display='none';getClass(document,'footer')[0].style.display='none'}";
//                    view.loadUrl(fun2);
//                    view.loadUrl("javascript:hideOther()");
//                }
                webView.setVisibility(View.VISIBLE);
                stopProgressDialog();
            }
        });

        //辅助处理js的对话框，网站图标，网站title，加载进度
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Toast.makeText(WebviewIncomingdbActivity.this, message, Toast.LENGTH_LONG).show();
                return true;
            }
        });

        webView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent ev) {

                ((WebView)v).requestDisallowInterceptTouchEvent(true);


                return false;
            }
        });


        //mDetailWebView.loadUrl(it.getStringExtra(Config.NEWS));
//        DetailDispatchdb.ResultsBean resultsBean= (DetailDispatchdb.ResultsBean) intent.getSerializableExtra(Config.NEWS);
        resultsBean= (ShouWenDbDetail.ResultsBean) intent.getSerializableExtra(Config.NEWS);
        //tv_content.setText(Html.fromHtml(resultsBean.content));
        if(resultsBean.getHtmls().startsWith("http")){
            webView.loadUrl(resultsBean.getHtmls());
        }else{
            webView.loadDataWithBaseURL(Config.BANNER_BASE_URL, resultsBean.getHtmls().toString().trim(), "text/html", "utf-8", null);
//            webView.loadDataWithBaseURL(Config.BANNER_BASE_URL, resultsBean.getHtmls(), "text/html", "utf-8", null);
        }
//        tv_title.setText(intent.getStringExtra("title").trim());
//        tv_username.setText("发送人:"+intent.getStringExtra("sender"));
//        tv_time.setText(intent.getStringExtra("time"));
        tv_title.setVisibility(View.GONE);
        tv_username.setVisibility(View.GONE);
        tv_time.setVisibility(View.GONE);
    }

    /**
     * 默认按返回键会直接退出app
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 滑轮处理
     */
//    @Override
//    public boolean onGenericMotionEvent(MotionEvent event) {
//        if (callback != null)
//            return callback.onGenericMotionEvent(event);
//        return super.onGenericMotionEvent(event);
//    }
//
//    //定义一个接口，把滚动事件传递出去
//    public interface GenericMotionCallback {
//        boolean onGenericMotionEvent(MotionEvent event);
//    }
//
//    GenericMotionCallback callback;
//
//    public void setCallback(GenericMotionCallback callback) {
//        this.callback = callback;
//    }

    @Override
    protected void onStop() {
        //处理长时间执行js动画导致耗电
        webView.getSettings().setJavaScriptEnabled(false);
        super.onStop();
    }



    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        unregisterReceiver(fawenDaibanBroadCastReciver);
        if (webView != null) {
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.removeAllViews();
            webView.clearCache(true);
            webView.stopLoading();
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    @OnClick({ R.id.title_left,R.id.btn_agree,R.id.btn_un_agree,R.id.btn_fujian})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left:
                finish();
                break;
            case R.id.btn_agree:
                dispatchBack("同意");
                break;
            case R.id.btn_un_agree:
                dispatchBack("不同意");
                break;
            case R.id.btn_fujian:
                /**
                 * 6.0系统动态权限申请需要
                 */
                String[] params = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE};
                if (EasyPermissions.hasPermissions(WebviewIncomingdbActivity.this, params)) {
                    Fujian();
                } else {
                    EasyPermissions.requestPermissions(WebviewIncomingdbActivity.this, "应用需要权限才能安全运行", PERMISSION, params);
                }
                break;
            default:
                break;
        }
    }

    private void dispatchBack(String button){

        /**
         * 初始化
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //生成对象的Service
        Api api = retrofit.create(Api.class);
        //调用方法得到Call
        Call<HuiZhiBean> call = api.IncomingBack(
                sp.getString(Config.USER_ID),
                sp.getString(Config.DEPTUNIT),
                sp.getString(Config.USERNAME),
                resultsBean.getFile_source_id(),
                resultsBean.getFlowsort(),
                resultsBean.getType_advice(),
                resultsBean.getUserQc(),
                resultsBean.getFanhui_man_sw(),
                button,
                edt_content.getText().toString().trim(),
                resultsBean.getColumn75(),resultsBean.getColumn76(),resultsBean.getColumn77(),resultsBean.getColumn78(),resultsBean.getColumn79());

        //异步执行
        call.enqueue(new Callback<HuiZhiBean>() {
            @Override
            public void onResponse(Call<HuiZhiBean> call, Response<HuiZhiBean> response) {
                Log.e("-----------",response.message()+"   "+response.body().getSuccess());
                if(response.body().getSuccess()=="true"){
                    AlertDialog.Builder builder=new AlertDialog.Builder(WebviewIncomingdbActivity.this);
                    builder.setTitle("通知详情");//设置对话框的标题
                    builder.setMessage("您填写的信息已成功提交，请返回");//设置对话框的内容
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //这个是设置确定按钮

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            EventBus.getDefault().post(new EventUtil("发送消息"));
                            finish();
                        }
                    });
                    AlertDialog b=builder.create();
                    b.show();  //必须show一下才能看到对话框，跟Toast一样的道理
                }
            }
            @Override
            public void onFailure(Call<HuiZhiBean> call, Throwable t) {
                Log.e("-------------",t.getMessage());
                Log.e("-------------",t.toString());
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this);
        }
    }

    private void Fujian(){
        /**
         * 初始化
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitUtils.getInstance().addTimeOut(60).addHttpLog().build())
                .build();

        //生成对象的Service
        Api api = retrofit.create(Api.class);
        //调用方法得到Call
        Call<XZfujian> call = api.getMarks(resultsBean.getWordFileId());
        //异步执行
        call.enqueue(new Callback<XZfujian>() {
            @Override
            public void onResponse(Call<XZfujian> call, Response<XZfujian> response) {

                if(response.body()!=null&&response.body().getSuccess().equals("true")){
                    init(Config.BANNER_BASE_URL+response.body().getPath());
                    Log.e("-------------",Config.BANNER_BASE_URL+response.body().getPath());
                }else{
                    showErrorHint("暂无附件");
                }
            }
            @Override
            public void onFailure(Call<XZfujian> call, Throwable t) {
                Log.e("-------------",t.getMessage());
                Log.e("-------------",t.toString());
            }
        });
    }


    private void init(final String pdfUrl) {
        // TODO Auto-generated method stub
//        Intent intent = getIntent();
//        final String Strname = intent.getStringExtra("url");
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        //截取最后14位 作为文件名
//        s = pdfUrl.substring(pdfUrl.length()-14);
        //s = pdfUrl.substring(pdfUrl.length()-14);
        //Log.e("------------>将地址截取14位",s);
        String dir = FileUtil.getSDCardPath()+"TestImage/imagePic/"; //定义一个文件夹储存图片
        FileUtil.checkDir(dir); //判断文件夹是否存在，不存在创建
        //文件存储
        file1 = new File(dir, getFileName(pdfUrl));

        Log.e("------------>文件路径",file1.toString());
        Log.e("------------>文件名",getFileName(pdfUrl));
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
//                    bundle.putString(WpsModel.OPEN_MODE, WpsModel.OpenMode.SAVE_ONLY); // 打开模式
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
                        data = FileProvider.getUriForFile(WebviewIncomingdbActivity.this, "com.gdtc.oasystem.fileprovider", file);
                        // 给目标应用一个临时授权
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        grantUriPermission(getPackageName(),data,Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } else {
                        data = Uri.fromFile(file);
                        intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                    }

                    intent.setDataAndType (data, "application/msword");
                    intent.putExtras(bundle);
                    startActivity(Intent.createChooser(intent, "标题"));
                    break;
                case DOWNLOAD_ERROR:
                    Toast.makeText(WebviewIncomingdbActivity.this, "文件加载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    // 接收函数二
    @Subscribe
    public void onEventBackgroundThread(EventUtil event){
        String msglog = "----收文待办onEventBackground收到了消息："+event.getMsg();
        Log.e("EventBus",msglog);

        AlertDialog.Builder builder=new AlertDialog.Builder(WebviewIncomingdbActivity.this);
        builder.setMessage("请将您批注的信息进行痕迹保留！");//设置对话框的内容
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //这个是设置确定按钮

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(WebviewIncomingdbActivity.this,"保存",Toast.LENGTH_SHORT).show();
                File[] files=getFiles("/storage/emulated/0/documents");
                for(int i =0;i<files.length;i++){
                    Log.i("路径：",files[i].getAbsolutePath());
                    File file = new File(files[i].getAbsolutePath());//访问手机端的文件资源，保证手机端sdcdrd中必须有这个文件
                    if(file!=null){
                        String str1=file.getName().substring(0,file.getName().lastIndexOf("."));
                        String str2 = str1.substring(0,str1.lastIndexOf("("));
                        Log.e("截取的字符串",str2+"."+getExtension(file));
                        upLoadFile(FileUtil.encodeBase64File(file.getPath()),str2+"."+getExtension(file),file);
                    }
                }
            }
        });
        AlertDialog b=builder.create();
        b.show();  //必须show一下才能看到对话框，跟Toast一样的道理
    }

    /**
     * 获取文件扩展名
     */
    private static String getExtension(final File file) {
        String suffix = "";
        String name = file.getName();
        final int idx = name.lastIndexOf(".");
        if (idx > 0) {
            suffix = name.substring(idx + 1);
        }
        return suffix;
    }


    private void upLoadFile(String stringBase64, String fileName, final File file){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<ResponseBody> call=api.upload(stringBase64,fileName);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.body()!=null){
                    if(response.body().equals("true")){
                        file.delete();
                        file1.delete();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("=============",t.getMessage());
            }
        });
    }

    /*
   path:为目录的路径
   String SDCarePath=Environment.getExternalStorageDirectory().toString();
    String path=SDCarePath+"/Test";
    Test为存放文件的目录
    */
    public File[] getFiles(String path){
        File file=new File(path);
        File[] files=file.listFiles();
        return files;
    }
}
