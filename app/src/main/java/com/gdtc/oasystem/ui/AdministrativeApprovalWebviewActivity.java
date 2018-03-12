package com.gdtc.oasystem.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.gdtc.oasystem.Config;
import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseActivity;
import com.gdtc.oasystem.bean.AdministrativeApprovalDetail;
import com.gdtc.oasystem.utils.MyScrollView;
import com.gdtc.oasystem.utils.NetWorkUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class AdministrativeApprovalWebviewActivity extends BaseActivity {

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview_dispatchdb;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        title_center.setText("审批");
        Intent intent = getIntent();

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
                        Toast.makeText(AdministrativeApprovalWebviewActivity.this, "没有安装微信，请选择其他支付方式", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AdministrativeApprovalWebviewActivity.this, message, Toast.LENGTH_LONG).show();
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
        AdministrativeApprovalDetail.ResultsBean resultsBean= (AdministrativeApprovalDetail.ResultsBean) intent.getSerializableExtra(Config.NEWS);
        //tv_content.setText(Html.fromHtml(resultsBean.content));
        if(resultsBean.getHtmls().startsWith("http")){
            webView.loadUrl(resultsBean.getHtmls());
        }else{
            webView.loadDataWithBaseURL(Config.BANNER_BASE_URL, resultsBean.getHtmls().toString().trim(), "text/html", "utf-8", null);
//            webView.loadDataWithBaseURL(Config.BANNER_BASE_URL, resultsBean.getHtmls(), "text/html", "utf-8", null);
        }
        tv_title.setText(intent.getStringExtra("title"));
        tv_username.setText("发送人:"+intent.getStringExtra("sender"));
        tv_time.setText(intent.getStringExtra("time"));
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

    @OnClick({ R.id.title_left})
    public void onClick(View view) {
        finish();
    }
}
