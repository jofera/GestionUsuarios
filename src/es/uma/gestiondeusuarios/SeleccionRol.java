package es.uma.gestiondeusuarios;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SeleccionRol extends Activity {
	
	private Intent intentPrevio;
	private Usuario usuarioLogeado = null;
	private Usuario UsuarioGestionar = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seleccion_rol);
		LinearLayout linearlayout = (LinearLayout) findViewById(R.id.RolesLinearLayout);
		final BD bd = new BD(getApplicationContext());
        final List<Rol> listaRoles = Rol.listaRoles(bd.getReadableDatabase());
		intentPrevio = getIntent();
		if(intentPrevio.hasExtra("Usuario")){
			UsuarioGestionar = new Usuario(bd.getReadableDatabase(), intentPrevio.getStringExtra("Usuario"));
		}
		usuarioLogeado = new Usuario(bd.getReadableDatabase(), intentPrevio.getStringExtra("UsuarioLogeado"));
		for(Rol rol : listaRoles){
			final TextView rolTV = new TextView(this.getApplicationContext());
			rolTV.setTextSize((float) 32.0);
			rolTV.setText(rol.getRolName());
			rolTV.setTag(rol.getID());
			rolTV.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent;
					if(UsuarioGestionar != null){
						UsuarioGestionar.setRol(bd.getWritableDatabase(), new Rol(bd.getReadableDatabase(),(Integer)rolTV.getTag()));
						Toast.makeText(getApplicationContext(), "Se ha cambiado el rol", Toast.LENGTH_SHORT).show();
						intent = new Intent(getApplicationContext(), GestionarUsuario.class);
						intent.putExtra("Usuario", UsuarioGestionar.getUsername());
					}else{
						intent = new Intent(getApplicationContext(), NuevoUsuario.class);
						intent.putExtra("Rol", (Integer) rolTV.getTag());
					}
					intent.putExtra("UsuarioLogeado", usuarioLogeado.getUsername());
					startActivity(intent);
				}
			});				
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
		Intent intent;
		if(UsuarioGestionar != null){
			intent = new Intent(getApplicationContext(), GestionarUsuario.class);
			intent.putExtra("Usuario", UsuarioGestionar.getUsername());
		}else{
			intent = new Intent(getApplicationContext(), ListaUsuarios.class);
		}
		intent.putExtra("UsuarioLogeado", intentPrevio.getStringExtra("UsuarioLogeado"));
		startActivity(intent);
	}
}
