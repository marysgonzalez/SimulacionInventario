/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventariosimulacion;
import java.util.*;

/**
 *
 * @author Mary S. Gonzalez
 */
public class Inventario {
    private double costoOrden, costoInventario, CostoconEspera, CostosinEspera;
    private int inicial;
    private float promedio;
    private int puntoReorden;
    private double TcostoOrden, TcostoInventario, TCostoconEspera, TCostosinEspera;
    private List<Cliente> colaEspera;
    private Orden orden;

    public Inventario(double costoOrden, double costoInventario, double CostoconEspera, double CostosinEspera, int inicial, float promedio, int puntoReorden) {
        this.costoOrden = costoOrden;
        this.costoInventario = costoInventario;
        this.CostoconEspera = CostoconEspera;
        this.CostosinEspera = CostosinEspera;
        this.inicial = inicial;
        this.promedio = promedio;
        this.puntoReorden = puntoReorden;
    }
    public void setInicial(int inicial) {
        this.inicial = inicial;
    }

    public void setPromedio(float promedio) {
        this.promedio = promedio;
    }

    public void setPuntoReorden(int puntoReorden) {
        this.puntoReorden = puntoReorden;
    }

    public void TCostoOrden() {
        this.TcostoOrden += this.costoOrden;
    }
    /**
     * 
     * @param promedio: inventario promedio
     */
    public void TCostoInventario(int promedio) {
        this.TcostoInventario += this.promedio*this.costoInventario;
    }
    /**
     * 
     * @param satisfecho: cantidad de productos vendidos a un cliente que espera
     */
    public void TCostoconEspera(int satisfecho) {
        this.TCostoconEspera += satisfecho*this.CostoconEspera;
    }
    /**
     * 
     * @param insatisfecho: cantidad de productos cuya venta se perdio
     */
    public void TCostosinEspera(int insatisfecho) {
        this.TCostosinEspera += insatisfecho*this.CostosinEspera;
    }

    public double getCostoOrden() {
        return costoOrden;
    }

    public double getCostoInventario() {
        return costoInventario;
    }

    public double getCostoconEspera() {
        return CostoconEspera;
    }

    public double getCostosinEspera() {
        return CostosinEspera;
    }

    public int getInicial() {
        return inicial;
    }

    public float getPromedio() {
        return promedio;
    }

    public int getPuntoReorden() {
        return puntoReorden;
    }

    public double getTcostoOrden() {
        return TcostoOrden;
    }

    public double getTcostoInventario() {
        return TcostoInventario;
    }

    public double getTCostoconEspera() {
        return TCostoconEspera;
    }

    public double getTCostosinEspera() {
        return TCostosinEspera;
    }

    public List<Cliente> getColaEspera() {
        return colaEspera;
    }
    

    
}
