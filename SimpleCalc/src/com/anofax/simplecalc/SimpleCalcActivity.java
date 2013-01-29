package com.anofax.simplecalc;

import java.util.Random;

import com.anofax.simplecalc.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class SimpleCalcActivity extends Activity implements TextWatcher, OnEditorActionListener {

	String _expectedText;
	Random _rand = new Random();
	int _ngood=0;
	int _nbad=0;
	public void challenge( View v ){
		findViewById(R.id.button1).setVisibility(View.INVISIBLE);
		ImageView iv = (ImageView) findViewById(R.id.imageView1);
		iv.setVisibility(View.INVISIBLE);
		TextView tv1 = (TextView)findViewById(R.id.textView1);
		TextView tv2 = (TextView)findViewById(R.id.textView2);
		TextView tv3 = (TextView)findViewById(R.id.textView3);
		tv3.setText( _ngood+" : " + _nbad );
		EditText ed = (EditText) findViewById(R.id.editText1);
		ed.setText("");
		ed.requestFocus();
		getWindow().setSoftInputMode (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		CheckBox check = (CheckBox)findViewById(R.id.checkBox1);
		boolean hard = check.isChecked();
		while(true){
			int a = _rand.nextInt(20)+1;
			int b = _rand.nextInt(21)-10;
			if( a+b<0 || a+b>20 )
				continue;
			int c = 0;
			if( hard ) 
				c = _rand.nextInt(21)-10;
			int d = a+b+c;
			if( d<0 || d>20 )
				continue;
			String s1 = ""+a;
			if( b>=0 )
				s1 += "+";
			else
				s1 += "-";
			_expectedText = ""+Math.abs(b);
			String s2 = "";
			if( c != 0 )
				s2 += (c>=0 ? "+" : "") + c;
			s2 += "=" + d;
			tv1.setText(s1);
			tv2.setText(s2);
			break;
		}
	}
				
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		((EditText)findViewById(R.id.editText1)).addTextChangedListener(this);
		((EditText)findViewById(R.id.editText1)).setOnEditorActionListener(this);
		challenge(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_book_tut_layout, menu);
		return true;
	}

	@Override
	public void afterTextChanged(Editable s) {
		String text = s.toString();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		ImageView iv = (ImageView) findViewById(R.id.imageView1);
        if (actionId == EditorInfo.IME_ACTION_DONE) {
	    	EditText ed = (EditText) v;
	    	String text = ed.getText().toString();
			if( text.equals(_expectedText) ){
				iv.setImageResource(R.drawable.good);
				findViewById(R.id.button1).setVisibility(View.VISIBLE);
				_ngood += 1;
			} else {
				iv.setImageResource(R.drawable.bad);
				_nbad += 1;
			}
			iv.setVisibility(View.VISIBLE);
			TextView tv3 = (TextView)findViewById(R.id.textView3);
			tv3.setText( _ngood+" : " + _nbad );
            return true;
        } else {
			iv.setVisibility(View.INVISIBLE);        	
	        return false;
        }
    }	
}
