/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Mary S. Gonzalez
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
    static final int diaSimulacion=2;
    public static void main(String[] args) {
        Archivo objeto = null;
        List<Tabla> tDemanda = null, tEntrega = null, tEspera = null;
        tDemanda = new ArrayList <Tabla>();
        tEntrega = new ArrayList <Tabla>();
        tEspera = new ArrayList <Tabla>();
        int MinQ = 0, MaxQ = 0;
        int MinPR = 0, MaxPR =0;
        
        try {    
            File file = new File("src/XML/prueba1.xml");
            JAXBContext jaxbContext;
            jaxbContext = JAXBContext.newInstance(Archivo.class);
            
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            objeto = (Archivo) jaxbUnmarshaller.unmarshal(file);
            
           // Calcular las prob acumuladas
            objeto.calcularProbAcum(objeto.getDemanda());
            objeto.calcularProbAcum(objeto.getTiempoEspera());
            objeto.calcularProbAcum(objeto.getTiempoEntrega());
            
           //Distribuir en las listas de tabla           
            objeto.asignarTabla(objeto.getDemanda(), tDemanda);
            objeto.asignarTabla(objeto.getTiempoEspera(), tEspera);
            objeto.asignarTabla(objeto.getTiempoEntrega(), tEntrega);
            
            Inventario inventario = new Inventario(objeto.getCostoOrden(),objeto.getCostoInv(),objeto.getCostoEspera(),objeto.getCostoSinEspera(), objeto.getInvInicial(), 75);
            //OJO, tienen que evaluarse todas las combinaciones de q y R en este punto
            // El costo de escasez (s), se calcula: la demanda mas peque;a con el s mas grande, y viceversa
            MinQ = inventario.calcularCantidadOrden(tDemanda.get(0).getValor(), inventario.getCostosinEspera());
            MaxQ =  inventario.calcularCantidadOrden(tDemanda.get(tDemanda.size()-1).getValor(), inventario.getCostoconEspera());

            System.out.println("Intervalo de Q:"+ MinQ +"-"+MaxQ);
            
            inventario.calcularPuntoReorden(tEntrega.get(0).getValor(), tDemanda.get(0).getValor(), MinQ);
            inventario.calcularPuntoReorden(tEntrega.get(tEntrega.size()-1).getValor(), tDemanda.get(tDemanda.size()-1).getValor(), MaxQ);
            System.out.println("Intervalo de R:"+ MinPR +"-"+MaxPR);

            /*Dia de simulacion*/
            for(int i=1; i<= diaSimulacion;i++){
                System.out.println("Dia Simulacion:"+ i);
//                if(i==1){
                    System.out.println("Inv Inicial:"+inventario.getInicial());
//                }
                inventario.VerificarOrden(diaSimulacion);
                //Ordenar clientes
                if(inventario.getColaEspera()!=null){
                    Collections.sort(inventario.getColaEspera());
                }
                inventario.ActualizarInventario(diaSimulacion, tDemanda, tEspera);
                inventario.GenerarOrden(diaSimulacion, tEntrega);
                System.out.println("-------------------");
            }
            System.out.println("Costo Inventario:"+ inventario.TCostoInventario(inventario.getPromedio()));
            System.out.println("Costo con espera:"+ inventario.TCostoconEspera(inventario.getSatisfecho()));
            System.out.println("Costo sin espera:"+ inventario.TCostosinEspera(inventario.getInsatisfecho()));
            //La suma de todos los costos
            System.out.println("Costo Total:");
//            System.out.println("Valor de la demanda:"+tDemanda.get(0).UbicarEnTabla(tDemanda));
//            System.out.println("Dia de espera:"+tEspera.get(0).UbicarEnTabla(tEspera));
//            System.out.println("Dia de entrega:"+tEntrega.get(0).UbicarEnTabla(tEntrega));
        } catch (JAXBException ex) {
            System.out.println("Error en Lectura");
        }
    }
    
}
