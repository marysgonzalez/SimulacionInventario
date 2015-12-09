/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;
import java.util.*;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Mary S. Gonzalez
 */
@XmlRootElement
public class Archivo {

    private List<String> demanda;
    private List<String> tiempoEntrega;
    private List<String> tiempoEspera;

    public List<String> getDemanda() {
        return demanda;
    }
    
    @XmlElement(name = "demanda") 
    public void setDemanda(List<String> demanda) {
        this.demanda = demanda;
    }

    public List<String> getTiempoEntrega() {
        return tiempoEntrega;
    }
    
    @XmlElement(name = "tiempoEntrega") 
    public void setTiempoEntrega(List<String> tiempoEntrega) {
        this.tiempoEntrega = tiempoEntrega;
    }

    public List<String> getTiempoEspera() {
        return tiempoEspera;
    }

    @XmlElement(name = "tiempoEspera") 
    public void setTiempoEspera(List<String> tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }

    
}
