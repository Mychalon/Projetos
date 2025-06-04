package model;

import java.util.Date;
import java.util.List;

public class Hospedagem {
    private int id;    
    private Hospede hospedePrincipal;
    private List<Acompanhante> acompanhantes;
    private Date checkIn;
    private Date checkOut;
    private List<Consumo> consumos;
    private Funcionario funcionario;
    
    // Getters, setters e métodos de negócio

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public Hospede getHospedePrincipal() {
        return hospedePrincipal;
    }

    public void setHospedePrincipal(Hospede hospedePrincipal) {
        this.hospedePrincipal = hospedePrincipal;
    }

    public List<Acompanhante> getAcompanhantes() {
        return acompanhantes;
    }

    public void setAcompanhantes(List<Acompanhante> acompanhantes) {
        this.acompanhantes = acompanhantes;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public List<Consumo> getConsumos() {
        return consumos;
    }

    public void setConsumos(List<Consumo> consumos) {
        this.consumos = consumos;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    private static class Hospede {

        public Hospede() {
        }
    }

    private static class Acompanhante {

        public Acompanhante() {
        }
    }

    private static class Consumo {

        public Consumo() {
        }
    }
}