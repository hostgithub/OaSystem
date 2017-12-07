package com.gdtc.oasystem.ui;

import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gdtc.oasystem.Config;
import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseActivity;
import com.gdtc.oasystem.utils.SharePreferenceTools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ZwsqSubmitActivity extends BaseActivity{

    @BindView(R.id.title_center)
    TextView title_center;
    @BindView(R.id.start)
    TextView start;
    @BindView(R.id.stop)
    TextView stop;
    private PopupWindow popupWindow;
    private View contentView;
    private View titleBar;

    private PopupWindow kindPopupWindow;
    private View kindContentView;

    private TextView tv_kind1;
    private TextView tv_kind2;
    private TextView tv_kind3;
    private TextView tv_kind4;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;

    @BindView(R.id.tv_bumen)
    TextView edt_bumen;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.edt_work_years)
    Spinner edt_work_years;
    @BindView(R.id.tv_kind)
    TextView tv_kind;
    @BindView(R.id.edt_content)
    EditText edt_content;
    @BindView(R.id.tv_send_person)
    TextView tv_send_person;
    @BindView(R.id.day_number)
    TextView day_number;

    //网格布局
    private GridView mGridview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    // 图片封装为一个数组
    private int[] icon = { R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private String[] iconName = { "徐建国", "郭冀平", "邵国强", "马里",
            "刘臣", "周宏", "顾百文", "钟文"};
    private SharePreferenceTools sp;


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //完成主界面更新,拿到数据
                    String data = (String) msg.obj;
                    day_number.setText(String.valueOf(data));
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zwsq_submit;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);// 让输入框不自动打开输入法
        title_center.setText("政务申请");
        sp=new SharePreferenceTools(this);
        tv_username.setText(sp.getString(Config.USER_NAME));
        contentView = LayoutInflater.from(this).inflate(R.layout.popupwindow, null);
        mGridview= (GridView) contentView.findViewById(R.id.gridview);
        titleBar=LayoutInflater.from(this).inflate(R.layout.layout_setting_title, null);
