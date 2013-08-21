package com.droidtest;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RecieverActivity extends Activity {
	
	private EditText edtBackLetter;
	private TextView tvwPhoneAction;
	private TextView tvwEmailAction;
	private PersonDataCls m_PersonInst=null;
	private final String LOG_TAG="com.droidtest - RecieverActivity";
	
	/** Occurs on activity creation */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reciever);
		
		try{
			edtBackLetter=(EditText)findViewById(R.id.edtBackLetter);
			tvwPhoneAction=(TextView)findViewById(R.id.tvwPhoneAction);
			tvwEmailAction=(TextView)findViewById(R.id.tvwEmailAction);
			Intent intnt=this.getIntent();
			m_PersonInst=(PersonDataCls)(intnt.getParcelableExtra(PersonDataCls.class.getCanonicalName()));
			
			if(m_PersonInst!=null){
				((TextView)findViewById(R.id.tvwNameVal)).setText(m_PersonInst.m_StrName);
				((TextView)findViewById(R.id.tvwBirthDateVal)).setText(m_PersonInst.m_BirthDate.toString());
				((TextView)findViewById(R.id.tvwSexVal)).setText(m_PersonInst.m_Sex);
				((TextView)findViewById(R.id.tvwPositionVal)).setText(m_PersonInst.m_StrPosition);
				((TextView)findViewById(R.id.tvwSalaryVal)).setText(""+m_PersonInst.m_Salary);
				tvwPhoneAction.setText(m_PersonInst.m_StrPhoneNumber);
				tvwEmailAction.setText(m_PersonInst.m_StrEmail);
				Linkify.addLinks(tvwPhoneAction, Linkify.ALL);
				Linkify.addLinks(tvwEmailAction, Linkify.ALL);
			}
		}
		catch(Exception ex){
			String strErr="Ошибка в onCreate ctor - "+ex.getMessage();
			Log.d(LOG_TAG,strErr);
			return;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_reciever, menu);
		return true;
	}

	/** Runs phone app */
	public void tvwPhoneAction_onClick(View vw){
		try{
			if(!tvwPhoneAction.getText().toString().trim().equals("")){
				Intent intnt=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+tvwPhoneAction.getText().toString().trim()));
				startActivity(intnt);
			}
		}
		catch(Exception ex){
			String strErr="Ошибка в tvwPhoneAction_onClick - "+ex.getMessage();
			Log.d(LOG_TAG,strErr);
			return;
		}
	}
	
	/** Runs e-mail app */
	public void tvwEmailAction_onClick(View vw){
		try{
			if(!tvwEmailAction.getText().toString().trim().equals("")){
				Intent intnt = new Intent(Intent.ACTION_SEND);
				intnt.setType("text/plain");
				intnt.putExtra(Intent.EXTRA_EMAIL  , new String[]{tvwEmailAction.getText().toString().trim()});
				intnt.putExtra(Intent.EXTRA_SUBJECT, "Добавьте заголовок");
				intnt.putExtra(Intent.EXTRA_TEXT   , "Добавьте сообщение");
				startActivity(intnt);
			}
		}
		catch(Exception ex){
			String strErr="Ошибка в tvwEmailAction_onClick - "+ex.getMessage();
			Log.d(LOG_TAG,strErr);
			return;
		}
	}
	
	/** Sends back letter */
	public void btnSendBack_onClick(View vw){
		try{
			if(edtBackLetter.getText().toString().trim().equals(""))
				throw new Exception("Ответ не может быть пустым");
			
			Intent intent = new Intent();
			intent.putExtra("callback", edtBackLetter.getText().toString().trim());
			setResult(RESULT_OK, intent);
			finish();
		}
		catch(Exception ex){
			String strErr="Ошибка в tvwPhoneAction_onClick - "+ex.getMessage();
			Log.d(LOG_TAG,strErr);
			return;
		}
	}
}
