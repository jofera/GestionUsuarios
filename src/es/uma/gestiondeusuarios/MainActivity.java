package es.uma.gestiondeusuarios;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
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
        final BDUsuarios bdUsuarios = new BDUsuarios(this, "BDUsuarios", null, 1);
        
        //Establezco permisos de sólo lectura para el login
        final SQLiteDatabase bd = bdUsuarios.getReadableDatabase();
        
        
        // Código para el botón de Login
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
					String consulta = "SELECT * FROM Usuarios WHERE usuario = '" + campoUsuario.getText().toString() + "';";
					// Esto hay que cambiarlo por un Select Escalar (sólo 1 usuario con mismo login por tabla)
					//List<Object[]> listaUsuarios = bd.select(consulta);
					
					Cursor c = bd.rawQuery(consulta, null);
					List<Object[]> listaUsuarios = new ArrayList<Object[]>();
					if(c.moveToFirst()){
						// La lista la he definido como array de objetos porque es como lo hemos visto en clase
						int numFilas = c.getColumnCount();
						Object[] usuario = new Object[numFilas];
						do{
							for(int i = 0;i<numFilas;i++){
								usuario[i] = c.getString(i);
							}
							listaUsuarios.add(usuario);
						}while(c.moveToNext());
					}
					
					if(listaUsuarios.size() > 0){
						// Pasar a la siguiente ventana
						Toast.makeText(getApplicationContext(), R.string.LoginCorrecto, Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(getApplicationContext(), R.string.ErrorLoginIncorrecto, Toast.LENGTH_LONG).show();
					}
				}
			}
		});
        
        // Código para el botón de Limpiar
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
