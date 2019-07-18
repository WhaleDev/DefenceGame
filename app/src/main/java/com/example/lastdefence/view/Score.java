package com.example.lastdefence.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.lastdefence.R;
import com.example.lastdefence.constant.Constants;
import com.example.lastdefence.utils.Utils;

public class Score
{
	transient GameView mv;
	transient Bitmap[] numbers=new Bitmap[10];

	public Score(GameView mv)
	{
		this.mv=mv;

		//生成0-9十个数字的纹理矩形
		numbers[0] = BitmapFactory.decodeResource(mv.getResources(),
				R.mipmap.scorenumber0);
		numbers[1] = BitmapFactory.decodeResource(mv.getResources(),
				R.mipmap.scorenumber1);
		numbers[2] = BitmapFactory.decodeResource(mv.getResources(),
				R.mipmap.scorenumber2);
		numbers[3] = BitmapFactory.decodeResource(mv.getResources(),
				R.mipmap.scorenumber3);
		numbers[4] = BitmapFactory.decodeResource(mv.getResources(),
				R.mipmap.scorenumber4);
		numbers[5] = BitmapFactory.decodeResource(mv.getResources(),
				R.mipmap.scorenumber5);
		numbers[6] = BitmapFactory.decodeResource(mv.getResources(),
				R.mipmap.scorenumber6);
		numbers[7] = BitmapFactory.decodeResource(mv.getResources(),
				R.mipmap.scorenumber7);
		numbers[8] = BitmapFactory.decodeResource(mv.getResources(),
				R.mipmap.scorenumber8);
		numbers[9] = BitmapFactory.decodeResource(mv.getResources(),
				R.mipmap.scorenumber9);
	}
	public void drawSelf(Canvas canvas, Paint paint, int num)
	{
		String scoreStr = null;
		if(num == 1)
		{
			scoreStr=mv.getScore+"";
			mv.points = mv.getScore;
		}
		else if(num == 2)
		{
			scoreStr=mv.boshu+"";
		}
		else if(num == 3)
		{
			scoreStr=mv.life+"";
		}
		else if(num == 4)
		{
			scoreStr=mv.totalScore+"";
		}
		float width = numbers[0].getWidth();
		float allWidth = scoreStr.length() * width;
		float alength = numbers[0].getHeight();
		for(int i=0;i<scoreStr.length();i++)
		{//将得分中的每个数字字符绘制
			char c=scoreStr.charAt(i);
			if(num == 1)
				canvas.drawBitmap(numbers[c-'0'], 80+i*width, 25, paint);
			else if(num == 2)
				canvas.drawBitmap(numbers[c-'0'], Constants.PMX/2+i*width, 25, paint);
			else if(num == 3)
				canvas.drawBitmap(numbers[c-'0'], mv.lbx.temp3[0]-allWidth/2+i*width, mv.lbx.temp3[1]-alength/2, paint);
			else if(num == 4){
				Bitmap bitmapt = Utils.small(numbers[c-'0'], 3);
				canvas.drawBitmap(bitmapt, 268+mv.gameOver.getWidth()+10+i*width*2,76+mv.gameOver.getHeight()+20+mv.gameOver.getHeight()/2-13, paint);
			}
		}
	}
}
