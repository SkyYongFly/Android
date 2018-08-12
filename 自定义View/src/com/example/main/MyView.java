package com.example.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
	private Bitmap bitmap;

	// 在代码中使用需要复写此方法
	public MyView(Context context) {
		super(context);
	}

	// 在xml中引用需要复写此方法
	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
	}

	// 重新绘制画布
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//新建一个画笔
		Paint paint = new Paint();

		//绘制文字
		paint.setTextSize(20);
		canvas.drawText("我就是我，不一样的烟火", 30, 100, paint);

		//绘制一条直线
		paint.setColor(Color.BLUE);//设置画笔的颜色
		canvas.drawLine(30, 120, 200, 120, paint);
		
		//绘制一个矩形
		canvas.drawRect(30, 140, 100, 180, paint);
		//---第二中方式绘制矩形,先定义一个矩形，再利用这个矩形绘制对象
		Rect  rect  = new Rect(30, 180, 100, 210);
		canvas.drawRect(rect, paint);
		//绘制圆角矩形
		canvas.drawRoundRect(130, 140, 160, 180, 10, 10, paint);
		
		//绘制圆
		paint.setColor(Color.YELLOW);
		canvas.drawCircle(30, 250, 30, paint);
		
		//绘制图片
		Matrix matrix = new Matrix();//可以用于图片的移动和旋转位置
		canvas.drawBitmap(bitmap, matrix, paint);
		
		
	}

}
