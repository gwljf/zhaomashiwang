package com.example.ai2048;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Title extends FrameLayout {

	private int num = 0;
	private TextView label;
	
	public Title(Context context) {
		super(context);
		label = new TextView(getContext());
		label.setTextSize(32);
		label.setGravity(Gravity.CENTER);
		label.setBackgroundColor(0x35ffffff);
		LayoutParams lp = new LayoutParams(-1,-1);
		lp.setMargins(10, 10, 0, 0);
		addView(label,lp);
		setNum(0);
	}
	
	public void setNum(int num) {
		this.num = num;
		if (num<=0) {
			label.setText("");
		}else {
			label.setText(num+"");
		}
		switch(num){
			case 2:
				setBackgroundColor(0xff0000ff);
				break;
			case 4:
				setBackgroundColor(0xff00ffff);
				break;
			case 8:
				setBackgroundColor(0xff444444);
				break;
			case 16:
				setBackgroundColor(0xff888888);
				break;
			case 32:
				setBackgroundColor(0xff00ff00);
				break;
			case 64:
				setBackgroundColor(0xffcccccc);
				break;
			case 128:
				setBackgroundColor(0xffff00ff);
				break;
			case 256:
				setBackgroundColor(0xffff0000);
				break;
			case 512:
				setBackgroundColor(0xffffffff);
				break;
			case 1024:
				setBackgroundColor(0xffffff00);
				break;
			case 2048:
				setBackgroundColor(0xf34ff6c0);
				break;
			case 4096:
				setBackgroundColor(0xff866688);
				break;
			default:
				setBackgroundColor(0xffbbada0);
		}
	}
	
	public int getNum() {
		return num;
	}
	
	public boolean equals(Title t) {
		return (this.getNum()==t.getNum());
	}
}
