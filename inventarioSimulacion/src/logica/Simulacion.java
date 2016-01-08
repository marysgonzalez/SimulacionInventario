/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.awt.Desktop;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;


public class Simulacion{
    static int diaSimulacion = 0;
    public static List<Double> alDemanda =  new ArrayList<Double>();
    public static List<Double> alEspera =  new ArrayList<Double>();
    public static List<Double> alEntrega =  new ArrayList<Double>();
    public static Random numeroAleatorio =  new Random();
    
    public boolean iniciarSim (Archivo objeto, int diaSim, int MinQ, int MinPR) throws IOException, WriteException{
        List<Tabla> tDemanda = null, tEntrega = null, tEspera = null;
        tDemanda = new ArrayList <Tabla>();
        tEntrega = new ArrayList <Tabla>();
        tEspera = new ArrayList <Tabla>();
        int datos[] = new int[2];
        int MaxQ = 0, qMin = 0;
        int MaxPR = 0, rMin = 0;
        Double tCosto = 0.0;
        String filePath = new File("").getAbsolutePath();
        String ruta = "";
        File archivo;
        
           
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
            
            ruta = filePath + File.separator + "src" + File.separator + "archivoExcel" + File.separator + "tablaEventos.xls";
            archivo = new File (ruta);
            WritableWorkbook excel = Workbook.createWorkbook(archivo); 
            WritableSheet hojaTrabajo = excel.createSheet("TotalCostos", 0);
            WritableSheet hojaTrabajo2 = excel.createSheet("Combinaciones_QR", 1);

            if(alDemanda!=null && alEntrega!=null && alEspera!=null){
                
                if(alDemanda.size() < diaSim || alEspera.size() < diaSim || alEntrega.size()< diaSim){
                    return false;
                }else{

                    diaSimulacion = diaSim;
                    Inventario inventario = new Inventario(objeto.getCostoOrden(),objeto.getCostoInv(),objeto.getCostoEspera(),objeto.getCostoSinEspera(), objeto.getInvInicial());
                    inventario.getOrden().setCantidad(MinQ);
                    inventario.setPuntoReorden(MinPR);

                    //Cabecera del archivo Excel
                    titulosArchivo(hojaTrabajo,null);
                    datos[1]=0;
                    for(int i=1; i<= diaSimulacion;i++){ 
                        //LLego orden?
                        inventario.VerificarOrden(i);
                        //Atender clientes en espera
                        inventario.VerificarColaClientes(i);

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
                        double aux = alEspera.get(0);
                        datos = inventario.ActualizarInventario(i, tDemanda, tEspera, 1);

                        if(datos[1]!=0.0 && i==1){
                            hojaTrabajo.addCell(new Number(10,i, aux));
                        }
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
          
                    }
                    inventario.TCostoInventario(inventario.getPromedio());
                    inventario.TCostoconEspera(inventario.getSatisfecho());
                    inventario.TCostosinEspera(inventario.getInsatisfecho());
                    tCosto = inventario.getTCostoconEspera()+inventario.getTCostosinEspera()+inventario.getTcostoInventario()+inventario.getTcostoOrden();

                    mostrarCostos(hojaTrabajo, inventario, tCosto, MinQ, MinPR);
                    //Cerrar y escribir archivo Excel
                    excel.write();
                    excel.close();
                }
                
            }else{
                diaSimulacion = diaSim;
                titulosArchivo(hojaTrabajo, hojaTrabajo2);
                Inventario inventario = new Inventario(objeto.getCostoOrden(),objeto.getCostoInv(),objeto.getCostoEspera(),objeto.getCostoSinEspera(), objeto.getInvInicial());
                alDemanda = new ArrayList<Double>();
                alEspera = new ArrayList<Double>();
                alEntrega = new ArrayList<Double>();

                // El costo de escasez (s), se calcula: la demanda mas peque;a con el s mas grande, y viceversa
                MinQ = inventario.calcularCantidadOrden(tDemanda.get(0).getMinValor(tDemanda), inventario.getCostosinEspera());
                MaxQ =  inventario.calcularCantidadOrden(tDemanda.get(0).getMaxValor(tDemanda), inventario.getCostoconEspera());

                MinPR = inventario.calcularPuntoReorden(tEntrega.get(0).getMinValor(tEntrega), tDemanda.get(0).getMinValor(tDemanda), MinQ);
                MaxPR = inventario.calcularPuntoReorden(tEntrega.get(0).getMaxValor(tEntrega), tDemanda.get(0).getMaxValor(tDemanda), MaxQ);
              
//                Combinaciones Q y R
                Double minC = 9999999.99999;
                int p = 1;
                for (int i= MinQ; i<=MaxQ; i++){
                    //Asignar q
                    inventario.getOrden().setCantidad(i);
                    for (int j = MinPR; j<=MaxPR; j++){
                        //Asignar R
                        inventario.setPuntoReorden(j);
                        numeroAleatorio.setSeed(100);
                        //Simulacion de 365 dias
                        for(int k=1; k<= diaSimulacion;k++){ 
                            //LLego orden?
                            inventario.VerificarOrden(k);
                            //Ordenar clientes
                            if(inventario.getColaEspera()!=null && inventario.getColaEspera().size()!=1){
                                Collections.sort(inventario.getColaEspera());
                            }
                            
                            inventario.VerificarColaClientes(k);

                            inventario.ActualizarInventario(k, tDemanda, tEspera,0);
                            inventario.GenerarOrden(k, tEntrega,0);
                        }
                        tCosto = 0.0;
                        inventario.TCostoInventario(inventario.getPromedio());
                        inventario.TCostoconEspera(inventario.getSatisfecho());
                        inventario.TCostosinEspera(inventario.getInsatisfecho());
                        tCosto = inventario.getTCostoconEspera()+inventario.getTCostosinEspera()+inventario.getTcostoInventario()+inventario.getTcostoOrden();

                        hojaTrabajo2.addCell(new Number(0,p, i));
                        hojaTrabajo2.addCell(new Number(1,p, j));
                        hojaTrabajo2.addCell(new Number(2,p, inventario.getTcostoInventario()));
                        hojaTrabajo2.addCell(new Number(3,p, inventario.getTCostosinEspera()+inventario.getTCostoconEspera()));
                        hojaTrabajo2.addCell(new Number(4,p, inventario.getTcostoOrden()));
                        hojaTrabajo2.addCell(new Number(5,p, tCosto));

                        p++;
                        //Limpiar las variables de la clase
                        inventario.limpiarInventario(objeto.getInvInicial());
                        //Conocer el minimo costo de la lista con su Q y R
                        if (tCosto < minC){
                            minC = tCosto;
                            qMin = i;
                            rMin = j;

                        }
                        
                    }
                } 
                //Construcción de la tabla de Eventos para el costo minimo
                alDemanda.clear();
                alEntrega.clear();
                alEspera.clear();
                inventario.getOrden().setCantidad(qMin);
                inventario.setPuntoReorden(rMin);
                numeroAleatorio.setSeed(100);
                for(int k=1; k<= diaSimulacion;k++){              
                    //LLego orden?
                    inventario.VerificarOrden(k);
                    //Ordenar clientes
                    if(inventario.getColaEspera()!=null && inventario.getColaEspera().size()!=1){
                        Collections.sort(inventario.getColaEspera());
                    }
                    inventario.VerificarColaClientes(k);
                    
                    //Escritura en Excel
                    hojaTrabajo.addCell(new jxl.write.Number(0, k, k));
                    hojaTrabajo.addCell(new jxl.write.Number(1,k, inventario.getInicial()));

                    datos = inventario.ActualizarInventario(k,tDemanda, tEspera, 0);
                    if(!alEspera.isEmpty()){
                        double aux = alEspera.get(0);
                        if(datos[1]!=0.0 && k==1){
                            hojaTrabajo.addCell(new Number(10,k, aux));
                        }
                    }
                    hojaTrabajo.addCell(new jxl.write.Number(2,k, alDemanda.get(k-1)));

                    hojaTrabajo.addCell(new jxl.write.Number(3,k, datos[0]));
                    hojaTrabajo.addCell(new jxl.write.Number(4,k, inventario.getInicial()));
                    hojaTrabajo.addCell(new jxl.write.Number(5,k, ((Integer.parseInt(hojaTrabajo.getCell(1, k).getContents())+inventario.getInicial())/2)));
                    //Demanda es mayor que el inv. inicial
                    if(datos[0] > Integer.parseInt(hojaTrabajo.getCell(1, k).getContents())){
                         for(int l=0; l<alEspera.size();l++){
                            if(alEspera.get(l)!=0.0){
                                hojaTrabajo.addCell(new jxl.write.Number(10,k, alEspera.get(l)));
                                alEspera.set(l, 0.0);
                                break;
                            }
                        }
                        hojaTrabajo.addCell(new jxl.write.Number(6,k, datos[0] - Integer.parseInt(hojaTrabajo.getCell(1, k).getContents())));
                        hojaTrabajo.addCell(new jxl.write.Number(11,k, datos[1]));
                    }
                    
                    if(inventario.GenerarOrden(k, tEntrega, 0)){
                        hojaTrabajo.addCell(new jxl.write.Number(7,k, inventario.getOrden().getNumero()));
                        hojaTrabajo.addCell(new jxl.write.Number(8,k, alEntrega.get(inventario.getOrden().getNumero()-1)));
                        hojaTrabajo.addCell(new jxl.write.Number(9,k, inventario.getOrden().getTiempoEntrega()-k-1));
                    }

                }
                inventario.TCostoInventario(inventario.getPromedio());
                inventario.TCostoconEspera(inventario.getSatisfecho());
                inventario.TCostosinEspera(inventario.getInsatisfecho());
                tCosto = inventario.getTCostoconEspera()+inventario.getTCostosinEspera()+inventario.getTcostoInventario()+inventario.getTcostoOrden();
               
                mostrarCostos(hojaTrabajo, inventario, tCosto, qMin, rMin);
                
                excel.write();
                excel.close();
            }
            
            if(archivo.exists() && !archivo.isDirectory()) { 
                Desktop dt = Desktop.getDesktop();
                dt.open(new File(ruta));
            }
        return true;    
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
            cellFormat.setBackground(jxl.format.Colour.CORAL);
        }
        
