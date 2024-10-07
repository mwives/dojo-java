package com.dojo.controller.aluno;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.dojo.App;
import com.dojo.model.Aluno;
import com.dojo.repository.AlunoRepository;
import com.dojo.repository.AlunoRepositorySQLite;

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

  private AlunoRepository alunoRepository = new AlunoRepositorySQLite();

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
    List<Aluno> alunosCadastrados = buscarAlunos();

    alunosList.clear();

    alunosList.addAll(alunosCadastrados);

    alunosTableView.setItems(alunosList);
  }

  private List<Aluno> buscarAlunos() {
    return alunoRepository.buscarTodos();
  }

  @FXML
  private void cadastrarAluno() throws IOException {
    com.dojo.App.setRoot("cadastrar_aluno");
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
        controller.setVisualizarAlunosController(this);

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
        alunoRepository.remover(alunoSelecionado);
        alunosList.remove(alunoSelecionado);
        alunosTableView.refresh();
      }
    }
  }

  @FXML
  private void gerenciarHistoricoFaixas() {
    Aluno alunoSelecionado = alunosTableView.getSelectionModel().getSelectedItem();
    if (alunoSelecionado != null) {
      try {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("gerenciar_historico_faixas.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Gerenciar Histórico de Faixas");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(loader.load()));

        HistoricoFaixasController controller = loader.getController();
        controller.setAluno(alunoSelecionado); // Passa o aluno selecionado para o próximo controller

        stage.showAndWait();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @FXML
  public void atualizarLista() {
    carregarAlunos();
  }

  @FXML
  private void voltarTela() throws IOException {
    App.setRoot("main");
  }

}
