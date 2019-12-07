/*
 * Created by JFormDesigner on Fri Dec 06 20:57:49 CET 2019
 */

package Cliente;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Cristian
 */
public class Cliente extends JFrame {
    private Socket cliente;
    private InetSocketAddress address;
    private OutputStream mensaje;
    private static final String USER_NAME = "Grupo EMRC";

    public Cliente() {
        initComponents();
        rellenarComboBox();
    }

    private void rellenarComboBox(){
        usuarios.addItem("Grupo 1 - 1.0.0.0");
        usuarios.addItem("Grupo 2 - 1.0.0.2");
        usuarios.addItem("Grupo 3 - 1.0.0.3");
        usuarios.addItem("Grupo EMRC - 192.168.0.23");
    }

    private void botonEnviarActionPerformed(ActionEvent e) {
        String texto ="- "+ USER_NAME +": " +campoTexto.getText();
        String encriptado = AES.AES.encrypt(texto,"2DAMA");
        enviar(encriptado,sacarIPseleccionada());
    }

    private String sacarIPseleccionada(){
        String servidor = (String) usuarios.getSelectedItem();
        String[] ip = servidor.split(" - ");

        return ip[1];
    }

    private void enviar(String encriptado,String ip){
        try{
            cliente = new Socket();
            address = new InetSocketAddress(ip,5555);
            cliente.connect(address);
            cambiarEstado();

            mensaje = cliente.getOutputStream();
            PrintWriter mandar = new PrintWriter(mensaje,true);
            mandar.println(encriptado);

            recibirMensajes();

            cliente.close();
        }catch (IOException ex){
            JOptionPane.showMessageDialog(this,"Error al conectarse");
        }
    }

    private void recibirMensajes() throws IOException {
        DataInputStream recibir = new DataInputStream(cliente.getInputStream());
        String mensaje = recibir.readUTF();
        mensajesRecibidos.setText(mensajesRecibidos.getText()+AES.AES.decrypt(mensaje,"2DAMA")+"\n");
    }

    private void cambiarEstado() {
        if (cliente.isConnected()) {
            estado.setForeground(Color.green);
            estado.setText("Estado conectado");
        } else {
            estado.setForeground(Color.red);
            estado.setText("Estado desconectado");
        }
    }

    private void usuariosItemStateChanged(ItemEvent e) {
        // TODO add your code here
    }

    private void usuariosActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Cristian
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        botonEnviar = new JButton();
        campoTexto = new JTextField();
        usuarios = new JComboBox();
        scrollPane1 = new JScrollPane();
        mensajesRecibidos = new JTextArea();
        label1 = new JLabel();
        label2 = new JLabel();
        estado = new JLabel();

        //======== this ========
        setTitle("PGV 4 - Interfaz Cliente");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax
            . swing. border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn" , javax. swing
            .border . TitledBorder. CENTER ,javax . swing. border .TitledBorder . BOTTOM, new java. awt .
            Font ( "Dia\u006cog", java .awt . Font. BOLD ,12 ) ,java . awt. Color .red
            ) ,dialogPane. getBorder () ) ); dialogPane. addPropertyChangeListener( new java. beans .PropertyChangeListener ( ){ @Override
            public void propertyChange (java . beans. PropertyChangeEvent e) { if( "\u0062ord\u0065r" .equals ( e. getPropertyName (
            ) ) )throw new RuntimeException( ) ;} } );
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(null);

                //---- botonEnviar ----
                botonEnviar.setText("Enviar");
                botonEnviar.addActionListener(e -> botonEnviarActionPerformed(e));
                contentPanel.add(botonEnviar);
                botonEnviar.setBounds(new Rectangle(new Point(360, 280), botonEnviar.getPreferredSize()));
                contentPanel.add(campoTexto);
                campoTexto.setBounds(20, 280, 335, 30);

                //---- usuarios ----
                usuarios.addItemListener(e -> usuariosItemStateChanged(e));
                usuarios.addActionListener(e -> usuariosActionPerformed(e));
                contentPanel.add(usuarios);
                usuarios.setBounds(205, 25, 190, usuarios.getPreferredSize().height);

                //======== scrollPane1 ========
                {

                    //---- mensajesRecibidos ----
                    mensajesRecibidos.setEditable(false);
                    scrollPane1.setViewportView(mensajesRecibidos);
                }
                contentPanel.add(scrollPane1);
                scrollPane1.setBounds(20, 95, 415, 170);

                //---- label1 ----
                label1.setText("Usuarios: ");
                contentPanel.add(label1);
                label1.setBounds(130, 25, 70, 25);

                //---- label2 ----
                label2.setText("Mensajes recibidos");
                label2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                contentPanel.add(label2);
                label2.setBounds(150, 65, 165, 20);

                //---- estado ----
                estado.setText("Estado: Desconectado");
                estado.setForeground(new Color(204, 0, 0));
                contentPanel.add(estado);
                estado.setBounds(170, 0, 155, estado.getPreferredSize().height);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < contentPanel.getComponentCount(); i++) {
                        Rectangle bounds = contentPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = contentPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    contentPanel.setMinimumSize(preferredSize);
                    contentPanel.setPreferredSize(preferredSize);
                }
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Cristian
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JButton botonEnviar;
    private JTextField campoTexto;
    private JComboBox usuarios;
    private JScrollPane scrollPane1;
    private JTextArea mensajesRecibidos;
    private JLabel label1;
    private JLabel label2;
    private JLabel estado;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
