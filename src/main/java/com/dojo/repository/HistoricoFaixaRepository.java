package com.dojo.repository;

import java.util.List;

import com.dojo.model.HistoricoFaixa;

public interface HistoricoFaixaRepository {
  HistoricoFaixa adicionar(Integer idAluno, HistoricoFaixa historicoFaixa);

  void remover(Integer idHistoricoFaixa);

  List<HistoricoFaixa> buscarPorIdAluno(Integer idAluno);
}
