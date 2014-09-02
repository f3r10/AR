package com.fis.ra;

import com.fis.ra.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class PrincipalActivity extends Activity {
	private Button btnClick;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		btnClick = (Button)findViewById(R.id.btnCall);
		btnClick.setOnClickListener(new ButtonClickHandler());
		
	}
	
	public class ButtonClickHandler implements View.OnClickListener {
		//When button is clicked
		public void onClick(View view) {
		Intent intObj = new Intent(PrincipalActivity.this,MainActivity.class);
		startActivity(intObj);
		}
		}


	
}
