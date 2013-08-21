package com.droidtest;

import java.sql.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class PersonDataCls implements Parcelable {

	public String m_StrName="";
	public Date m_BirthDate=null;
	public String m_Sex="";
	public String m_StrPosition="";
	public float m_Salary=0;
	public String m_StrPhoneNumber="";
	public String m_StrEmail="";
	
	/** Default constructor */
	public PersonDataCls(){}
	
	/** Not Default constructor */
	public PersonDataCls(Parcel prcl){
		m_StrName=prcl.readString();
		m_BirthDate=Date.valueOf(prcl.readString());
		m_Sex=prcl.readString();
		m_StrPosition=prcl.readString();
		m_Salary=prcl.readFloat();
		m_StrPhoneNumber=prcl.readString();
		m_StrEmail=prcl.readString();
	}
	
	/** Not Default constructor */
	public PersonDataCls(String strName,Date birthDate,String sex, String strPosition, 
			float salary,String strPhoneNumber,String strEmail){
		m_StrName=strName;
		m_BirthDate=birthDate;
		m_Sex=sex;
		m_StrPosition=strPosition;
		m_Salary=salary;
		m_StrPhoneNumber=strPhoneNumber;
		m_StrEmail=strEmail;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(m_StrName);
		dest.writeString(m_BirthDate.toString());
		dest.writeString(m_Sex);
		dest.writeString(m_StrPosition);
		dest.writeFloat(m_Salary);
		dest.writeString(m_StrPhoneNumber);
		dest.writeString(m_StrEmail);
	}
	
	public static final Parcelable.Creator<PersonDataCls> CREATOR = new Parcelable.Creator<PersonDataCls>() { 
		public PersonDataCls createFromParcel(Parcel prcl) {
	    	return new PersonDataCls(prcl);
		}

	    public PersonDataCls[] newArray(int size) {
	      return new PersonDataCls[size];
	    }
	};
}
