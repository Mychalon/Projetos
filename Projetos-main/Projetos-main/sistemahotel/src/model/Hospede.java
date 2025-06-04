package model;

import java.util.Date;

public class Hospede {
    private int id;
    private int idQuarto;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private Date checkIn;
    private Date checkOut;

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    
     public void setIdQuarto(int idQuarto) {
        this.idQuarto = idQuarto;
    }
    
    public int getIdQuarto() {
        return idQuarto;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public int getQuartoId() {
        return quartoId;
    }

    public void setQuartoId(int quartoId) {
        this.quartoId = quartoId;
    }
    private int quartoId;

    // Construtor
    public Hospede() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}