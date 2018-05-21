package com.gdtc.oasystem.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gdtc.oasystem.Config;
import com.gdtc.oasystem.MyApplication;
import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseActivity;
import com.gdtc.oasystem.bean.EventUtil;
import com.gdtc.oasystem.bean.HuiZhiBean;
import com.gdtc.oasystem.bean.MeetingDetail;
import com.gdtc.oasystem.service.Api;
import com.gdtc.oasystem.utils.SharePreferenceTools;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MeetingHandleDetailActivity extends BaseActivity {


    @BindView(R.id.tv_them)
    TextView tv_them;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.tv_zhuchi)
    TextView tv_zhuchi;
    @BindView(R.id.tv_shuoming)
    TextView tv_shuoming;
    @BindView(R.id.tv_fabudept)
    TextView tv_fabudept;
    @BindView(R.id.tv_faburen)
    TextView tv_faburen;
    @BindView(R.id.title_center)
    TextView title_center;
    @BindView(R.id.edt_content)
    EditText edt_content;
    @BindView(R.id.btn_huizhi)
    Button btn_huizhi;
    private SharePreferenceTools sp;
    private MeetingDetail.ResultsBean resultsBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_meeting_handle_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        title_center.setText("通知详情");
        sp = new SharePreferenceTools(MyApplication.getContext());

        Intent intent = getIntent();
        resultsBean= (MeetingDetail.ResultsBean) intent.getSerializableExtra(Config.NEWS);
        tv_them.setText(resultsBean.getTitle());
        tv_content.setText(resultsBean.getContent());
        tv_time.setText(resultsBean.getTime());
        tv_location.setText(resultsBean.getPlace());
        tv_zhuchi.setText(resultsBean.getHost());
        tv_shuoming.setText(resultsBean.getExplain());
        tv_fabudept.setText(resultsBean.getDept());
        tv_faburen.setText(resultsBean.getUser());
    }

    @OnClick({ R.id.title_left,R.id.btn_huizhi})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left:
                finish();
                break;
            case R.id.btn_huizhi:
                if(edt_content.getText().toString().trim().equals("")){
                    Toast.makeText(MeetingHandleDetailActivity.this,"请填写回执信息",Toast.LENGTH_SHORT).show();
                }else {
                    post(sp.getString(Config.USER_ID),edt_content.getText().toString(),resultsBean.getFlowsort());
                }
                break;
            default:
                break;
        }
    }



    private void post(String sign,String advice,String flowsort){
//        Gson gson=new Gson();
//        HashMap<String,String> paramsMap=new HashMap<>();
//        paramsMap.put("sign",sp.getString(Config.USER_ID));
//        paramsMap.put("advice",edt_content.getText().toString());
//        paramsMap.put("flowsort",resultsBean.getFlowsort());
//        String route= gson.toJson(paramsMap);
//        Log.e("ssssssss",route.toString());

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
//        @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
//        @POST("app_phone/hyblRebacks.do")
//        Call<HuiZhiBean> postFlyRoute(@Body RequestBody route);//传入的参数为RequestBody
//        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),route);
//        Log.e("ssssssss",body.toString());
//        Call<HuiZhiBean> call=api.postFlyRoute(body);
        Call<HuiZhiBean> call=api.getHuiZhiData(sign,advice,flowsort);
        call.enqueue(new Callback<HuiZhiBean>() {
            @Override
            public void onResponse(Call<HuiZhiBean> call, Response<HuiZhiBean> response) {
                Log.e("sssss","-----------------------"+response.body().getSuccess());
                if(response.body().getSuccess()=="true"){
                    AlertDialog.Builder builder=new AlertDialog.Builder(MeetingHandleDetailActivity.this);
                    builder.setTitle("回执情况");//设置对话框的标题
                    builder.setMessage("您填写的信息已成功提交，请返回");//设置对话框的内容
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //这个是设置确定按钮

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //Toast.makeText(Some_suggestionsActivity.this, "确定", Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post(new EventUtil("发送消息"));
                            finish();
                        }
                    });
//                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {  //取消按钮
//
//                        @Override
//                        public void onClick(DialogInterface arg0, int arg1) {
//                            Toast.makeText(Some_suggestionsActivity.this, "取消",Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    AlertDialog b=builder.create();
                    b.show();  //必须show一下才能看到对话框，跟Toast一样的道理
                }
            }

            @Override
            public void onFailure(Call<HuiZhiBean> call, Throwable t) {
                Log.e("sssss",t.getMessage());
                Toast.makeText(MeetingHandleDetailActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
