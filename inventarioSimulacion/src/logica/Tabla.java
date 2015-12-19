/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.text.DecimalFormat;
import java.util.*;

/**
 *
 * @author Mary S. Gonzalez
 */
public class Tabla {
    /**
     * Valor: correspondiente a la probabilidad
     * Probabilidad: lista de probabilidad acumulada
     */
    private int valor;
    private double probabilidad;

    public Tabla() {
        this.valor = 0;
        this.probabilidad = 0;
    }
    public Tabla(int valor, double probabilidad) {
        this.valor = valor;
        this.probabilidad = probabilidad;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public double getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(double probabilidad) {
        this.probabilidad = probabilidad;
    }
    /**
     * 
     * @param lista de demanda, entrega o espera
     * @param opcion 0: ejecucion normal 1:ejecucion de prueba
     * @param aleatorio numero aleatorio del archivo
     * @return 
     */
    public int UbicarEnTabla(List<Tabla> lista, int opcion, double aleatorio){
        Random numeroAleatorio;
        double num;
        DecimalFormat decimales;  
        numeroAleatorio = new Random();
        decimales = new  DecimalFormat("0.000");
        if(opcion==0){
            num = Double.parseDouble(decimales.format(numeroAleatorio.nextDouble()));
        }else{
            num = aleatorio;
        }
        //Recorrer la lista de probabilidades acumuladas
        System.out.println("Numero Aleatorio: "+num);
        for(int i=0; i<lista.size(); i++){
            //El primer elemento
            if(i==0 && num>=0 && num<lista.get(i).getProbabilidad()){
                return lista.get(i).getValor();
            }else if(i+1<lista.size() && lista.get(i).getProbabilidad()>=num && num<lista.get(i+1).getProbabilidad()){
                return lista.get(i).getValor();              
            }else if(i+1==lista.size() && lista.get(i).getProbabilidad()>=num){
                return lista.get(i).getValor();
            }
        }
        return 0;
    }

}
