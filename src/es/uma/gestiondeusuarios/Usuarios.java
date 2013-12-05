package es.uma.gestiondeusuarios;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Usuarios {
	
	public void addUsuario(SQLiteDatabase db,String usuario, String password, int idRol, String email, String nombre, String telefono){
		try {
			db.execSQL("INSERT INTO " + BD.TABLA_USUARIOS + " (usuario, password, rol, email, nombre, tlfno) VALUES ('" + usuario + "', '" + password + "', " +
					"'" + idRol + "', '" + email + "', '" + nombre + "', '" + telefono + "')");
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Object[]> obtenerUsuarios(SQLiteDatabase db){
        String consulta = "SELECT * FROM " + BD.TABLA_USUARIOS;
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
	
	public Object[] obtenerUsuario(SQLiteDatabase db,String user){
		String consulta = "SELECT * FROM " + BD.TABLA_USUARIOS + " WHERE usuario = '" + user + "'";
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
	
	public boolean compruebaLogin(SQLiteDatabase db,String usuario, String password){
		boolean res = false;
		try {
			Cursor c = db.rawQuery("SELECT * FROM " + BD.TABLA_USUARIOS + " WHERE usuario = '" + usuario + "' AND password = '" + password + "'", null);
			res = (c.getCount() > 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
}
