package com.br.main.antivirus;

import javax.swing.JOptionPane;

import com.br.controller.antivurs.AntivirusController;
import com.br.model.antivirus.AntivirusModel;
import com.br.view.antivirus.AntivirusView;

public class Main {
    public static void main(String[] args) {
        try {
            AntivirusModel model = new AntivirusModel();
            AntivirusView view = new AntivirusView(model);
            AntivirusController controller = new AntivirusController(model, view);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha de inicializa��o: " + e.getMessage(),
                    "Erro de Inicializa��o", JOptionPane.ERROR_MESSAGE);
        }
    }
}