package com.example.ai2048;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

@SuppressLint("ShowToast")
public class GameView  extends GridLayout {
	private Title[][] cardMaps = new Title[4][4];
	private Map<Point,Integer> emptyPoints = new HashMap<Point, Integer>();
	
	private void InitEmptyPoints(){
		for(int x=0; x<4; x++){
			for(int y=0; y<4; y++){
				emptyPoints.put(new Point(x,y), 0);
			}
		}
	}
	
	public GameView(Context context) {
		super(context);
		initGameView();
	}
	
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGameView();
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initGameView();
	}
	
	private void initGameView(){
		setRowCount(4);
		setColumnCount(4);
		setBackgroundColor(0xffbbada0);
		setOnTouchListener(new OnTouchListener() {
			private float startX,startY,offsetX,offsetY;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					offsetX = event.getX()-startX;
					offsetY = event.getY()-startY;
					if(Math.abs(offsetX)>Math.abs(offsetY)){
						if (offsetX<-5) {
							swipeLeft();
						}else if(offsetX>5){
							swipeRight();
						}
					}else {
						if (offsetY<-5) {
							swipeUp();
						}else if(offsetY>5) {
							swipeDown();
						}
					}
					break;
				default:
					break;
				}
				return true;
			}
		});
	}
	private void startGame() {
		MainActivity.getInstance().clearScore();
		
		for(int y = 0;y<4;y++){
			for(int x=0;x<4;x++){
				cardMaps[x][y].setNum(0);
			}
		}
		InitEmptyPoints();
		addRamdonNum();
		addRamdonNum();
		
	}
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		int cardWidth = (Math.min(w, h)-10)/4;
		addCards(cardWidth,cardWidth);
		
		startGame();
	}
	private void addCards(int cardWidth,int cardHeight){
		Title card;
		for(int x = 0;x<4;x++){
			for(int y=0;y<4;y++){
				card = new Title(getContext());
				card.setNum(0);
				addView(card, cardWidth, cardHeight);
				cardMaps[x][y] = card;
			}
		}
	}
	private void addRamdonNum(){
		if (emptyPoints.size()==0) {
			boolean over = checkGameOver();
			if(over){
				System.out.println("Game Over");
				new AlertDialog.Builder(this.getContext()).setTitle("Notify").setMessage("Game Over!").setPositiveButton("Try Again", new DialogInterface.OnClickListener() {			
					@Override
					public void onClick(DialogInterface dialog, int which) {
						startGame();
					}
				}).show();
			}
		}else {
			Random random = new Random();
			List<Point> keys = new ArrayList<Point>(emptyPoints.keySet());
			Point randomKey = keys.get( random.nextInt(keys.size()) );
			Point point = randomKey;
			emptyPoints.remove(point);
			int number = Math.random()>0.1?2:4;
			cardMaps[point.x][point.y].setNum(number);
			MainActivity.getInstance().addScore(number);
		}
	}
	
	private void swipeLeft(){
		boolean canAddNum = false;
		for (int x = 0; x < 4; x++){
			int Index = 0;
			while(Index<3){
				boolean Indexupdated = false;
				for(int cur=Index+1; cur<4; cur++){
					if(cardMaps[x][cur].getNum()!=0){
						if(cardMaps[x][Index].getNum()==0){
							cardMaps[x][Index].setNum(cardMaps[x][cur].getNum());
							cardMaps[x][cur].setNum(0);
							emptyPoints.remove(new Point(x,Index));
							emptyPoints.put(new Point(x,cur), 0);
							canAddNum = true;
						}else{
							if(cardMaps[x][cur].equals(cardMaps[x][Index])){
								cardMaps[x][Index].setNum(cardMaps[x][Index].getNum()<<1);
								cardMaps[x][cur].setNum(0);
								emptyPoints.put(new Point(x,cur),0);
								Index++;
								Indexupdated=true;
								canAddNum = true;
								break;
							}else{
								Index++;
								Indexupdated=true;
								break;
							}
						}
					}
				}
				if(!Indexupdated){
					Index++;
				}
			}
		}
		checkSuccess();
		if(canAddNum || emptyPoints.size()==0){
			addRamdonNum();
		}
	}
	

	private void swipeRight(){
		boolean canAddNum = false;
		for (int x = 0; x < 4; x++) {
			int Index = 3;
			while(Index>0){
				boolean Indexupdated=false;
				for(int cur=Index-1; cur>=0; cur--){
					if(cardMaps[x][cur].getNum()!=0){
						if(cardMaps[x][Index].getNum()==0){
							cardMaps[x][Index].setNum(cardMaps[x][cur].getNum());
							cardMaps[x][cur].setNum(0);
							emptyPoints.remove(new Point(x,Index));
							emptyPoints.put(new Point(x,cur), 0);
							canAddNum = true;
						}else{
							if(cardMaps[x][cur].equals(cardMaps[x][Index])){
								cardMaps[x][Index].setNum(cardMaps[x][Index].getNum()<<1);
								cardMaps[x][cur].setNum(0);
								emptyPoints.put(new Point(x,cur),0);
								Index--;
								Indexupdated=true;
								canAddNum = true;
								break;
							}else{
								Index--;
								Indexupdated=true;
								break;
							}
						}
					}
				}
				if(!Indexupdated){
					Index--;
				}
			}
		}
		checkSuccess();
		if(canAddNum || emptyPoints.size()==0){
			addRamdonNum();
		}
	}
	

	private void swipeUp(){
		boolean canAddNum = false;
		for (int y = 0; y < 4; y++) {
			int Index = 0;
			while(Index<3){
				boolean Indexupdated=false;
				for(int cur=Index+1; cur<4; cur++){
					if(cardMaps[cur][y].getNum()!=0){
						if(cardMaps[Index][y].getNum()==0){
							cardMaps[Index][y].setNum(cardMaps[cur][y].getNum());
							cardMaps[cur][y].setNum(0);
							emptyPoints.remove(new Point(Index,y));
							emptyPoints.put(new Point(cur,y), 0);
							canAddNum = true;
						}else{
							if(cardMaps[cur][y].equals(cardMaps[Index][y])){
								cardMaps[Index][y].setNum(cardMaps[Index][y].getNum()<<1);
								cardMaps[cur][y].setNum(0);
								emptyPoints.put(new Point(cur,y),0);
								Index++;
								Indexupdated=true;
								canAddNum = true;
								break;
							}else{
								Index++;
								Indexupdated=true;
								break;
							}
						}
					}
				}
				if(!Indexupdated){
					Index++;
				}
			}
		}
		checkSuccess();
		if(canAddNum || emptyPoints.size()==0){
			addRamdonNum();
		}
	}
	

	private void swipeDown(){
		boolean canAddNum = false;
		for (int y = 0; y < 4; y++) {
			int Index = 3;
			while(Index>0){
				boolean Indexupdated=false;
				for(int cur=Index-1; cur>=0; cur--){
					if(cardMaps[cur][y].getNum()!=0){
						if(cardMaps[Index][y].getNum()==0){
							cardMaps[Index][y].setNum(cardMaps[cur][y].getNum());
							cardMaps[cur][y].setNum(0);
							emptyPoints.remove(new Point(Index,y));
							emptyPoints.put(new Point(cur,y), 0);
							canAddNum = true;
						}else{
							if(cardMaps[cur][y].equals(cardMaps[Index][y])){
								cardMaps[Index][y].setNum(cardMaps[Index][y].getNum()<<1);
								cardMaps[cur][y].setNum(0);
								emptyPoints.put(new Point(cur,y),0);
								Index--;
								Indexupdated=true;
								canAddNum = true;
								break;
							}else{
								Index--;
								Indexupdated=true;
								break;
							}
						}
					}
				}
				if(!Indexupdated){
					Index--;
				}
			}
		}
		checkSuccess();
		if(canAddNum || emptyPoints.size()==0){
			addRamdonNum();
		}
	}
	
	public void checkSuccess(){
		for (int y = 0 ; y < 4; y++) {
			for (int x = 0 ; x < 4; x++) {
				if (cardMaps[x][y].getNum()==2048) {
					new AlertDialog.Builder(this.getContext()).setTitle("Notify").setMessage("Congratulations!").setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							startGame();
						}
					}).show();
				}
			}
		}
	}
	
	public boolean checkGameOver(){
		boolean NoEqual = true;
		for(int i=0; i<4; i++){
			for(int j=0; j<3; j++){
				if(i==0){
					if(cardMaps[i][j].getNum()!=0 && cardMaps[i][j].equals(cardMaps[i][j+1])){
						NoEqual = false;
						break;
					}
				}else{
					if(cardMaps[i][j].getNum()!=0 && (cardMaps[i][j].equals(cardMaps[i][j+1])) || cardMaps[i][j].equals(cardMaps[i-1][j])){
						NoEqual = false;
						break;
					}
				}
			}
		}
		for(int row=1; row<3; row++){
			if(cardMaps[row][3].equals(cardMaps[row-1][3])){
				NoEqual = false;
				break;
			}
		}
		return NoEqual;
	}
}
