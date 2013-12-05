package es.uma.gestiondeusuarios;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BD extends SQLiteOpenHelper {
	private static final int VERSION_DB = 1;
	private static final String NOMBRE_DB = "Usuarios";
	public static final String TABLA_ROLES = "tRoles";
	public static final String TABLA_USUARIOS = "tUsers";
	private static final String sqlCreate_Roles = 
			"CREATE TABLE IF NOT EXISTS " + TABLA_ROLES + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, rolName TEXT, rolDes TEXT);";
	private static final String sqlCreate_Usuarios = "CREATE TABLE IF NOT EXISTS " + TABLA_USUARIOS + " (usuario TEXT PRIMARY KEY NOT NULL, password TEXT, " +
			"rol INTEGER, email TEXT, nombre TEXT, tlfno TEXT, FOREIGN KEY(rol) REFERENCES " + TABLA_ROLES + "(ID));";
	public BD(Context context) {
		super(context, NOMBRE_DB, null, VERSION_DB);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sqlCreate_Roles);
		db.execSQL(sqlCreate_Usuarios);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_ROLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIOS);       
        onCreate(db);
	}

}
