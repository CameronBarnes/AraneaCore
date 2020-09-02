package com.cameronbarnes.AraneaCore.usermanagment;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class User {
	
	private int ID;
	private Role mRole;
	private String mEmail;
	private HashMap<String, String> mOtherContactInformation; //Contact type/Value
	
	public User(int id, String email, HashMap<String, String> otherContactInformation, Role role) {
		
		ID = id;
		mRole = role;
		mEmail = email;
		mOtherContactInformation = otherContactInformation;
		
	}
	
	@NotNull
	@Contract(" -> new")
	public static User getAnon() {
		return new User(0, "", new HashMap<>(), Role.Anon);
	}
	
	public int getID() {
		return ID;
	}
	
	public Role getRole() {
		return mRole;
	}
	
	public void setRole(Role role) {
		mRole = role;
	}
	
	public String getEmail() {
		return mEmail;
	}
	
	public void setEmail(String email) {
		mEmail = email;
	}
	
	public Map<String, String> getOtherContactInformation() {
		return mOtherContactInformation;
	}
	
	public void setOtherContactInformation(HashMap<String, String> otherContactInformation) {
		mOtherContactInformation = otherContactInformation;
	}
	
	public void removeContactInformation(String key) {
		mOtherContactInformation.remove(key);
	}
	
	public void addContactInformation(String key, String value) {
		mOtherContactInformation.put(key, value);
	}
	
	public enum Role {
		User,
		Developer,
		ProjectManager,
		Admin,
		Anon
	}
	
}
