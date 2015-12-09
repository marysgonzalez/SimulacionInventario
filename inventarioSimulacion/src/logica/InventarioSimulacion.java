/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Mary S. Gonzalez
 */
public class InventarioSimulacion {

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) {
        Archivo objeto = null;
        try {
            File file = new File("src/XML/prueba1.xml");
            JAXBContext jaxbContext;
            jaxbContext = JAXBContext.newInstance(Archivo.class);
            
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            objeto = (Archivo) jaxbUnmarshaller.unmarshal(file);
            
           // Obtener el valor de la demanda
            System.out.println(objeto.getDemanda().get(0).split("-")[0]);
           // Obtener la probabilidad de dicha demanda  
            System.out.println(objeto.getDemanda().get(0).split("-")[1]);
        
        } catch (JAXBException ex) {
            System.out.println("Error en Lectura");
        }
    }
    
}
