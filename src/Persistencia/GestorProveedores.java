package Persistencia;
import java.io.BufferedWriter; //para escribir en los archivos
import java.io.BufferedReader; //para leer los archivos
import java.io.FileWriter; //para abrir el archivo y luego escribir con buffered
import java.io.FileReader; //para abrir el archivo y luego leer con buffered
import java.io.IOException; //obligatorio de java para manejar situaciones de error
import java.util.ArrayList;
import java.util.List;
import Modelo.Proveedor; //para poder usar la clase proveedor en este paquete

public class GestorProveedores {

    private static final String rutaDatos = "Datos/proveedores.txt";

    public void guardarTodos(List<Proveedor> lista){
        try(BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaDatos))){
            for(Proveedor proveedorcito : lista){
                escritor.write(proveedorcito.getId() + ";" + proveedorcito.getNombre());
                escritor.newLine();
            }
        }catch(IOException error){
            System.out.println("Error al guardar todos: " + error.getMessage());
        }
    }
    
    public void guardarProveedor(Proveedor proveedor){
        try(BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaDatos, true))){
            escritor.write(proveedor.getId() + ";" + proveedor.getNombre());
            escritor.newLine();
        } catch(IOException error){
            System.out.println("Error al guardar proveedor: " + error.getMessage()); // error.getMessage() nos dice que paso
        }
    }
    
    public List<Proveedor> cargarProveedores(){
        List<Proveedor> listaProveedores = new ArrayList<>();
        
        try(BufferedReader lector = new BufferedReader(new FileReader(rutaDatos))){
            String renglon;
            while((renglon = lector.readLine()) != null){
                if(renglon.trim().isEmpty()) continue;
                String[] atributosProveedor = renglon.split(";");
                Proveedor proveedor = new Proveedor(atributosProveedor[0], atributosProveedor[1]);
                listaProveedores.add(proveedor);
            }
            
        }catch(IOException error){
            System.out.println("Error al cargar proveedores: " + error.getMessage());
        }
        return listaProveedores;
    }
}
