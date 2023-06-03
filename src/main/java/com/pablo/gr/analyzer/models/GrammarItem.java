package com.pablo.gr.analyzer.models;

import java.util.List;

public class GrammarItem {
	/*
	 * this is the final wrapper
	 * that contains the object to
	 * show in the frontend made with vue
	 */
	private List<RawText> rawTextList;
	private List<Variables> variablesList;
	private List<Terminals> terminalsList;
	private String simpleFalg;
	// using for create variables and terms [complex]
	private List<Variables> variablesListComplex;
	private List<Variables> terminalsListComplex;
	// using for fill the raw text without recursion to left
	private List<RawText> rawTextWithoutRecursionToLeft;

	private List<Variables> variablesListWithoutRecursionToLeft;

	private List<Terminals> terminalsListWithoutRecursionToLeft;

	private List<Variables> variablesListComplexWithoutRecursionToLeft;

	private List<Variables> terminalsListComplexWithoutRecursionToLeft;

	public List<Variables> getTerminalsListComplex() {
		return terminalsListComplex;
	}

	private List<Variables> variableFirstFunctionList;

	private List<Variables> variableFollowFunctionList;


	public void setTerminalsListComplex(List<Variables> terminalsListComplex) {
		this.terminalsListComplex = terminalsListComplex;
	}

	public List<Variables> getVariablesListComplex() {
		return variablesListComplex;
	}

	public void setVariablesListComplex(List<Variables> variablesListComplex) {
		this.variablesListComplex = variablesListComplex;
	}

	public List<Variables> getVariablesList() {
		return variablesList;
	}

	public void setVariablesList(List<Variables> variablesList) {
		this.variablesList = variablesList;
	}


	public List<Terminals> getTerminalsList() {
		return terminalsList;
	}

	public void setTerminalsList(List<Terminals> terminalsList) {
		this.terminalsList = terminalsList;
	}

	public GrammarItem() {}

	public GrammarItem(List<Variables> variablesList, List<Terminals> terminalsList, Boolean isWithRecursion) {
		if(isWithRecursion) {
			this.variablesList = variablesList;
			this.terminalsList = terminalsList;
		} else {
			this.variablesListWithoutRecursionToLeft = variablesList;
			this.terminalsListWithoutRecursionToLeft = terminalsList;
		}
	}

	public GrammarItem(List<RawText> rawTextList) {
		super();
		this.rawTextList = rawTextList;
	}

	public List<RawText> getRawTextList() {
		return rawTextList;
	}
	public void setRawTextList(List<RawText> rawTextList) {
		this.rawTextList = rawTextList;
	}

	public List<RawText> getRawTextWithoutLeftRecursion() {
		return rawTextWithoutRecursionToLeft;
	}
	public void setRawTextWithoutLeftRecursion(List<RawText> productions) {
		this.rawTextWithoutRecursionToLeft = productions;
	}

	public void setVariablesListWithoutRecursionToLeft(List<Variables> vWithoutRecursion) {
		this.variablesListWithoutRecursionToLeft = vWithoutRecursion;
	}

	public List<Variables> getVariablesListWithoutRecursionToLeft() {
		return this.variablesListWithoutRecursionToLeft;
	}

	public void setTerminalsListWithoutRecursionToLeft(List<Terminals> tWithoutRecursion) {
		this.terminalsListWithoutRecursionToLeft = tWithoutRecursion;
	}

	public List<Terminals> getTerminalsListWithoutRecursionToLeft() {
		return this.terminalsListWithoutRecursionToLeft;
	}

	public void setVariablesListComplexWithoutRecursionToLeft(List<Variables> vProductionsWithoutRecursion) {
		this.variablesListComplexWithoutRecursionToLeft = vProductionsWithoutRecursion;
	}

	public  List<Variables> getVariablesListComplexWithoutRecursionToLeft() {
		return this.variablesListComplexWithoutRecursionToLeft;
	}

	public void setTerminalsListComplexWithoutRecursionToLeft(List<Variables> tProductionWithoutRecursion) {
		this.terminalsListComplexWithoutRecursionToLeft = tProductionWithoutRecursion;
	}

	public List<Variables> getTerminalsListComplexWithoutRecursionToLeft() {
		return terminalsListComplexWithoutRecursionToLeft;
	}

	public void setVariableFirstFunctionList(List<Variables> vFirstFunctionList) {
		this.variableFirstFunctionList = vFirstFunctionList;
	}

	public  List<Variables> getVariableFirstFunctionList() {
		return this.variableFirstFunctionList;
	}

	public void setVariableFollowFunctionList(List<Variables> vFollowFunctionList) {
		this.variableFollowFunctionList = vFollowFunctionList;
	}

	public List<Variables> getVariableFollowFunctionList() {
		return this.variableFollowFunctionList;
	}
}
