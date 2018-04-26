package ui;

import java.io.File;

import secure.wchat.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class V_Copia extends Activity {
            
	private int myWidth;
	private int myHeight;
	
	private ImageView logo;
	private Button bempezar, babrir;
	private TextView tinfo, tpaso;

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
        
        setContentView(R.layout.v_copia);
        
        inicia();
        ajusta();
    }
        
    private void inicia() {
    	
    	  bempezar = (Button) findViewById(R.id.bempezar); 
          babrir = (Button) findViewById(R.id.babrir); 
          
          bempezar = (Button) findViewById(R.id.bempezar);  
          bempezar.setOnTouchListener(new OnTouchListener() {
  			
  			public boolean onTouch(View v, MotionEvent event) {

  				if (event.getAction() == MotionEvent.ACTION_DOWN) {

  					bempezar.setBackgroundResource(R.drawable.boton_azul_press);
  				}
  				else if (event.getAction() == MotionEvent.ACTION_UP)  {

  					bempezar.setBackgroundResource(R.drawable.boton_azul);
  					
  					if (isFileInSD()) {
  					
  		        		Intent i = new Intent(getApplicationContext(), V_Desinstalar.class);
  		        		startActivity(i);
  		        		overridePendingTransition(R.drawable.fadein, R.drawable.fadeout);
  		        		finish();	
  					}
  					else Toast.makeText(V_Copia.this, "haga la copia de seguridad para continuar", Toast.LENGTH_SHORT).show();
  				}
  				return false;
  			}
  		});
          
          babrir = (Button) findViewById(R.id.babrir);  
          babrir.setOnTouchListener(new OnTouchListener() {
  			
  			public boolean onTouch(View v, MotionEvent event) {

  				if (event.getAction() == MotionEvent.ACTION_DOWN) {

  					babrir.setBackgroundResource(R.drawable.boton_azul_press);
  				}
  				else if (event.getAction() == MotionEvent.ACTION_UP)  {

  					babrir.setBackgroundResource(R.drawable.boton_azul);
  					
  					if (isPackageInstalled("com.whatsapp", V_Copia.this)) {
  					
  						Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
  						startActivity(LaunchIntent);
  						overridePendingTransition(R.drawable.fadein, R.drawable.fadeout);
  					}
  					else Toast.makeText(V_Copia.this, "whatsapp no instalado", Toast.LENGTH_SHORT).show();
  				}
  				return false;
  			}
  		});
             
          tinfo = (TextView) findViewById(R.id.tinfo);  
          tinfo.setText(getString(R.string.tpaso1));
          tpaso = (TextView) findViewById(R.id.tpaso);  
          logo = (ImageView) findViewById(R.id.logo);  
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
          
  		params = new RelativeLayout.LayoutParams(tinfo.getLayoutParams());
  		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
  		params.addRule(RelativeLayout.BELOW,tpaso.getId());
  		params.topMargin = myHeight / 25;
  		tinfo.setLayoutParams(params); 
  		tinfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myWidth*5)/100);
  		tinfo.setGravity(Gravity.CENTER);
                    
		params = new RelativeLayout.LayoutParams((myWidth/3)*2,myHeight/8);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
 		params.addRule(RelativeLayout.BELOW,tinfo.getId());
  		params.topMargin = myHeight / 8;
		babrir.setLayoutParams(params); 
		babrir.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myWidth*7)/100);
	
		params = new RelativeLayout.LayoutParams((myWidth/3)*2,myHeight/8);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
 		params.addRule(RelativeLayout.BELOW,babrir.getId());
  		params.topMargin = myHeight / 55;
		bempezar.setLayoutParams(params); 
		bempezar.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myWidth*7)/100);
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
    
}