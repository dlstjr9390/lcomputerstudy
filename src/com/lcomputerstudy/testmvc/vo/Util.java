package com.lcomputerstudy.testmvc.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	
	public String RandomName() {
		String RandomName = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		int RandomNumber = (int)(Math.random()*99999);
		String strRandomNumber = String.valueOf(RandomNumber);
		Date time = new Date();
		
		String time1 = df.format(time);
		RandomName = time1 + strRandomNumber;
					
		return RandomName;
		
	}
	
}
