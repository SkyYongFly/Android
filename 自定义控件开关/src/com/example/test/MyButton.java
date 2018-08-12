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
 * �Զ���һ���ؼ������л������ص�Ч��
 * 
 * @author yzas
 * 
 *         view ������ʾ����Ļ�ϣ��м�����Ҫ���裺 1�����췽�� ���� ���� 2������view�Ĵ�С�� onMeasure(int,int);
 *         3��ȷ��view��λ�� ��view������һЩ����Ȩ������Ȩ�� ��view���С� onLayout(); 4������ view ������
 *         ��onDraw(Canvas)
 *
 */
public class MyButton extends View {
	private Bitmap background;
	private Bitmap button;
	private boolean state = false;// ����Ĭ�Ϲ�
	private int buttonLeft;
	private int startLocation;
	private int currentLocation;

	// 1��дһ����̳�View,��Ϊϵͳ�Ŀؼ����Ǽ̳���View

	// 2���������췽��,�ڴ���������øÿؼ���ʱ����ô˹��췽��
	public MyButton(Context context) {
		super(context);
	}

	// 2���������췽����һ���ڲ����ļ���ʹ�ÿؼ�ʹ�õ�ʱ�����þ������������Ĺ��췽��
	public MyButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	/**
	 * ��ʼ������
	 */
	private void initView() {
		// ��ȡ���ڿؼ�����ԴͼƬ
		background = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
		button = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);

		// Ĭ�����ð�ť��λ���������
		buttonLeft = 0;
		// ���ÿؼ��Ĵ����¼�
		setOnClickListener(new MyOnClickListener());
	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (!state) {//��������ǹرյ������
				buttonLeft = background.getWidth()-button.getWidth();
			} else {//��������Ǵ򿪵�����ر�
				buttonLeft = 0;
			}
			//���浱ǰ�Ĵ�״̬
			state = !state;
			
			//ˢ��view��ͼ���⽫����onDraw()����������ִ��
			invalidate();
		}
	}

	/**
	 * ����view�Ĵ�С,��Ϊ����view�Ĵ�С���������ı�����С�������������ﷵ�ر�����С
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(background.getWidth(), background.getHeight());
	}

	/**
	 * ���Ƶ�ǰView
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// �õ�һ������
		Paint paint = new Paint();
		paint.setAntiAlias(true);// �򿪿����
		// ��ʼ�ڻ����ϻ���ͼ��
		// --ָ��Ҫ���Ƶ�ͼ�ζ��󣬿�ʼ�� X Y ���� ���Լ�����
		canvas.drawBitmap(background, 0, 0, paint);
		
		//�ж��ƶ���λ���Ƿ����
		buttonLeft = buttonLeft>0?buttonLeft:0;
		buttonLeft = buttonLeft<(background.getWidth()-button.getWidth())?buttonLeft:background.getWidth()-button.getWidth();
		canvas.drawBitmap(button, buttonLeft, 0, paint);
	}
	
	/**
	 * �����¼������ð����������һ���������
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN://����ָ���µ�ʱ�����
			startLocation = (int) event.getX();
			
			break;
			
		case MotionEvent.ACTION_MOVE://����ָ�ƶ���ʱ��
			currentLocation = (int) event.getX();
			buttonLeft +=currentLocation -startLocation;
			//ˢ��view��ͼ���⽫����onDraw()����������ִ��
			invalidate();
			break;
			
		case MotionEvent.ACTION_UP://����ָ̧���ʱ��
			
			
			break;

		default:
			break;
		}
		
		
		return super.onTouchEvent(event);
	}

}
