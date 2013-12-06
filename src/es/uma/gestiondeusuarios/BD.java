package es.uma.gestiondeusuarios;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BD extends SQLiteOpenHelper {
	private static final int VERSION_DB = 1;
	private static final String NOMBRE_DB = "Usuarios";
	public static final String TABLA_ROLES = "tRoles";
	public static final String TABLA_USUARIOS = "tUsers";
	private static final String sqlCreate_Roles = 
			"CREATE TABLE IF NOT EXISTS " + TABLA_ROLES + " (ID INTEGER PRIMARY KEY, rolName TEXT, rolDes TEXT);";
	private static final String sqlCreate_Usuarios = "CREATE TABLE IF NOT EXISTS " + TABLA_USUARIOS + " (usuario TEXT PRIMARY KEY NOT NULL, password TEXT, " +
			"rol INTEGER, email TEXT, nombre TEXT, tlfno TEXT, FOREIGN KEY(rol) REFERENCES " + TABLA_ROLES + "(ID));";
	public BD(Context context) {
		super(context, NOMBRE_DB, null, VERSION_DB);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(sqlCreate_Roles);
			db.execSQL(sqlCreate_Usuarios);
			db.execSQL("INSERT INTO " + BD.TABLA_ROLES + " (ID, rolName,rolDes) VALUES ('" + Rol.ADMINISTRADOR + "', 'administrador', " +
					"'administrador del sistema')");
			db.execSQL("INSERT INTO " + BD.TABLA_ROLES + " (ID, rolName,rolDes) VALUES ('" + Rol.INVITADO + "', 'invitado', " +
					"'invitado del sistema')");
			db.execSQL("INSERT INTO " + BD.TABLA_ROLES + " (ID, rolName,rolDes) VALUES ('" + Rol.USUARIO + "', 'usuario', " +
					"'usuario del sistema')");
			db.execSQL("INSERT INTO " + BD.TABLA_USUARIOS + " (usuario, password, rol, email, nombre, tlfno) VALUES ('usuario', 'usuario', " +
					"'" + Rol.USUARIO + "', 'usuario@usuario.com', 'usuario', '111111')");
			db.execSQL("INSERT INTO " + BD.TABLA_USUARIOS + " (usuario, password, rol, email, nombre, tlfno) VALUES ('admin', 'admin', " +
					"'" + Rol.ADMINISTRADOR + "', 'admin@admin.com', 'admin', '000000')");
			db.execSQL("INSERT INTO " + BD.TABLA_USUARIOS + " (usuario, password, rol, email, nombre, tlfno) VALUES ('invitado', 'invitado', " +
					"'" + Rol.INVITADO + "', 'invitado@invitado.com', 'invitado', '222222')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_ROLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIOS);       
        onCreate(db);
	}

}
