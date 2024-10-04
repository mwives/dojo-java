package com.dojo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Aluno {

  private String nome;
  private LocalDate dataNascimento;
  private String faixa;

  private List<HistoricoFaixa> historicoFaixas;

  public Aluno(String nome, LocalDate dataNascimento, String faixa) {
    this.nome = nome;
    this.dataNascimento = dataNascimento;
    this.faixa = faixa;
    this.historicoFaixas = new ArrayList<>();
  }

  // Getters
  public String getNome() {
    return nome;
  }

  public LocalDate getDataNascimento() {
    return dataNascimento;
  }

  public String getFaixa() {
    return faixa;
  }

  public Integer getIdade() {
    return LocalDate.now().getYear() - dataNascimento.getYear();
  }

  // Setters
  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setDataNascimento(LocalDate dataNascimento) {
    this.dataNascimento = dataNascimento;
  }

  public void setFaixa(String faixa) {
    this.faixa = faixa;
  }

  // Métodos
  public void adicionarFaixa(HistoricoFaixa faixa) {
    historicoFaixas.add(faixa);
    this.faixa = faixa.getCorFaixa();
  }

}
