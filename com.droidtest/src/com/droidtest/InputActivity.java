package com.droidtest;

import java.sql.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.widget.DatePicker;

public class InputActivity extends Activity {

	private Spinner spnSex;
	private EditText edtName;
	private Button btnBirthDate;
	private ArrayAdapter<CharSequence> m_SexAdapter=null;
	private String m_CallBack="";
	private final String LOG_TAG="com.droidtest - InputActivity";
	private final int DIALOG_DATE=1;
	private final int DIALOG_CALLBACK=2;
	
	/** Occurs on activity creation */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input);
		
		try{
			this.setTitle(R.string.title_activity_input);
			spnSex=(Spinner)this.findViewById(R.id.spnSex);
			edtName=(EditText)this.findViewById(R.id.edtName);
			btnBirthDate=(Button)this.findViewById(R.id.btnBirthDate);
			m_SexAdapter = ArrayAdapter.createFromResource(
			        this, R.array.sex, android.R.layout.simple_spinner_dropdown_item);
			spnSex.setAdapter(m_SexAdapter);
			spnSex.setPrompt("Выберите пол");
			spnSex.setSelection(0);
		}
		catch(Exception ex){
			String strErr="Ошибка в onCreate - "+ex.getMessage();
			Log.d(LOG_TAG,strErr);
			return;
		}
	}

	/** Sends entered data */
	public void btnSend_onClick(View vw){
		try{
			if(edtName.getText().toString().trim().equals(""))
				throw new Exception("Не заполнено ФИО");
			
			PersonDataCls personDataInst=new PersonDataCls(
					edtName.getText().toString().trim(),
					btnBirthDate.getTag()==null?Date.valueOf("2013-06-25"):(Date)btnBirthDate.getTag(),
					spnSex.getSelectedItem()!=null&&spnSex.getSelectedItemPosition()>-1?
							(String)(m_SexAdapter.getItem(spnSex.getSelectedItemPosition())):getString(R.string.defSex),
					((EditText)this.findViewById(R.id.edtPosition)).getText().toString().trim(),
					Float.parseFloat(((EditText)this.findViewById(R.id.edtSalary)).getText().toString().trim()),
					((EditText)this.findViewById(R.id.edtPhone)).getText().toString().trim(),
					((EditText)this.findViewById(R.id.edtEmail)).getText().toString().trim()
					);
			Intent intnt=new Intent(this, RecieverActivity.class);
			intnt.putExtra(PersonDataCls.class.getCanonicalName(),personDataInst);
			startActivityForResult(intnt, 1);
		}
		catch(Exception ex){
			String strErr="Ошибка в btnSend_onClick - "+ex.getMessage();
			Log.d(LOG_TAG,strErr);
			return;
		}
	}
	
	/** Selects birthday*/
	public void btnBirthDate_onClick(View vw){
		try{
			showDialog(DIALOG_DATE);
		}
		catch(Exception ex){
			String strErr="Ошибка в btnBirthDate_onClick - "+ex.getMessage();
			Log.d(LOG_TAG,strErr);
			return;
		}
	}

	/** Creates date dialog */
	protected Dialog onCreateDialog(int dialogId) {
	    try{  
			if(dialogId == DIALOG_DATE){
				DatePickerDialog tpd = new DatePickerDialog(this, dateDialogCallBack, 2013, 1, 1);
		        return tpd;
			}
			
			if(dialogId==DIALOG_CALLBACK){
				AlertDialog.Builder adb = new AlertDialog.Builder(this);
				adb.setTitle(R.string.dlgTitle);
				adb.setMessage(m_CallBack);
				adb.setIcon(android.R.drawable.ic_dialog_info);
				//adb.setPositiveButton(R.string.yes, myClickListener);
				return adb.create();
			}
	    }
	    catch(Exception ex){
	    	String strErr="Ошибка в onCreateDialog - "+ex.getMessage();
			Log.d(LOG_TAG,strErr);
	    }
	      
	    return super.onCreateDialog(dialogId);
	}
	
	/** Date set handler */
	private OnDateSetListener dateDialogCallBack = new OnDateSetListener() {

	    public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
	    	try{
		    	String strDate=""+year + "-" + (monthOfYear+1>9?""+monthOfYear+1:"0"+(monthOfYear+1)) + "-" + (dayOfMonth>9?""+dayOfMonth:"0"+dayOfMonth) ;
		    	btnBirthDate.setTag(Date.valueOf(strDate));
		    	btnBirthDate.setText("Дата рождения - "+strDate );
	    	}
	    	catch(Exception ex){
	    		String strErr="Ошибка в onDateSet - "+ex.getMessage();
				Log.d(LOG_TAG,strErr);
				return;
	    	}
	    }
	};
	  
	/** Occurs on getting callback from child form */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try{
			if(resultCode==RESULT_CANCELED)
	    		return;
	    	
	    	if (data == null) 
		    	return;
	    	
	    	m_CallBack=data.getStringExtra("callback");
	    	showDialog(DIALOG_CALLBACK);
		}
		catch(Exception ex){
			String strErr="Ошибка в onActivityResult - "+ex.getMessage();
			Log.d(LOG_TAG,strErr);
			return;
		}
	}
	
	/** Occurs on context menu creation */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_input, menu);
		return true;
	}

}
