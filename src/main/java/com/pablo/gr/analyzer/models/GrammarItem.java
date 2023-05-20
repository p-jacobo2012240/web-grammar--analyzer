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

	public List<Variables> getTerminalsListComplex() {
		return terminalsListComplex;
	}


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

	public GrammarItem(List<Variables> variablesList, List<Terminals> terminalsList, String simpleFalg) {
		super();
		this.variablesList = variablesList;
		this.terminalsList = terminalsList;
		this.simpleFalg = simpleFalg;
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

}
