package com.cameronbarnes.AraneaCore.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ResourceBundle;

public class Utils {
	
	@NotNull
	@Contract(pure = true)
	public static String[] getStringsFromKeyArray(@NotNull String[] keys, @NotNull ResourceBundle lang) {
		
		String[] strings = new String[keys.length];
		for (int i = 0; i < keys.length; i++) {
			
			strings[i] = lang.getString(keys[i]);
			
		}
		
		return strings;
		
	}
	
}
