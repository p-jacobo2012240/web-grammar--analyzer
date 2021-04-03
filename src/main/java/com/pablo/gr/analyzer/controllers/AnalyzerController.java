package com.pablo.gr.analyzer.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pablo.gr.analyzer.engines.MainEngine;

@RestController
public class AnalyzerController {
	
	private Logger logger = LoggerFactory.getLogger(AnalyzerController.class);
	
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
				
				// set data to main engine 
				MainEngine.getInstance().processTxtFile(resultList);

			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
		return null;
	}

}
