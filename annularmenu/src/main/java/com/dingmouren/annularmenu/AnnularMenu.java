package com.dingmouren.annularmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;


/**
 * Created by dingmouren on 2017/6/11.
 * email:naildingmouren@gmail.com
 */

public class AnnularMenu extends ViewGroup implements View.OnClickListener {
    private static final String TAG = "AnnulardMenu";
    private static final int POSITION_LEFT_TOP = 0;
    private static final int POSITION_LEFT_BOTTOM = 1;
    private static final int POSITION_RIGHT_TOP = 2;
    private static final int POSITION_RIGHT_BOTTOM = 3;
    private static final int DEFAULT_TOGGLE_DURATION = 500;
    private int mToggleDuration = DEFAULT_TOGGLE_DURATION;
    private Position mPosition = Position.RIGHT_BOTTOM;//菜单位置
    private int mRadius;//菜单的 尺寸
    private Status mCurrentStatus = Status.CLOSE;//菜单默认的状态是关闭
    private View mCButton;//菜单的主按钮
    private OnMenuItemClickListener mOnMenuItemClickListener;

    /**
     * 菜单的打开 关闭的状态
     */
    public enum Status {
        OPEN, CLOSE
    }

    /**
     * 菜单的位置
     */
    public enum Position {
        LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM
    }

    public AnnularMenu(Context context) {
        this(context, null);
    }

