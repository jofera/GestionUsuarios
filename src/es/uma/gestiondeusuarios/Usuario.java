package es.uma.gestiondeusuarios;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class Usuario {
	

	private String username;
	private String pwd;
	private Rol rol;
	private String e_mail;
	private String nombre;
	private String tlfno;
	
	public Usuario(SQLiteDatabase db, String usuario){
		try {
			String consulta = "SELECT * FROM " + BD.TABLA_USUARIOS + " WHERE usuario = '" + usuario + "'";
			Cursor c = db.rawQuery(consulta, null);
			if(c.moveToFirst()){
				this.username = c.getString(c.getColumnIndex("usuario"));
				this.pwd = c.getString(c.getColumnIndex("password"));
				this.rol = new Rol(db,c.getInt((c.getColumnIndex("rol"))));
				this.e_mail = c.getString(c.getColumnIndex("email"));
				this.nombre = c.getString(c.getColumnIndex("nombre"));
				this.tlfno = c.getString(c.getColumnIndex("tlfno"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Rol getRol(){
		return this.rol;
	}
	
	// Convertir en un constructor
	public void addUsuario(SQLiteDatabase db, String usuario, String password, Rol rol, String email, String nombre, String telefono){
		if (this.rol.getID() != Rol.ADMINISTRADOR){
			throw new RuntimeException("Sólo los administradores pueden añadir usuarios");
		}else{
			try{
				db.execSQL("INSERT INTO " + BD.TABLA_USUARIOS + " (usuario, password, rol, email, nombre, tlfno) VALUES ('" + usuario + "', '" + password + "', " +
					"'" + rol.getID() + "', '" + email + "', '" + nombre + "', '" + telefono + "')");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void editarUsuario(SQLiteDatabase db, String usuario, String password, int idRol, String email, String nombre, String telefono){
		try {
			db.execSQL("UPDATE " + BD.TABLA_USUARIOS + " SET usuario = '" + usuario + "', password = '" + password + "', rol = '" + idRol + "', email = '" 
					+ email + "', nombre = '" + nombre + "', tlfno = '" + telefono + "' WHERE usuario = '" + this.getUsername() + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delUsuario(SQLiteDatabase db){
		try {
			db.execSQL("DELETE FROM " + BD.TABLA_USUARIOS + " WHERE usuario = '" + this.getUsername() + "'");
			this.e_mail = null;
			this.nombre = null;
			this.pwd = null;
			this.tlfno = null;
			this.username = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Usuario> listaUsuarios(SQLiteDatabase db){
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();		
        try {
			String consulta = "SELECT usuario FROM " + BD.TABLA_USUARIOS;
			Cursor c = db.rawQuery(consulta, null);
			if(c.moveToFirst()){
				Usuario usuario;
				do{
					usuario = new Usuario(db,c.getString(0));
					listaUsuarios.add(usuario);
				}while(c.moveToNext());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaUsuarios;
	}
	
	public static boolean compruebaLogin(SQLiteDatabase db,String usuario, String password){
		boolean res = false;
		try {
			Cursor c = db.rawQuery("SELECT * FROM " + BD.TABLA_USUARIOS + " WHERE usuario = '" + usuario + "' AND password = '" + password + "'", null);
			res = (c.getCount() > 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(SQLiteDatabase db,String username) {
		try {
			db.execSQL("UPDATE " + BD.TABLA_USUARIOS + " SET usuario = '" + username + "' WHERE usuario = '" + this.username + "'");
			this.username = username;
		} catch (SQLiteException e) {
			throw new RuntimeException("No se ha podido modificar el usuario.");
		}
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(SQLiteDatabase db,String pwd) {
		try {
			db.execSQL("UPDATE " + BD.TABLA_USUARIOS + " SET password = '" + pwd + "' WHERE usuario = '" + this.username + "'");
			this.pwd = pwd;
		} catch (SQLiteException e) {
			throw new RuntimeException("No se ha podido modificar la password.");
		}
	}

	public String getE_mail() {
		return e_mail;
	}

	public void setE_mail(SQLiteDatabase db,String e_mail) {
		try {
			db.execSQL("UPDATE " + BD.TABLA_USUARIOS + " SET email = '" + e_mail + "' WHERE usuario = '" + this.username + "'");
			this.e_mail = e_mail;
		} catch (SQLiteException e) {
			throw new RuntimeException("No se ha podido modificar el e-mail.");
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(SQLiteDatabase db,String nombre) {
		try {
			db.execSQL("UPDATE " + BD.TABLA_USUARIOS + " SET nombre = '" + nombre + "' WHERE usuario = '" + this.username + "'");
			this.nombre = nombre;
		} catch (SQLiteException e) {
			throw new RuntimeException("No se ha podido modificar el nombre.");
		}
	}

	public String getTlfno() {
		return tlfno;
	}

	public void setTlfno(SQLiteDatabase db,String tlfno) {
		try {
			db.execSQL("UPDATE " + BD.TABLA_USUARIOS + " SET tlfno = '" + tlfno + "' WHERE usuario = '" + this.username + "'");
			this.tlfno = tlfno;
		} catch (SQLiteException e) {
			throw new RuntimeException("No se ha podido modificar el telefono.");
		}
	}

	public void setRol(SQLiteDatabase db,Rol rol) {
		try {
			db.execSQL("UPDATE " + BD.TABLA_USUARIOS + " SET rol = '" + rol.getID() + "' WHERE usuario = '" + this.username + "'");
			this.rol = rol;
		} catch (SQLiteException e) {
			throw new RuntimeException("No se ha podido modificar el rol.");
		}
	}

}
