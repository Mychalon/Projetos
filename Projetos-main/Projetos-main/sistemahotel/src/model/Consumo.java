package model;

import java.time.LocalDateTime;

public class Consumo {
    private int id;
    private int idHospedagem;
    private int hospedeId;
    private int idProduto;
    private String nomeProduto;
    private String descricao;
    private int quantidade;
    private double valorUnitario;
    private LocalDateTime dataHora;

    // Construtor
    public Consumo() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getIdHospedagem() { return idHospedagem; }
    public void setIdHospedagem(int idHospedagem) { this.idHospedagem = idHospedagem; }
    
   public int getHospedeId() {
    return this.hospedeId;
}

public void setHospedeId(int hospedeId) {
    this.hospedeId = hospedeId;
}
    public int getIdProduto() { return idProduto; }
    public void setIdProduto(int idProduto) { this.idProduto = idProduto; }
    
    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
    
    public String getdescricao() { return descricao; }
    public void setdescricao(String descricao) { this.descricao = descricao; }
    
  
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    
    public double getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(double valorUnitario) { this.valorUnitario = valorUnitario; }
    
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    
    public double getValorTotal() {
        return quantidade * valorUnitario;
    }

   

    
}