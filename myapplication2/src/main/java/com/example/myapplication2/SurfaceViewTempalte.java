package com.example.myapplication2;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * date:2018/11/2
 * author:薛鑫欣(吧啦吧啦)
 * function:
 */
public class SurfaceViewTempalte extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    /*
    用于绘制的线程
     */
    private Thread mThread;
    /*
    线程的控制开关
     */
    private boolean isRunning;
    public SurfaceViewTempalte(Context context) {
        this(context,null);
    }

    public SurfaceViewTempalte(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder=getHolder();//holder管理surface的生命周期,获得canvas
        mHolder.addCallback(this);//让这个类实现callback
        //可获得焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        //设置常量
        setKeepScreenOn(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
      isRunning=true;
      mThread=new Thread(this);
      mThread.start();//开启线程
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    isRunning=false;//关闭线程
    }

    @Override
    public void run() {
        //不断进行绘制
      while (isRunning){
          draw();
      }
    }

    private void draw() {
        /*
        当按下Home时候，surface会被销毁，但是可能已经进入这个方法，canvas可能为null,所以判空，
        另一种情况，虽然surface，但是削成不容易销毁，可能会报一些异常。
        当再次回到主界面又会执行surfaceChanged方法
         */
        try {
            mCanvas=mHolder.lockCanvas();//从holder中获取canvas
            if(mCanvas!=null){
                //draw something
            }
        } catch (Exception e){

        }finally {
            mHolder.unlockCanvasAndPost(mCanvas);//绘制结束的时候释放canvas
        }

    }
}
