package ui;

import secure.wchat.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

public class V_Desinstalar extends Activity {
            
	private int myWidth;
	private int myHeight;
	
	private ImageView logo;
	private Button bempezar, babrir, batras;
	private TextView tinfo, tpaso;

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
        
        setContentView(R.layout.v_desinstalar);

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
					
					if (isPackageInstalled("com.whatsapp", V_Desinstalar.this)) {
					
						Toast.makeText(V_Desinstalar.this, "desinstale whatsapp para continuar", Toast.LENGTH_SHORT).show();
					}
					else {				
		        		Intent i = new Intent(getApplicationContext(), V_ProcesoNoRoot.class);
		        		startActivity(i);
		        		overridePendingTransition(R.drawable.fadein, R.drawable.fadeout);
		        		finish();	
	        		}
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
					
					Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
	                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                startActivity(dialogIntent);
	                overridePendingTransition(R.drawable.fadein, R.drawable.fadeout);
				}
				return false;
			}
		});
	       
	    batras = (Button) findViewById(R.id.batras);  
	    batras.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
	
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
	
					batras.setBackgroundResource(R.drawable.boton_azul_press);
				}
				else if (event.getAction() == MotionEvent.ACTION_UP)  {
	
					batras.setBackgroundResource(R.drawable.boton_azul);
					
	        		Intent i = new Intent(getApplicationContext(), V_Copia.class);
	        		startActivity(i);
	        		overridePendingTransition(R.drawable.fadein, R.drawable.fadeout);
	        		finish();	
				}
				return false;
			}
		});
	    
	    tinfo = (TextView) findViewById(R.id.tinfo);  
	    tinfo.setText(getString(R.string.tpaso2));
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
  		params.topMargin = myHeight / 15;
		babrir.setLayoutParams(params); 
		babrir.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myWidth*7)/100);
	
		params = new RelativeLayout.LayoutParams((myWidth/3)*2,myHeight/8);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
 		params.addRule(RelativeLayout.BELOW,babrir.getId());
  		params.topMargin = myHeight / 55;
		bempezar.setLayoutParams(params); 
		bempezar.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myWidth*7)/100);
		
		params = new RelativeLayout.LayoutParams((myWidth/3)*2,myHeight/8);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
 		params.addRule(RelativeLayout.BELOW,bempezar.getId());
  		params.topMargin = myHeight / 55;
		batras.setLayoutParams(params); 
		batras.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myWidth*7)/100);
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
}