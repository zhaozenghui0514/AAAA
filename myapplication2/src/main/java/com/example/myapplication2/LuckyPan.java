package com.example.myapplication2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * date:2018/11/2
 * author:薛鑫欣(吧啦吧啦)
 * function:
 */
public class LuckyPan extends SurfaceView implements SurfaceHolder.Callback,Runnable {
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


    private String[] mTexts=new String[]{"单反相机","ipad","恭喜发财","IPHONE","服装一套","恭喜发财"};
    private int[] mImgs=new int[]{R.mipmap.danfan,
            R.mipmap.ipad,
            R.mipmap.f040,
            R.mipmap.iphone,
            R.mipmap.meizi,
            R.mipmap.f015};
    //与图片对应的bitmap的数组
    private Bitmap [] mBitmaps;
    private Bitmap mBgBitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.bg2);
    //盘块颜色
    private int[] mColors=new int[]{Color.parseColor("#FFC125"),Color.YELLOW,
            Color.parseColor("#FFC125"),Color.YELLOW,
            Color.parseColor("#FFC125"),Color.YELLOW};
    //数量
    private int count=6;

    //绘制盘块的画笔，是弧形
    private Paint mArcPaint;
    //绘制文本的画笔
    private Paint mTextPaint;

    //文字大小，盘块颜色
    private float mTextSize=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
            20,getResources().getDisplayMetrics());


  /*
  2.整个盘块的范围
   */
  private RectF mRange=new RectF();
  /*
  整个盘块的直径
   */
  private int mRadius;
    //转盘的中心位置
    private int mCenter;
    //因为是圆形，所以这里padding为0，直接以paddingleft为准
    private int mPadding;

    //3.滚动的速度
    private double mSpeed=0;
    //绘制的角度,用这个变量声明，保持线程间可见性，因为会使用两个线程去更新他
    private volatile int mStartAngle=0;
    //判断是否点击了停止按钮
    private boolean isShouldEnd;



    public LuckyPan(Context context) {
        this(context,null);
    }

    public LuckyPan(Context context, AttributeSet attrs) {
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //转盘的边长
        int width=Math.min(getMeasuredWidth(),getMeasuredHeight());
        mPadding=getPaddingLeft();
        //半径
        mRadius=width-mPadding*2;
        //中心点
        mCenter=width/2;
        setMeasuredDimension(width,width);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRunning=true;

     //初始化绘制盘块的画笔
        mArcPaint=new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setDither(true);
        //初始化绘制文本的画笔
        mTextPaint=new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(mTextSize);
        //初始化盘块范围
        mRange=new RectF(mPadding,mPadding,mPadding+mRadius,mPadding+mRadius);
        //初始化图片
        mBitmaps =new Bitmap[count];
        for (int i = 0; i < count; i++) {
            mBitmaps[i]=BitmapFactory.decodeResource(getResources(),mImgs[i]);

        }
        new Thread(this).start();

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
          long start=System.currentTimeMillis();

          draw();
          long end=System.currentTimeMillis();
          if(end-start<50){
              try {
                  //如果在50毫秒绘制完成，那么让他休息，一直休息到50毫秒后再继续
                  Thread.sleep(50-(end-start));
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }

          }
      }
    }
  //在里面绘制背景，文字，图片
    private void draw() {
        /*
        当按下Home时候，surface会被销毁，但是可能已经进入这个方法，canvas可能为null,所以判空，
        另一种情况，虽然surface，但是削成不容易销毁，可能会报一些异常。
        当再次回到主界面又会执行surfaceChanged方法
         */
        try {
            mCanvas=mHolder.lockCanvas();//从holder中获取canvas
            if(mCanvas!=null){
                //绘制背景
                drawBg();
                //绘制盘块
                float tmpAngle=mStartAngle;//起始角度，绘制时候角度进行改变
                float sweepAngle=360/count;

                for (int i = 0; i <count ; i++) {
                    mArcPaint.setColor(mColors[i]);

                    //绘制盘块,参数：区域，起始角度，每块盘块的角度，要不要使用中心圆点,绘制盘块的画笔
                    mCanvas.drawArc(mRange,tmpAngle,sweepAngle,true,mArcPaint);
                    //绘制文本
                    //文本是弧形，所以用Path
                    drawText(tmpAngle,sweepAngle,mTexts[i]);
                    //绘制图片
                    drawIcon(tmpAngle,mBitmaps[i]);
                    tmpAngle+=sweepAngle;
                    
                }
                mStartAngle+=mSpeed;
                //如果点击了停止按钮
                if(isShouldEnd){
                    mSpeed-=1;
                }
                if(mSpeed<=0){
                    mSpeed=0;
                    isShouldEnd=false;
                }
                //draw something
            }
        } catch (Exception e){

        }finally {
            mHolder.unlockCanvasAndPost(mCanvas);//绘制结束的时候释放canvas
        }

    }
    public void LuckyStart(){
        mSpeed=50;
        isShouldEnd=false;
    }
    public void LuckyEnd(){
        isShouldEnd=true;

    }
    //转盘是否在旋转
    public boolean isStart(){
        return mSpeed!=0;
    }
    //停止按钮是否按下
    public boolean isShouldEnd(){
        return isShouldEnd;
    }
    /*
    绘制Icon
     */
    private void drawIcon(float tmpAngle, Bitmap bitmap) {
        //要知道图片宽高,为直径1/8
        int imgWidth=mRadius/8;
        //图片的角度=每个盘块的角度的一半乘π/180 1度=π/180
        float angle= (float) ((tmpAngle+360/count/2)*Math.PI/180);
        //中心点的位置
        //x坐标
        int x= (int) (mCenter+mRadius/4*Math.cos(angle));
        //y坐标
        int y= (int) (mCenter+mRadius/4*Math.sin(angle));

        //确定图片的位置
        Rect rect=new Rect(x-imgWidth/2,y-imgWidth/2,x+imgWidth/2,y+imgWidth/2);
        mCanvas.drawBitmap(bitmap,null,rect,null);

    }

    /*
    绘制每个盘块的文本
     */
    private void drawText(float tmpAngle, float sweepAngle, String text) {
        //用Path绘制弧形文本
        Path path=new Path();
        path.addArc(mRange,tmpAngle,sweepAngle);
        //水平偏移
        //圆的直径乘π除以数量等于每个盘块的长度/2-文本长度/2
        float textWeith=mTextPaint.measureText(text);
        int hOffset= (int) (mRadius*Math.PI/count/2-textWeith/2);
        //垂直偏移量,半径6分之一
        int vOffset=mRadius/2/6;
        //ho水平偏移，vo垂直偏移
        mCanvas.drawTextOnPath(text,path,hOffset,vOffset,mTextPaint);

    }

    /*
    绘制背景
     */
    private void drawBg() {

     mCanvas.drawColor(Color.WHITE);
     //背景在绘制盘的外面，宽度为padding的2分之一
     mCanvas.drawBitmap(mBgBitmap,null,new Rect(mPadding/2,mPadding/2,
             getMeasuredWidth()-mPadding/2,
             getMeasuredHeight()-mPadding/2),
             null);
    }
}
