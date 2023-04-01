package com.pablo.gr.analyzer.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pablo.gr.analyzer.engines.ParserFromPlainList;
import com.pablo.gr.analyzer.engines.MainEngine;
import com.pablo.gr.analyzer.models.GrammarItem;

@RestController
public class AnalyzerController {
	private Logger logger = LoggerFactory.getLogger(AnalyzerController.class);
	private GrammarItem grammarEntity = new GrammarItem();
	private Map<String, Object> responseError = new HashMap<>();
	
	@PostMapping("/upload/file")
	public ResponseEntity<Object> uploadFileTxT(@RequestParam("file") MultipartFile fileTxt) {
		if (!fileTxt.isEmpty()) {
			BufferedReader bufferedReader;
			List<String> resultList = new ArrayList<>();
			String line = "";
			try {
				InputStream inputStream = fileTxt.getInputStream();
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

				// setting data into string list
				while ((line = bufferedReader.readLine()) != null) {
					resultList.add(line);
				}

				// first analysis of the list of values
				Map<String, Object> resultMap = ParserFromPlainList
						.getInstance()
						.verificationofIntegrityInList(resultList);

				Object statusMap = resultMap.get("status");
				boolean resultExp = Boolean.valueOf(statusMap.toString());
				
				if (resultExp) {
					// set data to main engine 
					this.grammarEntity =  MainEngine.getInstance().processTxtFile(resultList);
				} else {
					this.logger.warn("corrupt chain....");
					this.responseError.put("status", false);
					return new ResponseEntity<Object>(this.responseError, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
		return new ResponseEntity<Object>(this.grammarEntity, HttpStatus.OK );
	}

}
