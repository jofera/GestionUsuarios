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
        final Usuarios usuarios = new Usuarios();
		
        List<Object[]> listaUsuarios = usuarios.obtenerUsuarios(bd.getReadableDatabase());
		
		for(Object[] usuario : listaUsuarios){
			final TextView usuarioTV = new TextView(this.getApplicationContext());
			usuarioTV.setTextSize((float) 32.0);
			usuarioTV.setText((String) usuario[0]);
			usuarioTV.setTag((String) usuario[0]);
			usuarioTV.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(), GestionarUsuario.class);
					intent.putExtra("Usuario", usuarioTV.getTag().toString());
					startActivity(intent);
				}
			});
			linearlayout.addView(usuarioTV);
		}
		TextView totalUsersTV = new TextView(this.getApplicationContext());
		totalUsersTV.setTextSize((float) 25.0);
		totalUsersTV.setText("Hay un total de " + listaUsuarios.size() + " usuarios");
		linearlayout.addView(totalUsersTV);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lista_usuarios, menu);
		return true;
	}

}
