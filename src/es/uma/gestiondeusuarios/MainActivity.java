package es.uma.gestiondeusuarios;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
        
        final BDUsuarios bdUsuarios = new BDUsuarios(this);
        bdUsuarios.addUsuario("admin", "admin");
        bdUsuarios.addUsuario("usuario", "usuario");
		bdUsuarios.addUsuario("invitado", "invitado");
		Log.d("Debug", "Se han insertado los usuarios");
        final Button botonLogin = (Button) findViewById(R.id.botonLogin);
        botonLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final EditText campoUsuario = (EditText) findViewById(R.id.campoUsuario);
				final EditText campoPassword = (EditText) findViewById(R.id.campoPassword);
				if((campoUsuario.getText().toString().isEmpty()) && 
						(campoPassword.getText().toString().isEmpty())){
					Toast.makeText(getApplicationContext(), R.string.ErrorLoginVacio, Toast.LENGTH_LONG).show();
				}else{
					Log.d("Debug", "Se va a comprobar el login");
					if(bdUsuarios.compruebaLogin((String) campoUsuario.getText().toString(), (String) campoPassword.getText().toString())){
						Toast.makeText(getApplicationContext(), R.string.LoginCorrecto, Toast.LENGTH_LONG).show();						
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
				// TODO Auto-generated method stub
				final EditText campoUsuario = (EditText) findViewById(R.id.campoUsuario);
				final EditText campoPassword = (EditText) findViewById(R.id.campoPassword);
				campoUsuario.setText("");
				campoPassword.setText("");
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
