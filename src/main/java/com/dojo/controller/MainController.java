package com.dojo.controller;

import java.io.IOException;

import javafx.fxml.FXML;

public class MainController {

    @FXML
    private void handleCadastrarAluno() throws IOException {
        com.dojo.App.setRoot("cadastrar_aluno");
    }

    @FXML
    private void handleVisualizarAlunos() throws IOException {
        com.dojo.App.setRoot("visualizar_alunos");
    }

    @FXML
    private void handleRegistrarCampeonato() throws IOException {
        com.dojo.App.setRoot("registrar_campeonato");
    }

    @FXML
    private void handleSair() {
        System.exit(0);
    }
}