    public AnnularMenu(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AnnularMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());//菜单尺寸的默认值
        //获取自定义属性
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AnnularMenu, defStyleAttr, 0);
        int position = a.getInt(R.styleable.AnnularMenu_position, POSITION_RIGHT_BOTTOM);
        switch (position) {
            case POSITION_LEFT_TOP:
                mPosition = Position.LEFT_TOP;
                break;
            case POSITION_LEFT_BOTTOM:
                mPosition = Position.LEFT_BOTTOM;
                break;
            case POSITION_RIGHT_TOP:
                mPosition = Position.RIGHT_TOP;
                break;
            case POSITION_RIGHT_BOTTOM:
                mPosition = Position.RIGHT_BOTTOM;
                break;
        }
        mRadius = (int) a.getDimension(R.styleable.AnnularMenu_radius, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics()));
        a.recycle();//释放资源
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        //测量child
        for (int i = 0; i < count; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override     //父元素确定子元素位置的方法
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            layoutCButton();   //定位主菜单
            //定位item
            int count = getChildCount();
            for (int i = 0; i < count - 1; i++) {
                View child = getChildAt(i + 1);
                child.setVisibility(GONE);
                int childLeft = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
                int childTop = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                //左下 右下时
                if (mPosition == Position.LEFT_BOTTOM || mPosition == Position.RIGHT_BOTTOM) {
                    childTop = getMeasuredHeight() - childHeight - childTop;
                }
                //右下 右上时
                if (mPosition == Position.RIGHT_BOTTOM || mPosition == Position.RIGHT_TOP) {
                    childLeft = getMeasuredWidth() - childWidth - childLeft;
                }
                child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);

            }
        }
    }

    /**
     * 定位主菜单
     */
    private void layoutCButton() {
        mCButton = getChildAt(0);
        mCButton.setOnClickListener(this);

        int left = 0;
        int top = 0;
        int width = mCButton.getMeasuredWidth();
        int height = mCButton.getMeasuredHeight();
        switch (mPosition) {
            case LEFT_TOP:
                left = 0;
                top = 0;
                break;
            case LEFT_BOTTOM:
                left = 0;
                top = getMeasuredHeight() - height;
                break;
            case RIGHT_TOP:
                left = getMeasuredWidth() - width;
                top = 0;
                break;
            case RIGHT_BOTTOM:
                left = getMeasuredWidth() - width;
                top = getMeasuredHeight() - height;
                break;
        }
        mCButton.layout(left, top, left + width, top + height);
    }
    @Override
    public void onClick(View v) {
        rotateCButton(v, 0f, 360f, 300);
        toggleMenu(mToggleDuration);//切换菜单打开关闭状态
    }

    private void toggleMenu(int duration) {
        //为item添加平移动画 和旋转动画
        int count = getChildCount();
        for (int i = 0; i < count - 1; i++) {
            final View child = getChildAt(i + 1);
            child.setVisibility(VISIBLE);
            // 动画结束坐标: ( 0,0)        动画开始坐标:
            int childLeft = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
            int childTop = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));
            int xflag = 1;
            int yflag = 1;
            if (mPosition == Position.LEFT_TOP || mPosition == Position.LEFT_BOTTOM) {
                xflag = -1;
            }
            if (mPosition == Position.LEFT_TOP || mPosition == Position.RIGHT_TOP) {
                yflag = -1;
            }

            AnimationSet animationSet = new AnimationSet(true);
            Animation tranAnim = null;
            AlphaAnimation alphaAnimation = null;
            //to open
            if (mCurrentStatus == Status.CLOSE) {
                alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                tranAnim = new TranslateAnimation(xflag * (childLeft - child.getMeasuredWidth()/2 ), 0, yflag * (childTop - child.getMeasuredWidth()/2 ),  0);
                child.setClickable(true);
                child.setFocusable(true);
            } else {        //to close
                alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                tranAnim = new TranslateAnimation( 0, xflag * (childLeft - child.getMeasuredWidth()/2 ), 0, yflag * (childTop - child.getMeasuredWidth()/2 ));
                child.setClickable(false);
                child.setFocusable(false);
            }
            tranAnim.setFillAfter(true);
            tranAnim.setDuration(duration);
            tranAnim.setStartOffset((i * 150) / count);
            tranAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mCurrentStatus == Status.CLOSE) {
                        child.setVisibility(GONE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            //旋转动画
            RotateAnimation rotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(duration);
            rotateAnimation.setFillAfter(true);
            //渐变动画
            alphaAnimation.setDuration(duration);
            alphaAnimation.setFillAfter(true);
            animationSet.addAnimation(rotateAnimation);
            animationSet.addAnimation(tranAnim);
            animationSet.addAnimation(alphaAnimation);
            child.startAnimation(animationSet);
            final int childPosition = i + 1;
            child.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnMenuItemClickListener != null) {
                        mOnMenuItemClickListener.onClick(child, childPosition);
                    }
                    itemAnimation(childPosition - 1);
                    changeStatus();
                }
            });
        }
        //切换菜单的状态
        changeStatus();
    }

    /**
     * item 点击效果
     */
    private void itemAnimation(int position) {
        for (int i = 0; i < getChildCount() - 1; i++) {
            View child = getChildAt(i + 1);
            if (i == position) {
                child.startAnimation(scaleBigAnimation(300));
            } else {
                child.startAnimation(scaleSmallAnimation(300));
            }
            child.setClickable(false);
            child.setFocusable(false);
        }
    }

    public boolean isOpen() {
        return mCurrentStatus == Status.OPEN;
    }

    public void toggle(){
        toggleMenu(mToggleDuration);
    }

    /*
     * item变小的动画
     * @return
     */
    private Animation scaleSmallAnimation(int duration) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5F);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    /**
     * item变大的动画
     *
     * @return
     */
    private Animation scaleBigAnimation(int duration) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 2.5f, 1.0f, 2.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5F);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    /**
     * 切换菜单状态
     */
    private void changeStatus() {
        mCurrentStatus = (mCurrentStatus == Status.CLOSE ? Status.OPEN : Status.CLOSE);
    }

    /**
     * 主按钮旋转动画
     */
    private void rotateCButton(View v, float start, float end, int duration) {
        RotateAnimation animation = new RotateAnimation(start, end, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        v.startAnimation(animation);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        mOnMenuItemClickListener = onMenuItemClickListener;
    }


    /**
     * 子菜单点击回调接口
     */
    public interface OnMenuItemClickListener {
        void onClick(View view, int position);
    }
}
