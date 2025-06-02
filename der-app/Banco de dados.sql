CREATE DATABASE der_app;
USE der_app;

CREATE TABLE TB_Aluno (
  id_aluno INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  cpf VARCHAR(11) NOT NULL UNIQUE
);

CREATE TABLE TB_Curso (
  id_curso INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE TB_Matricula (
  id_matricula    BIGINT NOT NULL AUTO_INCREMENT,
  id_aluno        INT NOT NULL,
  id_curso        INT NOT NULL,
  data_matricula  DATE NOT NULL,
  CONSTRAINT uq_matricula_aluno_curso UNIQUE (id_aluno, id_curso),
  PRIMARY KEY (id_matricula),
  FOREIGN KEY (id_aluno) REFERENCES TB_Aluno(id_aluno) ON DELETE CASCADE,
  FOREIGN KEY (id_curso) REFERENCES TB_Curso(id_curso) ON DELETE CASCADE
);

select * from TB_Matricula;