package ui;

import secure.wchat.R;
import android.app.Activity;
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

public class V_Final extends Activity {
            
	private int myWidth;
	private int myHeight;
	
	private boolean esRoot = false;
	
	private ImageView logo;
	private Button bempezar;
	private TextView tinfo, troot;

	
	
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
        
        setContentView(R.layout.v_final);

		inicia();
		ajusta(); 
    }
    
    private void inicia() {
    
        bempezar = (Button) findViewById(R.id.bempezar);  
        bempezar.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					bempezar.setBackgroundResource(R.drawable.boton_azul_press);
				}
				else if (event.getAction() == MotionEvent.ACTION_UP)  {

					bempezar.setBackgroundResource(R.drawable.boton_azul);

					finish();
				}
				return false;
			}
		});
        
        troot = (TextView) findViewById(R.id.troot);  
        tinfo = (TextView) findViewById(R.id.tinfo);  
        tinfo.setText(getString(R.string.tfinal));
        logo = (ImageView) findViewById(R.id.logo);  
    }
    
    private void ajusta() {
    	
        RelativeLayout.LayoutParams params;
		
        params = new RelativeLayout.LayoutParams(myWidth/4,myWidth/4);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params.topMargin = myHeight / 65;
		logo.setLayoutParams(params); 
        		
		params = new RelativeLayout.LayoutParams(troot.getLayoutParams());
  		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
  		params.addRule(RelativeLayout.BELOW,logo.getId());
  		params.topMargin = myHeight / 25;
  		troot.setLayoutParams(params); 
  		troot.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myWidth*7)/100);
          
  		params = new RelativeLayout.LayoutParams(tinfo.getLayoutParams());
  		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
  		params.addRule(RelativeLayout.BELOW,troot.getId());
  		params.topMargin = myHeight / 25;
  		tinfo.setLayoutParams(params); 
  		tinfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myWidth*5)/100);
  		tinfo.setGravity(Gravity.CENTER);
		
        params = new RelativeLayout.LayoutParams((myWidth/2),myHeight/8);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params.addRule(RelativeLayout.BELOW,tinfo.getId());
		params.topMargin = myHeight / 8;
		bempezar.setLayoutParams(params); 
		bempezar.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myWidth*8)/100);
    }
    
}