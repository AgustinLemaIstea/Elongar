package com.istea.agustinlema.elongar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME="Elongar";
    static final int DB_VERSION=1;

    static final String TABLA="Elongar";
    static final String ID="id";
    static final String FECHA="fecha";
    static final String HORA="hora";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    /***
     * Se ejecuta cada vez que se instancia el DBHelper.
     * Sólo si la DB no estaba creada anteriormente.!!!
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists " + TABLA+ " (id integer primary key, "
                + FECHA + " text, "
                + HORA + " text )");
    }

    /***
     * Se ejecuta cuando se hace una modificación en la estructura de la base
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        onCreate(sqLiteDatabase);
    }

    public boolean insertarElongacion(EventoElongacion evtElongacion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(FECHA, evtElongacion.getFecha());
            contentValues.put(HORA, evtElongacion.getHora());
            db.insert(TABLA, null, contentValues);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
/*
    public boolean actualizarUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(APELLIDO_USUARIO, usuario.getApellido());
            db.update(TABLA_USUARIOS, contentValues, "id = ?", new String[]{String.valueOf(usuario.getId())});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }*/

    /*public boolean borrarUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLA_USUARIOS, "id = ?", new String[]{String.valueOf(usuario.getId())});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }*/

    public ArrayList<EventoElongacion> obtenerElongaciones() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<EventoElongacion> evtsElongacion = new ArrayList<>();
        Cursor cur = db.rawQuery("select * from " + TABLA, null);
        cur.moveToFirst();
        //Recorro con un cursor toda la DB
        try {
            while (!cur.isAfterLast()) {
                int id = cur.getInt(cur.getColumnIndex("id")); //Get an INT from column id
                String fecha = cur.getString(cur.getColumnIndex(FECHA));
                String hora =  cur.getString(cur.getColumnIndex(HORA));

                EventoElongacion evtElongacion = new EventoElongacion(fecha, hora);

//                usuario.setId(cur.getInt(cur.getColumnIndex("id")));
//                usuario.setNombre(cur.getString(cur.getColumnIndex(NOMBRE_USUARIO)));
//                usuario.setApellido(cur.getString(cur.getColumnIndex(APELLIDO_USUARIO)));
//                usuario.setEmail(cur.getString(cur.getColumnIndex(EMAIL_USUARIO)));

                evtsElongacion.add(evtElongacion);
                cur.moveToNext();
            }
            return evtsElongacion;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
