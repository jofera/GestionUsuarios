package es.uma.gestiondeusuarios;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Roles{
	public void addRol(SQLiteDatabase db, int ID, String rolName, String rolDes){
		try {
			db.execSQL("INSERT INTO " + BD.TABLA_ROLES + " (ID, rolName, rolDes) VALUES ('" + ID + "', '" + rolName + "', " +
					"'" + rolDes + "')");
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Object[]> obtenerRoles(SQLiteDatabase db){
		String consulta = "SELECT * FROM " + BD.TABLA_ROLES;
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
	
	public Object[] obtenerRol(SQLiteDatabase db,int ID){
		String consulta = "SELECT * FROM " + BD.TABLA_ROLES + " WHERE ID = '" + ID + "'";
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
	
	public String nombreRol(SQLiteDatabase db,int ID){
		String consulta = "SELECT rolName FROM " + BD.TABLA_ROLES + " WHERE ID = '" + ID + "'";
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
