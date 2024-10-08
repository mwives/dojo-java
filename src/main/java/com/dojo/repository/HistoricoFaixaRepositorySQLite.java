package com.dojo.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.dojo.model.HistoricoFaixa;

public class HistoricoFaixaRepositorySQLite implements HistoricoFaixaRepository {

  private static final String DB_URL = "jdbc:sqlite:dojo.db";

  public HistoricoFaixaRepositorySQLite() {
    criarTabela();
  }

  private void criarTabela() {
    String sql = "CREATE TABLE IF NOT EXISTS historico_faixa ("
        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
        + "id_aluno INTEGER NOT NULL, "
        + "faixa TEXT NOT NULL, "
        + "data TEXT NOT NULL"
        + ");";
    try (Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.execute();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public HistoricoFaixa adicionar(Integer idAluno, HistoricoFaixa historicoFaixa) {
    String sql = "INSERT INTO historico_faixa(id_aluno, faixa, data) VALUES(?, ?, ?)";
    String[] generatedColumns = { "id" };
    try (Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement pstmt = conn.prepareStatement(sql, generatedColumns)) {
      pstmt.setInt(1, idAluno);
      pstmt.setString(2, historicoFaixa.getFaixa());
      pstmt.setString(3, historicoFaixa.getDataObtencao().toString());
      pstmt.executeUpdate();

      try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          historicoFaixa.setId(generatedKeys.getInt(1));
        } else {
          throw new SQLException("Creating historico_faixa failed, no ID obtained.");
        }
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return historicoFaixa;
  }

  @Override
  public void remover(Integer idHistoricoFaixa) {
    String sql = "DELETE FROM historico_faixa WHERE id = ?;";
    try (Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, idHistoricoFaixa);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public List<HistoricoFaixa> buscarPorIdAluno(Integer idAluno) {
    List<HistoricoFaixa> historicoFaixas = new ArrayList<>();
    String sql = "SELECT * FROM historico_faixa WHERE id_aluno = ? ORDER BY data";
    try (Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, idAluno);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        Integer id = rs.getInt("id");
        String faixa = rs.getString("faixa");
        LocalDate data = LocalDate.parse(rs.getString("data"));
        historicoFaixas.add(new HistoricoFaixa(id, faixa, data));
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return historicoFaixas;
  }

}
