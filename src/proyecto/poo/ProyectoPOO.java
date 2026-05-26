package proyecto.poo;
import com.formdev.flatlaf.FlatIntelliJLaf;


public class ProyectoPOO {

    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        
        //Libreria que ya viene dentro de Netbeans
        //UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        
        //Otra libreria externa como FlatLaf osea que tambien hay que descargarla
        //JTatto
        
        new Vista.MenuPrincipalVista().setVisible(true);
        
        
    }
    
}
