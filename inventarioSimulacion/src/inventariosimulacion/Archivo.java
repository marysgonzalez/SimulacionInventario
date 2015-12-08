/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventariosimulacion;
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

}
