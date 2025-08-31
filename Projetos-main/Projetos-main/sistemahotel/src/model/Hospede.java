package model;

public class Hospede {
    private int idhospede;
    private int idQuarto;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String status; // "Hospedado" ou "Saída"
    private int quartoId;
    private String placaVeiculo;

    // Construtor
    public Hospede() {
     
        
    }
    
   
  

    // Getter e Setter
    public String getPlacaVeiculo() {
        return placaVeiculo;
    }
        
    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }
    
    // Getters e Setters
     public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public int getIdhospede() {
        return idhospede;
    }

    public void setIdhospede(int idhospede) {
        this.idhospede = idhospede;
    }

    public int getIdQuarto() {
        return idQuarto;
    }

    public void setIdQuarto(int idQuarto) {
        this.idQuarto = idQuarto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpfHospede() {
        return cpf;
    }

    public void setCpfHospede(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getQuartoId() {
        return quartoId;
    }

    public void setQuartoId(int quartoId) {
        this.quartoId = quartoId;
    }

    // Método corrigido - removendo a exceção não suportada
    public int getidhospede() {
        return idhospede;
    }
}