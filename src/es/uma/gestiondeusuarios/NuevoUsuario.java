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

public class NuevoUsuario extends Activity {
	private Intent intentPrevio;
	private Usuario usuarioLogeado;
	private Rol rolUsuario;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_usuario);
		intentPrevio = getIntent();
		
        final BD bd = new BD(getApplicationContext());
		usuarioLogeado = new Usuario(bd.getReadableDatabase(),intentPrevio.getStringExtra("UsuarioLogeado"));
		
		if(!intentPrevio.hasExtra("Rol")){
			Intent intent = new Intent(getApplicationContext(), ListaUsuarios.class);
			intent.putExtra("UsuarioLogeado", usuarioLogeado.getUsername());
			startActivity(intent);
		}

		rolUsuario = new Rol(bd.getReadableDatabase(),intentPrevio.getIntExtra("Rol", 0));
		
		final EditText campoUsuario = (EditText) findViewById(R.id.campoGestionUsuario);
		final EditText campoPassword = (EditText) findViewById(R.id.campoGestionPassword);
		final EditText campoRol = (EditText) findViewById(R.id.campoGestionRol);
		final EditText campoNombre = (EditText) findViewById(R.id.campoGestionNombre);
		final EditText campoEmail = (EditText) findViewById(R.id.campoGestionEmail);
		final EditText campoTelefono = (EditText) findViewById(R.id.campoGestionTelefono);
		final Button botonGuardarCambios = (Button) findViewById(R.id.botonGuardarCambios);
		final Button botonBorrarUsuario = (Button) findViewById(R.id.botonBorrarUsuario);
		campoRol.setText(rolUsuario.getRolName());
		botonBorrarUsuario.setVisibility(View.INVISIBLE);
		
		botonGuardarCambios.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					 usuarioLogeado.addUsuario(bd.getWritableDatabase(), campoUsuario.getText().toString(), campoPassword.getText().toString(), 
							rolUsuario, campoEmail.getText().toString(), campoNombre.getText().toString(), campoTelefono.getText().toString());
				} catch (RuntimeException e) {
					Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(getApplicationContext(), ListaUsuarios.class);
					intent.putExtra("UsuarioLogeado", intentPrevio.getStringExtra("UsuarioLogeado"));
					startActivity(intent);
				}
				Toast.makeText(getApplicationContext(), "Usuario creado", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getApplicationContext(), ListaUsuarios.class);
				intent.putExtra("UsuarioLogeado", intentPrevio.getStringExtra("UsuarioLogeado"));
				startActivity(intent);
			}
		});
		
	}
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
	@Override
	public void onBackPressed(){
		Intent intent = new Intent(getApplicationContext(), ListaUsuarios.class);
		intent.putExtra("UsuarioLogeado", usuarioLogeado.getUsername());
		startActivity(intent);
	}
}
