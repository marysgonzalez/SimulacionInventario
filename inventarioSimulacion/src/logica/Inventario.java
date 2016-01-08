/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;
import java.util.*;
import static logica.Simulacion.*;

/**
 *
 * @author Mary S. Gonzalez
 */
public class Inventario {
    private final double costoOrden, costoInventario, CostoconEspera, CostosinEspera;
    private int inicial;
    private float promedio;
    private int puntoReorden, satisfecho, insatisfecho;
    private double TcostoOrden, TcostoInventario, TCostoconEspera, TCostosinEspera;
    private List<Cliente> colaEspera;
    private Orden orden;

    public Inventario(double costoOrden, double costoInventario, double CostoconEspera, double CostosinEspera, int inicial) {
        this.costoOrden = costoOrden;
        this.costoInventario = costoInventario;
        this.CostoconEspera = CostoconEspera;
        this.CostosinEspera = CostosinEspera;
        this.inicial = inicial;
        this.promedio = 0;
        this.satisfecho = 0;
        this.insatisfecho = 0;
        this.puntoReorden = 0;
        this.orden = new Orden();
        this.colaEspera = new ArrayList<Cliente>();
        this.TCostosinEspera = 0;
        this.TCostoconEspera = 0;
        this.TcostoInventario = 0;
        this.TcostoOrden = 0;
    }
    public void setInicial(int inicial) {
        this.inicial = inicial;
    }

    public void setPromedio(float promedio) {
        this.promedio += promedio;
    }
    public void setPuntoReorden(int puntoReorden) {
        this.puntoReorden = puntoReorden;
    }

    /**
     * Calcula el costo de orden
     */
    public void TCostoOrden() {
        this.TcostoOrden += this.costoOrden;
    }

    public void setTcostoOrden(double TcostoOrden) {
        this.TcostoOrden = TcostoOrden;
    }

    /**
     * Calcula el costo de inventario
     * @param promedio suma de todos los inventarios promedios
     */
    public void  TCostoInventario(float promedio) {
        this.TcostoInventario = this.promedio*(this.costoInventario/365);
    }
    /**
     * 
     * @param satisfecho: cantidad de productos vendidos a un cliente que espera
     */
    public void TCostoconEspera(int satisfecho) {
        this.TCostoconEspera = satisfecho*this.CostoconEspera;
    }
    /**
     * 
     * @param insatisfecho: cantidad de productos cuya venta se perdio
     */
    public void TCostosinEspera(int insatisfecho) {
        this.TCostosinEspera = insatisfecho*this.CostosinEspera;
    }
    public void setSatisfecho(int satisfecho) {
        this.satisfecho += satisfecho;
    }

    public int getSatisfecho() {
        return satisfecho;
    }

    public int getInsatisfecho() {
        return insatisfecho;
    }

