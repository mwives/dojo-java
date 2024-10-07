package com.dojo.repository;

import java.util.List;
import com.dojo.model.Aluno;

public interface AlunoRepository {
  void adicionar(Aluno aluno);

  void atualizar(Aluno aluno);

  void remover(Aluno aluno);

  Aluno buscarPorNome(String nome);

  List<Aluno> buscarTodos();
}
