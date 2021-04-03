package com.pablo.gr.analyzer.engines;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MainEngine {
	private static MainEngine instance; 
	private Logger logger = LoggerFactory.getLogger(MainEngine.class);
	
	public static MainEngine getInstance()  {
		if(instance == null) {
			instance = new MainEngine();
		}
		return instance;
	}
	
	public void processTxtFile(List<String> fileStruct) {
		for(String element: fileStruct) {
			this.logger.info("char --> "+ element );
		}
	}
	
	
}
