package com.pablo.gr.analyzer.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AnalyzerController {

	@PostMapping("/upload/file")
	public ResponseEntity<Object> uploadFileTxT(@RequestParam("filetxt") MultipartFile fileTxt) {
		if (!fileTxt.isEmpty()) {
			BufferedReader bufferedReader;
			List<String> resultList = new ArrayList<>();
			try {
				String line;
				InputStream inputStream = fileTxt.getInputStream();
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				// setting data into string list
				while ((line = bufferedReader.readLine()) != null) {
					resultList.add(line);
				}
				
				// read line...
				for(String letter: resultList) {
					System.out.println("line " + letter );
				}

			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
		return null;
	}

}
