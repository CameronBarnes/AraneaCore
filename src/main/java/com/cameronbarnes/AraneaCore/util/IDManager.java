package com.cameronbarnes.AraneaCore.util;

import java.util.Random;

public class IDManager {
	
	public static int getNewID() {
		return new Random().nextInt((999999 - 1) + 1) + 1; //TODO actually make this class function
	}
	
}
