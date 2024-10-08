package com.dojo.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.dojo.model.Aluno;

public class AlunoRepositorySQLite implements AlunoRepository {

  private static final String DB_URL = "jdbc:sqlite:dojo.db";

  public AlunoRepositorySQLite() {
    criarTabela();
  }

  private void criarTabela() {
    String sql = "CREATE TABLE IF NOT EXISTS alunos ("
        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
        + "nome TEXT NOT NULL, "
        + "data_nascimento TEXT NOT NULL, "
        + "faixa TEXT NOT NULL"
        + ");";
    try (Connection conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement()) {
      stmt.execute(sql);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void adicionar(Aluno aluno) {
    String sql = "INSERT INTO alunos(nome, data_nascimento, faixa) VALUES(?, ?, ?)";
    try (Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, aluno.getNome());
      pstmt.setString(2, aluno.getDataNascimento().toString());
      pstmt.setString(3, aluno.getFaixa());
      pstmt.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void atualizar(Aluno aluno) {
    String sql = "UPDATE alunos SET nome = ?, data_nascimento = ?, faixa = ? WHERE id = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, aluno.getNome());
      pstmt.setString(2, aluno.getDataNascimento().toString());
      pstmt.setString(3, aluno.getFaixa());
      pstmt.setInt(4, aluno.getId());
      pstmt.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void remover(Aluno aluno) {
    String sql = "DELETE FROM alunos WHERE id = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, aluno.getId());
      pstmt.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public Aluno buscarPorNome(String nome) {
    String sql = "SELECT * FROM alunos WHERE nome = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, nome);
      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        Integer id = rs.getInt("id");
        String nomeAluno = rs.getString("nome");
        LocalDate dataNascimento = LocalDate.parse(rs.getString("data_nascimento"));
        String faixa = rs.getString("faixa");
        return new Aluno(id, nomeAluno, dataNascimento, faixa);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  @Override
  public List<Aluno> buscarTodos() {
    List<Aluno> alunos = new ArrayList<>();
    String sql = "SELECT * FROM alunos";
    try (Connection conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        Integer id = rs.getInt("id");
        String nome = rs.getString("nome");
        LocalDate dataNascimento = LocalDate.parse(rs.getString("data_nascimento"));
        String faixa = rs.getString("faixa");
        alunos.add(new Aluno(id, nome, dataNascimento, faixa));
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return alunos;
  }

}
