package com.triggerhappy.android.controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

public class HDRView extends ImageView{
	private Rect boxSet0;
	private Rect boxSet1;
	private Rect boxSet2;
	private Rect boxSet3;
	private Rect boxSet4;
	
	private Rect dot0;
	private Rect dot1;
	private Rect dot2;
	private Rect dot3;
	private Rect dot4;
	private Rect dot5;
	private Rect dot6;
	private Rect dot7;
	
	private Rect pointer;
	private Path triangle;
	
	private Paint boxPaint;
	private Paint textPaint;
	private Paint pointerPaint;
	private Paint strokePaint;
	
	private int numberOfShots;
	private double evInterval;
	private String shutterLength;
	
	private int ll;
	private int lr;
	private int mid;
	private int rl;
	private int rr;
	
    public HDRView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initHDR();
	}

    public HDRView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHDR();
	}

    public HDRView(Context context) {
		super(context);
		initHDR();
	}
    
    public void setNoOfShots(int shots){
    	this.numberOfShots = shots;
        requestLayout();
        invalidate();
    }
    
    public void setShutterLength(String length){
    	this.shutterLength = length;
        requestLayout();
        invalidate();
    }
    
    public void setShotSeries(int _ll, int _lr, int _mid, int _rl, int _rr){
    	this.ll = _ll;
    	this.lr = _lr;
    	this.mid = _mid;
    	this.rl = _rl;
    	this.rr = _rr;

    	requestLayout();
        invalidate();
    }
    
    private void initHDR(){
    	this.numberOfShots = 3;
    	this.evInterval = .33;
    	this.shutterLength = "00:00:00";
    	
    	this.ll = 2;
    	this.lr = 1;
    	this.mid = 0;
    	this.rl = 1;
    	this.rr = 2;

    	boxSet0 = new Rect(25, 75, 55, 130);
        boxSet1 = new Rect(100, 75, 130, 130);
        boxSet2 = new Rect(195, 75, 225, 135);
        boxSet3 = new Rect(290, 75, 320, 135);
        boxSet4 = new Rect(365, 75, 395, 135);
        
        pointer = new Rect(192, 10, 228, 46);
        
        triangle = new Path();
        triangle.setFillType(Path.FillType.EVEN_ODD);
        triangle.moveTo(192, 46);
        triangle.lineTo(210, 66);
        triangle.lineTo(228, 46);
        triangle.lineTo(192, 46);
        
        dot0 = new Rect(65, 65, 70, 70);
        dot1 = new Rect(85, 65, 90, 70);
        
        dot2 = new Rect(140, 65, 145, 70);
        dot3 = new Rect(180, 65, 185, 70);
        
        dot4 = new Rect(235, 65, 240, 70);
        dot5 = new Rect(275, 65, 280, 70);
        
        dot6 = new Rect(330, 65, 335, 70);
        dot7 = new Rect(350, 65, 355, 70);
        
        boxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    	
        
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        
        textPaint.setTextSize(35);
        textPaint.setFakeBoldText(true);
        textPaint.setStrokeWidth(100);
        
        pointerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointerPaint.setColor(Color.WHITE);
        pointerPaint.setTextSize(50);
        
        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setStrokeWidth(5);
    }

    /**
     * Render the text
     * 
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(pointer, boxPaint);
        canvas.drawPath(triangle, boxPaint);
        
        canvas.drawLine(10, 45, 20, 45, strokePaint);
        
        canvas.drawText(this.ll + "", 25, 70, textPaint);
        canvas.drawText(this.lr + "", 100, 70, textPaint);
        
        canvas.drawText(this.mid + "", 196, 52, pointerPaint);

        canvas.drawText(this.rl + "", 290, 70, textPaint);
        canvas.drawText(this.rr + "", 365, 70, textPaint);

        strokePaint.setStrokeWidth(3);
        canvas.drawLine(350, 45, 360, 45, strokePaint);
        canvas.drawLine(355, 40, 355, 50, strokePaint);
        
        canvas.drawRect(dot0, boxPaint);
        canvas.drawRect(dot1, boxPaint);
        canvas.drawRect(dot2, boxPaint);
        canvas.drawRect(dot3, boxPaint);
        canvas.drawRect(dot4, boxPaint);
        canvas.drawRect(dot5, boxPaint);
        canvas.drawRect(dot6, boxPaint);
        canvas.drawRect(dot7, boxPaint);
        
        canvas.drawRect(boxSet0, boxPaint);
        canvas.drawRect(boxSet1, boxPaint);
        canvas.drawRect(boxSet2, boxPaint);
        canvas.drawRect(boxSet3, boxPaint);
        canvas.drawRect(boxSet4, boxPaint);
        
        canvas.drawText(this.numberOfShots + " Shots", 30, 180, textPaint);
        canvas.drawText(this.evInterval + " EV Interval", 175, 180, textPaint);
        canvas.drawText(this.shutterLength + " Shutter Length", 30, 235, textPaint);
    }
}