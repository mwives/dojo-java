package com.dojo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.dojo.model.Aluno;
import com.dojo.model.HistoricoFaixa;
import com.dojo.repository.HistoricoFaixaRepository;
import com.dojo.repository.HistoricoFaixaRepositorySQLite;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class HistoricoFaixasController {

  private HistoricoFaixaRepository historicoFaixaRepository = new HistoricoFaixaRepositorySQLite();

  private Aluno alunoSelecionado;

  @FXML
  private TableView<HistoricoFaixa> historicoFaixasTableView;

  @FXML
  private TableColumn<HistoricoFaixa, String> faixaColumn;

  @FXML
  private TableColumn<HistoricoFaixa, String> dataFaixaColumn;

  @FXML
  private ComboBox<String> faixaComboBox;

  @FXML
  private DatePicker dataGraduacaoDatePicker;

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

    faixaColumn.setCellValueFactory(new PropertyValueFactory<>("faixa"));

    dataFaixaColumn.setCellValueFactory(cellData -> {
      LocalDate dataFaixa = cellData.getValue().getDataObtencao();
      return new javafx.beans.property.SimpleStringProperty(
          dataFaixa.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    });
  }

  private ObservableList<HistoricoFaixa> historicoFaixasList = FXCollections.observableArrayList();

  public void setAluno(Aluno aluno) {
    this.alunoSelecionado = aluno;
    carregarHistoricoFaixas();
  }

  private void carregarHistoricoFaixas() {
    if (alunoSelecionado != null) {
      List<HistoricoFaixa> historico = historicoFaixaRepository.buscarPorIdAluno(alunoSelecionado.getId());
      historicoFaixasList.clear();
      historicoFaixasList.addAll(historico);
      historicoFaixasTableView.setItems(historicoFaixasList);
    }
  }

  @FXML
  private void adicionarFaixa() {
    String novaFaixa = faixaComboBox.getSelectionModel().getSelectedItem();
    LocalDate dataGraduacao = dataGraduacaoDatePicker.getValue();

    if (novaFaixa != null && !novaFaixa.isEmpty() && dataGraduacao != null) {
      HistoricoFaixa novoHistoricoFaixa = historicoFaixaRepository.adicionar(alunoSelecionado.getId(),
          new HistoricoFaixa(novaFaixa, dataGraduacao));

      historicoFaixasList.add(novoHistoricoFaixa);
      historicoFaixasTableView.refresh();

      this.resetaFormulario();
    }
  }

  private void resetaFormulario() {
    faixaComboBox.getSelectionModel().clearSelection();
    dataGraduacaoDatePicker.setValue(null);
  }

  @FXML
  private void removerFaixa() {
    HistoricoFaixa faixaSelecionada = historicoFaixasTableView.getSelectionModel().getSelectedItem();
    if (faixaSelecionada != null) {
      boolean confirmacao = confirmarRemoverFaixa();
      if (confirmacao) {
        historicoFaixaRepository.remover(faixaSelecionada.getId());
        historicoFaixasList.remove(faixaSelecionada);
        historicoFaixasTableView.refresh();
      }
    }
  }

  private boolean confirmarRemoverFaixa() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmação");
    alert.setHeaderText("Remover Faixa");
    alert.setContentText("Deseja realmente remover a faixa selecionada?");

    Optional<ButtonType> result = alert.showAndWait();
    return result.get() == ButtonType.OK;
  }

  @FXML
  private void fecharTela() {
    Stage stage = (Stage) historicoFaixasTableView.getScene().getWindow();
    stage.close();
  }
}
