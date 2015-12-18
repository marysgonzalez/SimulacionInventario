/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;
import java.util.*;

/**
 *
 * @author Mary S. Gonzalez
 */
public class Inventario {
    private double costoOrden, costoInventario, CostoconEspera, CostosinEspera;
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

    public void TCostoOrden() {
        this.TcostoOrden += this.costoOrden;
    }
    /**
     * 
     * @param promedio: sumatoriade todos los inventarios promedios
     */

    /**
     *
     * @param promedio : sumatoriade todos los inventarios promedios
     * @return total costo de inventario al final de la simulacion
     */
    public double  TCostoInventario(float promedio) {
        return this.TcostoInventario = this.promedio*(this.costoInventario/365);
    }
    /**
     * 
     * @param satisfecho: cantidad de productos vendidos a un cliente que espera
     */
    public double TCostoconEspera(int satisfecho) {
        return this.TCostoconEspera = satisfecho*this.CostoconEspera;
    }
    /**
     * 
     * @param insatisfecho: cantidad de productos cuya venta se perdio
     */
    public double TCostosinEspera(int insatisfecho) {
        return this.TCostosinEspera = insatisfecho*this.CostosinEspera;
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

    public void setOrden(Orden orden) {
        this.orden = orden;
    }
    
    /**
     * @int demanda: demanda del numero aleatorio
     * @double escasez: costo con espera o sin espera. 
     */
    public int calcularCantidadOrden(int demanda, double escasez){
        double formula = Math.sqrt((2*this.costoOrden*demanda*365*(this.costoInventario+escasez))/(this.costoInventario*escasez));
        int q = 0;
        q = (int)(Math.round(formula));
        return q;
    }
    /**
     * 
     * @param dia: dia de entrega minimo y maximo
     * @param demanda: demanda minima y maxima
     * @param q: cantidad minima y maxima 
     */
    public int calcularPuntoReorden(int dia, int demanda, int q){
        double to = 0;
        double L = 0;
        int Le = 0 , n = 0;
        to = (double)q/(demanda*365);
        L = ((double)dia/365);
//        System.out.println("to:"+to);
//        System.out.println("L:"+L);
//        System.out.println("D:"+demanda);
//        System.out.println("q:"+q);
        if(L < to){
            return ((int)Math.round(L*(demanda*365)));
        }else{
            n = (int) ((double)L/to);
            Le = (int)Math.round(L - n*to);
            return ((int)Math.round(Le*(demanda*365)));
        }
    }
    /**
     * Verifica si hay clientes en la cola y satisface sus pedidos por orden de llegada.
     * @param diaSimulacion dia actual de la simulacion
     */
    public void VerificarColaClientes(int diaSimulacion){
        if(!this.colaEspera.isEmpty() && this.colaEspera!=null){
            System.out.println("-------------------");
            System.out.println("Clientes en cola:"+ this.colaEspera.size());
            for(int i=0; i<this.colaEspera.size();i++){
                //Si aun no se cumple el dia maximo de espera del cliente
                if(diaSimulacion <= this.colaEspera.get(i).getTiempoEspera()){
                    System.out.println("Se atendio al cliente #"+i);
                    System.out.println("Demanda del cliente:"+ this.colaEspera.get(i).getDemanda());
                    //Se puede satisfacer la demanda del cliente con el inventario actual?
                    if(this.inicial >= this.colaEspera.get(i).getDemanda()){
                        System.out.println("Cliente satisfecho:"+this.colaEspera.get(i).getDemanda());
                        this.colaEspera.get(i).setSatisfecho(this.colaEspera.get(i).getDemanda());
                        this.setInicial(this.getInicial() - this.colaEspera.get(i).getDemanda());
                        //Calcular costo con espera
                        this.setSatisfecho(this.colaEspera.get(i).getSatisfecho());
//                        this.TCostoconEspera(this.colaEspera.get(i).getSatisfecho());
                        //Quitar cliente de la cola
                        this.colaEspera.remove(i);
                    }else{
                        //Vender todo lo que queda
                         System.out.println("No fue suficiente.");
                        this.colaEspera.get(i).setSatisfecho(this.inicial);
                        this.setInicial(0);
                        this.setInsatisfecho(this.colaEspera.get(i).getDemanda()-this.inicial);               
                    }
                }else{
                    //Paso el dia maximo de espera, se calcula el costo sin espera
                    System.out.println("Espera excedida");
                    this.setInsatisfecho(this.colaEspera.get(i).getDemanda());
//                    this.TCostosinEspera(this.colaEspera.get(i).getDemanda());
                    this.colaEspera.remove(i);
                }
            }
        }
    }
    /**
     * 
     * @param diaSimulacion dia actual de la simulacion
     * @param tDemanda tabla de la demanda
     * @param tEspera  tabla de la espera del cliente
     */
    public void ActualizarInventario(int diaSimulacion, List<Tabla>tDemanda, List<Tabla>tEspera){
        int InvFinal = 0;
        int demanda = 0, diaEspera = 0;
        Cliente clienteNuevo;
        //Generar numero aleatorio de la demanda y obtener el valor
        demanda = tDemanda.get(0).UbicarEnTabla(tDemanda, 1);
        System.out.println("Demanda:"+demanda);
        if(this.getInicial()>0){
            this.VerificarColaClientes(diaSimulacion);
            //Si se puede satisfacer la demanda del cliente
            if(demanda <= this.getInicial()){
                InvFinal = this.getInicial() - demanda;
                this.setPromedio((this.getInicial()+InvFinal)/2);
                System.out.println("Inventario Promedio:"+(this.getInicial()+InvFinal)/2);
                this.setInicial(InvFinal);
            }else{
                System.out.println("Inventario Promedio:"+(this.getInicial()+InvFinal)/2);
                //Generar espera para el cliente
                this.GenerarEspera(tEspera, diaSimulacion, demanda);               
            }
        }else{
            //Generar espera para el cliente
                System.out.println("Inventario Promedio:"+(this.getInicial()+InvFinal)/2);
                this.GenerarEspera(tEspera, diaSimulacion, demanda);
        }
         System.out.println("Inventario Final:"+this.getInicial());
    }
    public void VerificarOrden(int diaSimulacion){
        if(this.getOrden().getTiempoEntrega()==diaSimulacion){
            System.out.println("Llego orden:"+this.getOrden().getNumero());
            this.setInicial(this.getOrden().getCantidad());
            this.getOrden().setTiempoEntrega(0);
        }
    }
    public void GenerarOrden(int diaSimulacion, List<Tabla> tEntrega){
        int numero = this.getOrden().getNumero();
        int diaEntrega = 0;
        if(this.getInicial()< this.getPuntoReorden() && this.getOrden().getTiempoEntrega()==0){
            this.getOrden().setNumero(numero+1);
            diaEntrega = tEntrega.get(0).UbicarEnTabla(tEntrega, 2);
            System.out.println("Dia para la orden:"+diaEntrega);
            this.getOrden().setTiempoEntrega(diaSimulacion+diaEntrega+1);
            this.TCostoOrden();
            System.out.println("Dia llegada orden:"+this.getOrden().getTiempoEntrega());
        }
    }
    
    public void GenerarEspera(List<Tabla> tEspera, int diaSimulacion, int demanda){
        Cliente clienteNuevo;
        int InvFinal, diaEspera;
        diaEspera = tEspera.get(0).UbicarEnTabla(tEspera,3);
        InvFinal = demanda-this.getInicial();
        if(diaEspera!=0){
            //Se satisface lo que se puede
            clienteNuevo = new Cliente(InvFinal,0,0);
            //Se acabo el inventario
            this.setInicial(0);
            clienteNuevo.setTiempoEspera(diaSimulacion+diaEspera);
            this.colaEspera.add(clienteNuevo);
            System.out.println("Dia para el cliente:"+diaEspera);
            System.out.println("El cliente espera:"+clienteNuevo.getTiempoEspera());
            System.out.println("Faltante del cliente:"+ clienteNuevo.getDemanda());

        }else{
            System.out.println("El cliente no espera.");
            this.setInicial(0);
            this.setInsatisfecho(InvFinal);
        }
        
    }
}
