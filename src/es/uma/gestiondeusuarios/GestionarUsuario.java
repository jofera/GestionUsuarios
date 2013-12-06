package es.uma.gestiondeusuarios;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GestionarUsuario extends Activity {
	private Intent intentPrevio;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gestionar_usuario);
		Intent intent = getIntent();
		intentPrevio = getIntent();
		
        final BD bd = new BD(getApplicationContext());
		final Usuario usuarioLogeado = new Usuario(bd.getReadableDatabase(),intent.getStringExtra("UsuarioLogeado"));
		final Usuario usuario = new Usuario(bd.getReadableDatabase(),intent.getStringExtra("Usuario"));
		
		final EditText campoUsuario = (EditText) findViewById(R.id.campoGestionUsuario);
		final EditText campoPassword = (EditText) findViewById(R.id.campoGestionPassword);
		final EditText campoRol = (EditText) findViewById(R.id.campoGestionRol);
		final EditText campoNombre = (EditText) findViewById(R.id.campoGestionNombre);
		final EditText campoEmail = (EditText) findViewById(R.id.campoGestionEmail);
		final EditText campoTelefono = (EditText) findViewById(R.id.campoGestionTelefono);
		@SuppressWarnings("unused")
		final Button botonGuardarCambios = (Button) findViewById(R.id.botonGuardarCambios);
		@SuppressWarnings("unused")
		final Button botonBorrarUsuario = (Button) findViewById(R.id.botonBorrarUsuario);

		
		campoUsuario.setText(usuario.getUsername());
		campoPassword.setText(usuario.getPwd());
		Rol rolUsuario = usuario.getRol();
		campoRol.setHint(rolUsuario.getRolName());
		campoEmail.setText(usuario.getE_mail());
		campoNombre.setText(usuario.getNombre());
		campoTelefono.setText(usuario.getTlfno());
		campoRol.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(usuarioLogeado.getRol().getID() == Rol.ADMINISTRADOR){
					Intent intentRol = new Intent(getApplicationContext(), SeleccionRol.class);
					intentRol.putExtra("UsuarioLogeado", intentPrevio.getStringExtra("UsuarioLogeado"));
					intentRol.putExtra("Usuario",campoUsuario.getText().toString());
					startActivity(intentRol);
				}else{
					Toast.makeText(getApplicationContext(), "Necesita ser administrador", Toast.LENGTH_SHORT).show();
				}
			}
		});
		/*
		botonGuardarCambios.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					usuario.setUsername(bd.getWritableDatabase(), campoUsuario.getText().toString());
					usuario.setPwd(bd.getWritableDatabase(), campoPassword.getText().toString());
					if(usuario.getRol().getID() == Rol.ADMINISTRADOR){
						usuario.setRol(bd.getWritableDatabase(), usuario.getRol());
					}
					usuario.setE_mail(bd.getWritableDatabase(), campoEmail.getText().toString());
					usuario.setUsername(bd.getWritableDatabase(), campoUsuario.getText().toString());
					usuario.setTlfno(bd.getWritableDatabase(), campoTelefono.getText().toString());
				} catch (RuntimeException e) {
					Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(getApplicationContext(), ListaUsuarios.class);
					startActivity(intent);
				}
				Toast.makeText(getApplicationContext(), "Cambios guardados", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getApplicationContext(), ListaUsuarios.class);
				startActivity(intent);
			}
		});
		*/
		/*
		botonBorrarUsuario.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.gestionar_usuario, menu);
		return true;
	}
	
	@Override
	public void onBackPressed(){
		Intent intent = new Intent(getApplicationContext(), ListaUsuarios.class);
		intent.putExtra("UsuarioLogeado", intentPrevio.getStringExtra("UsuarioLogeado"));
		startActivity(intent);
	}

}
