package Controlador;

import Persistencia.GestorProveedores;
import Persistencia.GestorEntregas;
import Persistencia.GestorPedidos;
import Modelo.Proveedor;
import Modelo.Factura;
import Modelo.Entrega;
import Modelo.Pedido;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Set;

public class Controlador {
    
    private GestorProveedores gestorProveedores;
    private GestorEntregas gestorEntregas;
    private GestorPedidos gestorPedidos;

    public Controlador(GestorProveedores gestorProveedores, GestorEntregas gestorEntregas, GestorPedidos gestorPedidos){
        this.gestorProveedores = gestorProveedores;
        this.gestorEntregas = gestorEntregas;
        this.gestorPedidos = gestorPedidos;
    }
    
    //Obtener estadisticas
    
    public String obtenerEstadisticasMes(String mes){
        List<Pedido> listaPedidos = gestorPedidos.cargarPedidos();
        int cantidad = 0;
        double sumatoria = 0;
        Pedido pedidoMayor = null;
        for(Pedido pedidito : listaPedidos){
            if(pedidito.getFecha().contains(mes)){
                cantidad++;
                sumatoria += pedidito.getValorTotal();
                if(pedidoMayor == null || pedidito.getValorTotal()>pedidoMayor.getValorTotal()){
                    pedidoMayor = pedidito;
                }
            }
            
        }
        return ("Cantidad de pedidos: " + cantidad +
                "\nSumatoria total: " + sumatoria +
                "\nPedido de mayor valor: " + 
                (pedidoMayor != null ? pedidoMayor.getId() + " - $" + pedidoMayor.getValorTotal() : "No hay pedidos"));
    }
    
    //Buscar
    
    public List<Pedido> buscarPorNombreProveedor(String nombreProveedor){
        List<Pedido>listaPedidos = gestorPedidos.cargarPedidos();
        List<Proveedor> listaProveedores = gestorProveedores.cargarProveedores();
        List<Pedido> listaBuscados = new ArrayList<>();
        for(Proveedor proveedorcito : listaProveedores){
            if(proveedorcito.getNombre().equals(nombreProveedor)){
                for(Pedido pedidito : listaPedidos){
                    if(proveedorcito.getId().equals(pedidito.getIdProveedor())){
                        listaBuscados.add(pedidito);
                    }
                }
            }
        }
        return listaBuscados;
    }
    public List<Pedido> buscarPorIdProveedor(String idProveedor){
        List<Pedido> listaPedidos = gestorPedidos.cargarPedidos();
        List<Pedido> listaBuscados = new ArrayList<>();
        for(Pedido pedidito : listaPedidos){
            if(pedidito.getIdProveedor().equals(idProveedor)){
                listaBuscados.add(pedidito);
            }
        }
        return listaBuscados;
    }
    public List<Pedido> buscarPorFecha(String fecha){
        List<Pedido> listaPedidos = gestorPedidos.cargarPedidos();
        List<Pedido> listaBuscados = new ArrayList<>();
        for(Pedido pedidito : listaPedidos){
            if(pedidito.getFecha().equals(fecha)){
                listaBuscados.add(pedidito);
            }
        }
        return listaBuscados;
    }
    
    //Proveedor
    
    public void registrarProveedor(String id, String nombre){
        Proveedor proveedor = new Proveedor(id, nombre);
        gestorProveedores.guardarProveedor(proveedor);
    }
    public void eliminarProveedor(String id){
        List<Proveedor> lista = gestorProveedores.cargarProveedores();
        Iterator<Proveedor> iterador = lista.iterator();
        while(iterador.hasNext()){
            Proveedor proveedor = iterador.next();
            if(proveedor.getId().equals(id)){
                iterador.remove();
            }
        }
        gestorProveedores.guardarTodos(lista);
    }
    public List<Proveedor> obtenerListaProveedores(){
        return gestorProveedores.cargarProveedores();
    }
    
    //Pedido
    
    public void registrarPedido(String id, String fecha, double valorTotal, String idProveedor){
    Pedido pedido = new Pedido(id, fecha,valorTotal, 0.0, "Pendiente", idProveedor);
    gestorPedidos.guardarPedido(pedido);
    }
    public List<Pedido> obtenerListaPedidos(){
        return gestorPedidos.cargarPedidos();
    }
    public void cancelarPedido(String id){
        List<Pedido> listaPedidos = gestorPedidos.cargarPedidos();
        for (Pedido pedidito : listaPedidos){
            if(pedidito.getId().equals(id)){
                pedidito.setEstado("Cancelado");
            }
        }
        gestorPedidos.guardarTodos(listaPedidos);
    }
    
    //Entrega
    
    public String registrarEntrega(String id, String idPedido, String fecha, String hora, double valorParcial){
    List<Pedido> listaPedidos = obtenerListaPedidos();
    //validar que no se supera el valor total
    for(Pedido pedidito : listaPedidos){
        if(pedidito.getId().equals(idPedido)){
            if(pedidito.getValorAcumulado() + valorParcial > pedidito.getValorTotal()){
                return "Error: el valor de la entrega supera el total del pedido";
            }
        }
    }
    Entrega entrega = new Entrega(id, idPedido, fecha, hora, valorParcial);
    gestorEntregas.guardarEntregas(entrega);
        for(Pedido pedidito : listaPedidos){
            if(pedidito.getId().equals(idPedido)){
                pedidito.setValorAcumulado(pedidito.getValorAcumulado()+ valorParcial);
                if(pedidito.getValorAcumulado() >= pedidito.getValorTotal()){
                    pedidito.setEstado("Recibido");
                }
                else if(pedidito.getValorAcumulado()>0){
                    pedidito.setEstado("Incompleto");
                }
            }
        }
        gestorPedidos.guardarTodos(listaPedidos);
        return "Entrega registrada exitosamente";
    }
    public void cancelarEntrega(String id){
        //Se elimina la entrega
        List<Entrega> listaEntregas = gestorEntregas.cargarEntregas();
        double valorParcialCancelado = 0;
        String idPedidoAfectado = "";
        Iterator<Entrega> iterador = listaEntregas.iterator();
        
        while(iterador.hasNext()){
            Entrega entrega = iterador.next();
            if(entrega.getId().equals(id)){
                valorParcialCancelado = entrega.getValorParcial();
                idPedidoAfectado = entrega.getIdPedido();
                iterador.remove();
            }
        }
        gestorEntregas.guardarTodos(listaEntregas);
        //se actualiza el pedido
        List<Pedido>listaPedidos = obtenerListaPedidos();
        for(Pedido pedidito : listaPedidos){
            if(pedidito.getId().equals(idPedidoAfectado)){
                pedidito.setValorAcumulado(pedidito.getValorAcumulado()-valorParcialCancelado);
                if(pedidito.getValorAcumulado()==0){
                    pedidito.setEstado("Pendiente");
                    
                }else{
                    pedidito.setEstado("Incompleto");
                }
            }
        }
        gestorPedidos.guardarTodos(listaPedidos);
    }
    public List<Entrega> obtenerListaEntregas(){
        List<Entrega> listaEntregas = gestorEntregas.cargarEntregas();
        
        return listaEntregas;
        
        
    }



















}
    

