package ui;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.channels.FileChannel;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import log.UserEmailFetcher;

import org.apache.commons.ssl.OpenSSL;

import secure.wchat.R;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class V_ProcesoNoRoot extends Activity {
            
	private int myWidth;
	private int myHeight;
	
	private ImageView logo, ipaso1, ipaso2, ipaso3, ipaso4;
	private ProgressBar progreso1,progreso2,progreso3, progreso4;
	private TextView tpaso1, tpaso2, tpaso3, tpaso4, tpaso;
	private Button bseguir;
	
	private String EMAIL;
	
	ActivityManager AM;
	
	SQLiteDatabase db;
	
	private static final byte[] INITIALIZATION_VECTOR = hexStringToByteArray("1e39f369e90db33aa73b442bbbb6b0b9");
	private static final byte[] ENCRYPTION_KEY = hexStringToByteArray("8d4b155cc9ff81e5cbf6fa7819366a3ec621a656416cd793");


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
        
        setContentView(R.layout.v_proceso_no_root);
        
        inicia();
        ajusta();
        
        progreso2.setVisibility(View.INVISIBLE);
        progreso3.setVisibility(View.INVISIBLE);
        progreso4.setVisibility(View.INVISIBLE);
        ipaso1.setVisibility(View.INVISIBLE);
        ipaso2.setVisibility(View.INVISIBLE);
        ipaso3.setVisibility(View.INVISIBLE);
        ipaso4.setVisibility(View.INVISIBLE);
        
        AM = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);    
        EMAIL = getEmail(V_ProcesoNoRoot.this);
        
        
        new LongOperation1().execute("");
    }
        
    private void inicia() {
    	
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
     
        tpaso = (TextView) findViewById(R.id.tpaso); 
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
    
    private void ajusta() {
    	
        RelativeLayout.LayoutParams params;
        
        params = new RelativeLayout.LayoutParams(myWidth/4,myWidth/4);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params.topMargin = myHeight / 65;
		logo.setLayoutParams(params); 
          		
  		params = new RelativeLayout.LayoutParams(tpaso.getLayoutParams());
  		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
  		params.addRule(RelativeLayout.BELOW,logo.getId());
  		params.topMargin = myHeight / 25;
  		tpaso.setLayoutParams(params); 
  		tpaso.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myWidth*7)/100);
		
    	params = new RelativeLayout.LayoutParams(myWidth/10,myWidth/10);
    	params.addRule(RelativeLayout.BELOW,tpaso.getId());
    	params.topMargin = myHeight / 15;
    	params.leftMargin = myWidth / 50;
    	ipaso1.setLayoutParams(params);
		
    	params = new RelativeLayout.LayoutParams(myWidth/10,myWidth/10);
    	params.addRule(RelativeLayout.BELOW,tpaso.getId());
    	params.topMargin = myHeight / 15;
    	params.leftMargin = myWidth / 50;
    	progreso1.setLayoutParams(params);
    	progreso1.bringToFront();
            	
		params = new RelativeLayout.LayoutParams(tpaso1.getLayoutParams());
		params.addRule(RelativeLayout.RIGHT_OF, progreso1.getId());
		params.addRule(RelativeLayout.BELOW,tpaso.getId());
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
    
    
    
    
    
    
    
    
    static String getEmail(Context context) {
        AccountManager accountManager = AccountManager.get(context); 
        Account account = getAccount(accountManager);

        if (account == null) {
          return null;
        } else {
          return account.name;
        }
      }

      private static Account getAccount(AccountManager accountManager) {
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0) {
          account = accounts[0];      
        } else {
          account = null;
        }
        return account;
      }
    
    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
          data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    private static String md5(String md5) throws Exception {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(md5.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        return bigInt.toString(16);
    }
    
    // METODOS PRINCIPALES    
    
    private void desencripta(String mail) throws Exception {

        String emailMD5 = md5(mail);
        byte[] emailMD5Bytes = hexStringToByteArray(emailMD5 + emailMD5);

        byte[] decryptionKey = Arrays.copyOf(ENCRYPTION_KEY, 24);
        for (int i = 0; i < 24; i++) {
          decryptionKey[i] ^= emailMD5Bytes[i & 0xF];
        }
    	
    	
    	File place = Environment.getExternalStorageDirectory();
    	File file = new File(place,"/WhatsApp/Databases/msgstore.db.crypt5");
    	//File file = new File(place,"/SecureW/msgstore1.db.crypt");
    	    
    	//byte[] iv = hexStringToByteArray("1e39f369e90db33aa73b442bbbb6b0b9");
    	//byte[] password = hexStringToByteArray("8d4b155cc9ff81e5cbf6fa7819366a3ec621a656416cd793");
    	
    	/*
        byte[] password = {'8','d','4','b','1','5','5','c','c','9','f','f','8','1','e','5','c','b','f','6','f','a','7','8','1','9',
 				   '3','6','6','a','6','5','6','4','1','6','c','d','7','9','3'};
     	
	   	byte[] iv = {'8','d','4','b','1','5','5','c','c','9','f','f','8','1','e','5','c','b','f','6','f','a','7','8','1','9',
				   '3','6','6','a','6','5','6','4','1','6','c','d','7','9','3'};
	    */
    	
        int size = (int) file.length();
        byte[] bytes = new byte[size];
            
		try {
	         BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
	         buf.read(bytes, 0, bytes.length);
	         buf.close();
	             
	         byte[] data = OpenSSL.decrypt("aes-192-cbc", decryptionKey, INITIALIZATION_VECTOR, bytes);		
	
	         File fileAPP = new File(place,"/SecureW/msgstore.db.sqlite");
	         FileOutputStream fos = new FileOutputStream(fileAPP);
	         fos.write(data);
	         fos.close();
	         
	         //dumpSql();         
	         
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         } catch (GeneralSecurityException e) {
 			e.printStackTrace();
 		}  	
    }
    
    private void encripta(String mail) throws Exception {
    	
    	 String emailMD5 = md5(mail);
         byte[] emailMD5Bytes = hexStringToByteArray(emailMD5 + emailMD5);

         byte[] decryptionKey = Arrays.copyOf(ENCRYPTION_KEY, 24);
         for (int i = 0; i < 24; i++) {
           decryptionKey[i] ^= emailMD5Bytes[i & 0xF];
         }
    	    	
    	File place = Environment.getExternalStorageDirectory();
    	File file = new File(place,"/SecureW/new_msgstore.db.sqlite");


            int size = (int) file.length();
            byte[] bytes = new byte[size];
            
		    try {
		             BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
		             buf.read(bytes, 0, bytes.length);
		             buf.close();
		             
		             byte[] data = OpenSSL.encrypt("aes-192-cbc", decryptionKey, INITIALIZATION_VECTOR, bytes, false);		
		             //Log.d("XXX","size :" + data.length);
		             
		             
		             File fileAPP = new File(place,"/SecureW/msgstore.db.crypt5");
		             FileOutputStream fos = new FileOutputStream(fileAPP);
		             fos.write(data);
		             fos.close();
             
	         } catch (FileNotFoundException e) {
	             e.printStackTrace();
	         } catch (IOException e) {
	             e.printStackTrace();
	         } catch (GeneralSecurityException e) {
	 			e.printStackTrace();
	 		}       	
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
         
    // PROCESOS ASINCRONOS
    
    private void continuaPaso1() {
    	
    	// continua del paso 1
    	
    	new LongOperation2().execute("");
    }

    private void continuaPaso2() {
    	
    	new LongOperation3().execute("");
    }
    
    private void continuaPaso3() {
    	
    	new LongOperation4().execute("");
    }
       
    private void deleteShit() {
    	
    	new LongOperation5().execute("");
    }
    
    /////////
    
    private static void decrypt(File inputFile, File outputFile, String email) throws Exception {
    	
        String emailMD5 = md5(email);
        byte[] emailMD5Bytes = hexStringToByteArray(emailMD5 + emailMD5);

        byte[] decryptionKey = Arrays.copyOf(ENCRYPTION_KEY, 24);
        for (int i = 0; i < 24; i++) {
          decryptionKey[i] ^= emailMD5Bytes[i & 0xF];
        }

        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptionKey, "AES"), new IvParameterSpec(
          INITIALIZATION_VECTOR));
        CipherInputStream cIn = new CipherInputStream(new FileInputStream(inputFile), cipher);
        FileOutputStream fOut = new FileOutputStream(outputFile);

        byte[] buffer = new byte[8192];
        int n;
        while ((n = cIn.read(buffer)) != -1) {
          fOut.write(buffer, 0, n);
        }

        cIn.close();
        fOut.close();
      }
    
    
    
    //////////////////
    
    
    private class LongOperation1 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
        	        	
        	
			try {
				desencripta(EMAIL);
				limpia();
			} catch (Exception e) {
				e.printStackTrace();
			}

            return "Executed";
        }      

        @Override
        protected void onPostExecute(String result) {               
            
           	progreso1.setVisibility(View.INVISIBLE);
            ipaso1.setVisibility(View.VISIBLE);
        	
           	progreso2.setVisibility(View.VISIBLE);  

            continuaPaso1();
        }
    }
    
    private class LongOperation2 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
        	
        	try {     	
        		encripta(EMAIL);
        	} catch (Exception e) {
				e.printStackTrace();
			}
            return "Executed";
        }      

        @Override
        protected void onPostExecute(String result) {               

           	progreso2.setVisibility(View.INVISIBLE);
            ipaso2.setVisibility(View.VISIBLE);
        	
           	progreso3.setVisibility(View.VISIBLE); 
           	
           	continuaPaso2();
        }
    }
    
    private class LongOperation3 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
        
        	try {
        		String[] cmd = {"sh", "-c", "rm /mnt/sdcard/WhatsApp/Databases/*.crypt5"};      		     		 
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
            
        	
        	continuaPaso3();
        }
    }
    
    private class LongOperation4 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

        	try {
        		String[] cmd = {"sh", "-c", "mv /mnt/sdcard/SecureW/msgstore.db.crypt5 /mnt/sdcard/WhatsApp/Databases/msgstore.db.crypt5"};      		     		 
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
           	           	
           	progreso4.setVisibility(View.INVISIBLE);
            ipaso4.setVisibility(View.VISIBLE);
            
            //tpaso.setText("Paso 3 - Proceso completado!");
            
    		bseguir.setVisibility(View.VISIBLE);
        }
    }
    

    private class LongOperation5 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

        	try {
        		String[] cmd = {"sh", "-c", "rm -r /mnt/sdcard/SecureW/"};      		     		 
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

           	progreso4.setVisibility(View.INVISIBLE);
            ipaso4.setVisibility(View.VISIBLE);
            
            //tpaso.setText("Paso 3 - Proceso completado!");
            
    		bseguir.setVisibility(View.VISIBLE);
        }
    }
}