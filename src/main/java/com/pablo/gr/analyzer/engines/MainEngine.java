package com.pablo.gr.analyzer.engines;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pablo.gr.analyzer.models.GrammarItem;
import com.pablo.gr.analyzer.engines.ParserFromPlainList;
import com.pablo.gr.analyzer.engines.ScanerToken;


public class MainEngine {
	private static MainEngine instance; 
	private Logger logger = LoggerFactory.getLogger(MainEngine.class);
	
	public static MainEngine getInstance()  {
		if(instance == null) {
			instance = new MainEngine();
		}
		return instance;
	}
	  
	public GrammarItem processTxtFile(List<String> fileStruct) {
		GrammarItem grammarItem = new GrammarItem();
		
		
		GrammarItem listVariablesAndTerminals = ScanerToken
				.getInstance().evaluateToken(fileStruct);
		
		
		
		grammarItem.setRawTextList(
				ParserFromPlainList
				.getInstance()
				.fromStringToRawTextList(fileStruct)
		);
		
		grammarItem.setTerminalsList(listVariablesAndTerminals.getTerminalsList());
		grammarItem.setVariablesList(listVariablesAndTerminals.getVariablesList());
		
		// set dummy data
		grammarItem.setVariablesListComplex(ScanerToken.getInstance().getDummyProductionsVars(grammarItem.getVariablesList())); 
		grammarItem.setTerminalsListComplex(ScanerToken.getInstance().getDummyProductions(grammarItem.getVariablesList()));
		
		return grammarItem;
	}
	
	
}
