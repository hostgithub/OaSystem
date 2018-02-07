package com.gdtc.oasystem.header;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

/**
 * Created by wangjiawei on 2018-2-1.
 * Description :头像封装：可用于各种情况下的头像替代（群聊，单聊，列表，资料等。。。）
 * <a href="http://blog.csdn.net/binbinqq86/article/details/78329238">详情</a>
 */
public class HeaderImageView extends View {
    private static final String TAG = "HeaderImageView";
    /**
     * 要绘制的头像群组
     */
    private List<HeaderInfo> list;
    private int width, height;
    private int count;
    /**
     * 1人情况字体大小
     */
    private float textSize1;
    /**
     * 多人情况字体大小
     */
    private float textSizeOther;
    /**
     * 字体颜色，默认白色
     */
    private int textColor;
    /**
     * 背景paint
     */
    private Paint mPaint;
    /**
     * 文字paint
     */
    private TextPaint textPaint;
    /**
     * 头像之间的间隙，默认2dp
     */
    private float space;
    /**
     * x方向的偏移量，用于修正位置
     */
    private float xDelta;
    /**
     * y方向的偏移量，用于修正位置
     */
    private float yDelta;

    public HeaderImageView(Context context) {
        this(context, null);
    }

    public HeaderImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeJoin(Paint.Join.ROUND);// 笔刷图形样式
        mPaint.setStrokeCap(Paint.Cap.ROUND);// 设置画笔转弯的连接风格
        mPaint.setDither(true);//防抖动

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);//默认白色
        textPaint.setTextAlign(Paint.Align.LEFT);

        space = dp2Px(2f);
        yDelta = space;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        xDelta = w / 12f;
        dealPic();
    }

    /**
     * @param size 单位：dp
     * @return
     */
    public HeaderImageView setTextSize1(float size) {
        this.textSize1 = sp2Px(size);
        return this;
    }

    /**
     * @param size 单位：dp
     * @return
     */
    public HeaderImageView setTextSizeOther(float size) {
        this.textSizeOther = sp2Px(size);
        return this;
    }

    /**
     * @param color 资源id，非颜色值
     * @return
     */
    public HeaderImageView setTextColor(@ColorRes int color) {
        this.textColor = getResources().getColor(color);
        return this;
    }

    /**
     * @param space 单位：dp
     * @return
     */
    public HeaderImageView setSpace(float space) {
        this.space = dp2Px(space);
        return this;
    }

    public void setList(List<HeaderInfo> li) {
        this.list = li;
        if (list != null) {
            count = list.size();
        }
        invalidate();
        if (width > 0 && height > 0) {
            dealPic();
        }
    }

    /**
     * 默认显示底色+文字，有头像则去加载，完成后绘制出来
     */
    public void dealPic() {
        if (count == 1) {
            dealPicWithIndex(0);
        } else if (count == 2) {
            dealPicWithIndex(0);
            dealPicWithIndex(1);
        } else if (count == 3) {
            dealPicWithIndex(0);
            dealPicWithIndex(1);
            dealPicWithIndex(2);
        } else if (count >= 4) {
            dealPicWithIndex(0);
            dealPicWithIndex(1);
            dealPicWithIndex(2);
            dealPicWithIndex(3);
        }
    }

    private void dealPicWithIndex(final int index) {
        if (!TextUtils.isEmpty(list.get(index).uri)) {
            Glide.with(getContext())
                    .load(list.get(index).uri)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(width, height)//宽高统一存为控件大小
                    .centerCrop()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            list.get(index).bitmap = resource;
                            postInvalidate();
                        }
                    });
        } else {
            recycleBitmap(list.get(index).bitmap);
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (count == 0) {
            return;
        }
        //必须先设置字体大小
        if (count == 1) {
            if (textSize1 == 0) {
                return;
            }
        } else {
            if (textSizeOther == 0) {
                return;
            }
        }

        if (count == 1) {
            //一个人（两个汉字）
            deal1(canvas);
        } else if (count == 2) {
            //两个人（上下各两个字）
            deal2(canvas);
        } else if (count == 3) {
            //三个人则上面一个（两个汉字），下面两个（一个汉字）
            deal3(canvas);
        } else {
            //4人以上群聊取前四个人的头像拼接（一个汉字）从左到右，从上到下
            deal4(canvas);

        }
    }

    private void deal4(Canvas canvas) {
        textPaint.setTextSize(textSizeOther);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        //baseLine计算参考：http://blog.csdn.net/harvic880925/article/details/50423762
        float baseline = (height / 2f - space / 2f) / 2f + (fontMetrics.bottom - fontMetrics.top) / 2f - fontMetrics.bottom;
        //上面两个字==========================================================
        if (checkBitmap(list.get(0).bitmap)) {
            leftTop(canvas, 0);
        } else {
            mPaint.setColor(getBgColor(list.get(0).id));
            RectF rectf = new RectF(0f, 0f, width - space / 2f, height - space / 2f);
            //顺时针角度，3点钟方向为0度
            canvas.drawArc(rectf, 180f, 90f, true, mPaint);
            //画名字
            String name = list.get(0).presentName;
            name = name.length() > 1 ? name.substring(name.length() - 1, name.length()) : name;
            if (textColor != 0) {
                textPaint.setColor(textColor);
            }
            float x = (width / 2f - space / 2f - textPaint.measureText(name)) / 2f;
            canvas.drawText(name, x + xDelta, baseline + yDelta, textPaint);
        }

        //================================================
        if (checkBitmap(list.get(1).bitmap)) {
            rightTop(canvas, 1);
        } else {
            mPaint.setColor(getBgColor(list.get(1).id));
            RectF rectf0 = new RectF(space / 2f, 0f, width, height - space / 2f);
            canvas.drawArc(rectf0, 270f, 90f, true, mPaint);
            //画名字
            String name0 = list.get(1).presentName;
            name0 = name0.length() > 1 ? name0.substring(name0.length() - 1, name0.length()) : name0;
            float x0 = width / 2f + space / 2f + (width / 2f - space / 2f - textPaint.measureText(name0)) / 2f;
            canvas.drawText(name0, x0 - xDelta, baseline + yDelta, textPaint);
        }

        //下面两个字============================================
        float centerY = height * 0.75f - space * 0.25f;
        float baseline1 = centerY + (fontMetrics.bottom - fontMetrics.top) / 2f - fontMetrics.bottom;
        if (checkBitmap(list.get(2).bitmap)) {
            leftBottom(canvas, 2);
        } else {
            mPaint.setColor(getBgColor(list.get(2).id));
            RectF rectf1 = new RectF(0f, space / 2f, width - space / 2f, height);
            //把半径的线条也绘制出来
            canvas.drawArc(rectf1, 90f, 90f, true, mPaint);
            //画名字
            String name1 = list.get(2).presentName;
            name1 = name1.length() > 1 ? name1.substring(name1.length() - 1, name1.length()) : name1;
            float x1 = (width / 2f - space / 2f - textPaint.measureText(name1)) / 2f;
            canvas.drawText(name1, x1 + xDelta, baseline1 - yDelta, textPaint);
        }

        //=======================================================
        if (checkBitmap(list.get(3).bitmap)) {
            rightBottom(canvas, 3);
        } else {
            mPaint.setColor(getBgColor(list.get(3).id));
            RectF rectf2 = new RectF(space / 2f, space / 2f, width, height);
            canvas.drawArc(rectf2, 0f, 90f, true, mPaint);
            //画名字
            String name2 = list.get(3).presentName;
            name2 = name2.length() > 1 ? name2.substring(name2.length() - 1, name2.length()) : name2;
            float x2 = width / 2f + space / 2f + (width / 2f - space / 2f - textPaint.measureText(name2)) / 2f;
            canvas.drawText(name2, x2 - xDelta, baseline1 - yDelta, textPaint);
        }
    }

    /**
     * 四个图的右上部
     *
     * @param canvas
     * @param index
     */
    private void rightTop(Canvas canvas, int index) {
        //新建Canvas层级
        int saveCount = canvas.saveLayer(0f, 0f, width, height, null, Canvas.ALL_SAVE_FLAG);
        //绘制底图——原来canvas上的内容dst(只绘制图片中间部分)
        RectF dstf = new RectF((width + space) / 2f, 0f, width, (height - space) / 2f);//图片要绘制的画板区域
        canvas.drawBitmap(Bitmap.createScaledBitmap(list.get(index).bitmap, width / 2, height / 2, false), null, dstf, mPaint);

        //生成圆形图片蒙版src
        Bitmap mask = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvasMask = new Canvas(mask);
        canvasMask.drawCircle(width / 2f, height / 2f, width / 2f, mPaint);

        //设置交叉模式，绘制蒙版
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(mask, 0, 0, mPaint);

        //还原
        mPaint.setXfermode(null);
        //将如上画的层覆盖到原有层级上
        canvas.restoreToCount(saveCount);
    }

    /**
     * 四个图的左上部
     *
     * @param canvas
     * @param index
     */
    private void leftTop(Canvas canvas, int index) {
        //新建Canvas层级
        int saveCount = canvas.saveLayer(0f, 0f, width, height, null, Canvas.ALL_SAVE_FLAG);
        //绘制底图——原来canvas上的内容dst(只绘制图片中间部分)
        RectF dstf = new RectF(0f, 0f, (width - space) / 2f, (height - space) / 2f);//图片要绘制的画板区域
        canvas.drawBitmap(Bitmap.createScaledBitmap(list.get(index).bitmap, width / 2, height / 2, false), null, dstf, mPaint);

        //生成圆形图片蒙版src
        Bitmap mask = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvasMask = new Canvas(mask);
        canvasMask.drawCircle(width / 2f, height / 2f, width / 2f, mPaint);

        //设置交叉模式，绘制蒙版
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(mask, 0, 0, mPaint);

        //还原
        mPaint.setXfermode(null);
        //将如上画的层覆盖到原有层级上
        canvas.restoreToCount(saveCount);
    }

    private void deal3(Canvas canvas) {
        textPaint.setTextSize(textSizeOther);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        if (checkBitmap(list.get(0).bitmap)) {
            top(canvas);

        } else {
            mPaint.setColor(getBgColor(list.get(0).id));
            RectF rectf = new RectF(0f, 0f, width, height - space / 2f);
            //顺时针角度，3点钟方向为0度
            canvas.drawArc(rectf, 180f, 180f, false, mPaint);
            //画名字
            String name = list.get(0).presentName;
            name = name.length() > 2 ? name.substring(name.length() - 2, name.length()) : name;
            if (textColor != 0) {
                textPaint.setColor(textColor);
            }
            //baseLine计算参考：http://blog.csdn.net/harvic880925/article/details/50423762
            float baseline = (height / 2f - space / 2f) / 2f + (fontMetrics.bottom - fontMetrics.top) / 2f - fontMetrics.bottom;
            float x = (width - textPaint.measureText(name)) / 2f;
            canvas.drawText(name, x, baseline + yDelta, textPaint);
        }

        //下面两个字============================================
        float centerY = height * 0.75f - space * 0.25f;
        float baseline1 = centerY + (fontMetrics.bottom - fontMetrics.top) / 2f - fontMetrics.bottom;
        if (checkBitmap(list.get(1).bitmap)) {
            leftBottom(canvas, 1);
        } else {
            mPaint.setColor(getBgColor(list.get(1).id));
            RectF rectf1 = new RectF(0f, space / 2f, width - space / 2f, height);
            //把半径的线条也绘制出来
            canvas.drawArc(rectf1, 90f, 90f, true, mPaint);
            //画名字
            String name1 = list.get(1).presentName;
            name1 = name1.length() > 1 ? name1.substring(name1.length() - 1, name1.length()) : name1;
            float x1 = (width / 2f - space / 2f - textPaint.measureText(name1)) / 2f;
            canvas.drawText(name1, x1 + xDelta, baseline1 - yDelta, textPaint);//x方向加个偏移量，不是正中间
        }

        //=====================================================
        if (checkBitmap(list.get(2).bitmap)) {
            rightBottom(canvas, 2);
        } else {
            mPaint.setColor(getBgColor(list.get(2).id));
            RectF rectf2 = new RectF(space / 2f, space / 2f, width, height);
            canvas.drawArc(rectf2, 0f, 90f, true, mPaint);
            //画名字
            String name2 = list.get(2).presentName;
            name2 = name2.length() > 1 ? name2.substring(name2.length() - 1, name2.length()) : name2;
            float x2 = width / 2f + space / 2f + (width / 2f - space / 2f - textPaint.measureText(name2)) / 2f;
            canvas.drawText(name2, x2 - xDelta, baseline1 - yDelta, textPaint);
        }
    }

    /**
     * 处理两张或者三张图片的上部
     *
     * @param canvas
     */
    private void top(Canvas canvas) {
        //新建Canvas层级
        int saveCount = canvas.saveLayer(0f, 0f, width, height, null, Canvas.ALL_SAVE_FLAG);
        //绘制底图——原来canvas上的内容dst(只绘制图片中间部分)
        Rect srcf = new Rect(0, (int) ((height - space) / 4), width, (int) ((height - space) * 3 / 4));//要绘制的图片本身内容区域
        RectF dstf = new RectF(0f, 0f, width, (height - space) / 2f);//图片要绘制的画板区域
        canvas.drawBitmap(list.get(0).bitmap, srcf, dstf, mPaint);

        //生成圆形图片蒙版src
        Bitmap mask = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvasMask = new Canvas(mask);
        canvasMask.drawCircle(width / 2f, height / 2f, width / 2f, mPaint);

        //设置交叉模式，绘制蒙版
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(mask, 0, 0, mPaint);

        //还原
        mPaint.setXfermode(null);
        //将如上画的层覆盖到原有层级上
        canvas.restoreToCount(saveCount);
    }

    /**
     * 处理三或四张图的左下部
     *
     * @param canvas
     * @param index
     */
    private void leftBottom(Canvas canvas, int index) {
        //新建Canvas层级
        int saveCount = canvas.saveLayer(0f, 0f, width, height, null, Canvas.ALL_SAVE_FLAG);
        //绘制底图——原来canvas上的内容dst(只绘制图片中间部分)
        RectF dstf = new RectF(0f, (height + space) / 2f, (width - space) / 2f, height);//图片要绘制的画板区域
        canvas.drawBitmap(Bitmap.createScaledBitmap(list.get(index).bitmap, width / 2, height / 2, false), null, dstf, mPaint);

        //生成圆形图片蒙版src
        Bitmap mask = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvasMask = new Canvas(mask);
        canvasMask.drawCircle(width / 2f, height / 2f, width / 2f, mPaint);

        //设置交叉模式，绘制蒙版
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(mask, 0, 0, mPaint);

        //还原
        mPaint.setXfermode(null);
        //将如上画的层覆盖到原有层级上
        canvas.restoreToCount(saveCount);
    }

    /**
     * 处理三或四张图的右下部
     *
     * @param canvas
     * @param index
     */
    private void rightBottom(Canvas canvas, int index) {
        //新建Canvas层级
        int saveCount = canvas.saveLayer(0f, 0f, width, height, null, Canvas.ALL_SAVE_FLAG);
        //绘制底图——原来canvas上的内容dst(只绘制图片中间部分)
        RectF dstf = new RectF((width + space) / 2f, (height + space) / 2f, width, height);//图片要绘制的画板区域
        canvas.drawBitmap(Bitmap.createScaledBitmap(list.get(index).bitmap, width / 2, height / 2, false), null, dstf, mPaint);

        //生成圆形图片蒙版src
        Bitmap mask = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvasMask = new Canvas(mask);
        canvasMask.drawCircle(width / 2f, height / 2f, width / 2f, mPaint);

        //设置交叉模式，绘制蒙版
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(mask, 0, 0, mPaint);

        //还原
        mPaint.setXfermode(null);
        //将如上画的层覆盖到原有层级上
        canvas.restoreToCount(saveCount);
    }

    private void deal2(Canvas canvas) {
        textPaint.setTextSize(textSizeOther);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        if (checkBitmap(list.get(0).bitmap)) {
            //新建Canvas层级
            top(canvas);
        } else {
            mPaint.setColor(getBgColor(list.get(0).id));
            RectF rectf = new RectF(0f, 0f, width, height - space / 2f);
            //顺时针角度，3点钟方向为0度
            canvas.drawArc(rectf, 180f, 180f, false, mPaint);
            //画名字
            String name = list.get(0).presentName;
            name = name.length() > 2 ? name.substring(name.length() - 2, name.length()) : name;
            if (textColor != 0) {
                textPaint.setColor(textColor);
            }
            //baseLine计算参考：http://blog.csdn.net/harvic880925/article/details/50423762
            float centerY = (height / 2f - space / 2f) / 2f;
            float baseline = centerY + (fontMetrics.bottom - fontMetrics.top) / 2f - fontMetrics.bottom;
            float x = (width - textPaint.measureText(name)) / 2f;
            canvas.drawText(name, x, baseline + yDelta, textPaint);
        }

        //======================================================
        if (checkBitmap(list.get(1).bitmap)) {
            //新建Canvas层级
            int saveCount = canvas.saveLayer(0f, 0f, width, height, null, Canvas.ALL_SAVE_FLAG);
            //绘制底图——原来canvas上的内容dst(只绘制图片中间部分)
            Rect srcf = new Rect(0, (int) ((height - space) / 4), width, (int) ((height - space) * 3 / 4));//要绘制的图片本身内容区域
            RectF dstf = new RectF(0f, (height + space) / 2f, width, height);//图片要绘制的画板区域
            canvas.drawBitmap(list.get(1).bitmap, srcf, dstf, mPaint);

            //生成圆形图片蒙版src
            Bitmap mask = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvasMask = new Canvas(mask);
            canvasMask.drawCircle(width / 2f, height / 2f, width / 2f, mPaint);

            //设置交叉模式，绘制蒙版
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas.drawBitmap(mask, 0f, 0f, mPaint);

            //还原
            mPaint.setXfermode(null);
            //将如上画的层覆盖到原有层级上
            canvas.restoreToCount(saveCount);
        } else {
            mPaint.setColor(getBgColor(list.get(1).id));
            RectF rectf1 = new RectF(0f, space / 2f, width, height);
            canvas.drawArc(rectf1, 0f, 180f, false, mPaint);
            //画名字
            String name1 = list.get(1).presentName;
            name1 = name1.length() > 2 ? name1.substring(name1.length() - 2, name1.length()) : name1;
            float centerY1 = height * 0.75f - space * 0.25f;
            float baseline1 = centerY1 + (fontMetrics.bottom - fontMetrics.top) / 2f - fontMetrics.bottom;
            float x1 = (width - textPaint.measureText(name1)) / 2f;
            canvas.drawText(name1, x1, baseline1 - yDelta, textPaint);
        }
    }

    private void deal1(final Canvas canvas) {
        if (checkBitmap(list.get(0).bitmap)) {
            //新建Canvas层级
            int saveCount = canvas.saveLayer(0f, 0f, width, height, null, Canvas.ALL_SAVE_FLAG);
            //绘制底图——原来canvas上的内容dst
            canvas.drawBitmap(list.get(0).bitmap, 0, 0, mPaint);

            //生成圆形图片蒙版src
            Bitmap mask = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvasMask = new Canvas(mask);
            RectF rf = new RectF(0f, 0f, width, height);
            canvasMask.drawOval(rf, mPaint);

            //设置交叉模式，绘制蒙版
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas.drawBitmap(mask, 0, 0, mPaint);

            //还原
            mPaint.setXfermode(null);
            //将如上画的层覆盖到原有层级上
            canvas.restoreToCount(saveCount);
        } else {
            mPaint.setColor(getBgColor(list.get(0).id));
            canvas.drawCircle(width / 2f, height / 2f, width / 2f, mPaint);
            //画名字
            textPaint.setTextSize(textSize1);
            String name = list.get(0).presentName;
            name = name.length() > 2 ? name.substring(name.length() - 2, name.length()) : name;
            if (textColor != 0) {
                textPaint.setColor(textColor);
            }
            //baseLine计算参考：http://blog.csdn.net/harvic880925/article/details/50423762
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            float baseline = height / 2f + (fontMetrics.bottom - fontMetrics.top) / 2f - fontMetrics.bottom;
            float x = (width - textPaint.measureText(name)) / 2f;
            canvas.drawText(name, x, baseline, textPaint);
        }
    }

    /**
     * 根据用户id生成的底色，规则可以自定义
     *
     * @param id
     * @return
     */
    private int getBgColor(long id) {
        int temp = (int) id % 8;
        int color = Color.BLACK;
        switch (temp) {
            case 0:
                color = 0xff419FE2;
                break;
            case 1:
                color = 0xff1DADAD;
                break;
            case 2:
                color = 0xffD3824A;
                break;
            case 3:
                color = 0xff4995C9;
                break;
            case 4:
                color = 0xffB36E61;
                break;
            case 5:
                color = 0xff49A8B9;
                break;
            case 6:
                color = 0xff09A076;
                break;
            case 7:
                color = 0xff8179C9;
                break;
            default:
                break;
        }
        return color;
    }

    private float dp2Px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private float sp2Px(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (count == 1) {
            recycleBitmap(list.get(0).bitmap);
        } else if (count == 2) {
            recycleBitmap(list.get(0).bitmap);
            recycleBitmap(list.get(1).bitmap);
        } else if (count == 3) {
            recycleBitmap(list.get(0).bitmap);
            recycleBitmap(list.get(1).bitmap);
            recycleBitmap(list.get(2).bitmap);
        } else if (count >= 4) {
            recycleBitmap(list.get(0).bitmap);
            recycleBitmap(list.get(1).bitmap);
            recycleBitmap(list.get(2).bitmap);
            recycleBitmap(list.get(3).bitmap);
        }
    }

    private void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }

    private boolean checkBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return false;
        }
        return true;
    }
}
