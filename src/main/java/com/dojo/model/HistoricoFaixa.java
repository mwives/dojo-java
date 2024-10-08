package com.dojo.model;

import java.time.LocalDate;

public class HistoricoFaixa {
  private Integer id;
  private String faixa;
  private LocalDate dataObtencao;

  public HistoricoFaixa(Integer id, String corFaixa, LocalDate dataObtencao) {
    this.id = id;
    this.faixa = corFaixa;
    this.dataObtencao = dataObtencao;
  }

  public HistoricoFaixa(String corFaixa, LocalDate dataObtencao) {
    this.faixa = corFaixa;
    this.dataObtencao = dataObtencao;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFaixa() {
    return faixa;
  }

  public void setFaixa(String corFaixa) {
    this.faixa = corFaixa;
  }

  public LocalDate getDataObtencao() {
    return dataObtencao;
  }

  public void setDataObtencao(LocalDate dataObtencao) {
    this.dataObtencao = dataObtencao;
  }

}
