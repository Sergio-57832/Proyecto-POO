package Modelo;

public class Factura implements Registrable{

    private String id;//Para identificarla en el archivo de texto
    private String idEntrega;//Para identificar a que entrega pertenece esta factura
    private String rutaArchivo;//ruta en el computador del archivo pdf o jpg 
    private String formato;//dice si el archivo es PDF o JPG

    public Factura(String id, String idEntrega, String rutaArchivo,String formato){
        this.id = id;
        this.idEntrega = idEntrega;
        this.rutaArchivo = rutaArchivo;
        this.formato = formato;
    }
    
    //Getters
    public String getId(){
        return id;
    }
    public String getIdEntrega(){
        return idEntrega;
    }
    public String getRutaArchivo(){
        return rutaArchivo;
    }
    public String getFormato(){
        return formato;
    }
    
    //Setters
    public void setId(String id){
        this.id = id;
    }
    public void setIdEntrega(String idEntrega){
        this.idEntrega = idEntrega;
    }
    public void setRutaArchivo(String rutaArchivo){
        this.rutaArchivo = rutaArchivo;
    }
    public void setFormato(String formato){
        this.formato = formato;
    }
    
    //se usará para imprimir o confirmar que la factura quedó registrada(pero controlador lo guarda)
    public void confirmarRegistro(){}
}
