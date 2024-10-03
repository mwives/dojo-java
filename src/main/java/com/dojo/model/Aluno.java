package com.dojo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Aluno {
  private String nome;
  private LocalDate dataNascimento;
  private String faixaAtual;

  private List<Faixa> historicoFaixas;

  public Aluno(String nome, LocalDate dataNascimento, String faixaAtual) {
    this.nome = nome;
    this.dataNascimento = dataNascimento;
    this.faixaAtual = faixaAtual;
    this.historicoFaixas = new ArrayList<>();
  }

  public String getNome() {
    return nome;
  }

  public LocalDate getDataNascimento() {
    return dataNascimento;
  }

  public String getFaixaAtual() {
    return faixaAtual;
  }

  public void adicionarFaixa(Faixa faixa) {
    historicoFaixas.add(faixa);
    this.faixaAtual = faixa.getCorFaixa();
  }

}
