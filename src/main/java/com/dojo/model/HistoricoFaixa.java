package com.dojo.model;

import java.time.LocalDate;

public class HistoricoFaixa {
  private String corFaixa;
  private LocalDate dataObtencao;

  public HistoricoFaixa(String corFaixa, LocalDate dataObtencao) {
    this.corFaixa = corFaixa;
    this.dataObtencao = dataObtencao;
  }

  public String getCorFaixa() {
    return corFaixa;
  }

  // Outros getters e setters
}
