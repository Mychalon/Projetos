package model;

import java.util.ArrayList;
import java.util.List;

public class Quarto {
    private String nomeQuarto;
    private String nomeHospede;
    private String cpfHospede;
    private List<String> acompanhantes = new ArrayList<>();
    private String telefone1;
    private String telefone2;
    private String observacao;
    private String checkIn;
    private String checkOut;

    public Quarto(String nomeQuarto) {
        this.nomeQuarto = nomeQuarto;
    }

    // Getters e Setters para todos os campos
    public String getNomeQuarto() { return nomeQuarto; }
    public String getNomeHospede() { return nomeHospede; }
    public void setNomeHospede(String nomeHospede) { this.nomeHospede = nomeHospede; }
    public String getCpfHospede() { return cpfHospede; }
    public void setCpfHospede(String cpfHospede) { this.cpfHospede = cpfHospede; }
    public List<String> getAcompanhantes() { return acompanhantes; }
    public String getTelefone1() { return telefone1; }
    public void setTelefone1(String telefone1) { this.telefone1 = telefone1; }
    public String getTelefone2() { return telefone2; }
    public void setTelefone2(String telefone2) { this.telefone2 = telefone2; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
    public String getCheckIn() { return checkIn; }
    public void setCheckIn(String checkIn) { this.checkIn = checkIn; }
    public String getCheckOut() { return checkOut; }
    public void setCheckOut(String checkOut) { this.checkOut = checkOut; }

    public boolean estaOcupado() {
        return nomeHospede != null && !nomeHospede.isEmpty();
    }
}