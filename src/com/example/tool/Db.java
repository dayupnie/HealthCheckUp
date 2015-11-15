package com.example.tool;

import come.example.domain.DPB;
import come.example.domain.Imperance;
import come.example.domain.Pulse;
import come.example.domain.SBP;
import come.example.domain.User;
import come.example.domain.Weight;

import android.content.Context;
import android.database.Cursor;
//import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Db extends SQLiteOpenHelper {

	private static final String DB_NAME ="database.db";
	private static final int VERSION = 1;
	
	public static final String Usertab="user";
	public static final String Imperancetab="imperance";
	public static final String Weighttab="weight";
	public static final String DPBtab="DPB";
	public static final String SBPtab="SBP";
	public static final String Pulsetab="pulse";
	
	//�û���
	private static final String User_Table ="CREATE TABLE IF NOT EXISTS "+Usertab+
				"(UserId INTEGER PRIMARY KEY AUTOINCREMENT," +
				"UserName verchar(40)," +
				"UserPassword verchar(50)," +
				"Sex verchar(10)," +
				"Birth verchar(40)," +
				"Height double)";
	//�迹��
	private static final String Imperance_Table="CREATE TABLE IF NOT EXISTS " +
			Imperancetab +
			"(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
			"UserId INTEGER," +
			"Time verchar(40)," +
			"Imperance double)";
	//���ر�
	private static final String Weight_Table="CREATE TABLE IF NOT EXISTS " +
			Weighttab+
			"(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
			"UserId INTEGER," +
			"Time verchar(40)," +
			"Weight DOUBLE)";
	//����ѹ��
	private static final String DPB_Table="CREATE TABLE IF NOT EXISTS " +
			DPBtab +
			"(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
			"UserId INTEGER," +
			"Time verchar(40)," +
			"DPB DOUBLE)";
	//����ѹ��
	private static final String SBP_Table="CREATE TABLE IF NOT EXISTS " +
			SBPtab +
			"(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
			"UserId INTEGER," +
			"Time verchar(40)," +
			"SBP DOUBLE)";
	//������
	private static final String Pulse_Table="CREATE TABLE IF NOT EXISTS " +
			Pulsetab +
			"(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
			"UserId INTEGER," +
			"Time verchar(40)," +
			"Pulse DOUBLE)";
	

	
	public Db(Context context) {
		super(context, DB_NAME, null, VERSION);
		
		// TODO Auto-generated constructor stub
		
		
	}

private SQLiteDatabase getDB(){
	SQLiteDatabase db =getWritableDatabase();
	if(db == null){
		throw new RuntimeException("��ʼ�����ݿ�ʧ��");
	}
	return db;	
}
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		
		db.execSQL(User_Table);//�����û���
		db.execSQL(Imperance_Table);//�����迹��
		db.execSQL(Weight_Table);//�������ر�
		db.execSQL(DPB_Table);//��������ѹ��
		db.execSQL(SBP_Table);//��������ѹ��
		db.execSQL(Pulse_Table);//����������
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//���±�
		Log.w("LOG_TAG", "Upgrading database from version" +oldVersion+
				" to " +newVersion+
				",which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " +
				"User.Table");
		db.execSQL("DROP TABLE IF EXISTS " +
				"Imperance.Table");
		db.execSQL("DROP TABLE IF EXISTS " +
				"Weight.Table");
		db.execSQL("DROP TABLE IF EXISTS " +
				"DPB.Table");
		db.execSQL("DROP TABLE IF EXISTS " +
				"SBP.Table");
		db.execSQL("DROP TABLE IF EXISTS " +
				"Pulse.Table");
		onCreate(db);
	}
	
	//�����û���
		public void insertUser(User u){
			SQLiteDatabase db = getDB();
			db.execSQL("insert into User(UserName,UserPassword,Sex,Birth,Height)" +
					"values('"+u.getUserName()+"','" +u.getUserPassword()+"','" 
					+u.getSex()+"','" +u.getBirth()+"','" +u.getHeight()+"')");
			db.close();
			
		}
		//�����迹��
		public void insertImperance(Imperance i){
			SQLiteDatabase db = getDB();
			db.execSQL("insert into Imperancetab(Id,UserId,Time,Imperace)" +
					"value(" +i.getUserId()+"," +i.getTime()+"," +i.getImperance()+")");
			db.close();
		}
		//�������ر�
		public void insertWeight(Weight w){
			SQLiteDatabase db = getDB();
			db.execSQL("insert into Weighttab(Id,UserId,Time,Weight)" +
					"value(" +w.getUserId()+"," +w.getTime()+"," +w.getWeight()+")");
			db.close();
		}
		//��������ѹ
		public void insertDPB(DPB d){
			SQLiteDatabase db = getDB();
			db.execSQL("insert into DPBtab(Id,UserId,Time,Weight)" +
					"value(" +d.getUserid()+"," +d.getTime()+"," +d.getDPB()+")");
			db.close();
		}
		//��������ѹ
		public void insertSBP(SBP s){
			SQLiteDatabase db = getDB();
			db.execSQL("insert into SBPtab(Id,UserId,Time,Weight)" +
					"value("+s.getUserid()+","  +s.getTime()+"," +s.getSBP()+")");
			db.close();
		}
		//����������
		public void insertPulse(Pulse p){
			SQLiteDatabase db = getDB();
			db.execSQL("insert into Pulsetab(Id,UserId,Time,Weight)" +
					"value("+p.getUserid()+","  +p.getTime()+"," +p.getPulse()+")");
			db.close();
		}
		//*********��ѯ*********
		public User findUserData(String username){  
	 	          SQLiteDatabase db=this.getDB(); 
			          Cursor cursor =db.rawQuery("select * from User where UserName=? ", new String[]{username});
			          if(cursor.moveToFirst()){  
			                 
			                String _name = cursor.getString(cursor.getColumnIndex("UserName")); 
			                String _word = cursor.getString(cursor.getColumnIndex("UserPassword"));
			                int _id = cursor.getInt(cursor.getColumnIndex("UserId"));
			                String _sex = cursor.getString(cursor.getColumnIndex("Sex")); 
			                String _birthday=cursor.getString(cursor.getColumnIndex("Birth"));
			                double _height=cursor.getDouble(cursor.getColumnIndex("Height")); 
			                User u=new User(_id,_name,_word,_sex,_birthday,_height);
			                System.out.println(String.format("%s", _sex));
			                db.close();
			                return u;
			          }  
			          db.close();
			          return null;  
		}  
}