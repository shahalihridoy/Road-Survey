package io.github.shahalihridoy.roadsurvey;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Database extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/io.github.shahalihridoy.roadsurvey/databases/";
    private static String DB_NAME = "SurveyDatabase";
    private SQLiteDatabase database;
    private final Context context;

    public Database(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists road_list(rcode varchar, rlength varchar, rid varchar primary key)");
        db.execSQL("create table if not exists road_survey(logcode varchar, logvalue varchar,rwidth varchar, rlength varchar, logtime varchar, gps varchar, logdate varchar, pointgps varchar, rid varchar references road_list(rid) on update cascade )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS call_history");
        onCreate(db);
    }

    @Override
    public synchronized void close() {
        if (database!=null)
            database.close();
        super.close();
    }

    public void insertToRoadList(String rcode, String rlength, String rid)
    {
        SQLiteDatabase sdb=this.getWritableDatabase();
        sdb.execSQL("insert into road_list values('"+rcode+"','"+rlength+"','"+rid+"')");
    }

    public void insertToRoadSurvey(String rid, String code, String value, String location, String time, String date){
        SQLiteDatabase sdb=this.getWritableDatabase();
        sdb.execSQL("insert into road_survey(rid,logcode,logvalue,logtime,gps,logdate) values('"+rid+"','"+code+"','"+value+"','"+time+"','"+location+"','"+date+"')");
    }

    public Cursor getRoadList()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from road_list", null);
        return c;
    }

    public Cursor getRoadSurvey()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select road_list.rid,road_list.rcode,road_list.rlength,road_survey.logcode,road_survey.logvalue,road_survey.logtime,road_survey.gps,road_survey.logdate from road_list,road_survey where road_survey.rid like road_list.rid order by road_survey.logdate,road_survey.logtime", null);
        return c;
    }

    public Cursor runCustomQuery(String query){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        return c;
    }

    public void deletes(String table,String col, String condition){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+table+" where "+col+" like '"+condition+"'");
    }
}


//
//
//
//
//
//
//    public void createDatabase() {
//        boolean dbExist = checkDatabase();
//
//        if (!dbExist) {
//            this.getReadableDatabase();
//            try {
//                copyDatabase();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void copyDatabase() {
//
//        try{
//            InputStream inputStream = context.getAssets().open(DB_NAME);
//            String outFileName = DB_PATH+DB_NAME;
//            OutputStream outputStream = new FileOutputStream(outFileName);
//
//            byte[] buffer = new byte[1024];
//            int length;
//
//            while ((length = inputStream.read(buffer))>0){
//                outputStream.write(buffer,0,length);
//            }
//
//            outputStream.flush();
//            outputStream.close();
//            inputStream.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void openDatabase(){
//        String myPath = DB_PATH+DB_NAME;
//        database = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);
//    }
//    private boolean checkDatabase() {
//        SQLiteDatabase checkDB = null;
//
//        try {
//            String myPath = DB_PATH + DB_NAME;
//            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (checkDB != null)
//            return true;
//        else return false;
//    }