    public void setInsatisfecho(int insatisfecho) {
        this.insatisfecho += insatisfecho;
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

    public Orden getOrden() {
        return orden;
    }
    
    public void setTcostoInventario(double TcostoInventario) {
        this.TcostoInventario = TcostoInventario;
    }

    public void setTCostoconEspera(double TCostoconEspera) {
        this.TCostoconEspera = TCostoconEspera;
    }

    public void setTCostosinEspera(double TCostosinEspera) {
        this.TCostosinEspera = TCostosinEspera;
    }

    
    /**
     * @param demanda: demanda del numero aleatorio
     * @param escasez: costo con espera o sin espera. 
     */
    public int calcularCantidadOrden(int demanda, double escasez){
        double formula = Math.sqrt((2*this.costoOrden*demanda*365*(this.costoInventario+escasez))/(this.costoInventario*escasez));
        return (int)(Math.round(formula));
    }
    /**
     * 
     * @param dia: dia de entrega minimo y maximo
     * @param demanda: demanda minima y maxima
     * @param q: cantidad minima y maxima 
     * @return  valor del punto de reorden
     */
    public int calcularPuntoReorden(int dia, int demanda, int q){
        double to = 0;
        double L = 0;
        int Le = 0 , n = 0;
        to = (double)q/(demanda*365);
        L = ((double)dia/365);
        demanda = demanda*365;
        if(L < to){
            return ((int)Math.round(L*(demanda)));
        }else{
            n = (int) ((double)L/to);
            Le = (int)Math.round(L - n*to);
            return ((int)Math.round(Le*(demanda)));
        }
    }
    /**
     * Verifica si hay clientes en la cola y satisface sus pedidos por orden de llegada.
     * @param diaSimulacion dia actual de la simulacion
     */
    public void VerificarColaClientes(int diaSimulacion){
        int demanda = 0;
        if(!this.colaEspera.isEmpty() && this.colaEspera!=null){
            for(int i=0; i<this.colaEspera.size();i++){
                //Si aun no se cumple el dia maximo de espera del cliente
                if(this.colaEspera.get(i).getTiempoEspera() >= diaSimulacion){
                    demanda = this.colaEspera.get(i).getDemanda();
                    //Se puede satisfacer la demanda del cliente con el inventario actual?
                    if(this.getInicial() >= demanda){
                        this.setInicial(this.getInicial() - demanda);
                        this.setSatisfecho(demanda);
                        //Quitar cliente de la cola
                        this.colaEspera.remove(i);
                        i--;
                    }else if(this.getInicial()!=0){
                        //Vender todo lo que queda
                        demanda =demanda-this.getInicial();
                        this.setSatisfecho(this.getInicial());
                        this.setInicial(0);
                        this.colaEspera.get(i).setDemanda(demanda);
                    }
                }else{
                    //Paso el dia maximo de espera, se calcula el costo sin espera
                    this.setInsatisfecho(this.colaEspera.get(i).getDemanda());
                    this.colaEspera.remove(i);
                    i--;
                }
            }
        }  
    }
    /**
     * 
     * @param diaSimulacion dia actual de la simulacion
     * @param tDemanda tabla de la demanda
     * @param tEspera  tabla de la espera del cliente
     * @param opcion 1:lista de numeros aleatorios del archivo 0: numero aleatorio de la funcion
     */
    public int[] ActualizarInventario(int diaSimulacion, List<Tabla>tDemanda, List<Tabla>tEspera, int opcion){
        int InvFinal = 0, InvInicial=0;
        int demanda = 0;
        int tabla[] = new int[2];
        tabla[0] = tabla[1] = 0;
        double aleatorio;
        if(opcion==1){
            aleatorio = this.GenerarNumeroAleatorio(1, diaSimulacion-1);
            demanda = tDemanda.get(0).UbicarEnTabla(tDemanda, 1, aleatorio,1);
        }else{
            demanda = tDemanda.get(0).UbicarEnTabla(tDemanda, 0, 0,1);
        }
        tabla[0] = demanda;
        InvInicial = this.getInicial();
        if(InvInicial>0){
            //Si se puede satisfacer la demanda del cliente
            if(demanda <= InvInicial){
                InvFinal = InvInicial - demanda;
                this.setPromedio(Math.round((InvInicial+InvFinal)/2));
                this.setInicial(InvFinal);
            }else{
                this.setPromedio(Math.round((this.getInicial())/2));
                //Generar espera para el cliente
                if(opcion==1){
                    tabla[1] = this.GenerarEspera(tEspera, diaSimulacion, demanda, 1);
                }else{
                    tabla[1] = this.GenerarEspera(tEspera, diaSimulacion, demanda, 0);
                }         
            }
        }else{
            //Generar espera para el cliente
            this.setPromedio(Math.round((InvInicial)/2));
            if(opcion==1){
                tabla[1] = this.GenerarEspera(tEspera, diaSimulacion, demanda, 1);
            }else{
                tabla[1] = this.GenerarEspera(tEspera, diaSimulacion, demanda, 0);
            }
        }
         return tabla;
    }
    /**
     * 
     * @param diaSimulacion dia actual de la simulacion
     */
    public void VerificarOrden(int diaSimulacion){
        if(this.getOrden().getTiempoEntrega()==diaSimulacion){
            this.setInicial(this.getInicial()+this.getOrden().getCantidad());
            this.getOrden().setTiempoEntrega(0);
        }
    }
    /**
     * 
     * @param diaSimulacion dia actual de la simulacion
     * @param tEntrega tabla de entrega
     * @param opcion 1:ejecucion prueba 0: ejecucion normal
     */
    public boolean GenerarOrden(int diaSimulacion, List<Tabla> tEntrega,int opcion){
        int numero = this.getOrden().getNumero();
        int diaEntrega = 0;
        double aleatorio;
        //Si el inventario es menor al punto de reorden y no hay ordenes pendientes
        if(this.getInicial()<= this.getPuntoReorden() && this.getOrden().getTiempoEntrega()==0){
            this.getOrden().setNumero(numero+1);
            if(opcion==1){
                aleatorio = this.GenerarNumeroAleatorio(3,0);
                diaEntrega = tEntrega.get(0).UbicarEnTabla(tEntrega, 3, aleatorio,3);
            }else{
                diaEntrega = tEntrega.get(0).UbicarEnTabla(tEntrega, opcion, 0, 3);
            }  
            this.getOrden().setTiempoEntrega(diaSimulacion+diaEntrega+1);
            this.TCostoOrden();
            return true;
        }
        return false;
    }
    
    /**
     * 
     * @param tEspera tabla de espera
     * @param diaSimulacion dia actual de la simulacion
     * @param demanda demanda restante del cliente
     * @param opcion 1: ejecucion de prueba 0: ejecucion normal
     */
    public int GenerarEspera(List<Tabla> tEspera, int diaSimulacion, int demanda,int opcion){
        Cliente clienteNuevo;
        int InvFinal, diaEspera;
        double aleatorio;
        if(opcion == 1){
            aleatorio = this.GenerarNumeroAleatorio(2,0);
            diaEspera = tEspera.get(0).UbicarEnTabla(tEspera, 2, aleatorio,2);
        }else{
            diaEspera = tEspera.get(0).UbicarEnTabla(tEspera, 0, 0, 2);
        }
        InvFinal = demanda-this.getInicial();
        if(diaEspera!=0){
            //Se satisface lo que se puede
            clienteNuevo = new Cliente(InvFinal,0);
            //Se acabo el inventario
            this.setInicial(0);
            clienteNuevo.setTiempoEspera(diaSimulacion+diaEspera+1);
            this.colaEspera.add(clienteNuevo);
        }else{
//          El cliente no espera
            this.setInicial(0);
            this.setInsatisfecho(InvFinal);
        }
        return diaEspera;
    }
    /**
     * 
     * @param opcion 1: demanda 2: espera 3:entrega
     * @param index dia actual
     * @return 
     */
    public double GenerarNumeroAleatorio(int opcion, int index){
        double numero;
        switch(opcion){
            case 1:
                return alDemanda.get(index);
            case 2:
                for(int i=0; i<alEspera.size();i++){
                    if(alEspera.get(i)!=0.0){
                        numero = alEspera.get(i);
//                        alEspera.remove(i);
                        alEspera.set(i,0.0);
                        return numero;
                    }
                }
                break;
            case 3:
                return alEntrega.get(this.getOrden().getNumero()-1);
        }
        return 0;
    }
    /**
     * Inicializa todas las variables de inventario
     * @param inicial inventario inicial
     */
    public void limpiarInventario(int inicial){
        this.setInicial(inicial);
        this.getOrden().setNumero(0);
        this.getOrden().setTiempoEntrega(0);
        this.insatisfecho = 0;
        this.satisfecho = 0;
        this.promedio = 0;
        this.setTCostoconEspera(0);
        this.setTCostosinEspera(0);
        this.setTcostoInventario(0);
        this.setTcostoOrden(0);
        this.getColaEspera().clear();
    }
}
