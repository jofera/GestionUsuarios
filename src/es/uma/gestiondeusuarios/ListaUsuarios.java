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

public class ListaUsuarios extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_usuarios);
		
		LinearLayout linearlayout = (LinearLayout) findViewById(R.id.linearlayout);
		
        final BD bd = new BD(getApplicationContext());
        final Usuario UsuarioLogeado = new Usuario(bd.getReadableDatabase(), getIntent().getStringExtra("UsuarioLogeado"));
        final List<Usuario> listaUsuarios = Usuario.listaUsuarios(bd.getReadableDatabase());
		
		if (UsuarioLogeado.getRol().getID() == Rol.ADMINISTRADOR) {
			TextView agregarUserTV = new TextView(getApplicationContext());
			agregarUserTV.setTextSize((float) 25.0);
			agregarUserTV.setTextColor(getResources().getColor(
					android.R.color.black));
			agregarUserTV.setText("Agregar un usuario");
			agregarUserTV.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(), SeleccionRol.class);
					intent.putExtra("UsuarioLogeado", UsuarioLogeado.getUsername());
					startActivity(intent);
				}
			});
			linearlayout.addView(agregarUserTV);
		}
		
		for(Usuario usuario : listaUsuarios){
			final TextView usuarioTV = new TextView(getApplicationContext());
			usuarioTV.setTextSize((float) 32.0);
			usuarioTV.setText(usuario.getUsername());
			usuarioTV.setTag(usuario.getUsername());
			usuarioTV.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(), GestionarUsuario.class);
					intent.putExtra("UsuarioLogeado", UsuarioLogeado.getUsername());
					intent.putExtra("Usuario", usuarioTV.getTag().toString());
					startActivity(intent);
				}
			});
			linearlayout.addView(usuarioTV);
		}
		
		TextView totalUsersTV = new TextView(getApplicationContext());
		totalUsersTV.setTextSize((float) 25.0);
		totalUsersTV.setText("Hay un total de " + listaUsuarios.size() + " usuarios");
		linearlayout.addView(totalUsersTV);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lista_usuarios, menu);
		return true;
	}
	
	@Override
	public void onBackPressed(){
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(intent);
	}

}
