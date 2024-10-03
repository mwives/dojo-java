package com.dojo.controller.aluno;

import java.time.LocalDate;

import com.dojo.model.Aluno;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CadastrarAlunoController {

  @FXML
  private TextField nomeField;

  @FXML
  private DatePicker dataNascimentoPicker;

  @FXML
  private ComboBox<String> faixaComboBox;

  @FXML
  public void initialize() {
    // Inicializar o ComboBox com as faixas disponíveis
    faixaComboBox.getItems().addAll(
        "Branca",
        "Amarela",
        "Laranja",
        "Verde",
        "Roxa",
        "Marrom",
        "Preta",
        "1º Dan",
        "2º Dan",
        "3º Dan",
        "4º Dan",
        "5º Dan",
        "6º Dan");
  }

  @FXML
  private void salvarAluno() {
    String nome = nomeField.getText();
    LocalDate dataNascimento = dataNascimentoPicker.getValue();
    String faixa = faixaComboBox.getValue();

    // Validações
    if (nome.isEmpty() || dataNascimento == null || faixa.isEmpty()) {
      mostrarAlerta("Erro de Validação", "Todos os campos são obrigatórios.");
      return;
    }

    Aluno novoAluno = new Aluno(nome, dataNascimento, faixa);

    // TODO: Salvar o aluno
    // AlunoRepository.save(novoAluno);

    mostrarAlerta("Sucesso", "Aluno cadastrado com sucesso!");

    limparCampos();
  }

  @FXML
  private void cancelarCadastro() {
    limparCampos();
  }

  private void mostrarAlerta(String titulo, String mensagem) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(titulo);
    alert.setHeaderText(null);
    alert.setContentText(mensagem);
    alert.showAndWait();
  }

  private void limparCampos() {
    nomeField.clear();
    dataNascimentoPicker.setValue(null);
    faixaComboBox.getSelectionModel().clearSelection();
  }
}
