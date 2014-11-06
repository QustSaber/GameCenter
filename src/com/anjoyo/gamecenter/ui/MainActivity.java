package com.anjoyo.gamecenter.ui;

import com.anjoyo.gamecenter.R;
import com.anjoyo.gamecenter.fragment.RecommendFragment;

import android.os.Bundle;
import android.app.Activity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		FragmentManager fragmentManager = this.getSupportFragmentManager();
//		FragmentTransaction transaction = fragmentManager.beginTransaction();
//		RecommendFragment recommendFragment = new RecommendFragment();
//		transaction.replace(R.id.frame_activity_main, recommendFragment);
//		transaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
