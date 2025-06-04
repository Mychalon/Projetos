package model;

import java.util.Date;

public class Reserva {
    private int id;
    private int pessoaId;
    private int quartoId;
    private Date dataCheckin;
    private Date dataCheckout;
    private String status;
    private double valorTotal;
    
    // Construtor
    public Reserva() {}
    
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getPessoaId() { return pessoaId; }
    public void setPessoaId(int pessoaId) { this.pessoaId = pessoaId; }
    public int getQuartoId() { return quartoId; }
    public void setQuartoId(int quartoId) { this.quartoId = quartoId; }
    public Date getDataCheckin() { return dataCheckin; }
    public void setDataCheckin(Date dataCheckin) { this.dataCheckin = dataCheckin; }
    public Date getDataCheckout() { return dataCheckout; }
    public void setDataCheckout(Date dataCheckout) { this.dataCheckout = dataCheckout; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }
}