package com.dojo.controller.aluno;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.dojo.App;
import com.dojo.model.Aluno;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VisualizarAlunosController {

  @FXML
  private TableView<Aluno> alunosTableView;

  @FXML
  private TableColumn<Aluno, String> nomeColumn;

  @FXML
  private TableColumn<Aluno, String> dataNascimentoColumn;

  @FXML
  private TableColumn<Aluno, String> faixaColumn;

  private ObservableList<Aluno> alunosList = FXCollections.observableArrayList();

  @FXML
  public void initialize() {
    nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

    dataNascimentoColumn.setCellValueFactory(cellData -> {
      Aluno aluno = cellData.getValue();
      String formattedDate = aluno.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
      return new javafx.beans.property.SimpleStringProperty(formattedDate);
    });

    faixaColumn.setCellValueFactory(new PropertyValueFactory<>("faixa"));

    carregarAlunos();
  }

  private void carregarAlunos() {
    List<Aluno> alunosCadastrados = buscarAlunos(); // Implementar método para buscar alunos

    alunosList.clear();

    alunosList.addAll(alunosCadastrados);

    alunosTableView.setItems(alunosList);
  }

  private List<Aluno> buscarAlunos() {
    // TODO: carregar do BD
    return List.of(
        new Aluno("Carlos", LocalDate.of(2005, 5, 12), "Branca"),
        new Aluno("Ana", LocalDate.of(2008, 3, 8), "Amarela"),
        new Aluno("João", LocalDate.of(2003, 10, 15), "Verde"));
  }

  @FXML
  private void editarAluno() {
    Aluno alunoSelecionado = alunosTableView.getSelectionModel().getSelectedItem();
    if (alunoSelecionado != null) {
      try {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("atualizar_aluno.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Atualizar Aluno");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(loader.load()));

        AtualizarAlunoController controller = loader.getController();
        controller.inicializarCampos(alunoSelecionado);

        stage.showAndWait();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @FXML
  private void removerAluno() {
    Aluno alunoSelecionado = alunosTableView.getSelectionModel().getSelectedItem();
    if (alunoSelecionado != null) {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirmar Remoção");
      alert.setHeaderText("Remover Aluno");
      alert.setContentText("Tem certeza que deseja remover o aluno " + alunoSelecionado.getNome() + "?");

      if (alert.showAndWait().get() == ButtonType.OK) {
        alunosList.remove(alunoSelecionado);
        // TODO: Remover do BD
        alunosTableView.refresh();
      }
    }
  }

  @FXML
  private void atualizarLista() {
    carregarAlunos();
  }

  @FXML
  private void voltarTela() throws IOException {
    App.setRoot("main");
  }
}
