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

	// �ڴ�����ʹ����Ҫ��д�˷���
	public MyView(Context context) {
		super(context);
	}

	// ��xml��������Ҫ��д�˷���
	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
	}

	// ���»��ƻ���
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//�½�һ������
		Paint paint = new Paint();

		//��������
		paint.setTextSize(20);
		canvas.drawText("�Ҿ����ң���һ�����̻�", 30, 100, paint);

		//����һ��ֱ��
		paint.setColor(Color.BLUE);//���û��ʵ���ɫ
		canvas.drawLine(30, 120, 200, 120, paint);
		
		//����һ������
		canvas.drawRect(30, 140, 100, 180, paint);
		//---�ڶ��з�ʽ���ƾ���,�ȶ���һ�����Σ�������������λ��ƶ���
		Rect  rect  = new Rect(30, 180, 100, 210);
		canvas.drawRect(rect, paint);
		//����Բ�Ǿ���
		canvas.drawRoundRect(130, 140, 160, 180, 10, 10, paint);
		
		//����Բ
		paint.setColor(Color.YELLOW);
		canvas.drawCircle(30, 250, 30, paint);
		
		//����ͼƬ
		Matrix matrix = new Matrix();//��������ͼƬ���ƶ�����תλ��
		canvas.drawBitmap(bitmap, matrix, paint);
		
		
	}

}
