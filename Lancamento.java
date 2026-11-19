import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

// Representa um registro financeiro (uma receita ou uma despesa).
public class Lancamento {

    // Formato de data usado no sistema inteiro (STRICT recusa datas como 31/02/2025).
    public static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    // Atributos privados = encapsulamento.
    private int id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private TipoLancamento tipo;
    private Categoria categoria;

    public Lancamento(int id, String descricao, BigDecimal valor, LocalDate data,
                      TipoLancamento tipo, Categoria categoria) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
        this.categoria = categoria;
    }

    // Getters
    public int getId() { return id; }
    public String getDescricao() { return descricao; }
    public BigDecimal getValor() { return valor; }
    public LocalDate getData() { return data; }
    public TipoLancamento getTipo() { return tipo; }
    public Categoria getCategoria() { return categoria; }

    // Setters (o id não tem setter: ele não deve mudar depois de criado)
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public void setData(LocalDate data) { this.data = data; }
    public void setTipo(TipoLancamento tipo) { this.tipo = tipo; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    // Como o lançamento aparece quando fazemos System.out.println(lancamento)
    @Override
    public String toString() {
        return String.format("ID: %d | %s | R$ %.2f | %s | %s | %s",
                id, descricao, valor, data.format(FORMATO_DATA), tipo, categoria);
    }
}
