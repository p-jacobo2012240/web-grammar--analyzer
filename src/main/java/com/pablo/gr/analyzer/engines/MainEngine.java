package com.pablo.gr.analyzer.engines;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pablo.gr.analyzer.models.GrammarItem;
import com.pablo.gr.analyzer.models.RawText;
import com.pablo.gr.analyzer.engines.ParserFromPlainList;


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
		
		grammarItem.setRawTextList(
				ParserFromPlainList
				.getInstance()
				.fromStringToRawTextList(fileStruct)
		);
		
	
		return grammarItem;
	}
	
	
}
