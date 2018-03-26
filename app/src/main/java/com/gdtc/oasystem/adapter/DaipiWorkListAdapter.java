package com.gdtc.oasystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseViewHolder;
import com.gdtc.oasystem.bean.DaipiWork;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by llf on 2017/4/19.
 * 发现的适配器，分为两种样式
 */

public class DaipiWorkListAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int TYPE_FOOTER = 0;
    private static final int ITEM_NOIMAGE = 1;
    private static final int ITEM_HASIMAGE = 2;

    private List<DaipiWork.ResultsBean> datas;
    private Context mContext;
    private int viewFooter;
    private View footerView;
    private OnItemClickListener mOnItemClickListener;
//    private static final String HOST = "http://www.jcodecraeer.com";

    public DaipiWorkListAdapter(List<DaipiWork.ResultsBean> datas, Context context) {
        this.datas = datas;
        this.mContext = context;
    }

    public void replaceAll(List<DaipiWork.ResultsBean> elements) {
        if (datas.size() > 0) {
            datas.clear();
        }
        datas.addAll(elements);
        notifyDataSetChanged();
    }

    public void addAll(List<DaipiWork.ResultsBean> elements) {
        datas.addAll(elements);
        notifyDataSetChanged();
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_NOIMAGE) {
            return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_notice_list, parent, false));
        } else if (viewType == ITEM_HASIMAGE) {
            return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_notice_list, parent, false));
        } else {
            footerView = LayoutInflater.from(mContext).inflate(viewFooter, parent, false);
            return new BaseViewHolder(footerView);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        if (!(viewFooter != 0 && position == getItemCount() - 1)) {
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(position);
                    }
                });
            }

            int type = getItemViewType(position);
            final DaipiWork.ResultsBean item = datas.get(position);
//            if (type == ITEM_HASIMAGE) {
//                ImageLoaderUtils.loadingImg(mContext, (ImageView) holder.getView(R.id.cover), Config.BANNER_BASE_URL + item.picName);
//            }
            //CircleImageView avatar = holder.getView(R.id.avatar);
            holder.setText(R.id.tv_title, replaceBlank(item.getTitle()).toString().trim());//去除解析出来的含有\t 并去除标题前的空格
            holder.setText(R.id.tv_type, item.getType().toString().trim());//去除标题前的空格
            holder.setText(R.id.tv_time, item.getTime()==null?null:item.getTime());//判空的三元表达式
//            holder.setText(R.id.tv_name,"username");
            //holder.setText(R.id.content, item.text);
//            if(item.text.startsWith(";")){
//                String str=item.text.replace(';', ' ');
//                holder.setText(R.id.content, str);
//            }else{
//                holder.setText(R.id.content, item.text);
//            }

//            holder.setText(R.id.author, item.getAuthor());
//            holder.setText(R.id.seeNum, item.getWatch());
//            holder.setText(R.id.commentNum, item.getComments());
//            holder.setText(R.id.loveNum, item.getLike());
//            ImageLoaderUtils.loadingImg(mContext, avatar, HOST + item.getAuthorImg());
//            avatar.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    WebViewActivity.lanuch(mContext, HOST + "/member/index.php?uid" + item.getAuthor());
//                }
//            });

        }
    }

    @Override
    public int getItemCount() {
        int count = (datas == null ? 0 : datas.size());
        if (viewFooter != 0) {
            count++;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int type = ITEM_HASIMAGE;
        if (viewFooter != 0 && position == getItemCount() - 1) {
            type = TYPE_FOOTER;
            return type;
        }
        if (TextUtils.isEmpty(datas.get(position).getAddress())) {
            type = ITEM_NOIMAGE;
        } else {
            type = ITEM_HASIMAGE;
        }
        return type;
    }

    public void addFooterView(int footerView) {
        this.viewFooter = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    public static String replaceBlank(String src) {
        String dest = "";
        if (src != null) {
            Pattern pattern = Pattern.compile("\t|\r|\n|\\s*");
            Matcher matcher = pattern.matcher(src);
            dest = matcher.replaceAll("");
        }
        return dest;
    }

    public void setFooterVisible(int visible) {
        footerView.setVisibility(visible);
    }

    //设置点击事件
    public void setOnItemClickLitener(OnItemClickListener mLitener) {
        mOnItemClickListener = mLitener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
