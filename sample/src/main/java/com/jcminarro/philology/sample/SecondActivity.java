package com.jcminarro.philology.sample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jcminarro.philology.Philology;
import com.jcminarro.sample.R;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class SecondActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		Toast.makeText(this,R.string.hello_world,0).show();
	}
}
