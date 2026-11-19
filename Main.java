import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner usuario = new Scanner(System.in);
    static ControleFinanceiro controle = new ControleFinanceiro();

    public static void digitar(String texto, int delayMS) {
        for (char c : texto.toCharArray()) {
            System.out.print(c);
            System.out.flush(); // força o caracter a aparecer
            try {
                Thread.sleep(delayMS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.println();
    }

    // ---------- Métodos de leitura com validação (RN01, RN06, RN07) ----------
    // Todos repetem a pergunta até o usuário digitar algo válido,
    // então uma entrada errada nunca derruba o programa.

    // Lê uma linha inteira e converte para int. Usar nextLine() em vez de nextInt()
    // evita o problema do "Enter" que sobra no buffer e o erro com letras.
    public static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = usuario.nextLine().trim();
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    public static String lerDescricao() {
        while (true) {
            System.out.print("Descrição: ");
            String texto = usuario.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("A descrição não pode ser vazia.");
        }
    }

    public static BigDecimal lerValor() {
        while (true) {
            System.out.print("Valor (maior que zero): ");
            String texto = usuario.nextLine().trim().replace(",", "."); // aceita 10,50
            try {
                BigDecimal valor = new BigDecimal(texto);
                if (valor.compareTo(BigDecimal.ZERO) > 0) {
                    return valor;
                }
                System.out.println("O valor deve ser maior que zero.");
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Exemplo: 150.75");
            }
        }
    }

    public static LocalDate lerData(String mensagem) {
        while (true) {
            System.out.print(mensagem + " (dd/MM/aaaa): ");
            String texto = usuario.nextLine().trim();
            try {
                return LocalDate.parse(texto, Lancamento.FORMATO_DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Exemplo: 25/12/2025");
            }
        }
    }

    public static TipoLancamento lerTipo() {
        while (true) {
            System.out.println("Tipo: 1 - Receita | 2 - Despesa");
            int opcao = lerInteiro("Escolha: ");
            if (opcao == 1) return TipoLancamento.RECEITA;
            if (opcao == 2) return TipoLancamento.DESPESA;
            System.out.println("Opção inválida.");
        }
    }

    public static Categoria lerCategoria() {
        Categoria[] categorias = Categoria.values(); // array com todas as constantes do enum
        while (true) {
            System.out.println("Categoria:");
            for (int i = 0; i < categorias.length; i++) {
                System.out.println((i + 1) + " - " + categorias[i]);
            }
            int opcao = lerInteiro("Escolha: ");
            if (opcao >= 1 && opcao <= categorias.length) {
                return categorias[opcao - 1];
            }
            System.out.println("Opção inválida.");
        }
    }

    // ---------- Operações do menu ----------

    public static void cadastrar() {
        String descricao = lerDescricao();
        BigDecimal valor = lerValor();
        LocalDate data = lerData("Data");
        TipoLancamento tipo = lerTipo();
        Categoria categoria = lerCategoria();

        Lancamento novo = controle.adicionarLancamento(descricao, valor, data, tipo, categoria);
        System.out.println("Lançamento cadastrado com sucesso! ID: " + novo.getId());
    }

    // Usado por listar, filtrar por tipo e consultar por período
    public static void exibirLista(ArrayList<Lancamento> lista) {
        if (lista.isEmpty()) { // RN05
            System.out.println("Nenhum lançamento encontrado.");
            return;
        }
        for (Lancamento l : lista) {
            System.out.println(l);
        }
    }

    public static void consultarPorId() {
        int id = lerInteiro("Informe o identificador: ");
        Lancamento l = controle.buscarPorId(id);
        if (l == null) {
            System.out.println("Lançamento com ID " + id + " não encontrado.");
        } else {
            System.out.println(l);
        }
    }

    public static void alterar() {
        int id = lerInteiro("Informe o identificador do lançamento a alterar: ");
        Lancamento atual = controle.buscarPorId(id);
        if (atual == null) { // RN03
            System.out.println("Lançamento com ID " + id + " não encontrado.");
            return;
        }
        System.out.println("Dados atuais: " + atual);
        System.out.println("Informe os novos dados:");
        String descricao = lerDescricao();
        BigDecimal valor = lerValor();
        LocalDate data = lerData("Data");
        TipoLancamento tipo = lerTipo();
        Categoria categoria = lerCategoria();

        controle.alterarLancamento(id, descricao, valor, data, tipo, categoria);
        System.out.println("Lançamento alterado com sucesso!");
    }

    public static void excluir() {
        int id = lerInteiro("Informe o identificador do lançamento a excluir: ");
        if (controle.excluirLancamento(id)) {
            System.out.println("Lançamento excluído com sucesso!");
        } else { // RN03
            System.out.println("Lançamento com ID " + id + " não encontrado.");
        }
    }

    public static void filtrarPorTipo() {
        TipoLancamento tipo = lerTipo();
        exibirLista(controle.filtrarPorTipo(tipo));
    }

    public static void consultarPorPeriodo() {
        LocalDate inicio = lerData("Data inicial");
        LocalDate fim = lerData("Data final");
        if (inicio.isAfter(fim)) { // RN06
            System.out.println("Período inválido: a data inicial não pode ser posterior à final.");
            return;
        }
        exibirLista(controle.consultarPorPeriodo(inicio, fim));
    }

    public static void exibirResumo() {
        System.out.printf("Total de receitas: R$ %.2f%n", controle.calcularTotalReceitas());
        System.out.printf("Total de despesas: R$ %.2f%n", controle.calcularTotalDespesas());
        System.out.printf("Saldo:             R$ %.2f%n", controle.calcularSaldo());
    }

    public static void main(String[] args) {
        digitar("----Sistema de Controle Financeiro----", 28);
        int resposta;

        do {
            digitar("Selecione uma opção disponível: ", 28);
            System.out.println("");
            digitar("1 - Cadastrar Lançamento", 10);
            digitar("2 - Listar Lançamento", 10);
            digitar("3 - Consultar por identificador", 10);
            digitar("4 - Alterar lançamento", 10);
            digitar("5 - Excluir lançamento", 10);
            digitar("6 - Filtrar por tipo", 10);
            digitar("7 - Consultar por período", 10);
            digitar("8 - Exibir resumo financeiro", 10);
            digitar("0 - Sair", 10);

            resposta = lerInteiro("> "); // no lugar de usuario.nextInt()

            switch (resposta) {
                case 1:
                    digitar("Opção selecionada - Cadastrar Lançamento", 10);
                    cadastrar();
                    break;

                case 2:
                    digitar("Opção selecionada - Listar Lançamento", 10);
                    exibirLista(controle.listarLancamentos());
                    break;

                case 3:
                    digitar("Opção selecionada - Consultar por identificador", 10);
                    consultarPorId();
                    break;

                case 4:
                    digitar("Opção selecionada - Alterar lançamento", 10);
                    alterar();
                    break;

                case 5:
                    digitar("Opção selecionada - Excluir lançamento", 10);
                    excluir();
                    break;

                case 6:
                    digitar("Opção selecionada - Filtrar por tipo", 10);
                    filtrarPorTipo();
                    break;

                case 7:
                    digitar("Opção selecionada - Consultar por período", 10);
                    consultarPorPeriodo();
                    break;

                case 8:
                    digitar("Opção selecionada - Exibir resumo financeiro", 10);
                    exibirResumo();
                    break;

                case 0:
                    digitar("encerrando...", 10);
                    break;

                default:
                    digitar("Erro... Opção inválida", 30);
                    break;
            }
            System.out.println();
        } while (resposta != 0);
    }
}
