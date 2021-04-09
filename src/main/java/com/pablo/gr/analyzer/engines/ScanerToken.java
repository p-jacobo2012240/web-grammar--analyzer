package com.pablo.gr.analyzer.engines;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import com.pablo.gr.analyzer.utils.SyntaxCharacters;

public class ScanerToken {
	private static ScanerToken instance;
	private Map<String, Object> objByToken = new HashMap<>();
	private SyntaxCharacters syntaxCharacters = new SyntaxCharacters();
	
	public static ScanerToken getInstance() {
		if (instance == null) {
			instance = new ScanerToken();
		}
		return instance;
	}

	public Map<String, Object> evaluateToken(List<String> fileStruct) {
		Map<String, Object> resultEval = new HashMap<>();

		fileStruct.stream().forEach(strTkn -> {
			Map<String, Object>  mapResults = this.splitedToken(strTkn);
			System.out.println("val " +   mapResults  );
		});

		return resultEval;
	}

	public Map<String, Object> splitedToken(String currentToken) {
		String[] splCurrentToken = currentToken.split("");
		Stack<String> stackTokenChar = new Stack<>();

		for (String value : splCurrentToken) {
			stackTokenChar.add(value);
		}
		
		this.objByToken.put("variable", stackTokenChar.firstElement());
		
		List<String> stackFiltered = stackTokenChar
			.stream()
			.filter(tkn -> !tkn.equals(stackTokenChar.firstElement()))
			.filter(tkn -> !tkn.equals(syntaxCharacters.QUOTATION_MARK_SIMPLE))
			.filter(tkn -> !tkn.equals(syntaxCharacters.EQUAL_SIGN))
			.filter(tkn -> !tkn.equals(syntaxCharacters.QUOTATION_MARK))
			.collect(Collectors.toList());
		
		this.objByToken.put("terminalList", stackFiltered);

		return this.objByToken;
	}

}
