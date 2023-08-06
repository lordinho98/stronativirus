package com.br.model.antivirus;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import com.br.view.antivirus.*;

import java.net.InetSocketAddress;
import xyz.capybara.clamav.ClamavClient;
import xyz.capybara.clamav.commands.scan.result.ScanResult;
import xyz.capybara.clamav.exceptions.ClamavException;
import java.io.InputStream;


public class AntivirusModel {
	
	private static final String CLAMAV_HOST = "localhost"; // Endereço do servidor ClamAV
	private static final int CLAMAV_PORT = 3310; // Porta do servidor ClamAV
	private DynamicDoubleStack<File> quarantineStack;
	 private AntivirusView view;
	 private List<File> cleanFiles;
	private List<File> infectedFiles;
	 public AntivirusModel() {
	        quarantineStack = new DynamicDoubleStack<File>();
	        cleanFiles = new ArrayList<>();
	        infectedFiles = new ArrayList<>();
	    }

	 public void startScan(File directory) {
		 cleanFiles.clear(); // Limpa a lista de arquivos limpos
		    infectedFiles.clear(); // Limpa a lista de arquivos infectados
		    
		    ClamavClient clamavClient = new ClamavClient(getClamavAddress());

		    try {
				scanDirectory(directory, clamavClient);
			} catch (IOException | ClamavException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    view.showCleanFilesScreen(cleanFiles);
	    }
	 
	 
	 
	 private void scanDirectory(File directory, ClamavClient clamavClient) throws IOException, ClamavException {
	        if (directory.isDirectory()) {
	            File[] files = directory.listFiles();
	            if (files != null) {
	                for (File file : files) {
	                    if (file.isFile()) {
	                        // Faça a verificação do arquivo aqui usando o ClamavClient
	                        if (isInfected(file, clamavClient)) {
	                            // O arquivo está infectado, tome ação apropriada (ex: adicionar à quarentena)
	                            System.out.println("Arquivo infectado: " + file.getAbsolutePath());
	                            addToQuarantine(file);
	                        } else {
	                            System.out.println("Arquivo limpo: " + file.getAbsolutePath());
	                        }
	                    } else if (file.isDirectory()) {
	                        scanDirectory(file, clamavClient); // Verifica o conteúdo do subdiretório recursivamente
	                    }
	                }
	            }
	        }
	    }
	 
	 
	 private boolean isInfected(File file, ClamavClient clamavClient) throws IOException, ClamavException {
		 try {
		        InputStream inputStream = new FileInputStream(file);
		        ScanResult detections = clamavClient.scan(inputStream);
		        inputStream.close(); // Não esqueça de fechar o InputStream após o uso
		        return !((CharSequence) detections).isEmpty();
		    } catch (ClamavException e) {
		        // Erro no cliente ClamAV
		        e.printStackTrace();
		        throw e;
		    }
	 }
	 
	 public List<File> getCleanFiles() {
	        return cleanFiles;
	    }	 
	 
	 
	 
	 
	 

	 public void setView(AntivirusView view) {
	        this.view = view;
	    }
	 
	 
	 
	public void addToQuarantine(File file) {
		DetectedFile detectedFile = new DetectedFile(null, file);
        quarantineStack.pushBackward(file);
    }
	public DetectedFile removeFromQuarantine() {
	    try {
	        return quarantineStack.popForward();
	    } catch (EmptyStackException e) {
	        return null; // Se a pilha estiver vazia, retorna null
	    }
	}

    public boolean isQuarantineEmpty() {
        return quarantineStack.isEmptyBackward();
    }
	
    
    private InetSocketAddress getClamavAddress() {
        return new InetSocketAddress(CLAMAV_HOST, CLAMAV_PORT);
    }
	

	public List<File> getInfectedFiles() {
		// TODO Auto-generated method stub
		return infectedFiles;
	}
	

}
