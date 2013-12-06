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
import android.widget.Toast;

public class SeleccionRol extends Activity {
	
	private Intent intentPrevio;
	private Usuario usuario;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seleccion_rol);
		LinearLayout linearlayout = (LinearLayout) findViewById(R.id.RolesLinearLayout);
		
		intentPrevio = getIntent();
		final BD bd = new BD(getApplicationContext());
        final List<Rol> listaRoles = Rol.listaRoles(bd.getReadableDatabase());
		usuario = new Usuario(bd.getReadableDatabase(), intentPrevio.getStringExtra("Usuario"));
		for(Rol rol : listaRoles){
			final TextView rolTV = new TextView(this.getApplicationContext());
			rolTV.setTextSize((float) 32.0);
			rolTV.setText(rol.getRolName());
			rolTV.setTag(rol.getID());
			Usuario usuarioProv = new Usuario(bd.getWritableDatabase(), intentPrevio.getStringExtra("UsuarioLogeado"));
			if(usuarioProv.getRol().getID() == Rol.ADMINISTRADOR){
				rolTV.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						usuario.setRol(bd.getWritableDatabase(), new Rol(bd.getReadableDatabase(),(Integer)rolTV.getTag()));
						Toast.makeText(getApplicationContext(), "Se ha cambiado el rol", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(getApplicationContext(), GestionarUsuario.class);
						intent.putExtra("Usuario", intentPrevio.getStringExtra("Usuario"));
						intent.putExtra("UsuarioLogeado", intentPrevio.getStringExtra("UsuarioLogeado"));
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
		intent.putExtra("UsuarioLogeado", intentPrevio.getStringExtra("UsuarioLogeado"));
		intent.putExtra("Usuario", intentPrevio.getStringExtra("Usuario"));
		startActivity(intent);
	}
}
