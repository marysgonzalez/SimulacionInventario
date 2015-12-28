    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import interfaz.Ventana;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import jxl.Workbook;
import jxl.write.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.Pattern;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author Mary S. Gonzalez
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
    static int diaSimulacion=365;
    public static List<Double> alDemanda = new ArrayList<Double>();
    public static List<Double> alEspera = new ArrayList<Double>();
    public static List<Double> alEntrega = new ArrayList<Double>();
    
    public static void main(String[] args) {
/*
        Archivo objeto = null;
        List<Tabla> tDemanda = null, tEntrega = null, tEspera = null;
        tDemanda = new ArrayList <Tabla>();
        tEntrega = new ArrayList <Tabla>();
        tEspera = new ArrayList <Tabla>();
        int datos[] = new int[2];
        int MinQ = 0, MaxQ = 0;
        int MinPR = 0, MaxPR = 0;
        
        try { 
            JAXBContext jaxbContext;
            jaxbContext = JAXBContext.newInstance(Archivo.class);
            
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            
           // Calcular las prob acumuladas
            objeto.calcularProbAcum(objeto.getDemanda());
            objeto.calcularProbAcum(objeto.getTiempoEspera());
            objeto.calcularProbAcum(objeto.getTiempoEntrega());
            
           //Distribuir en las listas de tabla           
            objeto.asignarTabla(objeto.getDemanda(), tDemanda);
            objeto.asignarTabla(objeto.getTiempoEspera(), tEspera);
            objeto.asignarTabla(objeto.getTiempoEntrega(), tEntrega);

            //numeros aleatorios
            alDemanda = objeto.getNroAleatorioDemanda();
            alEntrega = objeto.getNroAleatorioEntrega();
            alEspera = objeto.getNroAleatorioEspera();
            
            WritableWorkbook excel = Workbook.createWorkbook(new File("C:\\Users\\Vicky\\Desktop\\tablaEventos.xls")); 
            WritableSheet hojaTrabajo = excel.createSheet("TotalCostos", 0);
            
            
            if(alDemanda!=null && alEntrega!=null && alEspera!=null){
                System.out.println("Archivo de prueba");
                diaSimulacion = 15;
                Inventario inventario = new Inventario(objeto.getCostoOrden(),objeto.getCostoInv(),objeto.getCostoEspera(),objeto.getCostoSinEspera(), objeto.getInvInicial());
                MinQ = 100;
                MinPR = 75;
                inventario.getOrden().setCantidad(MinQ);
                inventario.setPuntoReorden(MinPR);
                Dia de simulacion
                System.out.println("Valor de q:"+ inventario.getOrden().getCantidad());
                System.out.println("Punto de Reorden:"+inventario.getPuntoReorden());
                System.out.println("--------------------------");
//                
                //Cabecera del archivo Excel

                Label[] titulos = new Label[18];
                titulos[0] = new Label(0,0,"Dia",getCellFormat(jxl.format.Colour.GREEN));
                titulos[1] = new Label(1,0,"Inv. Inicial",getCellFormat(jxl.format.Colour.GREEN));
                titulos[2] = new Label(2,0,"Nro. Alea. Dem.",getCellFormat(jxl.format.Colour.GREEN));
                titulos[3] = new Label(3,0,"Demanda",getCellFormat(jxl.format.Colour.GREEN));
                titulos[4] = new Label(4,0,"Inv. Final",getCellFormat(jxl.format.Colour.GREEN));
                titulos[5] = new Label(5,0,"Inv. Prom",getCellFormat(jxl.format.Colour.GREEN));
                titulos[6] = new Label(6,0,"Faltante",getCellFormat(jxl.format.Colour.GREEN));
                titulos[7] = new Label(7,0,"Nro. Orden",getCellFormat(jxl.format.Colour.GREEN));
                titulos[8] = new Label(8,0,"Nro. Alea. T_Entrega",getCellFormat(jxl.format.Colour.GREEN));
                titulos[9] = new Label(9,0,"Tiempo Entrega",getCellFormat(jxl.format.Colour.GREEN));
                titulos[10] = new Label(10,0,"Nro. Alea. T_Espera",getCellFormat(jxl.format.Colour.GREEN));
                titulos[11] = new Label(11,0,"Tiempo Espera",getCellFormat(jxl.format.Colour.GREEN));
                
                titulos[12] = new Label(0,diaSimulacion+4,"Costo de Inventario");
                titulos[13] = new Label(0,diaSimulacion+5,"Costo Faltante");
                titulos[14] = new Label(0,diaSimulacion+6,"Costo de Orden");
                titulos[15] = new Label(0,diaSimulacion+7,"Costo Total", getCellFormat(null)); 
                titulos[16] = new Label(0,diaSimulacion+8,"Q", getCellFormat(null));
                titulos[17] = new Label(0,diaSimulacion+9,"PR", getCellFormat(null));
                
                for (int in=0;in<titulos.length; in++){
                    hojaTrabajo.addCell(titulos[in]);
                }
                
                for(int i=1; i<= diaSimulacion;i++){
                    System.out.println("Dia Simulacion:"+ i); 
                    //LLego orden?
                    
                    inventario.VerificarOrden(i);
                    System.out.println("Inv Inicial:"+inventario.getInicial());
                    
                    if(inventario.getInicial()>0){
                        inventario.VerificarColaClientes(i);
                    }
                    
                    //Escritura en Excel
                    hojaTrabajo.addCell(new Number(0, i, i));
                    hojaTrabajo.addCell(new Number(1,i, inventario.getInicial()));
                    hojaTrabajo.addCell(new Number(2,i, alDemanda.get(i-1)));
    
                    if(datos[0] > Integer.parseInt(hojaTrabajo.getCell(1, i).getContents())){
                        for(int l=0; l<alEspera.size();l++){
                            if(alEspera.get(l)!=0.0){
                                hojaTrabajo.addCell(new Number(10,i, alEspera.get(l)));
                                break;
                            }
                        }
                    }
                    
                    datos = inventario.ActualizarInventario(i, tDemanda, tEspera, 1);
                    
                    hojaTrabajo.addCell(new Number(3,i, datos[0]));
                    hojaTrabajo.addCell(new Number(4,i, inventario.getInicial()));
                    hojaTrabajo.addCell(new Number(5,i, ((Integer.parseInt(hojaTrabajo.getCell(1, i).getContents())+inventario.getInicial())/2)));
                    
                    if(datos[0] > Integer.parseInt(hojaTrabajo.getCell(1, i).getContents())){
                        hojaTrabajo.addCell(new Number(6,i, (datos[0] - Integer.parseInt(hojaTrabajo.getCell(1, i).getContents()))));
                        hojaTrabajo.addCell(new Number(11,i, datos[1]));
                    }
                    
                    if(inventario.GenerarOrden(i, tEntrega, 1)){
                        hojaTrabajo.addCell(new Number(7,i, inventario.getOrden().getNumero()));
                        hojaTrabajo.addCell(new Number(8,i, alEntrega.get(inventario.getOrden().getNumero()-1)));
                        hojaTrabajo.addCell(new Number(9,i, inventario.getOrden().getTiempoEntrega()-i-1));
                    }
                    System.out.println("-------------------");

                }
                
                inventario.TCostoInventario(inventario.getPromedio());
                inventario.TCostoconEspera(inventario.getSatisfecho());
                inventario.TCostosinEspera(inventario.getInsatisfecho());
                tCosto = inventario.getTCostoconEspera()+inventario.getTCostosinEspera()+inventario.getTcostoInventario()+inventario.getTcostoOrden();
                hojaTrabajo.addCell(new Number(1,diaSimulacion+4, inventario.getTcostoInventario()));
                hojaTrabajo.addCell(new Number(1,diaSimulacion+5, (inventario.getTCostoconEspera()+inventario.getTCostosinEspera())));
                hojaTrabajo.addCell(new Number(1,diaSimulacion+6, inventario.getTcostoOrden()));
                hojaTrabajo.addCell(new Number(1,diaSimulacion+7, tCosto));
                hojaTrabajo.addCell(new Number(1,diaSimulacion+8, MinQ));
                hojaTrabajo.addCell(new Number(1,diaSimulacion+9, MinPR));
                //Cerrar y escribir archivo Excel
                excel.write();
                excel.close();
                
//              
            }else{
                Inventario inventario = new Inventario(objeto.getCostoOrden(),objeto.getCostoInv(),objeto.getCostoEspera(),objeto.getCostoSinEspera(), objeto.getInvInicial());
                //OJO, tienen que evaluarse todas las combinaciones de q y R en este punto
                // El costo de escasez (s), se calcula: la demanda mas peque;a con el s mas grande, y viceversa
                MinQ = inventario.calcularCantidadOrden(tDemanda.get(0).getMinValor(tDemanda), inventario.getCostosinEspera());
                MaxQ =  inventario.calcularCantidadOrden(tDemanda.get(0).getMaxValor(tDemanda), inventario.getCostoconEspera());
                
                System.out.println("Intervalo de Q:"+ MinQ +"-"+MaxQ);

                MinPR = inventario.calcularPuntoReorden(tEntrega.get(0).getMinValor(tEntrega), tDemanda.get(0).getMinValor(tDemanda), MinQ);
                MaxPR = inventario.calcularPuntoReorden(tEntrega.get(0).getMaxValor(tEntrega), tDemanda.get(0).getMaxValor(tDemanda), MaxQ);
                System.out.println("Intervalo de R:"+ MinPR +"-"+MaxPR);
                
                //Para mostrar en Excel
//                File archivo = new File("C:\\Users\\Vicky\\Desktop\\totalCosto.xls"); 
//                WritableWorkbook excel;
//                try {
//                    excel = Workbook.createWorkbook(archivo);
//                    WritableSheet hojaTrabajo = excel.createSheet("TotalCostos", 0);
//                    Label input = new Label(0,0,"Dato 1");
//                    hojaTrabajo.addCell(input);
//                    excel.write();
//                    excel.close();
//                } catch (IOException ex) {
//                    System.out.println("Error al crear el archivo Excel: "+ex);
//                }
//                
//                Combinaciones Q y R
                
                Double minC = 99999.99999;
                for (int i= MinQ; i<=MaxQ; i++){
                    //Asignar q
                    inventario.getOrden().setCantidad(i);
                    for (int j = MinPR; j<=MaxPR; j++){
                        //Asignar R
                        inventario.setPuntoReorden(j);
                        //Simulacion de 365 dias
                        for(int k=1; k<= diaSimulacion;k++){  
                            //LLego orden?
                            inventario.VerificarOrden(k);
                            //Ordenar clientes
                            if(inventario.getColaEspera()!=null && inventario.getColaEspera().size()!=1){
                                Collections.sort(inventario.getColaEspera());
                            }
                            if(inventario.getInicial()>0){
                                inventario.VerificarColaClientes(k);
                            }
                            inventario.ActualizarInventario(k, tDemanda, tEspera,0);
                            inventario.GenerarOrden(k, tEntrega,0);
                        }
                        
                        inventario.TCostoInventario(inventario.getPromedio());
                        inventario.TCostoconEspera(inventario.getSatisfecho());
                        inventario.TCostosinEspera(inventario.getInsatisfecho());
                        tCosto = 0.0;
                        tCosto= inventario.getTCostoconEspera()
                        +inventario.getTCostosinEspera()
                        +inventario.getTcostoInventario()
                        +inventario.getTcostoOrden();
                        listaCostos.add(tCosto);
                        //System.out.println("Costo Total: (Q): "+i+" (R): "+j+"   "+(tCosto));
                        //Limpiar las variables de la clase
                        inventario.limpiarInventario(objeto.getInvInicial());
                        //Conocer el minimo costo de la lista con su Q y R
                        
                        if (tCosto < minC){
                            minC = tCosto;
                            System.out.println("Costo Minimo (Lista): "+minC+" >> Q: "+i+" R: "+j);
                        }
                    }
                } 

                //Para saber el indice del minimo costo de la lista
                double minIndice = listaCostos.indexOf(Collections.min(listaCostos));
                System.out.println("Indice:"+minIndice);
              
                //Dia de simulacion
                System.out.println("Valor de q:"+ inventario.getOrden().getCantidad());
                System.out.println("Punto de Reorden:"+inventario.getPuntoReorden());
                System.out.println("--------------------------");
                for(int i=1; i<= diaSimulacion;i++){
                    System.out.println("Dia Simulacion:"+ i);                
    //             
                    //LLego orden?
                    inventario.VerificarOrden(i);
                    //Ordenar clientes
                    if(inventario.getColaEspera()!=null){
                        Collections.sort(inventario.getColaEspera());
                    }
                    System.out.println("Inv Inicial:"+inventario.getInicial());
                    inventario.ActualizarInventario(i, tDemanda, tEspera,0);
                    inventario.GenerarOrden(i, tEntrega,0);
                    System.out.println("-------------------");
                }
                System.out.println("Costo Inventario:"+ inventario.TCostoInventario(inventario.getPromedio()));
                System.out.println("Costo con espera:"+ inventario.TCostoconEspera(inventario.getSatisfecho()));
                System.out.println("Costo sin espera:"+ inventario.TCostosinEspera(inventario.getInsatisfecho()));
                System.out.println("Costo de Orden:"+ inventario.getTcostoOrden());
                //La suma de todos los costos
                System.out.println("Costo Total:"+(inventario.TCostoInventario(inventario.getPromedio())
                +inventario.TCostoconEspera(inventario.getSatisfecho())
                +inventario.TCostosinEspera(inventario.getInsatisfecho())
                +inventario.getTcostoOrden()));
    //            System.out.println("Valor de la demanda:"+tDemanda.get(0).UbicarEnTabla(tDemanda));
    //            System.out.println("Dia de espera:"+tEspera.get(0).UbicarEnTabla(tEspera));
    //            System.out.println("Dia de entrega:"+tEntrega.get(0).UbicarEnTabla(tEntrega));
            
        }
//            }
            
            
        } catch (JAXBException ex) {
            System.out.println("Error en Lectura");
        }
*/        
    }
    private static WritableCellFormat getCellFormat(jxl.format.Colour colour) throws WriteException {
        WritableFont cellFont;
        WritableCellFormat cellFormat;
        if(colour!=null){
            cellFont = new WritableFont(WritableFont.ARIAL, 10,WritableFont.BOLD);
            cellFormat = new WritableCellFormat(cellFont);
            cellFont.setColour(jxl.format.Colour.WHITE);
            cellFormat.setBackground(colour);
        }else{
            cellFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            cellFormat = new WritableCellFormat(cellFont);
            cellFormat.setBackground(jxl.format.Colour.AQUA);
        }
        
    return cellFormat;
  }
}
