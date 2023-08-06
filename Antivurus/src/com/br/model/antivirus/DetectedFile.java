package com.br.model.antivirus;

import java.io.File;

public class DetectedFile {
    private String filename;
	private File file; // Tipo de ameaça (trojan, malware, etc.)

    public DetectedFile(String filename, File file) {
        this.filename = filename;
        this.file = file;
    }

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	
   
}