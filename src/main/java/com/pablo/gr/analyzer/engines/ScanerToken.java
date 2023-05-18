package com.pablo.gr.analyzer.engines;

import java.util.*;
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

		// validate that terminalList not contains duplicates
		ArrayList<String> notRepeated = new ArrayList<>();
		ArrayList<Terminals> terminals = new ArrayList<>();
		for(Terminals element: this.terminalList) {
			notRepeated.add(element.getCharacter());
		}

		// TEMP this is a simple way for it
		notRepeated.stream().distinct().forEach(ele -> terminals.add(new Terminals(ele)));

		GrammarItem grammarItemLists = new GrammarItem(variablesList, terminals, "lists");
		return grammarItemLists;
	}

	public Map<String, Object> splitedToken(String currentToken) {
		String[] parts = currentToken.split("=");

		List<String> stackFiltered = extractTerminals(parts[1]).stream()
				.filter(tkn -> !tkn.equals(parts[0].trim()))
				.filter(tkn -> !tkn.equals(syntaxCharacters.QUOTATION_MARK_SIMPLE))
				.filter(tkn -> !tkn.equals(syntaxCharacters.EQUAL_SIGN))
				.filter(tkn -> !tkn.equals(syntaxCharacters.QUOTATION_MARK))
				.filter(tkn -> !tkn.equals(syntaxCharacters.PIPE))
				.filter(tkn -> !tkn.equals(syntaxCharacters.EPSILON))
				.filter(tkn -> !tkn.equals(syntaxCharacters.QUOTATION_MARK_INVERSE))
				.filter(tkn -> !tkn.equals(syntaxCharacters.EMPTY_SPACE)).collect(Collectors.toList());


		// fill variables
		Variables var = new Variables(parts[0]);
		this.objByToken.put("variable", var);

		// simple process can be contain duplicates tokens
		stackFiltered.stream().forEach(terminalValue -> {
			this.terminalList.add(new Terminals(terminalValue));
		});

		return this.objByToken;
	}

	public GrammarItem buildVariablesAndProductions(List<String> fileStruct) {
		/**
		 * Important Note for the time in the UI show the second part of object like
		 * UI -> setVariablesListComplex =  Var
		 * UI -> setTerminalsListComplex = Produc
		 * **/
		List<String> rules = new ArrayList<>();
		GrammarItem grammarItemLists = new GrammarItem();

		for (String line : fileStruct) {
			rules.add(line);
		}

		List<String> productions = findProductions(rules);
		for (String production : productions) {
			System.out.println("production = " + production);
		}

		String[] rawSyntax = productions.toArray(new String[0]);
		List<String> variableList = new ArrayList<>();
		List<String> productionList = new ArrayList<>();
		for (String inputString : rawSyntax) {
			String[] parts = inputString.split("=");
			String variable = parts[0].trim();
			String production = parts[1].trim().replaceAll("'", "");
			variableList.add(variable);
			productionList.add(production);
		}
		System.out.println("Variables: " + variableList);
		System.out.println("Productions: " + productionList);

		// fill a custom object
		List<Variables> variablesOfProductionList = new ArrayList<>();
		for(String v : variableList) {
			Variables newTerminal = new Variables(v);
			variablesOfProductionList.add(newTerminal);
		}

		// fill a custom object
		List<Variables> terminalsOfProductionList = new ArrayList<>();
		for(String t : productionList) {
			Variables newProduction = new Variables(t);
			terminalsOfProductionList.add(newProduction);
		}

		grammarItemLists.setVariablesListComplex(variablesOfProductionList);
		grammarItemLists.setTerminalsListComplex(terminalsOfProductionList);
		return grammarItemLists;
	}

	public List<String> extractTerminals(String rightSideText ) {
		List<String> characters = new ArrayList<>();

		int index = 0;
		while (index < rightSideText.length()) {
			char c = rightSideText.charAt(index);
			if (c == '\'') {
				StringBuilder sb = new StringBuilder();
				index++;
				while (index < rightSideText.length()) {
					char currentChar = rightSideText.charAt(index);
					if (currentChar == '\'') {
						break;
					}
					sb.append(currentChar);
					index++;
				}
				characters.add(sb.toString());
			}
			index++;
		}

		return characters;
	}

	public List<String> findProductions(List<String> grammarRules) {
		List<String> productions = new ArrayList<>();
		Set<String> alreadyFound = new HashSet<>(); // to avoid duplicates
		for (String rule : grammarRules) {
			String[] parts = rule.split("=");
			String leftSideVariable = parts[0].trim();
			if (leftSideVariable != null) {
				String[] options = parts[1].trim().split("\\|");
				for (String option : options) {
					String production = option.trim();
					if (!alreadyFound.contains(production)) {
						productions.add(leftSideVariable + " = " + production);
						alreadyFound.add(production);
					}
				}
			}
		}
		return productions;
	}
}
