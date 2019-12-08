package Servidor;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.border.*;

/**
 * This class is the GUI that represents a server, that will
 * receive a message, will decrypt it and send a confirmation message
 * GLOBAL VARIABLES:
 *      servidor - is a ServerSocket that will be use when the app starts
 *      socket - is a Socket
 *      in - An DataInputStream to read the messages
 *      out - An DataOutputStream to send the confirmation messages
 *      PUERTO - The dock that will be using
 *      MENSAJE_CONFIRMACION - The confirmation message that the client will receive
 *      CLAVE_ENCRIPTACION - The key of encryption that will be use
 * @author Eduardo
 */
public class Servidor extends JFrame
{
    private ServerSocket servidor = null;
    private Socket socket = null;
    private DataInputStream in;
    private DataOutputStream out;
    private int PUERTO = 5000;
    private final String MENSAJE_CONFIRMACION = "Mensaje recibido: Servidor EMRC";
    private static String CLAVE_ENCRIPTACION = "2DAMA";

    /**
     * Constructor, only calls the initComponents
     */
    public Servidor()
    {
        setTitle("Servidor");
        initComponents();
    }

    /**
     *
     * @return the dock that is being using
     */
    public int getPUERTO() {
        return PUERTO;
    }

    /**
     * Opens the server, receive the message from the client
     * decrypt it and shows in the TextArea, then it send the confirmation to the client
     * finally, close the connection with the client
     * @throws IOException if a connection error happens
     */
    public void iniciar() throws IOException
    {
        abrirPuerto();
        if(servidor != null)
        {
            while (true) {
                socket = servidor.accept();
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                String mensaje = in.readLine();
                imprimirMensaje(AES.AES.decrypt(mensaje,CLAVE_ENCRIPTACION));
                mensaje = AES.AES.encrypt(MENSAJE_CONFIRMACION, CLAVE_ENCRIPTACION);
                out.writeUTF(mensaje);
                socket.close();
                in.close();
                out.close();
            }
        }
    }

    /**
     * Prints the message in the TextArea 'campoTexto'
     * @param mensaje is the message to print
     */
    private void imprimirMensaje(String mensaje) {
        campoTexto.append("\n" + mensaje);
    }

    /**
     * Opens the ServerSocket and change to GUI to show that it has been open
     * @throws IOException
     */
    private void abrirPuerto() throws IOException
    {
        servidor = new ServerSocket(PUERTO);
        estado.setText("Servidor abierto:");
        campoTexto.append("Bienvenido al terminal de mensajes.");
        estado.setForeground(Color.GREEN);
    }

    /**
     * initComponents methods, don't modify
     */
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Cristian
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        scrollPane1 = new JScrollPane();
        campoTexto = new JTextArea();
        label2 = new JLabel();
        estado = new JLabel();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new
            javax.swing.border.EmptyBorder(0,0,0,0), "JFor\u006dDesi\u0067ner \u0045valu\u0061tion",javax
            .swing.border.TitledBorder.CENTER,javax.swing.border.TitledBorder.BOTTOM,new java
            .awt.Font("Dia\u006cog",java.awt.Font.BOLD,12),java.awt
            .Color.red),dialogPane. getBorder()));dialogPane. addPropertyChangeListener(new java.beans.
            PropertyChangeListener(){@Override public void propertyChange(java.beans.PropertyChangeEvent e){if("bord\u0065r".
            equals(e.getPropertyName()))throw new RuntimeException();}});
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(null);

                //======== scrollPane1 ========
                {

                    //---- campoTexto ----
                    campoTexto.setEditable(false);
                    scrollPane1.setViewportView(campoTexto);
                }
                contentPanel.add(scrollPane1);
                scrollPane1.setBounds(20, 45, 415, 170);

                //---- label2 ----
                label2.setText("Mensajes recibidos");
                label2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                contentPanel.add(label2);
                label2.setBounds(145, 20, 165, 20);

                //---- estado ----
                estado.setText("Servidor  desconectado");
                estado.setForeground(new Color(204, 0, 0));
                contentPanel.add(estado);
                estado.setBounds(150, 0, 155, estado.getPreferredSize().height);

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
    private JScrollPane scrollPane1;
    private JTextArea campoTexto;
    private JLabel label2;
    private JLabel estado;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
