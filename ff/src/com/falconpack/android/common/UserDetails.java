package com.falconpack.android.common;

public class UserDetails {

//	value[0] = prefs.getString(USERNAME, "");
//	value[1] = prefs.getString(PASSWORD, "");
//	value[2] = prefs.getString(ID, "");
//	value[3] = prefs.getString(NAME, "");
//	value[4] = prefs.getString(ISREMEMBER, "false");
//	value[5] = prefs.getString(M_NAME, "");
//	value[6] = prefs.getString(M_ID, "");
//	value[7] = prefs.getString(D_ID, "");
//	value[8] = prefs.getString(U_ID,"");
	
	
	String username,password,id,name,isRemember,mname,mid,did,uid;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsRemember() {
		return isRemember;
	}

	public void setIsRemember(String isRemember) {
		this.isRemember = isRemember;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
	
}
