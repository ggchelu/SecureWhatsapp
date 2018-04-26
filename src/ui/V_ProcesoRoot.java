package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import secure.wchat.R;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class V_ProcesoRoot extends Activity {
            
	private int myWidth;
	private int myHeight;
	
	private ImageView logo, ipaso1, ipaso2, ipaso3, ipaso4;
	private ProgressBar progreso1,progreso2,progreso3, progreso4;
	private TextView tinfo, tpaso1, tpaso2, tpaso3, tpaso4;
	private Button bseguir;
	
	ActivityManager AM;
	
	SQLiteDatabase db;

    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                       WindowManager.LayoutParams.FLAG_FULLSCREEN); 
        
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        myHeight = metrics.heightPixels;
        myWidth = metrics.widthPixels;
        
        setContentView(R.layout.v_proceso_root);
        
        init();
        adjust();
        
        progreso2.setVisibility(View.INVISIBLE);
        progreso3.setVisibility(View.INVISIBLE);
        progreso4.setVisibility(View.INVISIBLE);
        ipaso1.setVisibility(View.INVISIBLE);
        ipaso2.setVisibility(View.INVISIBLE);
        ipaso3.setVisibility(View.INVISIBLE);
        ipaso4.setVisibility(View.INVISIBLE);
        
        AM = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);        
        new LongOperation1().execute("");
    }
    
    
    private int procesoActivo(ActivityManager am) {
    	
    	int enc = -1;

        List<ActivityManager.RunningAppProcessInfo> listOfProcesses = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo process : listOfProcesses) {
        	
        	//Log.d("XXX",process.processName);
        	if (process.processName.contains("com.whatsapp"))
        		enc = process.pid;
        }
        
        return enc;
    }
    
    private boolean comandoKill(int pid) {
      	 
    	boolean res = false;
    	
    	try {
    		String[] cmd = {"sh", "-c", "su -c 'kill -9 " + pid + "'"};
    		     		 
			Process process = Runtime.getRuntime().exec(cmd);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			int read;
			char[] buffer = new char[4096];
			StringBuffer output = new StringBuffer();
			while ((read = reader.read(buffer)) > 0) {
			     output.append(buffer, 0, read);
			}
			reader.close();

			// Waits for the command to finish.
			process.waitFor();

			//Log.d("ALKON",output.toString());
			res = true;
			
		} catch (IOException e) {
			e.printStackTrace();
			res = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			res = false;
		}
        
        return res;
    }
    
    private void copyToSD() {
    	
    	new LongOperation2().execute("");
    }
    
    private void dumpSDToSD() {
    	
    	new LongOperation3().execute("");
    }
    
    private void deleteDB() {
    	
    	new LongOperation5().execute("");
    }
    
    private void copyFinal() {
    	
    	new LongOperation6().execute("");
    }
    
    private void deleteShit() {
    	
    	new LongOperation7().execute("");
    }
    
    
    private void limpia() {
    	
       	 try {
                String sdcardpath = Environment.getExternalStorageDirectory().getPath();       
                db = SQLiteDatabase.openDatabase(sdcardpath + "/SecureW/msgstore.db.sqlite" , null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
               
                
                File place = Environment.getExternalStorageDirectory();
            	 File file = new File(place,"/SecureW/new_msgstore.db.sqlite");
                //File newDatabase = new File(
                //		 file.getParentFile(), 
                //		 file.getName().replaceFirst("msgstore", "new_msgstore"));
                
                
                SQLiteDatabase newDB = SQLiteDatabase.openOrCreateDatabase(file, null);

               // VOLCAMOS MESSAGES 
                
               newDB.execSQL("CREATE TABLE messages (_id INTEGER PRIMARY KEY AUTOINCREMENT, key_remote_jid TEXT NOT NULL, key_from_me INTEGER, key_id TEXT NOT NULL, status INTEGER, needs_push INTEGER, data TEXT, timestamp INTEGER, media_url TEXT, media_mime_type TEXT, media_wa_type TEXT, media_size INTEGER, media_name TEXT, media_hash TEXT, latitude REAL, longitude REAL, thumb_image TEXT, remote_resource TEXT, received_timestamp INTEGER, send_timestamp INTEGER, receipt_server_timestamp INTEGER, receipt_device_timestamp INTEGER, raw_data BLOB, recipient_count INTEGER, media_duration INTEGER, origin INTEGER);");
               newDB.execSQL("CREATE TABLE chat_list (_id INTEGER PRIMARY KEY AUTOINCREMENT, key_remote_jid TEXT UNIQUE, message_table_id INTEGER, subject TEXT, creation INTEGER);");
               newDB.execSQL("CREATE UNIQUE INDEX messages_key_index on messages (key_remote_jid, key_from_me, key_id);");
               newDB.execSQL("DELETE FROM sqlite_sequence;");      
               newDB.execSQL("DROP TABLE android_metadata;"); 
               
               Cursor cursor = db.rawQuery("select * from messages", null);
    			if (cursor.moveToFirst()) {
    	            do {
    	            	ContentValues values = new ContentValues();

    	            	for(int c=0; c<cursor.getColumnCount(); c++) {
    	            		
    	            		if (cursor.getType(c) == Cursor.FIELD_TYPE_BLOB)
    	            			values.put(cursor.getColumnName(c), cursor.getBlob(c));
    	            		else values.put(cursor.getColumnName(c), cursor.getString(c));

    	            	}
    	            	newDB.insert("messages", null, values);
    	            } while (cursor.moveToNext());
    	        }
    			
    			
    			// VOLCAMOS CHAT_LIST
    			
    			cursor = db.rawQuery("select * from chat_list", null);
    			if (cursor.moveToFirst()) {
    	            do {
    	            	ContentValues values = new ContentValues();
    	            	for(int c=0; c<cursor.getColumnCount(); c++) {
    	            		
    	            		if (cursor.getType(c) == Cursor.FIELD_TYPE_BLOB)
    	            			values.put(cursor.getColumnName(c), cursor.getBlob(c));
    	            		else values.put(cursor.getColumnName(c), cursor.getString(c));

    	            	}
    	            	newDB.insert("chat_list", null, values);
    	            } while (cursor.moveToNext());
    	        }

    			cursor = db.rawQuery("select * from sqlite_sequence", null);
    			if (cursor.moveToFirst()) {
    	            do {
    	            	ContentValues values = new ContentValues();
    	            	for(int c=0; c<cursor.getColumnCount(); c++) {
    	            		
    	            		if (cursor.getType(c) == Cursor.FIELD_TYPE_BLOB)
    	            			values.put(cursor.getColumnName(c), cursor.getBlob(c));
    	            		else values.put(cursor.getColumnName(c), cursor.getString(c));

    	            	}
    	            	newDB.insert("sqlite_sequence", null, values);
    	            } while (cursor.moveToNext());
    	        }
    			 	         
    			if (newDB != null){
    				newDB.close();
    			}

            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (db != null){
            	db.close();
            }
       
   }
    
    private void init() {
    	
    	bseguir = (Button) findViewById(R.id.bseguir);  
    	
        logo = (ImageView) findViewById(R.id.logo);  
        ipaso1 = (ImageView) findViewById(R.id.ipaso1); 
        ipaso2 = (ImageView) findViewById(R.id.ipaso2); 
        ipaso3 = (ImageView) findViewById(R.id.ipaso3); 
        ipaso4 = (ImageView) findViewById(R.id.ipaso4); 
        
        progreso1 = (ProgressBar) findViewById(R.id.ppaso1);  
        progreso2 = (ProgressBar) findViewById(R.id.ppaso2);  
        progreso3 = (ProgressBar) findViewById(R.id.ppaso3);  
        progreso4 = (ProgressBar) findViewById(R.id.ppaso4); 
        tinfo = (TextView) findViewById(R.id.tinfo);   
                
        tpaso1 = (TextView) findViewById(R.id.paso1);  
        tpaso2 = (TextView) findViewById(R.id.paso2);  
        tpaso3 = (TextView) findViewById(R.id.paso3);  
        tpaso4 = (TextView) findViewById(R.id.paso4); 
        
        
        
        bseguir.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					bseguir.setBackgroundResource(R.drawable.boton_azul_press);
				}
				else if (event.getAction() == MotionEvent.ACTION_UP)  {

					bseguir.setBackgroundResource(R.drawable.boton_azul);
      		
		        		Intent i = new Intent(getApplicationContext(), V_Final.class);
		        		startActivity(i);
		        		overridePendingTransition(R.drawable.fadein, R.drawable.fadeout);
		        		finish();						
				}
				return false;
			}
		});
    }
    
    private void adjust() {
    	
        RelativeLayout.LayoutParams params;
        
        params = new RelativeLayout.LayoutParams(myWidth/4,myWidth/4);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params.topMargin = myHeight / 65;
		logo.setLayoutParams(params); 
        		
		params = new RelativeLayout.LayoutParams(tinfo.getLayoutParams());
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params.addRule(RelativeLayout.BELOW,logo.getId());
		params.topMargin = myHeight / 15;
		tinfo.setLayoutParams(params); 
		tinfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myWidth*8)/100);
		
    	params = new RelativeLayout.LayoutParams(myWidth/10,myWidth/10);
    	params.addRule(RelativeLayout.BELOW,tinfo.getId());
    	params.topMargin = myHeight / 15;
    	params.leftMargin = myWidth / 50;
    	ipaso1.setLayoutParams(params);
		
    	params = new RelativeLayout.LayoutParams(myWidth/10,myWidth/10);
    	params.addRule(RelativeLayout.BELOW,tinfo.getId());
    	params.topMargin = myHeight / 15;
    	params.leftMargin = myWidth / 50;
    	progreso1.setLayoutParams(params);
    	progreso1.bringToFront();
            	
		params = new RelativeLayout.LayoutParams(tpaso1.getLayoutParams());
		params.addRule(RelativeLayout.RIGHT_OF, progreso1.getId());
		params.addRule(RelativeLayout.BELOW,tinfo.getId());
		params.topMargin = myHeight / 15;
		params.leftMargin = myWidth / 25;
		tpaso1.setLayoutParams(params); 
		tpaso1.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myWidth*6)/100);
		
    	params = new RelativeLayout.LayoutParams(myWidth/10,myWidth/10);
    	params.addRule(RelativeLayout.BELOW,tpaso1.getId());
    	params.topMargin = myHeight / 15;
    	params.leftMargin = myWidth / 50;
    	ipaso2.setLayoutParams(params);
            	
    	params = new RelativeLayout.LayoutParams(myWidth/10,myWidth/10);
    	params.addRule(RelativeLayout.BELOW,tpaso1.getId());
    	params.topMargin = myHeight / 15;
    	params.leftMargin = myWidth / 50;
    	progreso2.setLayoutParams(params);
    	progreso2.bringToFront();
            	
		params = new RelativeLayout.LayoutParams(tpaso2.getLayoutParams());
		params.addRule(RelativeLayout.RIGHT_OF, progreso2.getId());
		params.addRule(RelativeLayout.BELOW,tpaso1.getId());
		params.topMargin = myHeight / 15;
		params.leftMargin = myWidth / 25;
		tpaso2.setLayoutParams(params); 
		tpaso2.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myWidth*6)/100);
    	
    	params = new RelativeLayout.LayoutParams(myWidth/10,myWidth/10);
    	params.addRule(RelativeLayout.BELOW,tpaso2.getId());
    	params.topMargin = myHeight / 15;
    	params.leftMargin = myWidth / 50;
    	ipaso3.setLayoutParams(params);
		
    	params = new RelativeLayout.LayoutParams(myWidth/10,myWidth/10);
    	params.addRule(RelativeLayout.BELOW,tpaso2.getId());
    	params.topMargin = myHeight / 15;
    	params.leftMargin = myWidth / 50;
    	progreso3.setLayoutParams(params);
    	progreso3.bringToFront();
            	
		params = new RelativeLayout.LayoutParams(tpaso3.getLayoutParams());
		params.addRule(RelativeLayout.RIGHT_OF, progreso3.getId());
		params.addRule(RelativeLayout.BELOW,tpaso2.getId());
		params.topMargin = myHeight / 15;
		params.leftMargin = myWidth / 25;
		tpaso3.setLayoutParams(params); 
		tpaso3.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myWidth*6)/100);
		
    	params = new RelativeLayout.LayoutParams(myWidth/10,myWidth/10);
    	params.addRule(RelativeLayout.BELOW,tpaso3.getId());
    	params.topMargin = myHeight / 15;
    	params.leftMargin = myWidth / 50;
    	ipaso4.setLayoutParams(params);
		
    	params = new RelativeLayout.LayoutParams(myWidth/10,myWidth/10);
    	params.addRule(RelativeLayout.BELOW,tpaso3.getId());
    	params.topMargin = myHeight / 15;
    	params.leftMargin = myWidth / 50;
    	progreso4.setLayoutParams(params);
    	progreso4.bringToFront();
            	
		params = new RelativeLayout.LayoutParams(tpaso4.getLayoutParams());
		params.addRule(RelativeLayout.RIGHT_OF, progreso4.getId());
		params.addRule(RelativeLayout.BELOW,tpaso3.getId());
		params.topMargin = myHeight / 15;
		params.leftMargin = myWidth / 25;
		tpaso4.setLayoutParams(params); 
		tpaso4.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myWidth*6)/100);
		
        params = new RelativeLayout.LayoutParams((myWidth/2),myHeight/8);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params.addRule(RelativeLayout.BELOW,tpaso4.getId());
		params.topMargin = myHeight / 15;
		bseguir.setLayoutParams(params); 
		bseguir.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myWidth*8)/100);
		bseguir.setVisibility(View.GONE);
    }
    
    
    
    
    private class LongOperation1 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
        	
        	
            int pid = -1;
            if ((pid = procesoActivo(AM)) != -1) { 
            	
            	android.os.Process.killProcess(pid);    
            }

            return "Executed";
        }      

        @Override
        protected void onPostExecute(String result) {               
            
           	progreso1.setVisibility(View.INVISIBLE);
            ipaso1.setVisibility(View.VISIBLE);
        	
           	progreso2.setVisibility(View.VISIBLE);  
           	        	
        	copyToSD();
        }
    }
    
    private class LongOperation2 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
        	        	
        	try {
        		String[] cmd = {"sh", "-c", "su -c 'mv /data/data/com.whatsapp/databases/msgstore.db /mnt/sdcard/SecureW/msgstore.db.sqlite'"};
        		     		 
        		Process process = Runtime.getRuntime().exec(cmd);
        		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        		int read;
        		char[] buffer = new char[4096];
        		StringBuffer output = new StringBuffer();
        		while ((read = reader.read(buffer)) > 0) {
        		     output.append(buffer, 0, read);
        		}
        		reader.close();

        		// Waits for the command to finish.
        		process.waitFor();

        		//Log.d("ALKON",output.toString());
        		
        	} catch (IOException e) {
        		e.printStackTrace();
        	} catch (InterruptedException e) {
        		e.printStackTrace();
        	}

            return "Executed";
        }      

        @Override
        protected void onPostExecute(String result) {               
            
        	dumpSDToSD(); 
        }
    }
    
    private class LongOperation3 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
        	        	
        		limpia();
        	
        	return "Executed";
        }      

        @Override
        protected void onPostExecute(String result) {               
                    	
           	progreso2.setVisibility(View.INVISIBLE);
            ipaso2.setVisibility(View.VISIBLE);
        	
           	progreso3.setVisibility(View.VISIBLE); 
           	
           	deleteDB();
        }
    }

    private class LongOperation5 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

        	try {
        		String[] cmd = {"sh", "-c", "su -c 'rm -r /data/data/com.whatsapp/databases/msgstore.db'"};      		     		 
        		Process process = Runtime.getRuntime().exec(cmd);
        		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        		int read;
        		char[] buffer = new char[4096];
        		StringBuffer output = new StringBuffer();
        		while ((read = reader.read(buffer)) > 0) {
        		     output.append(buffer, 0, read);
        		}
        		reader.close();

        		// Waits for the command to finish.
        		process.waitFor();

        		//Log.d("ALKON",output.toString());
        		
        	} catch (IOException e) {
        		e.printStackTrace();
        	} catch (InterruptedException e) {
        		e.printStackTrace();
        	}

            return "Executed";
        }      

        @Override
        protected void onPostExecute(String result) {               

        	copyFinal();
        }
    }
    
    private class LongOperation6 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

        	try {
        		String[] cmd = {"sh", "-c", "su -c 'mv /mnt/sdcard/SecureW/new_msgstore.db.sqlite /data/data/com.whatsapp/databases/msgstore.db'"};      		     		 
        		Process process = Runtime.getRuntime().exec(cmd);
        		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        		int read;
        		char[] buffer = new char[4096];
        		StringBuffer output = new StringBuffer();
        		while ((read = reader.read(buffer)) > 0) {
        		     output.append(buffer, 0, read);
        		}
        		reader.close();

        		// Waits for the command to finish.
        		process.waitFor();

        		//Log.d("ALKON",output.toString());
        		
        	} catch (IOException e) {
        		e.printStackTrace();
        	} catch (InterruptedException e) {
        		e.printStackTrace();
        	}

            return "Executed";
        }      

        @Override
        protected void onPostExecute(String result) {               

           	progreso3.setVisibility(View.INVISIBLE);
            ipaso3.setVisibility(View.VISIBLE);
            
           	progreso4.setVisibility(View.VISIBLE);            	
           	deleteShit();
        }
    }
    
    private class LongOperation7 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

        	try {
        		String[] cmd = {"sh", "-c", "su -c 'rm -r /mnt/sdcard/SecureW'"};      		     		 
        		Process process = Runtime.getRuntime().exec(cmd);
        		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        		int read;
        		char[] buffer = new char[4096];
        		StringBuffer output = new StringBuffer();
        		while ((read = reader.read(buffer)) > 0) {
        		     output.append(buffer, 0, read);
        		}
        		reader.close();

        		// Waits for the command to finish.
        		process.waitFor();

        		Log.d("ALKON",output.toString());
        		
        	} catch (IOException e) {
        		e.printStackTrace();
        	} catch (InterruptedException e) {
        		e.printStackTrace();
        	}

            return "Executed";
        }      

        @Override
        protected void onPostExecute(String result) {               

           	progreso4.setVisibility(View.INVISIBLE);
            ipaso4.setVisibility(View.VISIBLE);
            
            tinfo.setText("Proceso completado!");
            
    		bseguir.setVisibility(View.VISIBLE);
        }
    }
}