//        popupWindow = new PopupWindow(contentView,
//                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        // 这里单独写一篇文章来分析
        //popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置好参数之后再show
        // 获得位置 这里的v是目标控件，就是你要放在这个v的上面还是下面
        if (popupWindow == null) {
            popupWindow = new PopupWindow(this);
            popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setBackgroundDrawable(new ColorDrawable());
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setTouchable(true);
            popupWindow.setContentView(contentView);
            popupWindow.setOnDismissListener(new poponDismissListener());
        }

        kindContentView = LayoutInflater.from(this).inflate(R.layout.popupwindow_kind, null);
        tv_kind1= (TextView) kindContentView.findViewById(R.id.tv_kind1);
        tv_kind2= (TextView) kindContentView.findViewById(R.id.tv_kind2);
        tv_kind3= (TextView) kindContentView.findViewById(R.id.tv_kind3);
        tv_kind4= (TextView) kindContentView.findViewById(R.id.tv_kind4);
        rb1= (RadioButton) kindContentView.findViewById(R.id.rb1);
        rb1.setChecked(true);
        rb2= (RadioButton) kindContentView.findViewById(R.id.rb2);
        rb3= (RadioButton) kindContentView.findViewById(R.id.rb3);
        rb4= (RadioButton) kindContentView.findViewById(R.id.rb4);
        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_kind.setText("事假");
                rb2.setChecked(false);
                rb3.setChecked(false);
                rb4.setChecked(false);
                if(kindPopupWindow!=null){
                    kindPopupWindow.dismiss();
                }
            }
        });
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_kind.setText("病假");
                rb1.setChecked(false);
                rb3.setChecked(false);
                rb4.setChecked(false);
                if(kindPopupWindow!=null){
                    kindPopupWindow.dismiss();
                }
            }
        });
        rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_kind.setText("年假");
                rb2.setChecked(false);
                rb1.setChecked(false);
                rb4.setChecked(false);
                if(kindPopupWindow!=null){
                    kindPopupWindow.dismiss();
                }
            }
        });
        rb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_kind.setText("其他");
                rb2.setChecked(false);
                rb3.setChecked(false);
                rb1.setChecked(false);
                if(kindPopupWindow!=null){
                    kindPopupWindow.dismiss();
                }
            }
        });

        if (kindPopupWindow == null) {
            kindPopupWindow = new PopupWindow(this);
            kindPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            kindPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            kindPopupWindow.setBackgroundDrawable(new ColorDrawable());
            kindPopupWindow.setFocusable(true);
            kindPopupWindow.setOutsideTouchable(true);
            kindPopupWindow.setTouchable(true);
            kindPopupWindow.setContentView(kindContentView);
            kindPopupWindow.setOnDismissListener(new poponDismissListener());
        }

        //网格布局
        // 新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        //新建适配器
        String [] from ={"image","text"};
        int [] to = {R.id.image,R.id.send_person};
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.item_gridview, from, to);
        //配置适配器
        mGridview.setAdapter(sim_adapter);
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_send_person.setText(iconName[position]);
                if(popupWindow!=null){
                    popupWindow.dismiss();
                }
            }
        });
    }

    @OnClick({ R.id.title_left,R.id.btn_submit,R.id.btn_cancel,R.id.tv_send_person,R.id.start,R.id.stop,R.id.tv_kind})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left:
                finish();
                break;
            case R.id.tv_kind:

                kindPopupWindow.showAtLocation(titleBar, Gravity.CENTER, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                popupWindow.showAtLocation(contentView, Gravity.TOP,20,20);
//                popupWindow.showAsDropDown(titleBar, 0, 0,Gravity.CENTER);
                //添加pop窗口关闭事件，主要是实现关闭时改变背景的透明度
                backgroundAlpha(0.5f);
                break;
            case R.id.start:
                showDatePickDlg(start);
                break;
            case R.id.stop:
                if(!start.getText().toString().trim().equals("")){
                    showDatePickDlg(stop);
                    Log.e("----点击后的stop_date",stop.getText().toString());
                }else{
                    Toast.makeText(ZwsqSubmitActivity.this,"请选择开始时间",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_send_person:
                //Toast.makeText(ZwsqSubmitActivity.this,"请从列表中选择发送人",Toast.LENGTH_SHORT).show();
                popupWindow.showAtLocation(titleBar, Gravity.CENTER, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                popupWindow.showAtLocation(contentView, Gravity.TOP,20,20);
//                popupWindow.showAsDropDown(titleBar, 0, 0,Gravity.CENTER);
                //添加pop窗口关闭事件，主要是实现关闭时改变背景的透明度
                backgroundAlpha(0.5f);
                break;
            case R.id.btn_submit://提交
                Toast.makeText(ZwsqSubmitActivity.this,"提交",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_cancel://取消
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 网格布局初始化数据
     * @return
     */
    public List<Map<String, Object>> getData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }
        return data_list;
    }

    protected void showDatePickDlg(final TextView textView) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(ZwsqSubmitActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                textView.setText(year + "-" + ++monthOfYear + "-" + dayOfMonth);
                if(textView.getId()==R.id.stop){
                    stop.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
                    Log.e("----选完日期后的stop_date",stop.getText().toString());
                    dayNumber(start.getText().toString(),stop.getText().toString());
                }
                if(textView.getId()==R.id.start&&!stop.getText().toString().equals("")){
                    dayNumber(start.getText().toString(),stop.getText().toString());
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);  getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * 返回或者点击空白位置的时候将背景透明度改回来
     */
    class poponDismissListener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }

    private void dayNumber(String start,String stop){
        String str1 = start; // "yyyy-MM-dd"格式 如 2013-10-22
        Log.e("----方法中的start_date",str1);
        String str2 = stop; // "yyyy-MM-dd"格式 如 2013-10-22
        Log.e("----方法中的stop_date",str2);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// 输入日期的格式
        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse(str1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2 = null;
        try {
            date2 = simpleDateFormat.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        cal1.setTime(date1);
        cal2.setTime(date2);
        double dayCount = (cal2.getTimeInMillis() - cal1.getTimeInMillis()) / (1000 * 3600 * 24);// 从间隔毫秒变成间隔天数
        day_number.setText(String.valueOf(dayCount));
    }

    private void sendMessage(final String day) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                //耗时操作，完成之后发送消息给Handler，完成UI更新；
                mHandler.sendEmptyMessage(0);

                //需要数据传递，用下面方法；
                Message msg = new Message();
                msg.obj = day;//可以是基本类型，可以是对象，可以是List、map等；
                mHandler.sendMessage(msg);
            }

        }).start();
    }
}
