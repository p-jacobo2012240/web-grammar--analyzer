package com.pablo.gr.analyzer.engines;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScanerToken {
	private static ScanerToken instance;
	
	public static ScanerToken getInstance() {
		if (instance == null) {
			instance = new ScanerToken();
		}
		return instance;
	}
	

	public Map<String, Object> evaluateToken(List<String> fileStruct) {
		Map<String, Object> resultEval = new HashMap<>();
		
		
		fileStruct.stream().forEach( strTkn -> {
			System.out.println("element feach " + this.splitedToken(strTkn));
			System.out.println("*************************************");
		});
		
		
		return resultEval;
	}
	
	public String splitedToken(String currentToken) {
		String[] splCurrentToken = currentToken.split("");
		
		for(String val : splCurrentToken) {
			System.out.println("tkn --> "+ val  );
		}
		
		return " ";
	}
	
	

}
