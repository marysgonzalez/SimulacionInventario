/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.text.NumberFormat;
import java.util.*;
import static logica.Simulacion.*;

/**
 *
 * @author Mary S. Gonzalez
 */
public class Tabla{
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
     * @param tipo 1: demanda, 2:espera, 3: entrega
     * @return 
     */
    public int UbicarEnTabla(List<Tabla> lista, int opcion, double aleatorio, int tipo){
//        Random numeroAleatorio;
        double num;
//        numeroAleatorio = new Random();
        NumberFormat deci = NumberFormat.getNumberInstance(Locale.UK);
        deci.setMaximumFractionDigits(3);
        if(opcion==0){
            num = Double.parseDouble(deci.format(numeroAleatorio.nextDouble()));
            if (tipo == 1){
                alDemanda.add(num);
            }else if (tipo == 2){
                alEspera.add(num);
            }else if(tipo == 3){
                alEntrega.add(num);
            }
        }else{
            num = aleatorio;
        }
        //Recorrer la lista de probabilidades acumuladas
//        num = 0.69;
//        System.out.println("Numero:"+num);
        for(int i=0; i<lista.size(); i++){
            //El primer elemento
            if(i==0 && num>=0 && num<lista.get(i).getProbabilidad()){
                return lista.get(i).getValor();
            }else if(i!=0 && num>=lista.get(i-1).getProbabilidad() && num<lista.get(i).getProbabilidad()){
                return lista.get(i).getValor();              
            }else if(i+1==lista.size() && num>=lista.get(i-1).getProbabilidad()){
                return lista.get(i).getValor();
            }
        }
        return 0;
    }
    public int getMinValor(List<Tabla> t){
        int min = 100000;
        for(int i=0; i<t.size();i++){
            if(min > t.get(i).getValor()){
                min = t.get(i).getValor();
            }
        }
        return min;
    }
    public int getMaxValor(List<Tabla> t){
        int max = 0;
        for(int i=0; i<t.size();i++){
            if(max < t.get(i).getValor()){
                max = t.get(i).getValor();
            }
        }
        return max;
    }
}
