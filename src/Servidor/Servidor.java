package Servidor;

import Ficheros.TextFile;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;
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
 *      ARCHIVO - The file that will load the messages
 *      semaforo - A Semaphore to control the entry of messages
 *      fichero - An instance of the TextFile class
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
    private final String ARCHIVO = "src/Servidor/mensajes.txt";
    private final Semaphore semaforo = new Semaphore(1);
    private TextFile fichero = new TextFile();

    /**
     * Constructor, only calls the initComponents
     */
    public Servidor()
    {
        setTitle("PGV 4 - Interfaz Servidor");
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
    public void iniciar() throws IOException, InterruptedException {
        abrirPuerto();
        if(servidor != null)
        {
            while (true) {
                if(semaforo.availablePermits() > 0)
                {
                    semaforo.acquire();
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
                    semaforo.release();
                }
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
        if(fichero.exists(ARCHIVO))
        {
            campoTexto.append(fichero.read(ARCHIVO));
            if(campoTexto.getText().isEmpty())
                campoTexto.append("Bienvenido al terminal de mensajes.");
        }
        else
            campoTexto.append("Bienvenido al terminal de mensajes.");
        estado.setForeground(Color.GREEN);
    }

    /**
     * Saves the messages in the txt, so the chat can be saved,
     * once is saved, it will be loaded every time every time the
     * app is open
     * @param e the event itself
     */
    private void botonGuardarActionPerformed(ActionEvent e) {
        if(fichero.exists(ARCHIVO))
        {
            fichero.write(ARCHIVO,campoTexto.getText());
        }
        else
            JOptionPane.showMessageDialog(this,"Fichero mensajes no creado en paquete Servidor");
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
        botonGuardar = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder
            (0,0,0,0), "JF\u006frm\u0044es\u0069gn\u0065r \u0045va\u006cua\u0074io\u006e",javax.swing.border.TitledBorder.CENTER,javax.swing.border
            .TitledBorder.BOTTOM,new java.awt.Font("D\u0069al\u006fg",java.awt.Font.BOLD,12),java.awt
            .Color.red),dialogPane. getBorder()));dialogPane. addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override public void
            propertyChange(java.beans.PropertyChangeEvent e){if("\u0062or\u0064er".equals(e.getPropertyName()))throw new RuntimeException()
            ;}});
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

                //---- botonGuardar ----
                botonGuardar.setText("Guardar");
                botonGuardar.addActionListener(e -> botonGuardarActionPerformed(e));
                contentPanel.add(botonGuardar);
                botonGuardar.setBounds(new Rectangle(new Point(410, 225), botonGuardar.getPreferredSize()));

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
    private JButton botonGuardar;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
