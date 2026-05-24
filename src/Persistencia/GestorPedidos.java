package Persistencia;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import Modelo.Pedido;

public class GestorPedidos {

    private static final String rutaDatos = "Datos/pedidos.txt";

    public void guardarPedido(Pedido pedido){
        try(BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaDatos, true))){
            escritor.write(pedido.getId() + ";"
                            + pedido.getFecha() + ";"
                            + pedido.getValorTotal() + ";"
                            + pedido.getValorAcumulado() + ";"
                            + pedido.getEstado() + ";"
                            + pedido.getIdProveedor());
            escritor.newLine();
        }catch(IOException error){
            System.out.println("Erro al guardar pedido: " + error.getMessage());
        }
    }
    
    public List<Pedido> cargarPedidos(){
        List<Pedido> listaPedidos = new ArrayList<>();
        try(BufferedReader lector = new BufferedReader(new FileReader(rutaDatos))){
            String renglon;
            while((renglon = lector.readLine()) != null){
            String[] atributosPedidos = renglon.split(";");
            Pedido pedido = new Pedido(atributosPedidos[0], 
                                        atributosPedidos[1], 
                                        Double.parseDouble(atributosPedidos[2]), 
                                        Double.parseDouble(atributosPedidos[3]), 
                                        atributosPedidos[4], 
                                        atributosPedidos[5]);
            listaPedidos.add(pedido);
            }
            
        }catch(IOException error){
            System.out.println("Error al cargar pedidos: " + error.getMessage());
        }
        return listaPedidos;
    }
    
    
    
    
    
}
