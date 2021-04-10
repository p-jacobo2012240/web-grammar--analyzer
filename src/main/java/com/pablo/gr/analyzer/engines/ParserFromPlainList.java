package com.pablo.gr.analyzer.engines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import com.pablo.gr.analyzer.models.RawText;
import com.pablo.gr.analyzer.utils.SyntaxCharacters;

public class ParserFromPlainList {
	private static ParserFromPlainList instance;
	private SyntaxCharacters syntaxCharacters = new SyntaxCharacters();
	boolean integrityChain = true;;
	private Map<String, Object> configMap = new HashMap<>();
	

	public static ParserFromPlainList getInstance() {
		if (instance == null) {
			instance = new ParserFromPlainList();
		}
		return instance;
	}

	/**
	 * parse simple token string to List<RawText>
	 * 
	 * @param List<String>
	 * @return List<RawText>
	 * 
	 */
	public List<RawText> fromStringToRawTextList(List<String> strList) {
		List<RawText> rawTextList = new ArrayList<>();
		rawTextList = strList.stream().map(strToken -> new RawText(strToken)).collect(Collectors.toList());
		return rawTextList;
	}

	/**
	 * 
	 * Validate Vector Vertical Terminals (length ≥ 1) Vector Vertical Variables
	 * (length ≥ 1)
	 * 
	 * @param List<String>
	 * @return Map<String, Object>
	 * 
	 */
	public Map<String, Object> verificationofIntegrityInList(List<String> strList) {
		 strList.stream().forEach(valueT -> {
			 String[] splValue = valueT.split("");
			 List<String> stackValidations = new ArrayList<>();
			      
			 for(String vl: splValue) {
				 stackValidations.add(vl);
			 }
			 
			 System.out.println("tamanio " + strList.size());
			 
			 if( stackValidations.contains("â") || stackValidations.contains("€") || stackValidations.contains("™")) {
				 System.out.println("cadena invalida....");
				 this.integrityChain = false;
			 } else {
				 if (strList.size() >= 1 ) {
					 System.out.println("cadena limpia... " + valueT );
				 } else {
					 System.out.println("archivo en blanco....");
				 }
			 }
 			 
			 
			 			 
		
					 
	
		 });
		

		return configMap;
	}

}
