/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 *
 * @author Mary S. Gonzalez
 */
public class Cliente implements Comparable <Cliente>{
    /**
     * demanda: cantidad de un item, generado de la tabla de demanda
     * tiempoEspera: dia de la simulacion hasta el cual el cliente espera un pedido.
     */
    private int demanda;
    private int tiempoEspera;

    /**
     * 
     * @param demanda
     * @param tiempoEspera
     */
    public Cliente(int demanda, int tiempoEspera) {
        this.demanda = demanda;
        this.tiempoEspera = tiempoEspera;
    }

    public int getDemanda() {
        return demanda;
    }

    public void setDemanda(int demanda) {
        this.demanda = demanda;
    }

    public int getTiempoEspera() {
        return tiempoEspera;
    }

    public void setTiempoEspera(int tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }

    @Override
    public int compareTo(Cliente t) {
        return Integer.compare(demanda, t.getDemanda());
    }
    
}
