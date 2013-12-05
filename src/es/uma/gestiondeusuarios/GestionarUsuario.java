package es.uma.gestiondeusuarios;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

public class GestionarUsuario extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gestionar_usuario);
		Intent intent = getIntent();
		// Aquí hay que llamar a una nueva clase donde se gestione el usuario
		Toast.makeText(getApplicationContext(), "Ha seleccionado " + (String) intent.getStringExtra("Usuario"), Toast.LENGTH_SHORT).show();
		final BDUsuarios bdUsuarios = new BDUsuarios(this.getApplicationContext());
		final BDRoles bdRoles = new BDRoles(this.getApplicationContext());
		Object[] usuario = bdUsuarios.obtenerUsuario((String) intent.getStringExtra("Usuario"));
		
		final EditText campoUsuario = (EditText) findViewById(R.id.campoGestionUsuario);
		final EditText campoPassword = (EditText) findViewById(R.id.campoGestionPassword);
		final EditText campoRol = (EditText) findViewById(R.id.campoGestionRol);
		final EditText campoNombre = (EditText) findViewById(R.id.campoGestionNombre);
		final EditText campoEmail = (EditText) findViewById(R.id.campoGestionEmail);
		final EditText campoTelefono = (EditText) findViewById(R.id.campoGestionTelefono);
		
		campoUsuario.setText((String) usuario[0]);
		campoPassword.setText((String) usuario[1]);
		campoRol.setText(bdRoles.nombreRol(Integer.valueOf((usuario[2].toString()))));
		campoNombre.setText((String) usuario[3]);
		campoEmail.setText((String) usuario[4]);
		campoTelefono.setText((String) usuario[5]);		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gestionar_usuario, menu);
		return true;
	}

}
