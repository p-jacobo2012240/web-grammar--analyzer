package com.pablo.gr.analyzer.controllers;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AnalyzerController {

	@PostMapping("/upload/file")
	public ResponseEntity<Object> uploadFileTxT(@RequestParam("filetxt") MultipartFile fileTxt  ){
		if(!fileTxt.isEmpty()) {
			try {
				System.out.println(" content "+  fileTxt.getInputStream().toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();  
			}
		}
		return null;
	}
	
}
