package telas;

public class Quarto {
    private int idQuarto;
    private String numero;
    private String tipo;
    private String status;
    private String nomeHospede;
    private String cpfHospede;
     private String telefone;
     
    
    // Construtor vazio
    public Quarto() {}
    
    // Construtor com número apenas (adicione este)
    public Quarto(String numero) {
        this.numero = numero;
    }
    
    // Construtor com parâmetros
    public Quarto(int idQuarto, String numero, String tipo, String status) {
        this.idQuarto = idQuarto;
        this.numero = numero;
        this.tipo = tipo;
        this.status = status;
    }

    
    // Getters e Setters completos
    public String getNumero() {
        return numero;
    }
    
     public int getIdQuarto() {
        return idQuarto;
    }
    
    public void setIdQuarto(int idQuarto) {
        this.idQuarto = idQuarto;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo; // Implementação correta agora
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    // Método útil para exibição
    @Override
    public String toString() {
        return numero + " - " + tipo;
    }

     // Adicione estes métodos:
    public String getNomeHospede() {
        return this.nomeHospede;
    }
    
    public String getCpfHospede() {
        return this.cpfHospede;
    }
    
    public String getTelefone() {
        return this.telefone;
    }
    
    // E também os setters correspondentes:
    public void setNomeHospede(String nomeHospede) {
        this.nomeHospede = nomeHospede;
    }
    
    public void setCpfHospede(String cpfHospede) {
        this.cpfHospede = cpfHospede;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    

    
   
    
}