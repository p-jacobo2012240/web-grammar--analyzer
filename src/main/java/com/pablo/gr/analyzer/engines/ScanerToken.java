package com.pablo.gr.analyzer.engines;

import java.util.*;
import java.util.stream.Collectors;

import com.pablo.gr.analyzer.models.RawText;
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
  
	public GrammarItem evaluateToken(List<String> fileStruct, Boolean isWithRecursion) {
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

		GrammarItem grammarItemLists = new GrammarItem(variablesList, terminals, isWithRecursion);
		return grammarItemLists;
	}

	public Map<String, Object> splitedToken(String currentToken) {
		String[] parts = currentToken.split("=");

		List<String> stackFiltered = extractTerminals(parts[1]).stream()
				.filter(tkn -> !tkn.equals(parts[0].trim()))  // remove when terminals contain a variable token
				.filter(tkn -> !tkn.equals(syntaxCharacters.QUOTATION_MARK_SIMPLE))
				.filter(tkn -> !tkn.equals(syntaxCharacters.EQUAL_SIGN))
				.filter(tkn -> !tkn.equals(syntaxCharacters.QUOTATION_MARK))
				.filter(tkn -> !tkn.equals(syntaxCharacters.PIPE))
				.filter(tkn -> !tkn.equals(syntaxCharacters.EPSILON))
				.filter(tkn -> !tkn.equals(syntaxCharacters.QUOTATION_MARK_INVERSE))
				.filter(tkn -> !tkn.equals(syntaxCharacters.EMPTY_SPACE))
				.collect(Collectors.toList());


		// fill variables
		Variables var = new Variables(parts[0]);
		this.objByToken.put("variable", var);

		// simple process can be contain duplicates tokens
		stackFiltered.stream().forEach(terminalValue -> {
			this.terminalList.add(new Terminals(terminalValue));
		});

		return this.objByToken;
	}

	public GrammarItem buildVariablesAndProductions(List<String> fileStruct, Boolean isWithRecursion ) {
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

		// get productions
		List<String> productions = findProductions(rules);

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

		if(isWithRecursion) {
			grammarItemLists.setVariablesListComplex(variablesOfProductionList);
			grammarItemLists.setTerminalsListComplex(terminalsOfProductionList);
		} else {
			grammarItemLists.setVariablesListComplexWithoutRecursionToLeft(variablesOfProductionList);
			grammarItemLists.setTerminalsListComplexWithoutRecursionToLeft(terminalsOfProductionList);
		}

		return grammarItemLists;
	}

	public List<RawText> removeRecursion(List<String> fileStruct) {
		List<String> modifiedProductions = removeLeftRecursion(fileStruct);
		return ParserFromPlainList
				.getInstance()
				.fromStringToRawTextList(modifiedProductions);
	}

	public void generateFirstAndFollowFunction(List<String> fileStructWithOutRecursion) {
		Map<String, List<List<String>>> productions = new HashMap<>();
		productions.put("S", Arrays.asList(
				Arrays.asList("T", "S'")
		));
		productions.put("S'", Arrays.asList(
				Arrays.asList("+", "T", "S'"),
				Arrays.asList()
		));
		productions.put("T", Arrays.asList(
				Arrays.asList("F", "T'")
		));
		productions.put("T'", Arrays.asList(
				Arrays.asList("*", "F", "T'"),
				Arrays.asList()
		));
		productions.put("F", Arrays.asList(
				Arrays.asList("a"),
				Arrays.asList("(", "S", ")")
		));

		// Crear una instancia del generador
		FirstFollowGenerator generator = new FirstFollowGenerator(productions);

		// Generar la función "primera" (first function)
		Map<String, Set<String>> first = generator.generateFirst();
		System.out.println("Primera (First) función:");
		for (String nonTerminal : first.keySet()) {
			System.out.println("First(" + nonTerminal + ") = " + first.get(nonTerminal));
		}

		System.out.println();

		// Generar la función "siguiente" (follow function)
		String startSymbol = "S"; // Símbolo inicial de la gramática
		Map<String, Set<String>> follow = generator.generateFollow(startSymbol);
		System.out.println("Siguiente (Follow) función:");
		for (String nonTerminal : follow.keySet()) {
			System.out.println("Follow(" + nonTerminal + ") = " + follow.get(nonTerminal));
		}
	}

	public GrammarItem  parseToFirstFunctionType(List<Variables> variableList, Boolean isFirstFunction ) {
		GrammarItem grammarItem = new GrammarItem();
		if(isFirstFunction) {
			List<Variables> firstFunctionList = new ArrayList<>();
			for(Variables v: variableList) {
				Variables temp = new Variables("P( "+ v.getCharacter() + ")");
				firstFunctionList.add(temp);
			}
			grammarItem.setVariableFirstFunctionList(firstFunctionList);
		} else {
			List<Variables> followFunctionList = new ArrayList<>();
			for(Variables vf: variableList) {
				Variables tempFollow = new Variables("Sig( "+ vf.getCharacter() + ")");
				followFunctionList.add(tempFollow);
			}
			grammarItem.setVariableFollowFunctionList(followFunctionList);
		}

		return grammarItem;
	}

	public List<String> removeLeftRecursion(List<String> productions) {
		List<String> modifiedProductions = new ArrayList<>();

		for (int i = 0; i < productions.size(); i++) {
			String currentProduction = productions.get(i);
			String[] parts = currentProduction.split("=");
			String nonTerminal = parts[0].trim();
			String[] alternatives = parts[1].trim().split("\\|");

			List<String> newAlternatives = new ArrayList<>();
			List<String> recursiveAlternatives = new ArrayList<>();

			for (String alternative : alternatives) {
				if (alternative.trim().startsWith(nonTerminal)) {
					recursiveAlternatives.add(alternative.trim().substring(1));
				} else {
					newAlternatives.add(alternative.trim());
				}
			}

			if (recursiveAlternatives.isEmpty()) {
				modifiedProductions.add(currentProduction);
			} else {
				String newNonTerminal = nonTerminal + "'";

				modifiedProductions.add(nonTerminal + " = " + String.join(" " + newNonTerminal + " | ", newAlternatives) + " " + newNonTerminal);
				modifiedProductions.add(newNonTerminal + " = " + String.join(" " + newNonTerminal + " | ", recursiveAlternatives) + " " + newNonTerminal + " | e");
			}
		}

		return modifiedProductions;
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
