💰 Sistema de Controle Financeiro Pessoal

Aplicação de console em Java para registrar e acompanhar receitas e despesas pessoais. Projeto final da disciplina Linguagem de Programação I (Ciência da Computação, 2º período).

📋 Funcionalidades
Cadastrar lançamentos (descrição, valor, data, tipo e categoria)
Listar todos os lançamentos
Consultar um lançamento pelo identificador
Alterar e excluir lançamentos existentes
Filtrar lançamentos por tipo (receita ou despesa)
Consultar lançamentos por período
Exibir resumo financeiro: total de receitas, total de despesas e saldo

Os dados ficam armazenados em memória (ArrayList) durante a execução. Ao encerrar o programa, os registros são perdidos.

🖥️ Menu
1 - Cadastrar Lançamento
2 - Listar Lançamento
3 - Consultar por identificador
4 - Alterar lançamento
5 - Excluir lançamento
6 - Filtrar por tipo
7 - Consultar por período
8 - Exibir resumo financeiro
0 - Sair

✅ Validações
A descrição não pode ser vazia.
O valor deve ser maior que zero.
As datas devem existir e seguir o formato dd/MM/aaaa (por exemplo, 31/02/2026 é recusada).
Na consulta por período, a data inicial não pode ser posterior à final.
Identificadores inexistentes geram mensagem clara, sem encerrar o programa.
Entradas inválidas (como letras no lugar de números) são tratadas e o menu continua funcionando.

🗂️ Estrutura do projeto
Arquivo	Responsabilidade
Main.java	Menu interativo, leitura e validação das entradas do usuário
Lancamento.java	Representa um registro financeiro (atributos, construtor, getters e setters)
ControleFinanceiro.java	Mantém a lista de lançamentos e implementa as regras do sistema
TipoLancamento.java	Enum com RECEITA e DESPESA
Categoria.java	Enum com as categorias dos lançamentos

🧰 Tecnologias e conceitos aplicados
Java (JDK 8 ou superior)
Classes, objetos, construtores e encapsulamento
enum para classificar informações
ArrayList para armazenamento em memória
BigDecimal para valores monetários (evita erros de precisão do double)
LocalDate para datas
Scanner para leitura de dados
Estruturas condicionais (if/else, switch) e de repetição (for, while, do/while)

▶️ Como executar

Pré-requisito: ter o JDK instalado.

Clone o repositório:
bash
git clone https://github.com/Rafael-3608/controle-financeiro.git
cd controle-financeiro
Compile os arquivos:
bash
javac -encoding UTF-8 *.java
Execute:
bash
java Main

Dica (Windows): se os acentos aparecerem como ? no terminal, rode chcp 65001 antes de executar o programa.

👤 Autor

SEU NOME GitHub: @Rafael-3608

📄 Licença

Este projeto está sob a licença MIT. Consulte o arquivo LICENSE para mais detalhes.