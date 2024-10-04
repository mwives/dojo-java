package com.dojo.controller.aluno;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.dojo.model.Aluno;
import com.dojo.model.HistoricoFaixa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HistoricoFaixasController {

  private Aluno alunoSelecionado;

  @FXML
  private TableView<HistoricoFaixa> historicoFaixasTableView;

  @FXML
  private TableColumn<HistoricoFaixa, String> faixaColumn;

  @FXML
  private TableColumn<HistoricoFaixa, String> dataFaixaColumn;

  @FXML
  private TextField faixaTextField;

  @FXML
  private DatePicker dataGraduacaoDatePicker;

  private ObservableList<HistoricoFaixa> historicoFaixasList = FXCollections.observableArrayList();

  public void setAluno(Aluno aluno) {
    this.alunoSelecionado = aluno;
    carregarHistoricoFaixas();
  }

  private void carregarHistoricoFaixas() {
    if (alunoSelecionado != null) {
      List<HistoricoFaixa> historico = buscarHistoricoFaixas(alunoSelecionado);
      historicoFaixasList.clear();
      historicoFaixasList.addAll(historico);
      historicoFaixasTableView.setItems(historicoFaixasList);
    }
  }

  private List<HistoricoFaixa> buscarHistoricoFaixas(Aluno aluno) {
    // TODO: Buscar do BD
    return List.of(
        new HistoricoFaixa("Branca", LocalDate.of(2019, 5, 12)),
        new HistoricoFaixa("Amarela", LocalDate.of(2020, 7, 8)));
  }

  @FXML
  private void adicionarFaixa() {
    String novaFaixa = faixaTextField.getText();
    LocalDate dataGraduacao = dataGraduacaoDatePicker.getValue();

    if (novaFaixa != null && !novaFaixa.isEmpty() && dataGraduacao != null) {
      HistoricoFaixa novaGraduacao = new HistoricoFaixa(novaFaixa, dataGraduacao);
      historicoFaixasList.add(novaGraduacao);
      historicoFaixasTableView.refresh();

      // TODO: Salvar no BD
    }
  }

  @FXML
  private void fecharTela() {
    Stage stage = (Stage) historicoFaixasTableView.getScene().getWindow();
    stage.close();
  }
}
