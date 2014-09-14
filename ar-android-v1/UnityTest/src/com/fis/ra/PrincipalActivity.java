package com.fis.ra;

import com.fis.ra.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PrincipalActivity extends Activity {
	private Button btnIniciar;
	private Button btnSalir;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		btnIniciar = (Button) findViewById(R.id.btnIniciar);
		btnIniciar.setOnClickListener(new IniciarAplicacion());
		btnSalir = (Button) findViewById(R.id.btnSalir);
		btnSalir.setOnClickListener(new Salir());

	}

	public class IniciarAplicacion implements View.OnClickListener {
		public void onClick(View view) {
			Intent intObj = new Intent(PrincipalActivity.this,
					MainActivity.class);
			startActivity(intObj);
		}
	}

	public class Salir implements View.OnClickListener {
		public void onClick(View view) {
			finish();
		}
	}

}
