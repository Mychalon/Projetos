package model;

public class Acompanhante {
    private int idacompanhante;
    private String nome;
    private String telefoneacompanhate;
    private String CPF;
    private int idhospede;
    private int idQuarto;
    private boolean extra; 
    private double valorExtra;

    // Construtor
    public Acompanhante() {}

    public boolean isExtra() {
        return extra;
    }

    public void setExtra(boolean extra) {
        this.extra = extra;
    }

    public double getValorExtra() {
        return valorExtra;
    }

    public void setValorExtra(double valorExtra) {
        this.valorExtra = valorExtra;
    }
    // Getters e Setters
    public int getId() { 
        return idacompanhante; 
    }
    
    public void setId(int id) { 
        this.idacompanhante = id; 
    }
   
    public String getNome() { 
        return nome; 
    }
    
    public void setNome(String nome) { 
        this.nome = nome; 
    }
    
    public String getCpfacompanhante() { 
        return CPF; 
    }
    
    public void setCpfacompanhante(String cpf) { 
        this.CPF = cpf; 
    }
    
    public String getTelefone() { 
        return telefoneacompanhate; 
    }
    
    public void setTelefone(String telefone) { 
        this.telefoneacompanhate = telefone; 
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

    // Método corrigido - removendo a exceção não suportada
    public void setidhospede(int idHospede) {
        this.idhospede = idHospede;
    }

    
}