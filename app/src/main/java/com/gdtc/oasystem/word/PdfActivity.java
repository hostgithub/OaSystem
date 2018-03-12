//package com.gdtc.oasystem.word;
//
//import android.graphics.Canvas;
//import android.os.Bundle;
//import android.os.StrictMode;
//import android.view.View;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import com.gdtc.oasystem.R;
//import com.gdtc.oasystem.base.BaseActivity;
//import com.github.barteksc.pdfviewer.PDFView;
//import com.github.barteksc.pdfviewer.listener.OnDrawListener;
//import com.github.barteksc.pdfviewer.listener.OnErrorListener;
//import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
//import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
//import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//import butterknife.BindView;
//
///**
// * 在程序内部下载网络PDF显示
// *
// * compile 'com.github.barteksc:android-pdf-viewer:2.4.0'
// */
//public class PdfActivity extends BaseActivity{
//
//    @BindView(R.id.pdfView)
//    PDFView pdfView;
//    @BindView(R.id.load_progress)
//    ProgressBar progressBar;
//
////    private String pdfUrl= "https://pic.bincrea.com/bc_bg_6D40C91A170D41C182511ABBB8A634A4.pdf";
//    private String pdfUrl= "http://file.chmsp.com.cn/colligate/file/00100000224821.pdf";
//    private InputStream is;
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_pdf;
//    }
//
//    @Override
//    protected void initView(Bundle savedInstanceState) {
//
//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }
//        downPdf();
//    }
//
//    private void downPdf(){
//
//        new Thread(new Runnable() {//先开个子线程-->  不能在主线程去请求网络 会阻塞  报异常NetworkOnMainThreadException
//            @Override
//            public void run() {
//                //先开个子线程-->
//                try {
//                    URL url = new URL(pdfUrl);
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.setConnectTimeout(5000);
//                    if (conn.getResponseCode() == 200) {
//                        int max = conn.getContentLength();
//                        //pd.setMax(max);
//                        is = conn.getInputStream();
//
//                        //这里给过去就行了
//                        runOnUiThread(new Runnable() {//子线程不能直接操作主线程 所以这样做
//                            @Override
//                            public void run() {
//                                openWps(is);
//                            }
//                        });
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//    }
//
//    private void openWps(InputStream stream){
//        pdfView.fromStream(stream)
////.pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
//                .enableSwipe(true)
//                .swipeHorizontal(false)
//                .enableDoubletap(true)
//                .defaultPage(0)
//                .onDraw(new OnDrawListener() {
//                    @Override
//                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
//
//                    }
//                })
//                .onLoad(new OnLoadCompleteListener() {
//                    @Override
//                    public void loadComplete(int nbPages) {
//                        Toast.makeText(getApplicationContext(), "loadComplete", Toast.LENGTH_SHORT).show();
//                        progressBar.setVisibility(View.INVISIBLE);
//                    }
//                })
//                .onPageChange(new OnPageChangeListener() {
//                    @Override
//                    public void onPageChanged(int page, int pageCount) {
//
//                    }
//                })
//                .onPageScroll(new OnPageScrollListener() {
//                    @Override
//                    public void onPageScrolled(int page, float positionOffset) {
//
//                    }
//                })
//                .onError(new OnErrorListener() {
//                    @Override
//                    public void onError(Throwable t) {
//                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .enableAnnotationRendering(false)
//                .password(null)
//                .scrollHandle(null)
//                .load();
//    }
//}
