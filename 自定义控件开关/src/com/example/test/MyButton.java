package com.example.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自定义一个控件，具有滑动开关的效果
 * 
 * @author yzas
 * 
 *         view 对象显示的屏幕上，有几个重要步骤： 1、构造方法 创建 对象。 2、测量view的大小。 onMeasure(int,int);
 *         3、确定view的位置 ，view自身有一些建议权，决定权在 父view手中。 onLayout(); 4、绘制 view 的内容
 *         。onDraw(Canvas)
 *
 */
public class MyButton extends View {
	private Bitmap background;
	private Bitmap button;
	private boolean state = false;// 开关默认关
	private int buttonLeft;
	private int startLocation;
	private int currentLocation;

	// 1、写一个类继承View,因为系统的控件都是继承于View

	// 2、创建构造方法,在代码里面调用该控件的时候调用此构造方法
	public MyButton(Context context) {
		super(context);
	}

	// 2、创建构造方法，一般在布局文件中使用控件使用的时候会调用具有两个参数的构造方法
	public MyButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	/**
	 * 初始化布局
	 */
	private void initView() {
		// 获取用于控件的资源图片
		background = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
		button = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);

		// 默认设置按钮的位置在最左边
		buttonLeft = 0;
		// 设置控件的触摸事件
		setOnClickListener(new MyOnClickListener());
	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (!state) {//如果开关是关闭的则将其打开
				buttonLeft = background.getWidth()-button.getWidth();
			} else {//如果开关是打开的则将其关闭
				buttonLeft = 0;
			}
			//保存当前的打开状态
			state = !state;
			
			//刷新view视图，这将导致onDraw()方法的重新执行
			invalidate();
		}
	}

	/**
	 * 测量view的大小,因为整个view的大小是由最外层的背景大小决定，所以这里返回北京大小
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(background.getWidth(), background.getHeight());
	}

	/**
	 * 绘制当前View
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 得到一个画笔
		Paint paint = new Paint();
		paint.setAntiAlias(true);// 打开抗锯齿
		// 开始在画布上绘制图形
		// --指定要绘制的图形对象，开始的 X Y 坐标 ，以及画笔
		canvas.drawBitmap(background, 0, 0, paint);
		
		//判断移动的位置是否合理
		buttonLeft = buttonLeft>0?buttonLeft:0;
		buttonLeft = buttonLeft<(background.getWidth()-button.getWidth())?buttonLeft:background.getWidth()-button.getWidth();
		canvas.drawBitmap(button, buttonLeft, 0, paint);
	}
	
	/**
	 * 触摸事件，设置按键可以左右滑动的特性
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN://当手指按下的时候调用
			startLocation = (int) event.getX();
			
			break;
			
		case MotionEvent.ACTION_MOVE://当手指移动的时候
			currentLocation = (int) event.getX();
			buttonLeft +=currentLocation -startLocation;
			//刷新view视图，这将导致onDraw()方法的重新执行
			invalidate();
			break;
			
		case MotionEvent.ACTION_UP://当手指抬起的时候
			
			
			break;

		default:
			break;
		}
		
		
		return super.onTouchEvent(event);
	}

}
