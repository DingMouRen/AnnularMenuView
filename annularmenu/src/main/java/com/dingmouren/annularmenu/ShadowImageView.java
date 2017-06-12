package com.dingmouren.annularmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dingmouren on 2017/6/12.
 */

public class ShadowImageView extends FrameLayout {
    private static final String TAG = "ShadowImageView";
    private static final String DEFAULT_SHADOW_COLOR = "#88FFFFFF";//默认的阴影颜色
    private float mShadowOffsetX;//阴影在x方向的偏移量
    private float mShadowOffsetY;//阴影在y方向的偏移量
    private float mShadowRadius;//阴影的圆角半径
    private ImageView mImageView;//图片
    private int mImgId;

    public ShadowImageView(@NonNull Context context) {
        this(context, null);
    }

    public ShadowImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowImageView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(getResources().getColor(android.R.color.transparent));
        //获取自定义属性值
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ShadowImageView);
        if (attrs == null) return;
        try {
            mShadowOffsetX = a.getDimension(R.styleable.ShadowImageView_shadowOffsetX, 0.0f);
            mShadowOffsetY = a.getDimension(R.styleable.ShadowImageView_shadowOffsetY, 0.0f);
            mShadowRadius = a.getDimension(R.styleable.ShadowImageView_shadowRadius, 0.0f);
            mImgId = a.getResourceId(R.styleable.ShadowImageView_src, -1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            a.recycle();
        }
        mImageView = new CircleImageView(context);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImageView.setImageResource(mImgId);
        addView(mImageView);

        int xPadding = (int) (mShadowRadius + Math.abs(mShadowOffsetX));
        int yPadding = (int) (mShadowRadius + Math.abs(mShadowOffsetY));
        setPadding(xPadding, yPadding, xPadding, yPadding);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            setBackgroundCompat(w, h);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChild(getChildAt(0), widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setBackgroundCompat(right - left, bottom - top);
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

    private void setBackgroundCompat(int w, int h) {
        Bitmap bitmap = createShadowBitmap(w, h, mShadowRadius, mShadowOffsetX, mShadowOffsetY, Color.TRANSPARENT);
        BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
        if (Build.VERSION.SDK_INT <= 16) {
            setBackgroundDrawable(drawable);
        } else {
            setBackground(drawable);
        }
    }

    private Bitmap createShadowBitmap(int shadowWidth, int shadowHeight, float shadowRadius, float shadowOffsetX, float shadowOffsetY, int fillColor) {
        Bitmap bitmap = Bitmap.createBitmap(shadowWidth, shadowHeight, Bitmap.Config.ALPHA_8);
        Canvas canvas = new Canvas(bitmap);
        RectF shadowRect = new RectF(shadowRadius, shadowRadius, shadowWidth - shadowRadius, shadowHeight - shadowRadius);
        if (shadowOffsetX > 0) {
            shadowRect.left += shadowOffsetX;
            shadowRect.right -= shadowOffsetX;
        } else if (shadowOffsetX < 0) {
            shadowRect.left += Math.abs(shadowOffsetX);
            shadowRect.right -= Math.abs(shadowOffsetX);
        }

        if (shadowOffsetY > 0) {
            shadowRect.top += shadowOffsetY;
            shadowRect.bottom -= shadowOffsetY;
        } else if (shadowOffsetY < 0) {
            shadowRect.top += Math.abs(shadowOffsetY);
            shadowRect.bottom -= Math.abs(shadowOffsetY);
        }

        Paint shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shadowPaint.setColor(Color.parseColor(DEFAULT_SHADOW_COLOR));
        shadowPaint.setStyle(Paint.Style.FILL);
        shadowPaint.setShadowLayer(shadowRadius, shadowOffsetX, shadowOffsetY, Color.parseColor(DEFAULT_SHADOW_COLOR));
        int radius = Math.max(mImageView.getMeasuredWidth(), mImageView.getMeasuredHeight());
        canvas.drawRoundRect(shadowRect, radius, radius, shadowPaint);

        return bitmap;
    }

}
