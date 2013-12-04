package es.uma.gestiondeusuarios;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
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
        
        //Creo una isntancia de BDUsuarios donde doy de alta la BD "DBusuarios"
        BDUsuarios bdUsuarios = new BDUsuarios(this, "BDUsuarios", null, 1);
        
        //Establezco permisos de s�lo lectura para el login
        SQLiteDatabase bd = bdUsuarios.getReadableDatabase();
        
        
        // C�digo para el bot�n de Login
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
					String consulta = "SELECT * FROM Usuarios WHERE Username = '" + campoUsuario.getText().toString() + "';";
					// Esto hay que cambiarlo por un Select Escalar (s�lo 1 usuario con mismo login por tabla)
					List<Object[]> usuarios = bdUsuarios.select(consulta);
				}
			}
		});
        
        // C�digo para el bot�n de Limpiar
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