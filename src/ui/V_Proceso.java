package ui;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.security.GeneralSecurityException;

import javax.xml.datatype.Duration;

import org.apache.commons.ssl.OpenSSL;

import secure.wchat.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;

public class V_Proceso extends Activity {
            
	private int myWidth;
	private int myHeight;
	
	private ProgressBar progreso;
	private TextView tinfo;

	SQLiteDatabase db;

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
        
        setContentView(R.layout.v_proceso);
        
        progreso = (ProgressBar) findViewById(R.id.progreso);  
        tinfo = (TextView) findViewById(R.id.tinfo);  
        
        RelativeLayout.LayoutParams params;
        
		params = new RelativeLayout.LayoutParams(tinfo.getLayoutParams());
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params.topMargin = myHeight / 15;
		tinfo.setLayoutParams(params); 
		tinfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myWidth*8)/100);

    	params = new RelativeLayout.LayoutParams(progreso.getLayoutParams());
    	params.addRule(RelativeLayout.CENTER_HORIZONTAL); 
    	params.topMargin = (myHeight / 2);
    	progreso.setLayoutParams(params);
    	progreso.bringToFront();
        
        /*
        texto = (TextView) findViewById(R.id.texto);    
        actualizaLog("Iniciando..");
        
        db = openOrCreateDatabase("proof.db", Context.MODE_PRIVATE, null); 
        
        importDB();
        Log.d("XXX",db.getPath());
        Log.d("XXX",new File(db.getPath()).length() + "");
        //generateDB();
        //decrypt();
        exportDB();
        */
        
        /*
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

                String currentDBPath = "//data//secure.wchat//databases//prueba.db";
                String backupDBPath = "/SecureW/backUp.sqlite";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    @SuppressWarnings("resource")
					FileChannel src = new FileInputStream(currentDB).getChannel();
                    @SuppressWarnings("resource")
					FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    Log.d("XXX",src.size() + "");
                    Log.d("XXX",dst.size() + "");
                    src.close();
                    dst.close();
                }            
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        
        Log.d("XXX",size + "");*/
        /*
        RelativeLayout rel = (RelativeLayout) findViewById(R.id.rel);        
        if (RootTools.isAccessGiven()) { // ROOTEADO
        	
        	rel.setBackgroundColor(Color.GREEN);
        }
        else { // NO ROOTEADO
        	
        	if (RootTools.isRootAvailable()) { // TIENE USUARIO ROOT
        		
        		rel.setBackgroundColor(Color.YELLOW);
        	}
        	else { // NO TIENE USUARIO ROOT
        		
        		rel.setBackgroundColor(Color.RED);
        	}
        }
        */
        
        //leerFichero();
        //tratarFichero();
        
        //dumpSql();
        
        //lanzaComandos();        
        //decrypt();
        
        
        //inflateDB();
        //dumpBD();
    }
    
    
    
    private boolean isPackageInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }
    
    private boolean isFileInSD() {
    	
    	File place = Environment.getExternalStorageDirectory();
    	File file = new File(place,"/WhatsApp/Databases/msgstore.db.crypt5");
    	
    	if (file.exists()) return true;   	
    	else return false;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //importing database
    private void importDB() {
 
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data  = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + "secure.wchat" + "//databases//" + "proof.db";
                String backupDBPath  = "/SecureW/msgstore.db.sqlite";
                File  backupDB= new File(data, currentDBPath);
                File currentDB  = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
    
    private void exportDB() {

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + "secure.wchat" + "//databases//" + "proof.db";
                String backupDBPath  = "/SecureW/proof.db.sqlite";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                
            }
        } catch (Exception e) {

        	e.printStackTrace();
        }
    }
    
    
    private void inflateDB() {
    	
    	File sdcard = Environment.getExternalStorageDirectory();       
    	final File myFile = new File(sdcard, "/SecureW/volcado" + ".txt");
    	
    	 try {
             final BufferedReader reader = new BufferedReader(new FileReader(myFile));
             String res = "";
             try {
                 String line;
                 while ((line = reader.readLine()) != null) {
                	 res += line;  
                 }                 
             }
             finally {
                 reader.close();
             }
            
            // Log.d("XXX",res.length() + "");
             
            //db.execSQL(res);       
             
         }
         catch (final Exception e) {
        	 e.printStackTrace();
         }
    }
    
    
    private void dumpBD() {
    	
    	try {
    		
			Process process = Runtime.getRuntime().exec("sqlite3 //data//data//secure.wchat//databases//prueba.db .dump");
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
			   
			/*actualizaLog(output.toString());
			//Log.d("ALKON",output.toString());*/
			actualizaLog(output.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    
    
    
    
    private void generateDB() {
    	
    	try {
    		
    		String[] cmd = {"cat /mnt/sdcard/SecureW/volcado.txt"};
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
			//actualizaLog(output.toString());
			actualizaLog("TODO OK");
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    
    private void decrypt() {
    	
    	try {
    		
			Process process = Runtime.getRuntime().exec("sqlite3 /mnt/sdcard/SecureW/msgstore.db.sqlite .dump");
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
			   
			writeToFile(output.toString());
			
			/*actualizaLog(output.toString());
			Log.d("ALKON",output.toString());*/
			actualizaLog("TODO OK");
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    
    private void actualizaLog(String msg) {
    	
    	//texto.setText(texto.getText().toString() + msg + "\n");
    }
    
    
    private void tratarFichero() {
    	   	
    	actualizaLog("Tratando fichero..");
    	
    	File place = Environment.getExternalStorageDirectory();
    	File file = new File(place,"/WhatsApp/Databases/msgstore.db.crypt5");
    	
    	File folder = new File(Environment.getExternalStorageDirectory() + "/SecureW");
    	boolean success = true;
    	if (!folder.exists()) {
    	    success = folder.mkdir();
    	    actualizaLog("Directorio app creado..");
    	}
    	
    	if (success) {    		
        
        	byte[] password = {'3','4','6','a','2','3','6','5','2','a','4','6','3','9','2','b','4','d','7','3','2','5','7','c','6','7',
 				   '3','1','7','e','3','5','2','e','3','3','7','2','4','8','2','1','7','7','6','5','2','c'};
     	
	     	byte[] iv = {'3','4','6','a','2','3','6','5','2','a','4','6','3','9','2','b','4','d','7','3','2','5','7','c','6','7',
	 				   '3','1','7','e','3','5','2','e','3','3','7','2','4','8','2','1','7','7','6','5','2','c'};
	     	
            int size = (int) file.length();
            byte[] bytes = new byte[size];
            
		    try {
		             BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
		             buf.read(bytes, 0, bytes.length);
		             buf.close();
		             
		             byte[] data = OpenSSL.decrypt("aes-192-ecb", password, iv, bytes);		
		
		             File fileAPP = new File(place,"/SecureW/msgstore.db.sqlite");
		             FileOutputStream fos = new FileOutputStream(fileAPP);
		             fos.write(data);
		             fos.close();
		             
		             actualizaLog("Proceso desencriptado terminado");
		             actualizaLog("Volcando datos..");
		             dumpSql();
             
	         } catch (FileNotFoundException e) {
	             e.printStackTrace();
	         } catch (IOException e) {
	             e.printStackTrace();
	         } catch (GeneralSecurityException e) {
	 			e.printStackTrace();
	 		}        	
    	} else {
    	    // Do something else on failure 
    	}
    }
    
    private void dumpSql() {
    	
    	try {
    		    	    		
    		Process process = Runtime.getRuntime().exec("echo '.dump' | sqlite3 /mnt/sdcard/SecureW/msgstore.db.sqlite > /mnt/sdcard/SecureW/volcado.txt");

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
			actualizaLog("Volcado terminado");			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
        
    private void writeToFile(String data) {
    	
    	FileOutputStream fos = null;
    	
        try {
        	
        	File sdcard = Environment.getExternalStorageDirectory();       
        	final File myFile = new File(sdcard, "/SecureW/volcado" + ".sql");

            if (!myFile.exists()) {    
                myFile.createNewFile();
            } 
        	
            fos = new FileOutputStream(myFile);
            fos.write(data.getBytes());
            fos.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        } 
    }
    
    private void leerFichero() {
    	
    	File sdcard = Environment.getExternalStorageDirectory();
    	
    	File fileSD = new File(sdcard,"/WhatsApp/Databases/msgstore.db.crypt5");
    	
    	File folder = new File(Environment.getExternalStorageDirectory() + "/SecureW");
    	boolean success = true;
    	if (!folder.exists()) {
    	    success = folder.mkdir();
    	}
    	
    	if (success) {
    		
        	File fileAPP = new File(sdcard,"/SecureW/msgstore.db.crypt5");
        	
        	try {
    			copyFile(fileSD, fileAPP);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        	
    	} else {
    	    // Do something else on failure 
    	}
    	
    	
    	

    }
    
    @SuppressWarnings("resource")
	public static void copyFile(File src, File dst) throws IOException {
    	
		FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try
        {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        }
        finally
        {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }

















    private void lanzaComandos() {
    	
    	try {
    		
    		String[] cmd = {"ls -l"};
    		
    		Process p = Runtime.getRuntime().exec(new String[]{"echo","'.dump'","ls /home/XXX"});
    		
    		Process process = Runtime.getRuntime().exec(  "ls");

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
			    actualizaLog(output.toString());	

			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }









}