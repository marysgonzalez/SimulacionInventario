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
     * @param diaSimulacion
     * @param tipo si es tabla de 1:demanda, 2:entrega o 3:espera
     * @return 
     */
    public int UbicarEnTabla(List<Tabla> lista, int tipo){
        Random numeroAleatorio;
        double num;
        DecimalFormat decimales;  
        numeroAleatorio = new Random();
        decimales = new  DecimalFormat("0.000");
        num = Double.parseDouble(decimales.format(numeroAleatorio.nextDouble()));
        //Recorrer la lista de probabilidades acumuladas
//        num = 0.995;
        System.out.println("Numero Aleatorio: "+num);
        for(int i=0; i<lista.size(); i++){
            //El primer elemento
            if(i==0 && num>=0 && num<lista.get(i).getProbabilidad()){
//                System.out.println("Iteracion:"+i);
//                System.out.println("Probabilidad:"+lista.get(i).getProbabilidad());
//                System.out.println("Valor:"+lista.get(i).getValor());
                return lista.get(i).getValor();
            }else if(i+1<lista.size() && lista.get(i).getProbabilidad()>=num && num<lista.get(i+1).getProbabilidad()){
//                System.out.println("Iteracion:"+i);
//                System.out.println("Probabilidad:"+lista.get(i).getProbabilidad());
//                System.out.println("Valor:"+lista.get(i).getValor());  
                return lista.get(i).getValor();              
            }else if(i+1==lista.size() && lista.get(i).getProbabilidad()>=num){
//                System.out.println("Iteracion:"+i);
//                System.out.println("Probabilidad:"+lista.get(i).getProbabilidad());
//                System.out.println("Valor:"+lista.get(i).getValor()); 
                return lista.get(i).getValor();
            }
        }
        return 0;
    }
}
