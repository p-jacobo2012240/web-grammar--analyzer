package com.pablo.gr.analyzer.engines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import com.pablo.gr.analyzer.utils.SyntaxCharacters;
import com.pablo.gr.analyzer.models.Terminals;
import com.pablo.gr.analyzer.models.Variables;

public class ScanerToken {
	private static ScanerToken instance;
	private Map<String, Object> objByToken = new HashMap<>();
	private SyntaxCharacters syntaxCharacters = new SyntaxCharacters();
	private List<Terminals> terminalList = new ArrayList<>();
	private List<Variables> variablesList = new ArrayList<>();

	public static ScanerToken getInstance() {
		if (instance == null) {
			instance = new ScanerToken();
		}
		return instance;
	}

	public Map<String, Object> evaluateToken(List<String> fileStruct) {
		Map<String, Object> resultEval = new HashMap<>();

		fileStruct.stream().forEach(strTkn -> {
			Map<String, Object> mapResults = this.splitedToken(strTkn);
			Object mapVariableValue = mapResults.get("variable");
			Variables tmpElement = (Variables) mapVariableValue;
			
			boolean isValid = true;
			isValid = this.variablesList.stream()
					.anyMatch(var -> var.getCharacter().equals(tmpElement.getCharacter()));
			
			if(!isValid) {
				this.variablesList.add( new Variables(tmpElement.getCharacter()));
				isValid = true;
			}
		});
		for(Variables val:  this.variablesList) {
			 System.out.println("values... " + val.getCharacter() );
		 }
		return resultEval;
	}

	public Map<String, Object> splitedToken(String currentToken) {
		String[] splCurrentToken = currentToken.split("");
		Stack<String> stackTokenChar = new Stack<>();

		for (String value : splCurrentToken) {
			stackTokenChar.add(value);
		}

		Variables var = new Variables(stackTokenChar.firstElement());
		this.objByToken.put("variable", var);

		List<String> stackFiltered = stackTokenChar.stream().filter(tkn -> !tkn.equals(stackTokenChar.firstElement()))
				.filter(tkn -> !tkn.equals(syntaxCharacters.QUOTATION_MARK_SIMPLE))
				.filter(tkn -> !tkn.equals(syntaxCharacters.EQUAL_SIGN))
				.filter(tkn -> !tkn.equals(syntaxCharacters.QUOTATION_MARK))
				.filter(tkn -> !tkn.equals(syntaxCharacters.PIPE))
				.filter(tkn -> !tkn.equals(syntaxCharacters.T_IN_CHAIN))
				.filter(tkn -> !tkn.equals(syntaxCharacters.F_IN_CHAIN))
				.filter(tkn -> !tkn.equals(syntaxCharacters.EPSILON)).collect(Collectors.toList());

		stackFiltered.stream().forEach(terminalValue -> {
			this.terminalList.add(new Terminals(terminalValue));
		});

		return this.objByToken;
	}

}
