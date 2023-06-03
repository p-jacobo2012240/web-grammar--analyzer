package com.pablo.gr.analyzer.engines;

import java.util.List;

import com.pablo.gr.analyzer.models.RawText;
import com.pablo.gr.analyzer.models.Terminals;
import com.pablo.gr.analyzer.models.Variables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pablo.gr.analyzer.models.GrammarItem;

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

		GrammarItem listVariablesAndTerminals = ScanerToken
				.getInstance().evaluateToken(fileStruct, true);
		
		grammarItem.setRawTextList(
				ParserFromPlainList
				.getInstance()
				.fromStringToRawTextList(fileStruct)
		);

		/**
		 * assignment of variables and terminals
		 * identified as [V, T] in UI
		 * */
		grammarItem.setTerminalsList(listVariablesAndTerminals.getTerminalsList());
		grammarItem.setVariablesList(listVariablesAndTerminals.getVariablesList());


		/**
		 * assignment of variables and terminals
		 * identified as [Var, Produc] in UI
		 * */

		GrammarItem variablesAndProductions = ScanerToken
				.getInstance()
				.buildVariablesAndProductions(fileStruct, true );

		grammarItem.setVariablesListComplex(variablesAndProductions.getVariablesListComplex());
		grammarItem.setTerminalsListComplex(variablesAndProductions.getTerminalsListComplex());

		/**
		 * assignment of raw text recursivity to the left
		 * identified as [ Sin recursividad izq ] in UI
		 * */

		grammarItem.setRawTextWithoutLeftRecursion(
				ScanerToken
					.getInstance()
					.removeRecursion(fileStruct)
		);


		/**
		 * assignment of variables and terminals
		 * whithout recursion to left
		 * identified as [V, T] in UI
		 * */

		ScanerToken skToken = new ScanerToken();

		GrammarItem variablesAndTerminalsWithoutRecursionFromLeft = skToken
				.evaluateToken(ParserFromPlainList
						.getInstance().fromRawTextListToString(grammarItem.getRawTextWithoutLeftRecursion()), false );

		grammarItem.setVariablesListWithoutRecursionToLeft(variablesAndTerminalsWithoutRecursionFromLeft
				.getVariablesListWithoutRecursionToLeft());
		grammarItem.setTerminalsListWithoutRecursionToLeft(
				listVariablesAndTerminals.getTerminalsList());


		/**
		 * assignment of variables and terminals
		 * whithout recursion to left
		 * identified as [Var, Produc] in UI
		 * */

		GrammarItem variablesAndProductionsWlr = skToken
				.buildVariablesAndProductions(ParserFromPlainList
						.getInstance()
						.fromRawTextListToString(grammarItem.getRawTextWithoutLeftRecursion()), false );

		grammarItem.setVariablesListComplexWithoutRecursionToLeft(variablesAndProductionsWlr
				.getVariablesListComplexWithoutRecursionToLeft());
		grammarItem.setTerminalsListComplexWithoutRecursionToLeft(variablesAndProductionsWlr
				.getTerminalsListComplexWithoutRecursionToLeft());

		/**
		 * Generation of function first
		 * and follow function
		 * **/

		ScanerToken.getInstance().generateFirstAndFollowFunction();

		return grammarItem;
	}
	
	
}
