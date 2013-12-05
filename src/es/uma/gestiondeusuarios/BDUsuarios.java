package es.uma.gestiondeusuarios;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDUsuarios extends SQLiteOpenHelper {
	private static final int VERSION_DB = 1;
	private static final String NOMBRE_DB = "Usuarios";
	private static final String NOMBRE_TABLA = "tUsers";
	private static final String sqlCreate = "CREATE TABLE " + NOMBRE_TABLA + " (usuario TEXT PRIMARY KEY, password TEXT, " +
			"rol INTEGER REFERENCES tRoles(ID), e_mail TEXT, nombre TEXT, tlfno TEXT)";

	public BDUsuarios(Context context) {
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
	
	public void addUsuario(String usuario, String password, int idRol, String email, String nombre, String telefono){
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL("INSERT INTO " + NOMBRE_TABLA + " (usuario, password, rol, e_mail, nombre, tlfno) VALUES ('" + usuario + "', '" + password + "', " +
					"'" + idRol + "', '" + email + "', '" + nombre + "', '" + telefono + "')");
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Object[]> obtenerUsuarios(){
		SQLiteDatabase db = this.getReadableDatabase();
        String consulta = "SELECT * FROM " + NOMBRE_TABLA;
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
		String consulta = "SELECT * FROM " + NOMBRE_TABLA + " WHERE usuario = '" + nombre + "'";
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
	
	public boolean compruebaLogin(String usuario, String password){
		boolean res = false;
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor c = db.rawQuery("SELECT * FROM " + NOMBRE_TABLA + " WHERE usuario = '" + usuario + "' AND password = '" + password + "'", null);
			res = (c.getCount() > 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
}
