package com.jmigration.db.jmigration;

import java.util.List;

public class Utility {

	public static String ToCsvString(List<String> items) {
		if (items == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
        for(String str:items){
            if(sb.length() != 0){
                sb.append(",");
            }
            sb.append(str);
        }
        return sb.toString();
	}

}
