package es.uma.gestiondeusuarios;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Rol{
	
	public static final int INVITADO = -1;
	public static final int ADMINISTRADOR = 0;
	public static final int USUARIO = 1;
	
	private int id;
	private String rolName;
	private String rolDes;	
	
	public Rol(SQLiteDatabase db, int ID){
		try {
			String consulta = "SELECT * FROM " + BD.TABLA_ROLES + " WHERE ID = '" + ID + "'";
			Cursor c = db.rawQuery(consulta, null);
			if(c.moveToFirst()){
				this.id = c.getInt(c.getColumnIndex("ID"));
				this.rolName = c.getString(c.getColumnIndex("rolName"));
				this.rolDes = c.getString(c.getColumnIndex("rolDes"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Rol(SQLiteDatabase db, int ID, String rn, String rd){
		try {
			String consulta = "INSERT INTO " + BD.TABLA_ROLES + " (ID, rolName, rolDes) VALUES('" + ID + "', '" + rn + "', '" + rd + "')";
			db.execSQL(consulta);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addRol(SQLiteDatabase db, int ID, String rolName, String rolDes){
		try {
			String consulta = "INSERT INTO " + BD.TABLA_ROLES + " (ID, rolName, rolDes) VALUES ('" + ID + "', '" + rolName + "', " +
					"'" + rolDes + "')";
			db.execSQL(consulta);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Rol> listaRoles(SQLiteDatabase db){
		List<Rol> listaRoles = new ArrayList<Rol>();
		try {
			String consulta = "SELECT ID FROM " + BD.TABLA_ROLES;
			Cursor c = db.rawQuery(consulta, null);
			if(c.moveToFirst()){
				Rol rol;
				do{
					rol = new Rol(db,c.getInt(0));
					listaRoles.add(rol);
				}while(c.moveToNext());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaRoles;
	}
	
	public int getID() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRolName() {
		return rolName;
	}

	public void setRolName(String rolName) {
		this.rolName = rolName;
	}

	public String getRolDes() {
		return rolDes;
	}

	public void setRolDes(String rolDes) {
		this.rolDes = rolDes;
	}
}
