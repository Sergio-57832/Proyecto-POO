package Persistencia;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;
import Modelo.Entrega;

public class GestorEntregas {
    
    private static final String rutaDatos = "Datos/entregas.txt";
    
    public void guardarEntregas(Entrega entrega){
    
        try(BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaDatos,true))){
            escritor.write(entrega.getId() + ";"
                    + entrega.getIdPedido() + ";"
                    + entrega.getFecha() + ";"
                    + entrega.getHora()+ ";"
                    + entrega.getValorParcial());
            escritor.newLine();
        }catch(IOException error){
            System.out.println("Error al guardar entrega/s: " + error.getMessage());
        }
    }
    public List<Entrega> cargarEntregas(){
        List<Entrega> listaEntregas = new ArrayList<>();
        try(BufferedReader lector = new BufferedReader(new FileReader(rutaDatos))){
            String renglon;
            while((renglon = lector.readLine()) != null){
                String[] atributosEntrega = renglon.split(";");
                Entrega entrega = new Entrega(atributosEntrega[0],
                                                atributosEntrega[1],
                                                atributosEntrega[2],
                                                atributosEntrega[3],
                                                Double.parseDouble(atributosEntrega[4]));
                listaEntregas.add(entrega);
            }
        }catch(IOException error){
            System.out.println("Error al cargar entregas: " + error.getMessage());
        }
        return listaEntregas;
    }
}
