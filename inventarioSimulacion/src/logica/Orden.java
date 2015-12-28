/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import static logica.Simulacion.*;

/**
 *
 * @author Mary S. Gonzalez
 */
public class Orden {
    /**
     * tiempoEntrega: dia de la simulacion que corresponde la llegada de la orden
     * cantidad: cantidad a reabastecer (q)
     * numero: numero de la orden (1,2,3...) es un contador.
     */
    private int tiempoEntrega;
    private int cantidad;
    private int numero;
    
    public Orden() {
        this.tiempoEntrega = 0;
        this.cantidad = 0;
        this.numero = 0;
    }
    public Orden(int tiempoEntrega, int cantidad, int numero) {
        this.tiempoEntrega = tiempoEntrega;
        this.cantidad = cantidad;
        this.numero = numero;
    }

    public int getTiempoEntrega() {
        return tiempoEntrega;
    }

    public void setTiempoEntrega(int tiempoEntrega) {
        this.tiempoEntrega = tiempoEntrega;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
}
