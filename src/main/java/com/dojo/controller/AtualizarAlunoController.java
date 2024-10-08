package com.dojo.controller;

import java.time.LocalDate;

import com.dojo.model.Aluno;
import com.dojo.repository.AlunoRepository;
import com.dojo.repository.AlunoRepositorySQLite;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AtualizarAlunoController {

  private AlunoRepository alunoRepository = new AlunoRepositorySQLite();
  private VisualizarAlunosController visualizarAlunosController;

  @FXML
  private TextField nomeTextField;

  @FXML
  private DatePicker dataNascimentoPicker;

  @FXML
  private ComboBox<String> faixaComboBox;

  private Aluno alunoAtual;

  @FXML
  public void initialize() {
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

  public void setVisualizarAlunosController(VisualizarAlunosController visualizarAlunosController) {
    this.visualizarAlunosController = visualizarAlunosController;
  }

  public void inicializarCampos(Aluno aluno) {
    this.alunoAtual = aluno;
    nomeTextField.setText(aluno.getNome());
    dataNascimentoPicker.setValue(aluno.getDataNascimento());
    faixaComboBox.setValue(aluno.getFaixa());
  }

  @FXML
  public void salvarAluno() {
    Integer id = alunoAtual.getId();
    String nome = nomeTextField.getText();
    LocalDate dataNascimento = dataNascimentoPicker.getValue();
    String faixa = faixaComboBox.getValue();

    alunoAtual.setId(id);
    alunoAtual.setNome(nome);
    alunoAtual.setDataNascimento(dataNascimento);
    alunoAtual.setFaixa(faixa);

    alunoRepository.atualizar(alunoAtual);

    if (visualizarAlunosController != null) {
      visualizarAlunosController.atualizarLista();
    }

    fecharTela();
  }

  @FXML
  public void cancelar() {
    fecharTela();
  }

  private void fecharTela() {
    Stage stage = (Stage) nomeTextField.getScene().getWindow();
    stage.close();
  }
}