    return cellFormat;
    }
    
    private void titulosArchivo(WritableSheet hojaTrabajo,WritableSheet hojaTrabajo2) throws WriteException{
        Label[] titulos = new Label[18];
        Label[] titulos2 = new Label[6];
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
        titulos[12] = new Label(0,diaSimulacion+4,"Costo de Inventario", getCellFormat(null));
        titulos[13] = new Label(0,diaSimulacion+5,"Costo Faltante", getCellFormat(null));
        titulos[14] = new Label(0,diaSimulacion+6,"Costo de Orden", getCellFormat(null));
        titulos[15] = new Label(0,diaSimulacion+7,"Costo Total", getCellFormat(null)); 
        titulos[16] = new Label(0,diaSimulacion+8,"Q", getCellFormat(null));
        titulos[17] = new Label(0,diaSimulacion+9,"PR", getCellFormat(null));

        for (int in=0;in<titulos.length; in++){
            hojaTrabajo.addCell(titulos[in]);
        }
        if(hojaTrabajo2 != null){
            titulos2[0] = new Label(0,0,"Q",getCellFormat(jxl.format.Colour.GREEN));
            titulos2[1] = new Label(1,0,"R",getCellFormat(jxl.format.Colour.GREEN));
            titulos2[2] = new Label(2,0,"Costo de Inventario",getCellFormat(jxl.format.Colour.GREEN));
            titulos2[3] = new Label(3,0,"Costo Faltante",getCellFormat(jxl.format.Colour.GREEN));
            titulos2[4] = new Label(4,0,"Costo de Orden",getCellFormat(jxl.format.Colour.GREEN));
            titulos2[5] = new Label(5,0,"Costo Total",getCellFormat(jxl.format.Colour.GREEN));
            for (int in=0;in<titulos2.length; in++){
                hojaTrabajo2.addCell(titulos2[in]);
            }
        }
        
    }
    /**
     * 
     * @param hojaTrabajo hoja de excel
     * @param inventario 
     * @param tCosto costo total
     * @param MinQ  minimo de q
     * @param MinPR minimo de pr
     * @throws WriteException 
     */
    private void mostrarCostos(WritableSheet hojaTrabajo, Inventario inventario, double tCosto, int MinQ, int MinPR) throws WriteException{
        hojaTrabajo.addCell(new Number(1,diaSimulacion+4, inventario.getTcostoInventario()));
        hojaTrabajo.addCell(new Number(1,diaSimulacion+5, (inventario.getTCostoconEspera()+inventario.getTCostosinEspera())));
        hojaTrabajo.addCell(new Number(1,diaSimulacion+6, inventario.getTcostoOrden()));
        hojaTrabajo.addCell(new Number(1,diaSimulacion+7, tCosto));
        hojaTrabajo.addCell(new Number(1,diaSimulacion+8, MinQ));
        hojaTrabajo.addCell(new Number(1,diaSimulacion+9, MinPR));
    }
    
}
