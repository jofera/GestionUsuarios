package es.uma.gestiondeusuarios;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Clase que crea, inicializa y destruye la base de datos
public class BDUsuarios extends SQLiteOpenHelper {
	private static final int VERSION_DB = 1;
	private static final String NOMBRE_DB = "Usuarios";
	private static final String NOMBRE_TABLA = "tUsers";
	private static final String sqlCreate = "CREATE TABLE " + NOMBRE_TABLA + " (usuario TEXT PRIMARY KEY, password TEXT)";

	public BDUsuarios(Context context) {
		super(context, NOMBRE_DB, null, VERSION_DB);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(sqlCreate);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		//Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS " + NOMBRE_TABLA);
        //Se crea la nueva versión de la tabla
        onCreate(db);
	}
	
	public void addUsuario(String nombre, String password){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("INSERT INTO " + NOMBRE_TABLA + " (usuario, password) VALUES ('" + nombre + "', '" + password + "')");
		db.close();
	}
	
	public List<Object[]> obtenerUsuarios(){
		SQLiteDatabase db = this.getReadableDatabase();
        String consulta = "SELECT * FROM Usuarios";
		Cursor c = db.rawQuery(consulta, null);
		List<Object[]> listaUsuarios = new ArrayList<Object[]>();
		if(c.moveToFirst()){
			Object[] usuario;
			do{
				usuario = new Object[c.getColumnCount()];
				for(int i = 0;i<c.getColumnCount();i++){
					usuario[i] = c.getString(i);
				}
				listaUsuarios.add(usuario);
			}while(c.moveToNext());
		}
		db.close();
		return listaUsuarios;
	}
	
	public Object[] obtenerUsuario(String nombre){
		SQLiteDatabase db = this.getReadableDatabase();
		String consulta = "SELECT * FROM " + NOMBRE_TABLA + " WHERE usuario = '" + nombre + "';";
		Cursor c = db.rawQuery(consulta, null);
		Object[] usuario = null;
		List<Object[]> listaUsuarios = new ArrayList<Object[]>();
		if(c.moveToFirst()){
			usuario = new Object[c.getColumnCount()];
			int numFilas = c.getColumnCount();
			do{
				for(int i = 0;i<numFilas;i++){
					usuario[i] = c.getString(i);
				}
				listaUsuarios.add(usuario);
			}while(c.moveToNext());
		}
		return usuario;
	}
}
