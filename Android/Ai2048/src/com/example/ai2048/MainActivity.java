package com.example.ai2048;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends Activity {

	private int score;
	private TextView tvScore;
	private static MainActivity instance;
	public MainActivity(){instance = this;}
	
	public static MainActivity getInstance(){
		return instance;
	}
	
	public void clearScore(){
		this.score = 0;
		tvScore.setText(score+"");
	}
	
	public void showScore(){
		tvScore.setText(score+"");
	}
	
	public void addScore(int s){
		this.score+=s;
		showScore();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvScore = (TextView)findViewById(R.id.score);	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	/**
	 * A placeholder fragment containing a simple view.
	 */

}
