package es.uma.gestiondeusuarios;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListaUsuarios extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_usuarios);
		LinearLayout linearlayout = (LinearLayout) findViewById(R.id.linearlayout);
		
        final BDUsuarios bdUsuarios = new BDUsuarios(this, "BDUsuarios", null, 1);
        final SQLiteDatabase bd = bdUsuarios.getReadableDatabase();
		
        String consulta = "SELECT * FROM Usuarios;";
		Cursor c = bd.rawQuery(consulta, null);
		List<Object[]> listaUsuarios = new ArrayList<Object[]>();
		if(c.moveToFirst()){
			int numFilas = c.getColumnCount();
			Object[] usuario = new Object[numFilas];
			do{
				for(int i = 0;i<numFilas;i++){
					usuario[i] = c.getString(i);
				}
				listaUsuarios.add(usuario);
			}while(c.moveToNext());
		}
		
		for(Object[] usuario : listaUsuarios){
			TextView usuarioTV = new TextView(getApplicationContext());
			usuarioTV.setTextSize((float) 32.0);
			usuarioTV.setText((String) usuario[0]);
			linearlayout.addView(usuarioTV);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_usuarios, menu);
		return true;
	}

}
