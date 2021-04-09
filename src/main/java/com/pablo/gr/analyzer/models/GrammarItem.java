package com.pablo.gr.analyzer.models;

import java.util.List;

public class GrammarItem {
	/*
	 * this is the final wrapper 
	 * that contains the object to 
	 * show in the frontend made with vue 
	*/
	private List<RawText> rawTextList;
	private List<WithoutRecursivityText> withoutRecursivityTextList;
	
	public GrammarItem() {}
	
	public GrammarItem(List<RawText> rawTextList) {
		super();
		this.rawTextList = rawTextList;
	}
	
	public GrammarItem(List<RawText> rawTextList, List<WithoutRecursivityText> withoutRecursivityTextList) {
		super();
		this.rawTextList = rawTextList;
		this.withoutRecursivityTextList = withoutRecursivityTextList;
	}
	
	
	public List<RawText> getRawTextList() {
		return rawTextList;
	}
	public void setRawTextList(List<RawText> rawTextList) {
		this.rawTextList = rawTextList;
	}
	public List<WithoutRecursivityText> getWithoutRecursivityTextList() {
		return withoutRecursivityTextList;
	}
	public void setWithoutRecursivityTextList(List<WithoutRecursivityText> withoutRecursivityTextList) {
		this.withoutRecursivityTextList = withoutRecursivityTextList;
	} 
	
	
	
}
