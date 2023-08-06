package com.br.view.antivirus;

import javax.swing.*;

import com.br.model.antivirus.AntivirusModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Locale;

public class AntivirusView {
    private JFrame mainFrame;
    private JProgressBar progressBar;
    private JButton scanButton;
    private AntivirusModel model;
    private JFrame resultFrame;
    private JLabel resultLabel;
    private JLabel progressLabel; 
	private int progress;
	private JFrame cleanFilesFrame;
	private JTextArea cleanFilesTextArea;
    public AntivirusView( AntivirusModel model) {
    	this.model = model; 
    	this.model.setView(this);
    	
        mainFrame = new JFrame("Stron Antivirus");
        mainFrame.getContentPane().setBackground(Color.WHITE);
        mainFrame.setForeground(Color.BLACK);
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBackground(Color.white);

        progressBar = new JProgressBar(0, 100);
        progressBar.setForeground(Color.BLACK);
        progressBar.setStringPainted(true);

        scanButton = new JButton("Escanear Agora");
        scanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(mainFrame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedDirectory = fileChooser.getSelectedFile();
                    model.startScan(selectedDirectory);
                }
            }
        });
    
        progressLabel = new JLabel("Progresso da varredura: 0%");
        mainFrame.getContentPane().add(progressLabel, BorderLayout.NORTH);

        mainFrame.getContentPane().setLayout(new BorderLayout());
        mainFrame.getContentPane().add(progressBar, BorderLayout.CENTER);
        mainFrame.getContentPane().add(scanButton, BorderLayout.SOUTH);
        
        resultFrame = new JFrame("Resultados do Scan");
        resultFrame.setSize(800, 600);
        resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        resultFrame.getContentPane().setLayout(new BorderLayout());

        resultLabel = new JLabel();
        resultFrame.getContentPane().add(resultLabel, BorderLayout.CENTER);
        resultFrame.setVisible(false); // Começa invisível
        
        cleanFilesFrame = new JFrame("Arquivos Limpos");
        cleanFilesFrame.setSize(800, 600);
        cleanFilesFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        cleanFilesTextArea = new JTextArea();
        cleanFilesTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(cleanFilesTextArea);

        cleanFilesFrame.getContentPane().setLayout(new BorderLayout());
        cleanFilesFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        
    }
    
    
    
    

    

    public void showMainFrame() {
        mainFrame.setVisible(true);
    }
    
    public void setScanProgress(int progress) {
        this.progress = progress; // Atualiza o valor da variável de progresso na classe
        progressBar.setValue(progress);
        progressLabel.setText("Progresso da varredura: " + progress + "%");
    }
    
    public void showResultScreen() {
        progressBar.setValue(progress);
        if (progress == 100) {
            resultFrame.setVisible(true); // Exibe a janela de resultados quando a verificação estiver completa
        }
    }

    public String getSystemLanguage() {
        Locale locale = Locale.getDefault();
        return locale.getDisplayLanguage();
    }
    
    
    
    public void showCleanFilesScreen(List<File> cleanFiles2) {
        cleanFilesTextArea.setText(""); // Limpa o conteúdo do JTextArea
        List<File> cleanFiles = model.getCleanFiles();
        List<File> infectedFiles = model.getInfectedFiles();
        		
        if (cleanFiles != null && !cleanFiles.isEmpty()) {
            for (File file : cleanFiles) {
                cleanFilesTextArea.append("Arquivo limpo: " + file.getAbsolutePath() + "\n");
            }
        } else {
            cleanFilesTextArea.append("Nenhum arquivo limpo encontrado.\n");
        }

        if (infectedFiles != null && !infectedFiles.isEmpty()) {
            for (File file : infectedFiles) {
                cleanFilesTextArea.append("Arquivo infectado: " + file.getAbsolutePath() + "\n");
            }
        } else {
            cleanFilesTextArea.append("Nenhum arquivo infectado encontrado.\n");
        }

        cleanFilesFrame.setVisible(true);
        
    }
    
    
}