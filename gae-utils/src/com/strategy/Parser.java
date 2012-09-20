package com.strategy;

public abstract class Parser {
	
	private String fileNameToRead;
	
	public String getFileNameToRead() {
		return fileNameToRead;
	}

	public Parser(String fileNameToRead) {
		this.fileNameToRead = fileNameToRead;
	}

}
