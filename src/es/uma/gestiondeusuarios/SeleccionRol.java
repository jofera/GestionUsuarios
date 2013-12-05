package es.uma.gestiondeusuarios;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SeleccionRol extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seleccion_rol);
		LinearLayout linearlayout = (LinearLayout) findViewById(R.id.RolesLinearLayout);
		
		final Intent intentRecib = getIntent();
        final BD bd = new BD(getApplicationContext());
        final Roles roles = new Roles();

        List<Object[]> listaRoles = roles.obtenerRoles(bd.getReadableDatabase());
		
		for(Object[] rol : listaRoles){
			final TextView rolTV = new TextView(this.getApplicationContext());
			rolTV.setTextSize((float) 32.0);
			rolTV.setText((String) rol[1]);
			rolTV.setTag((String) rol[0]);
			rolTV.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(), GestionarUsuario.class);
					intent.putExtra("Usuario", (String) intentRecib.getStringExtra("Usuario"));
					intent.putExtra("Rol", rolTV.getTag().toString());
					startActivity(intent);
				}
			});
			linearlayout.addView(rolTV);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.seleccion_rol, menu);
		return true;
	}

}
