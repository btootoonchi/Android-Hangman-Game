package com.example.hangman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawView extends View{

	Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	float startViewX;
	float startViewY;
    float endViewX;
    float endViewY;
    int typeShape;
    
	public DrawView(Context context, float startX, float startY, float endX, float endY, int typeShape, int color) {
		super(context);
		
		this.startViewX = startX;
		this.startViewY = startY;
		this.endViewX = endX;
		this.endViewY = endY;
		this.typeShape = typeShape;
		paint.setColor(color);
		paint.setStrokeWidth(3);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		//super.onDraw(canvas);
		if(typeShape == 1001)
			canvas.drawLine(startViewX, startViewY, endViewX, endViewY, paint);
		else if(typeShape == 1002)
			canvas.drawCircle(startViewX, startViewY, endViewX, paint);
	}

}
