package es.uma.gestiondeusuarios;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

// Clase que crea, inicializa y destruye la base de datos
public class BDUsuarios extends SQLiteOpenHelper {
	private final String sqlCreate = "CREATE TABLE Usuarios (usuario TEXT, password TEXT)";

	public BDUsuarios(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(sqlCreate);
		db.execSQL("INSERT INTO Usuarios (usuario, password) VALUES ('admin', 'admin')");
		db.execSQL("INSERT INTO Usuarios (usuario, password) VALUES ('usuario', 'usuario')");
		db.execSQL("INSERT INTO Usuarios (usuario, password) VALUES ('invitado', 'invitado')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		//Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
	}
}
