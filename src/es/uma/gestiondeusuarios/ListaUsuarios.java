package es.uma.gestiondeusuarios;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ListaUsuarios extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_usuarios);
		LinearLayout linearlayout = (LinearLayout) findViewById(R.id.linearlayout);
		
        final BDUsuarios bdUsuarios = new BDUsuarios(this.getApplicationContext());
		
        List<Object[]> listaUsuarios = bdUsuarios.obtenerUsuarios();
		
		for(Object[] usuario : listaUsuarios){
			final TextView usuarioTV = new TextView(this.getApplicationContext());
			usuarioTV.setTextSize((float) 32.0);
			usuarioTV.setText((String) usuario[0]);
			usuarioTV.setTag((String) usuario[0]);
			usuarioTV.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// Aquí hay que llamar a una nueva clase donde se gestione el usuario
					Toast.makeText(getApplicationContext(), "Ha seleccionado " + usuarioTV.getTag(), Toast.LENGTH_SHORT).show();
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
