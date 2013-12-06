package es.uma.gestiondeusuarios;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        findViewById(R.id.campoGestionUsuario).requestFocus();
        final BD bd = new BD(getApplicationContext());

        final Button botonLogin = (Button) findViewById(R.id.botonLogin);
        botonLogin.setOnClickListener(new View.OnClickListener() {

        	@Override
			public void onClick(View v) {
				final EditText campoUsuario = (EditText) findViewById(R.id.campoGestionUsuario);
				final EditText campoPassword = (EditText) findViewById(R.id.campoPassword);
				if((campoUsuario.getText().toString().isEmpty()) && 
						(campoPassword.getText().toString().isEmpty())){
					Toast.makeText(getApplicationContext(), R.string.ErrorLoginVacio, Toast.LENGTH_LONG).show();
				}else{
					if(Usuario.compruebaLogin(bd.getReadableDatabase(),(String) campoUsuario.getText().toString(), (String) campoPassword.getText().toString())){
						Toast.makeText(getApplicationContext(), getResources().getString(R.string.LoginCorrecto) + " " + campoUsuario.getText().toString(), Toast.LENGTH_SHORT).show();						
						Intent intent = new Intent(getApplicationContext(),ListaUsuarios.class);
						overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
						startActivity(intent);
					}else{
						Toast.makeText(getApplicationContext(), R.string.ErrorLoginIncorrecto, Toast.LENGTH_LONG).show();
					}
				}
			}
		});
        
        final Button botonLimpiar = (Button) findViewById(R.id.botonLimpiar);
        botonLimpiar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final EditText campoUsuario = (EditText) findViewById(R.id.campoGestionUsuario);
				final EditText campoPassword = (EditText) findViewById(R.id.campoPassword);
				campoUsuario.setText("");
				campoPassword.setText("");
		        findViewById(R.id.campoGestionUsuario).requestFocus();
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
