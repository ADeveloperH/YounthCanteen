package com.mobile.younthcanteen.ui;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Scroller;

import com.mobile.younthcanteen.R;

/**
 * author：hj
 * time: 2017/2/26 0026 22:17
 */

public class HomeRefreshListView extends ListView implements AbsListView.OnScrollListener {

    private final int PULL_REFRESH = 0;//下拉刷新
    private final int RELEASE_REFRESH = 1;//释放刷新
    private final int REFRESHING = 2;//刷新中
    private final int REFRESH_FINISH = 3;//刷新完成
    private final int RATIO = 3;//手指移动的距离和刷新头移动的距离的比
    private View refreshHeaderView;
    private int refreshHeaderHeight;//刷新头的高度
    private float downY;
    private int curState = REFRESH_FINISH;//当前的刷新状态
    private Scroller scroller;
    private int needPaddingTop;
    private ImageView imageView;
    private AnimationDrawable animationDrawable;
    private int mFirstVisibleItem;
    private ViewGroup.LayoutParams layoutParams;
    private int imageViewHeight;//图片的真实高度
    private int imageViewWidth;//图片的真是宽度

    public HomeRefreshListView(Context context) {
        super(context);
        init(context);
    }

    public HomeRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HomeRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOnScrollListener(this);

        refreshHeaderView = LayoutInflater.from(context).inflate(R.layout.home_lv_refresh_header,
                null, false);
        imageView = (ImageView) refreshHeaderView.findViewById(R.id.iv);
        imageView.setBackgroundResource(R.drawable.pull_to_refresh_anim);
        animationDrawable = (AnimationDrawable) imageView.getBackground();

        refreshHeaderView.measure(0, 0);
//        measureView(refreshHeaderView);
        this.addHeaderView(refreshHeaderView);
        refreshHeaderHeight = refreshHeaderView.getMeasuredHeight();
        refreshHeaderView.setPadding(0, -refreshHeaderHeight, 0, 0);

        imageViewHeight = imageView.getMeasuredHeight();
        imageViewWidth = imageView.getMeasuredWidth();
        Log.d("hj", "refreshHeaderViewHeight:" + refreshHeaderView.getMeasuredHeight());
        Log.d("hj", "refreshHeaderViewWidth:" + refreshHeaderView.getMeasuredWidth());
        Log.d("hj", "imageViewHeight:" + imageViewHeight);
        Log.d("hj", "imageViewWidth:" + imageViewWidth);

        scroller = new Scroller(context);
        layoutParams = imageView.getLayoutParams();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d("hj", "ev.getAction()::" + ev.getAction());
        if (mFirstVisibleItem == 0) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downY = ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (curState != REFRESHING) {
                        //当前不是正在刷新状态
                        float tempY = ev.getY();
                        Log.d("hj", "downY::" + downY);
                        if (downY == 0) {
                            downY = tempY;
                        }
                        float offsetY = tempY - downY;
                        Log.d("hj", "offsetY::" + offsetY);
                        needPaddingTop = (int) (-refreshHeaderHeight + offsetY / RATIO);
                        Log.d("hj", "needPaddingTop::" + needPaddingTop);
//                        float scale =  (needPaddingTop-refreshHeaderHeight) / refreshHeaderHeight;
                        float scale = 1 + (float) needPaddingTop / (float) refreshHeaderHeight;
//                        scale = scale * scale ;
                        Log.d("hj", "---------------------------------scale::" + scale);
                        if (scale >= 1) {
                            scale = 1;
                        } else if (scale <= 0) {
                            scale = 0;
                        }
                        if (needPaddingTop >= 0) {
                            //刷新头完全显示
                            setSelection(0);
                            curState = RELEASE_REFRESH;
                            layoutParams.height = (int) (imageViewHeight * scale);
                            layoutParams.width = (int) (imageViewWidth * scale);
                            imageView.setLayoutParams(layoutParams);
                            refreshHeaderView.setPadding(0, needPaddingTop, 0, 0);
                        } else if (needPaddingTop < 0 && needPaddingTop >= -refreshHeaderHeight) {
                            //刷新头部分显示
                            setSelection(0);
                            curState = PULL_REFRESH;
                            layoutParams.height = (int) (imageViewHeight * scale);
                            layoutParams.width = (int) (imageViewWidth * scale);
                            imageView.setLayoutParams(layoutParams);
                            refreshHeaderView.setPadding(0, needPaddingTop, 0, 0);
                        } else {
                            //未显示刷新头
                            curState = REFRESH_FINISH;
                        }
                        changeState(curState);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    downY = 0;
                    int paddingTop = refreshHeaderView.getPaddingTop();
                    if (curState == RELEASE_REFRESH) {
                        //刷新头完全显示了.释放刷新setpaddingtop=0
                        scroller.startScroll(0, paddingTop,
                                0, -paddingTop, 500);
                        Log.d("hj", "getPaddingTop())" + paddingTop);
                        Log.d("hj", "needPaddingTop" + needPaddingTop);
                        curState = REFRESHING;
                    } else if (curState == PULL_REFRESH) {
                        //刷新头部分显示。下拉刷新。setpaddingtop=-refreshHeaderHeight
                        scroller.startScroll(0, paddingTop,
                                0, -refreshHeaderHeight - paddingTop, 500);
                        Log.d("hj", "getPaddingTop())" + paddingTop);
                        curState = REFRESH_FINISH;
                    }
                    changeState(curState);
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()
                && curState != REFRESH_FINISH) {
            setSelection(0);
            refreshHeaderView.setPadding(0, scroller.getCurrY(), 0, 0);
            Log.d("hj", "scroller.getCurrY()" + scroller.getCurrY());
            invalidate();
        }
    }

    private void changeState(int state) {
        switch (state) {
            case REFRESH_FINISH://刷新完成.隐藏刷新头.停止动画
                Log.d("hj", "-----------------------------刷新完成.隐藏刷新头.停止动画");
                if (getPaddingTop() != -refreshHeaderHeight) {
                    Log.d("hj", "-----------------------------getPaddingTop() != -refreshHeaderHeight");
                    refreshHeaderView.setPadding(0, -refreshHeaderHeight, 0, 0);
                }
                if (animationDrawable.isRunning()) {
                    Log.d("hj", "-----------------------------animationDrawable.isRunning()");
                    animationDrawable.stop();
                }
                break;
            case PULL_REFRESH://下拉刷新
                if (!animationDrawable.isRunning()) {
                    animationDrawable.start();
                }
                break;
            case RELEASE_REFRESH://释放刷新
                if (!animationDrawable.isRunning()) {
                    animationDrawable.start();
                }
                break;
            case REFRESHING://刷新中
                if (!animationDrawable.isRunning()) {
                    animationDrawable.start();
                }
                if (refreshListener != null) {
                    refreshListener.onRefresh();
                }
                break;

        }
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_FINISH:
                    changeState(curState);
                    break;
            }
        }
    };


    /**
     * 刷新数据完成
     * 延迟隐藏刷新头。隐藏过快太突兀
     */
    public void setRefreshFinished() {
        curState = REFRESH_FINISH;
//        changeState(curState);
        handler.sendEmptyMessageDelayed(REFRESH_FINISH, 500);
        Log.d("hj", "-----------------------------setRefreshFinished");
//        setSelection(0);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mFirstVisibleItem = firstVisibleItem;
    }


    private OnListViewRefreshListener refreshListener;

    public interface OnListViewRefreshListener {
        void onRefresh();//正在刷新
    }

    /**
     * 设置刷新监听
     *
     * @param refreshListener
     */
    public void setOnRefreshListener(OnListViewRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }
}
