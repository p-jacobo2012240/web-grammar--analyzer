package com.pablo.gr.analyzer.engines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import com.pablo.gr.analyzer.utils.SyntaxCharacters;
import com.pablo.gr.analyzer.models.GrammarItem;
import com.pablo.gr.analyzer.models.Terminals;
import com.pablo.gr.analyzer.models.Variables;

public class ScanerToken {
	private static ScanerToken instance;
	private Map<String, Object> objByToken = new HashMap<>();
	private SyntaxCharacters syntaxCharacters = new SyntaxCharacters();
	private List<Terminals> terminalList = new ArrayList<>();
	private List<Variables> variablesList = new ArrayList<>();
	//dummy values
	private List<Variables> variablesListDummy = new ArrayList<>();
	private List<Variables> variablesListDummyvars = new ArrayList<>();

	public static ScanerToken getInstance() {
		if (instance == null) {
			instance = new ScanerToken();
		}
		return instance;
	}
  
	public GrammarItem evaluateToken(List<String> fileStruct) {
		variablesList.clear();
		terminalList.clear();
		
		fileStruct.stream().forEach(strTkn -> {
			Map<String, Object> mapResults = this.splitedToken(strTkn);
			Object mapVariableValue = mapResults.get("variable");
			Variables tmpElement = (Variables) mapVariableValue;

			boolean isValid = true;
			isValid = variablesList.stream().anyMatch(var -> var.getCharacter().equals(tmpElement.getCharacter()));

			if (!isValid) {
				variablesList.add(new Variables(tmpElement.getCharacter()));
				isValid = true;
			}
		});

		// validate that a character of the variables is not 
		// passed as it is a derivation
		variablesList.stream().forEach(var -> {
			String element = var.getCharacter();
			terminalList.removeIf(val -> val.getCharacter().equals(element));
		});

		GrammarItem grammarItemLists = new GrammarItem(variablesList, terminalList, "lists");
		return grammarItemLists;
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
				.filter(tkn -> !tkn.equals(syntaxCharacters.EPSILON))
				.filter(tkn -> !tkn.equals(syntaxCharacters.QUOTATION_MARK_INVERSE))
				.filter(tkn -> !tkn.equals(syntaxCharacters.EMPTY_SPACE)).collect(Collectors.toList());

		stackFiltered.stream().forEach(terminalValue -> {
			this.terminalList.add(new Terminals(terminalValue));
		});
		
		//clear pile
		stackTokenChar.clear();
		return this.objByToken;
	}
	
	// generate dummy productions
	public List<Variables> getDummyProductions(List<Variables> variablesList ) {
		this.variablesListDummy.clear();
		
		for (int i=0; i < (variablesList.size() * 2 ); i++ ) {
			this.variablesListDummy.add(new Variables("-"));
		}
	
		return this.variablesListDummy;
	}
	
	public List<Variables> getDummyProductionsVars(List<Variables> variablesList ) {
		this.variablesListDummyvars.clear();
		
		
		for (Variables varls: variablesList ) {
			this.variablesListDummyvars.add(new Variables(varls.getCharacter()));
			this.variablesListDummyvars.add(new Variables(varls.getCharacter()));
		}
	
		return this.variablesListDummyvars;
	}

}
