package Modelo;

public class Pedido implements Registrable {
    
    private String id;
    private String fecha;
    private double valorTotal;
    private double valorAcumulado;
    private String estado;
    private String idProveedor;

    public Pedido(String id, String fecha, double valorTotal, double valorAcumulado, String estado, String idProveedor){
        this.id = id;
        this.fecha = fecha;
        this.valorTotal = valorTotal;
        this.valorAcumulado = valorAcumulado;
        this.estado = estado;
        this.idProveedor = idProveedor;
    }
    
    //Getters
    public String getId(){
        return id;
    }
    public String getFecha(){
        return fecha;
    }
    public double getValorTotal(){
        return valorTotal;
    }
    public double getValorAcumulado(){
        return valorAcumulado;
    }
    public String getEstado(){
        return estado;
    }
    public String getIdProveedor(){
        return idProveedor;
    }
    
    //Setters
    public void setId(String id){
        this.id = id;
    }
    public void setFecha(String fecha){
        this.fecha = fecha;
    }
    public void setValorTotal(double valorTotal){
        this.valorTotal = valorTotal;
    }
    public void setValorAcumulado(double valorAcumulado){
        this.valorAcumulado = valorAcumulado;
    }
    public void setEstado(String estado){
        this.estado = estado;
    }
    public void setIdProveedor(String idProveedor){
        this.idProveedor = idProveedor;
    }
    
    //se usará para imprimir o confirmar que el pedido quedó registrado(pero controlador lo guarda)
    public void confirmarRegistro(){}
}
