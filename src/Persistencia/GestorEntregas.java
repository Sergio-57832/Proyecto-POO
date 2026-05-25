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
    
    public void guardarTodos(List<Entrega> lista){
        try(BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaDatos))){
            for(Entrega entreguitas : lista){
                escritor.write(entreguitas.getId() + ";" 
                                + entreguitas.getIdPedido() + ";" 
                                + entreguitas.getFecha() + ";" 
                                + entreguitas.getHora() + ";"
                                + entreguitas.getValorParcial());
                escritor.newLine();
            }
        }catch(IOException error){
            System.out.println("Error al guardar todos: " + error.getMessage());
        }
    }
    
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
                if(renglon.trim().isEmpty()) continue;
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
