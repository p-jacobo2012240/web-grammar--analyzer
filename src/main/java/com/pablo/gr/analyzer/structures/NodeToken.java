package com.pablo.gr.analyzer.structures;

public class NodeToken {
	private String valueToken;
	private NodeToken nextNode;
	
	public NodeToken() {
		this.valueToken = "";
		this.nextNode = null;
	}

	public String getValueToken() {
		return valueToken;
	}

	public void setValueToken(String valueToken) {
		this.valueToken = valueToken;
	}

	public NodeToken getNextNode() {
		return nextNode;
	}

	public void setNextNode(NodeToken nextNode) {
		this.nextNode = nextNode;
	}
	
	

}
