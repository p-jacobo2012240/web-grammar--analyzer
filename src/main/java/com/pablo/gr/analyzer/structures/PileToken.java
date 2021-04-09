package com.pablo.gr.analyzer.structures;

public class PileToken {
	private NodeToken startNode;
	private int sizePile;
	
	public PileToken() {
		 startNode = null;
		 sizePile = 0;
	}
	
	public boolean isEmpty() {
		return startNode == null;
	}
	
	public int getSizePile() {
		return sizePile;
	}
	
	public void push(String value) {
		NodeToken newNodeToken = new NodeToken();
		newNodeToken.setValueToken(value);
		if (isEmpty()) {
			startNode = newNodeToken;
		} else {
			newNodeToken.setNextNode(newNodeToken);
			startNode = newNodeToken;
		}
		sizePile++;
	}
	
	public NodeToken pop() {
		NodeToken tmpNodeToken = new NodeToken();
		if(!isEmpty()) {
			tmpNodeToken = startNode;
			startNode = startNode.getNextNode();
			sizePile--;
		}
		return tmpNodeToken;
	}
	

}
