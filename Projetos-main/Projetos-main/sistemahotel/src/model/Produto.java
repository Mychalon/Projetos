package model;


public class Produto {
 private int id;
    private String nome;
    private String codigo;  // VARCHAR no banco
    private int quantidade; // INT no banco
    private double preco;
    
    // Construtor
    public Produto(int id, String nome, String codigo, int quantidade, double preco) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.quantidade = quantidade;
        this.preco = preco;
    } 
    // Construtor vazio
    public Produto() {
     
    }
    


    
   
    
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public Object getDescricao() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}