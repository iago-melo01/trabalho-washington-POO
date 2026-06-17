# Projeto Equipe Escola

Sistema escolar desenvolvido em Java como trabalho da disciplina de Programação Orientada a Objetos (POO).

## Objetivo

O objetivo do projeto é criar uma aplicação de console para auxiliar no gerenciamento acadêmico de uma escola, aplicando conceitos fundamentais de POO, como classes, objetos, encapsulamento, herança, polimorfismo, interfaces, associações entre classes e tratamento de exceções.

## Funcionalidades Implementadas

- Cadastro, listagem, atualização e remoção de cursos.
- Cadastro, listagem, atualização, ativação e desativação de disciplinas.
- Cadastro, listagem, atualização, ativação e desativação de turmas.
- Cadastro, listagem, atualização e remoção de alunos.
- Cadastro, listagem, atualização e remoção de professores.
- Associação de professores a turmas.
- Controle de professores aptos por disciplina.
- Realização, confirmação, cancelamento e consulta de matrículas.
- Lançamento e listagem de notas pelo professor responsável pela turma.
- Listagem geral de pessoas cadastradas no sistema.
- Geração automática de IDs para as principais entidades.
- Exibição prévia de registros cadastrados antes da seleção por ID.

## Conceitos de POO Aplicados

- **Classes e objetos:** representação de entidades como `Aluno`, `Professor`, `Curso`, `Disciplina`, `Turma`, `Matricula` e `Escola`.
- **Encapsulamento:** uso de atributos privados/protegidos e métodos de acesso.
- **Herança:** `Aluno` e `Professor` herdam de `Pessoa`; `Curso` e `Disciplina` herdam de `ConteudoEducacional`.
- **Polimorfismo:** objetos do tipo `Pessoa` podem representar alunos e professores.
- **Interfaces:** `Professor` implementa `Avaliavel`; `Curso` e `Disciplina` implementam `Gerenciavel`.
- **Associações:** `Matricula` liga `Aluno` e `Turma`; `Turma` possui um `Professor`; `Curso` possui `Disciplina`; `Disciplina` possui `Turma`.
- **Tratamento de exceções:** uso de validações e exceções para impedir operações inválidas.
- **Separação em camadas:** organização em pacotes de modelo, serviço, repositório, exceções, utilitários e classe principal.

## Estrutura do Projeto

```text
projeto-equipe-escola/
|-- docs/
|   `-- diagrama-uml/
|-- src/
|   |-- exception/
|   |-- main/
|   |-- model/
|   |-- repository/
|   |-- service/
|   `-- util/
`-- README.md
```

## Organização dos Pacotes

- `src/main/`: contém a classe `Main`, responsável pelo menu e pela interação com o usuário.
- `src/model/`: contém as classes principais do domínio escolar.
- `src/repository/`: contém classes responsáveis pelo armazenamento em memória e consulta dos dados.
- `src/service/`: contém regras de negócio e operações sobre as entidades.
- `src/exception/`: contém exceções personalizadas do sistema.
- `src/util/`: contém classes auxiliares de validação.
- `docs/`: contém diagramas UML e materiais de documentação do projeto.

## Principais Regras de Negócio

- IDs são gerados automaticamente pelos repositórios e serviços.
- Alunos não podem ser matriculados duas vezes na mesma turma.
- Matrículas podem estar pendentes, confirmadas ou canceladas.
- Notas só podem ser lançadas em matrículas confirmadas.
- Não é permitido lançar nota em matrícula cancelada.
- Apenas o professor responsável pela turma pode lançar ou listar notas daquela turma.
- A nota deve estar entre 0 e 10.
- Cursos, disciplinas e turmas podem ser ativados ou desativados.

## Como Executar

Compile o projeto a partir da raiz `projeto-equipe-escola`:

```bash
javac -d out $(find src -name "*.java")
```

Em sistemas Windows com PowerShell:

```powershell
javac -d out (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })
```

Execute a classe principal:

```bash
java -cp out main.Main
```

Caso a IDE ou terminal apresente problemas com acentuação, configure o projeto para usar UTF-8 ou compile com:

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
```

## Documentação

A documentação do trabalho está localizada na pasta `docs/`, incluindo versões do diagrama UML utilizadas durante o desenvolvimento. O projeto também pode ser apresentado junto ao material complementar da equipe no Google Sites, conforme solicitado na proposta.

## Status

Projeto funcional para apresentação final da disciplina de Programação Orientada a Objetos.
