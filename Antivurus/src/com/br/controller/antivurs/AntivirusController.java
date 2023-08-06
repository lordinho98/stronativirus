package com.br.controller.antivurs;

import com.br.model.antivirus.AntivirusModel;
import com.br.view.antivirus.AntivirusView;

public class AntivirusController {
    private AntivirusModel model;
    private AntivirusView view;

    public AntivirusController(AntivirusModel model, AntivirusView view) {
        this.model = model;
        this.view = view;
        initView();
    }

    private void initView() {
        view.showMainFrame();
    }
}