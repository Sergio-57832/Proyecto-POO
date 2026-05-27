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

public class Controlador {
    
    private GestorProveedores gestorProveedores;
    private GestorEntregas gestorEntregas;
    private GestorPedidos gestorPedidos;

    public Controlador(GestorProveedores gestorProveedores, GestorEntregas gestorEntregas, GestorPedidos gestorPedidos){
        this.gestorProveedores = gestorProveedores;
        this.gestorEntregas = gestorEntregas;
        this.gestorPedidos = gestorPedidos;
    }
    
    //GenerarID
    
    public String generarIdEntrega(){
        List<Entrega> listaEntregas = gestorEntregas.cargarEntregas();
        int numeroInicial = 1;
        while(true){
            String candidato = "E" + String.format("%03d", numeroInicial);
            boolean existe = false;
            for(Entrega entreguita : listaEntregas){
                if(entreguita.getId().equals(candidato)){
                    existe = true;
                    break;
                }
            }
            if(!existe) return candidato;
            numeroInicial++;
        }
    }
    public String generarIdProveedor(){
        List<Proveedor> listaProveedores = gestorProveedores.cargarProveedores();
        int numeroInicial = 1;
        while(true){
            String candidato = "P" + String.format("%03d", numeroInicial);
            boolean existe = false;
            for(Proveedor proveedorcito : listaProveedores){
                if(proveedorcito.getId().equals(candidato)){
                    existe = true;
                    break;
                }
            }
            if(!existe) return candidato;
            numeroInicial++;
        }
    }
    public String generarIdPedido(){
        List<Pedido> listaPedidos = gestorPedidos.cargarPedidos();
        int numeroInicial = 1;
        while(true){
            String candidato = "P" + String.format("%03d", numeroInicial);
            boolean existe = false;
            for(Pedido pedidito : listaPedidos){
                if(pedidito.getId().equals(candidato)){
                    existe = true;
                    break;
                }
            }
            if(!existe) return candidato;
            numeroInicial++;
        }
    }
    
    //Obtener estadisticas
    
    public String obtenerEstadisticasRango(String fechaInicio, String fechaFin, List<String> idsProveedores){
        List<Pedido> listaPedidos = gestorPedidos.cargarPedidos();
        int cantidad = 0;
        double sumatoria = 0;
        int recibidos = 0, incompletos = 0, pendientes = 0;
        Pedido pedidoMayor = null;
        java.util.Map<String, Integer> conteoPorProveedor = new java.util.HashMap<>();

        for(Pedido pedidito : listaPedidos){
            boolean enRango = pedidito.getFecha().compareTo(fechaInicio) >= 0 && 
                              pedidito.getFecha().compareTo(fechaFin) <= 0;
            boolean esProveedor = idsProveedores.isEmpty() || idsProveedores.contains(pedidito.getIdProveedor());

            if(enRango && esProveedor){
                cantidad++;
                sumatoria += pedidito.getValorTotal();
                if(pedidoMayor == null || pedidito.getValorTotal() > pedidoMayor.getValorTotal()){
                    pedidoMayor = pedidito;
                }
                switch(pedidito.getEstado()){
                    case "Recibido": recibidos++; break;
                    case "Incompleto": incompletos++; break;
                    case "Pendiente": pendientes++; break;
                }
                conteoPorProveedor.merge(pedidito.getIdProveedor(), 1, Integer::sum);
            }
        }

        double promedio = cantidad > 0 ? sumatoria / cantidad : 0;
        String proveedorMas = conteoPorProveedor.entrySet().stream()
            .max(java.util.Map.Entry.comparingByValue())
            .map(e -> e.getKey() + " (" + e.getValue() + " pedidos)")
            .orElse("N/A");

        return String.format(
            "=== ESTADÍSTICAS DEL PERÍODO ===\n" +
            "Desde: %s  Hasta: %s\n\n" +
            "Total de pedidos: %d\n" +
            "Sumatoria total: $%.2f\n" +
            "Valor promedio por pedido: $%.2f\n\n" +
            "=== ESTADOS ===\n" +
            "Recibidos: %d\n" +
            "Incompletos: %d\n" +
            "Pendientes: %d\n\n" +
            "=== DESTACADOS ===\n" +
            "Pedido de mayor valor: %s\n" +
            "Proveedor con más pedidos: %s",
            fechaInicio, fechaFin,
            cantidad, sumatoria, promedio,
            recibidos, incompletos, pendientes,
            pedidoMayor != null ? pedidoMayor.getId() + " - $" + pedidoMayor.getValorTotal() : "N/A",
            proveedorMas
        );
    }
    public String obtenerEstadisticasMes(String mes){
        List<Pedido> listaPedidos = gestorPedidos.cargarPedidos();
        int cantidadPedidos = 0;
        double sumatoriaValorPedidos = 0;
        Pedido pedidoMayor = null;
        for(Pedido pedidito : listaPedidos){
            if(pedidito.getFecha().contains(mes)){
                cantidadPedidos++;
                sumatoriaValorPedidos += pedidito.getValorTotal();
                if(pedidoMayor == null || pedidito.getValorTotal()>pedidoMayor.getValorTotal()){
                    pedidoMayor = pedidito;
                }
            }
            
        }
        return ("Cantidad de pedidos: " + cantidadPedidos +
                "\nSumatoria total: " + sumatoriaValorPedidos +
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
                if(pedidito.getFecha().contains(fecha)){
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
    
    public List<Entrega> obtenerEntregasPorPedido(String idPedido){
    List<Entrega> todas = gestorEntregas.cargarEntregas();
    List<Entrega> resultado = new ArrayList<>();
        for(Entrega entreguitas : todas){
            if(entreguitas.getIdPedido().equals(idPedido)){
                resultado.add(entreguitas);
            }
        }
    return resultado;
}
        public String registrarEntrega(String id, String idPedido, String fecha, String hora, double valorParcial){
        List<Pedido> listaPedidos = obtenerListaPedidos();
        for(Pedido pedidito : listaPedidos){
            if(pedidito.getId().equals(idPedido)){
                if(pedidito.getValorAcumulado() + valorParcial > pedidito.getValorTotal()){
                    double faltante = pedidito.getValorTotal() - pedidito.getValorAcumulado();
                    double exceso = (pedidito.getValorAcumulado() + valorParcial) - pedidito.getValorTotal();
                    return String.format(
                        "No se puede registrar la entrega.\n" +
                        "El valor ingresado ($%.2f) excede en $%.2f lo permitido.\n" +
                        "Valor máximo que puedes registrar: $%.2f",
                        valorParcial, exceso, faltante
                    );
                }
            }
        }
        Entrega entrega = new Entrega(id, idPedido, fecha, hora, valorParcial);
        gestorEntregas.guardarEntregas(entrega);
        for(Pedido pedidito : listaPedidos){
            if(pedidito.getId().equals(idPedido)){
                pedidito.setValorAcumulado(pedidito.getValorAcumulado() + valorParcial);
                if(pedidito.getValorAcumulado() >= pedidito.getValorTotal()){
                    pedidito.setEstado("Recibido");
                } else if(pedidito.getValorAcumulado() > 0){
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
    

