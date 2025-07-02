package telas;

import javax.swing.JLabel;

public class Quarto {
    private int idQuarto;
    private String numero;
    private String tipo;
    private String status;
    private int limiteAcompanhantes;
    private String checkIn;
    private String checkOut;

     
    
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
// No construtor ou métodos que criam o quarto, defina o limite baseado no tipo:
    public void setTipo(String tipo) {
        this.tipo = tipo;
        switch(tipo) {
            case "Luxo":
                this.limiteAcompanhantes = 3;
                break;
            case "Executivo":
                this.limiteAcompanhantes = 2;
                break;
            default:
                this.limiteAcompanhantes = 1;
        }
    }

    public int getLimiteAcompanhantes() {
        return limiteAcompanhantes;
    }

    
    // Getters e Setters completos
     public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }
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

   
 
}