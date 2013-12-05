package es.uma.gestiondeusuarios;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class GestionarUsuario extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gestionar_usuario);
		Intent intent = getIntent();
        final BD bd = new BD(getApplicationContext());
		final Usuarios usuarios = new Usuarios();
		final Roles roles = new Roles();
		Object[] usuario = usuarios.obtenerUsuario(bd.getReadableDatabase(),(String) intent.getStringExtra("Usuario"));
		
		final EditText campoUsuario = (EditText) findViewById(R.id.campoGestionUsuario);
		final EditText campoPassword = (EditText) findViewById(R.id.campoGestionPassword);
		final EditText campoRol = (EditText) findViewById(R.id.campoGestionRol);
		final EditText campoNombre = (EditText) findViewById(R.id.campoGestionNombre);
		final EditText campoEmail = (EditText) findViewById(R.id.campoGestionEmail);
		final EditText campoTelefono = (EditText) findViewById(R.id.campoGestionTelefono);
		
		campoUsuario.setText((String) usuario[0]);
		campoPassword.setText((String) usuario[1]);
		campoRol.setHint(roles.nombreRol(bd.getReadableDatabase(),Integer.valueOf(
				(intent.getStringExtra("Rol") != null) ? intent.getStringExtra("Rol") : (usuario[2].toString()))));
		campoNombre.setText((String) usuario[3]);
		campoEmail.setText((String) usuario[4]);
		campoTelefono.setText((String) usuario[5]);
		campoRol.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intentRol = new Intent(getApplicationContext(), SeleccionRol.class);
				intentRol.putExtra("Usuario",campoUsuario.getText().toString());
				startActivity(intentRol);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.gestionar_usuario, menu);
		return true;
	}

}
