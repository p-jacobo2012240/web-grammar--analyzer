package com.pablo.gr.analyzer.engines;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.pablo.gr.analyzer.models.RawText;

public class ParserFromPlainList {
	private static ParserFromPlainList instance;
	
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
	public List<RawText> fromStringToRawTextList (List<String> strList ) {
		List<RawText> rawTextList = new ArrayList<>();
		rawTextList = strList
				.stream()
				.map(strToken -> new RawText(strToken))
				.collect(Collectors.toList());
		return rawTextList;
	}
	
	
}
