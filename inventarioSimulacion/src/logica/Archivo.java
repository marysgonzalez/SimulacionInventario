/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;
import java.text.DecimalFormat;
import java.util.*;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Mary S. Gonzalez
 */
@XmlRootElement
public class Archivo {

    private int invInicial;
    private List<String> demanda;
    private List<String> tiempoEntrega;
    private List<String> tiempoEspera;
    private List<Double> nroAleatorioDemanda;
    private List<Double> nroAleatorioEspera;
    private List<Double> nroAleatorioEntrega;
    private double costoInv;
    private double costoOrden;
    private double costoEspera;
    private double costoSinEspera;

    public int getInvInicial() {
        return invInicial;
    }

    public List<Double> getNroAleatorioDemanda() {
        return nroAleatorioDemanda;
    }
    @XmlElement(name = "nroAleatorioDemanda")
    public void setNroAleatorioDemanda(List<Double> nroAleatorioDemanda) {
        this.nroAleatorioDemanda = nroAleatorioDemanda;
    }

    public List<Double> getNroAleatorioEspera() {
        return nroAleatorioEspera;
    }
    @XmlElement(name = "nroAleatorioEspera")
    public void setNroAleatorioEspera(List<Double> nroAleatorioEspera) {
        this.nroAleatorioEspera = nroAleatorioEspera;
    }

    public List<Double> getNroAleatorioEntrega() {
        return nroAleatorioEntrega;
    }
    @XmlElement(name = "nroAleatorioEntrega") 
    public void setNroAleatorioEntrega(List<Double> nroAleatorioEntrega) {
        this.nroAleatorioEntrega = nroAleatorioEntrega;
    }


    @XmlElement(name = "invInicial") 
    public void setInvInicial(int invInicial) {
        this.invInicial = invInicial;
    }

    public double getCostoInv() {
        return costoInv;
    }

    @XmlElement(name = "costoInventario") 
    public void setCostoInv(double costoInv) {
        this.costoInv = costoInv;
    }

    public double getCostoOrden() {
        return costoOrden;
    }

    @XmlElement(name = "costoOrden") 
    public void setCostoOrden(double costoOrden) {
        this.costoOrden = costoOrden;
    }

    public double getCostoEspera() {
        return costoEspera;
    }

    @XmlElement(name = "costoEspera") 
    public void setCostoEspera(double costoEspera) {
        this.costoEspera = costoEspera;
    }

    public double getCostoSinEspera() {
        return costoSinEspera;
    }

    @XmlElement(name = "costoSinEspera") 
    public void setCostoSinEspera(double costoSinEspera) {
        this.costoSinEspera = costoSinEspera;
    }

    public List<String> getDemanda() {
        return demanda;
    }
    
    @XmlElement(name = "demanda") 
    public void setDemanda(List<String> demanda) {
        this.demanda = demanda;
    }

    public List<String> getTiempoEntrega() {
        return tiempoEntrega;
    }
    
    @XmlElement(name = "tiempoEntrega") 
    public void setTiempoEntrega(List<String> tiempoEntrega) {
        this.tiempoEntrega = tiempoEntrega;
    }

    public List<String> getTiempoEspera() {
        return tiempoEspera;
    }

    @XmlElement(name = "tiempoEspera") 
    public void setTiempoEspera(List<String> tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }

    /**
     * 
     * @param lista
     * @return 
     */
    public List<String> calcularProbAcum(List<String> lista){
        double prob = 0;
        for (int i=0; i< lista.size(); i++){
            prob += Double.parseDouble(lista.get(i).split("-")[1]);    
            lista.set(i, lista.get(i).split("-")[0]+"-"+prob);
        }
        return lista;
    }
    
    public void asignarTabla(List<String> lista, List<Tabla> tDemanda){
        DecimalFormat decimales = new  DecimalFormat("0.000");
        for (int i=0; i<lista.size(); i++){
            Tabla tabla = new Tabla();
            tabla.setValor(Integer.parseInt(lista.get(i).split("-")[0]));
            tabla.setProbabilidad(Double.parseDouble(decimales.format(Double.parseDouble(lista.get(i).split("-")[1]))));
            tDemanda.add(tabla);
        }
    }
}
