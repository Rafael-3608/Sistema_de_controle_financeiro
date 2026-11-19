import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

// Guarda a lista de lançamentos e implementa as regras do sistema.
// Não imprime nem lê nada: quem conversa com o usuário é a Main.
public class ControleFinanceiro {

    private ArrayList<Lancamento> lancamentos = new ArrayList<>();
    private int proximoId = 1; // garante identificadores únicos (RN02)

    // RF01
    public Lancamento adicionarLancamento(String descricao, BigDecimal valor, LocalDate data,
                                          TipoLancamento tipo, Categoria categoria) {
        Lancamento novo = new Lancamento(proximoId, descricao, valor, data, tipo, categoria);
        lancamentos.add(novo);
        proximoId++; // o próximo lançamento terá um id diferente
        return novo;
    }

    // RF02
    public ArrayList<Lancamento> listarLancamentos() {
        return lancamentos;
    }

    // RF03 - devolve null se não encontrar
    public Lancamento buscarPorId(int id) {
        for (Lancamento l : lancamentos) {
            if (l.getId() == id) {
                return l;
            }
        }
        return null;
    }

    // RF04 - devolve false se o id não existir (RN03)
    public boolean alterarLancamento(int id, String descricao, BigDecimal valor, LocalDate data,
                                     TipoLancamento tipo, Categoria categoria) {
        Lancamento l = buscarPorId(id);
        if (l == null) {
            return false;
        }
        l.setDescricao(descricao);
        l.setValor(valor);
        l.setData(data);
        l.setTipo(tipo);
        l.setCategoria(categoria);
        return true;
    }

    // RF05 - devolve false se o id não existir (RN03)
    public boolean excluirLancamento(int id) {
        Lancamento l = buscarPorId(id);
        if (l == null) {
            return false;
        }
        lancamentos.remove(l);
        return true;
    }

    // RF06
    public ArrayList<Lancamento> filtrarPorTipo(TipoLancamento tipo) {
        ArrayList<Lancamento> resultado = new ArrayList<>();
        for (Lancamento l : lancamentos) {
            if (l.getTipo() == tipo) {
                resultado.add(l);
            }
        }
        return resultado;
    }

    // RF07 - inclui as datas inicial e final
    public ArrayList<Lancamento> consultarPorPeriodo(LocalDate inicio, LocalDate fim) {
        ArrayList<Lancamento> resultado = new ArrayList<>();
        for (Lancamento l : lancamentos) {
            boolean naoAntes = !l.getData().isBefore(inicio);
            boolean naoDepois = !l.getData().isAfter(fim);
            if (naoAntes && naoDepois) {
                resultado.add(l);
            }
        }
        return resultado;
    }

    // RF08 / RN04
    public BigDecimal calcularTotalReceitas() {
        return somarPorTipo(TipoLancamento.RECEITA);
    }

    public BigDecimal calcularTotalDespesas() {
        return somarPorTipo(TipoLancamento.DESPESA);
    }

    public BigDecimal calcularSaldo() {
        return calcularTotalReceitas().subtract(calcularTotalDespesas());
    }

    // Método auxiliar para não repetir o mesmo laço duas vezes
    private BigDecimal somarPorTipo(TipoLancamento tipo) {
        BigDecimal total = BigDecimal.ZERO;
        for (Lancamento l : lancamentos) {
            if (l.getTipo() == tipo) {
                total = total.add(l.getValor()); // BigDecimal não usa o operador +
            }
        }
        return total;
    }
}
