package Modelo;

public class Proveedor implements Registrable{
    
    private String id;
    private String nombre;
    
    public Proveedor(String id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }
    
    public String getId(){
        return this.id;
    }
    public String getNombre(){
        return this.nombre;
    }
    
    public void setId(String id){
        this.id = id;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    //se usará para imprimir o confirmar que el proveedor quedó registrado(pero controlador lo guarda)
    public void confirmarRegistro(){}
}
