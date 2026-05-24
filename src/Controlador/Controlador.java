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
    
}
