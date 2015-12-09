/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.File;
import java.util.ArrayList;
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
    
    
    public static void main(String[] args) {
        Archivo objeto = null;
        List<Tabla> tDemanda = null, tEntrega = null, tEspera = null;

        try {
            tDemanda = new ArrayList <Tabla>();
            tEntrega = new ArrayList <Tabla>();
            tEspera = new ArrayList <Tabla>();
            
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

            System.out.println(tDemanda.get(9).getProbabilidad());
            System.out.println(tEspera.get(3).getProbabilidad());
            System.out.println(tEntrega.get(3).getProbabilidad());
        } catch (JAXBException ex) {
            System.out.println("Error en Lectura");
        }
    }
    
}
