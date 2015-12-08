/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventariosimulacion;

/**
 *
 * @author Mary S. Gonzalez
 */
public class Cliente {
    /**
     * demanda: cantidad de un item, generado de la tabla de demanda
     * tiempoEspera: dia de la simulacion hasta el cual el cliente espera un pedido.
     * satisfecho: cantidad satisfecha de la demanda, cuando espera el  cliente en la cola.
     */
    private int demanda;
    private int tiempoEspera;
    private int satisfecho; 

    public Cliente(int demanda, int tiempoEspera, int satisfecho) {
        this.demanda = demanda;
        this.tiempoEspera = tiempoEspera;
        this.satisfecho = satisfecho;
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

    public int getSatisfecho() {
        return satisfecho;
    }

    public void setSatisfecho(int satisfecho) {
        this.satisfecho = satisfecho;
    }
    
}
