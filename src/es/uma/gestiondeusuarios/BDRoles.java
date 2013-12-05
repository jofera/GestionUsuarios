package es.uma.gestiondeusuarios;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDRoles extends SQLiteOpenHelper {
	private static final int VERSION_DB = 1;
	private static final String NOMBRE_DB = "Usuarios";
	private static final String NOMBRE_TABLA = "tRoles";
	private static final String sqlCreate = "CREATE TABLE " + NOMBRE_TABLA + " (ID INTEGER PRIMARY KEY, rolName TEXT, " +
			"rolDes TEXT)";

	public BDRoles(Context context) {
		super(context, NOMBRE_DB, null, VERSION_DB);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sqlCreate);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NOMBRE_TABLA);
        onCreate(db);
	}
	
	public void addRol(int ID, String rolName, String rolDes){
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL("INSERT INTO " + NOMBRE_TABLA + " (ID, rolName, rolDes) VALUES ('" + ID + "', '" + rolName + "', " +
					"'" + rolDes + "')");
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Object[]> obtenerRoles(){
		SQLiteDatabase db = this.getReadableDatabase();
        String consulta = "SELECT * FROM " + NOMBRE_TABLA;
		Cursor c = db.rawQuery(consulta, null);
		List<Object[]> listaRoles = new ArrayList<Object[]>();
		if(c.moveToFirst()){
			Object[] rol;
			do{
				rol = new Object[c.getColumnCount()];
				for(int i = 0;i<c.getColumnCount();i++){
					rol[i] = c.getString(i);
				}
				listaRoles.add(rol);
			}while(c.moveToNext());
		}
		db.close();
		return listaRoles;
	}
	
	public Object[] obtenerRol(int ID){
		SQLiteDatabase db = this.getReadableDatabase();
		String consulta = "SELECT * FROM " + NOMBRE_TABLA + " WHERE ID = '" + ID + "'";
		Cursor c = db.rawQuery(consulta, null);
		Object[] rol = null;
		if(c.moveToFirst()){
			rol = new Object[c.getColumnCount()];
			int numFilas = c.getColumnCount();
			for(int i = 0;i<numFilas;i++){
				rol[i] = c.getString(i);
			}
		}
		return rol;
	}
	
	public String nombreRol(int ID){
		SQLiteDatabase db = this.getReadableDatabase();
		String consulta = "SELECT rolName FROM " + NOMBRE_TABLA + " WHERE ID = '" + ID + "'";
		Cursor c = db.rawQuery(consulta, null);
		Object[] rol = null;
		if(c.moveToFirst()){
			rol = new Object[c.getColumnCount()];
			int numFilas = c.getColumnCount();
			for(int i = 0;i<numFilas;i++){
				rol[i] = c.getString(i);
			}
		}
		return rol[0].toString();
	}
}
