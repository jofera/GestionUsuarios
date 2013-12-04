package es.uma.gestiondeusuarios;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

// Clase que crea, inicializa y destruye la base de datos
public class BDUsuarios extends SQLiteOpenHelper {
	private SQLiteDatabase db;
	private final String sqlCreate = "CREATE TABLE Usuarios (usuario TEXT, password TEXT)";

	public BDUsuarios(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(sqlCreate);
		db.execSQL("INSERT INTO Usuarios (usuario, password) " + "VALUES (admin, admin)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		//Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
	}
	
	public List<Object[]> select(String consulta){
		// La consulta rawQuery me devuelve un cursos a la base de datos
		Cursor c = db.rawQuery(consulta, null);
		List<Object[]> lista = new ArrayList<Object[]>();
		
		if(c.getCount() > 0){
			// La lista la he definido como array de objetos porque es como lo hemos visto en clase
			Object[] usuario = new Object[c.getColumnCount()];
			do{
				for(int i = 0;i<usuario.length;i++){
					usuario[i] = (Object) c.getString(i);
				}
				lista.add(usuario);
			}while(c.moveToNext());
		}
		return lista;
	}
}
