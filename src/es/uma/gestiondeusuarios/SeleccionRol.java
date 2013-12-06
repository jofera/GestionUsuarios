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
	
	private String usuarioProviene;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seleccion_rol);
		LinearLayout linearlayout = (LinearLayout) findViewById(R.id.RolesLinearLayout);
		
		final Intent intentRecib = getIntent();
		usuarioProviene = (String) intentRecib.getStringExtra("Usuario");
		final BD bd = new BD(getApplicationContext());
        final List<Rol> listaRoles = Rol.listaRoles(bd.getReadableDatabase());
		
		for(Rol rol : listaRoles){
			final TextView rolTV = new TextView(this.getApplicationContext());
			rolTV.setTextSize((float) 32.0);
			rolTV.setText(rol.getRolName());
			rolTV.setTag(rol.getID());
			Usuario usuarioProv = new Usuario(bd.getWritableDatabase(), usuarioProviene);
			if(usuarioProv.getRol().getID() == Rol.ADMINISTRADOR){
				rolTV.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getApplicationContext(), GestionarUsuario.class);
						intent.putExtra("Usuario", usuarioProviene);
						intent.putExtra("Rol", rolTV.getTag().toString());
						startActivity(intent);
					}
				});
			}
			linearlayout.addView(rolTV);
		}
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.seleccion_rol, menu);
		return true;
	}
	
	@Override
	public void onBackPressed(){
		Intent intent = new Intent(getApplicationContext(), GestionarUsuario.class);
		intent.putExtra("Usuario", usuarioProviene);
		startActivity(intent);
	}
}
