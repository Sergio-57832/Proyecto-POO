package Modelo;

public class Entrega implements Registrable{

    private String id;//identificador par ael archivo de texto
    private String idPedido;//Para saber a que pedido pertenece la entrega
    private String fecha;
    private String hora;
    private double valorParcial;//valor de la entrega que se hace 

    public Entrega(String id, String idPedido,String fecha, String hora, double valorParcial){
        this.id = id;
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.hora = hora;
        this.valorParcial = valorParcial;
    }
    
    //Getters
    public String getId(){
        return id;
    }
    public String getIdPedido(){
        return idPedido;
    }
    public String getFecha(){
        return fecha;
    }
    public String getHora(){
        return hora;
    }
    public double getValorParcial(){
        return valorParcial;
    }
    
    //Setters
    public void setId(String id){
        this.id = id;
    }
    public void setIdPedido(String idPedido){
        this.idPedido = idPedido;
    }
    public void setFecha(String fecha){
        this.fecha = fecha;
    }
    public void setHora(String hora){
        this.hora = hora;
    }
    public void setValorParcial(double valorParcial){
        this.valorParcial = valorParcial;
    }
    //se usará para imprimir o confirmar que la entrega quedó registrado(pero controlador lo guarda)
    public void confirmarRegistro(){}
}
