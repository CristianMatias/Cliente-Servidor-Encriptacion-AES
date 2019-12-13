package Cliente;

import Ficheros.Fichero;
import com.sun.xml.internal.messaging.saaj.util.Base64;
import javafx.stage.FileChooser;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.swing.*;
import javax.swing.border.*;

/**
 * This class is the GUI of an User that wants to send
 * a message to a dedicated server and receive a message from them
 * Global variables:
 *      socketClient - The socket that will be used to send and receive messages
 *      USER_NAME - The name of the group, it will be sent to the server to being easy to the verification of the client
 *      DOCK - The dock that will be using to communicate, default is 5000
 *      ENCRYPT_CODE - The code that will be use to encrypt and decrypt the messages
 *      SEND_MESSAGE - The int that will send if it will send a message
 *      SEND_FILE - the int that will send if it will send a file
 * @author Cristian
 */
public class Client extends JFrame {
    private Socket socketClient;
    private static final String USER_NAME = "Grupo EMRC";
    private static int DOCK = 5000;
    private static String ENCRYPT_CODE = "2DAMA";

    /**
     * The constructor, calls the methods initComponents() and fillComboBox();
     */
    public Client() {
        initComponents();
        fillComboBox();
    }

    /**
     * With this method you can change the dock that will be use
     * @param newDock new Dock to use
     */
    public void setDock(int newDock){
        DOCK = newDock;
    }

    /**
     * Returns the messages from the TextArea receivedMessages
     * @return an String with the messages in the TextArea
     */
    public String getMessages(){
        return receivedMessage.getText();
    }

    /**
     * Puts a String in the TextArea receivedMessages
     * @param text is the String that will be added
     */
    public void setMessages(String text){
        receivedMessage.setText(text);
    }

    /**
     * Change the key of encryption
     * @param key the new key to encrypt
     */
    public void setEncryptCode(String key){
        ENCRYPT_CODE = key;
    }

    /**
     * This method fills the ComboBox with the name and ip
     * of the different groups
     *
     * The different groups are read from an csv file
     */
    private void fillComboBox(){
        try {
            Fichero fichero = new Fichero("src/Cliente/direccionesIP.csv");
            String[] direcciones = fichero.leer();

            for(String usuario : direcciones){
                users.addItem(usuario);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,"Fichero de clientes no encontrado");
        }
    }

    /**
     *  Gets the ip from the ComboBox
     * @return the selected ip
     */
    private String getSelectedIP(){
        String server = (String) users.getSelectedItem();
        String[] ip = server.split(" - ");

        return ip[1];
    }

    /**
     * Send to the server an encrypted message, also it will call
     * a method to receive a message back from the server
     * @param encryptMessage is the message that will be sent
     * @param ip is the ip of the server
     * @throws IOException if it wasn't able to connect
     */
    private void connectToServer(String encryptMessage, String ip){
        try{
            setConnection(ip);
            sendMessage(encryptMessage);
            receiveMessage();
            socketClient.close();
        }catch (IOException ex){
            JOptionPane.showMessageDialog(this,"Error al conectarse");
        }
    }

    /**
     * Starts the connection with a server
     * @param ip of the server that will connect
     * @throws IOException if it wasn't able to connect
     */
    private void setConnection(String ip) throws IOException {
        socketClient = new Socket();
        InetSocketAddress address = new InetSocketAddress(ip, DOCK);
        socketClient.connect(address);
        stateChange();
    }

    /**
     * Sends the message to the server, this message is encrypted
     * @param encryptMessage the message that will be sent to the server
     * @throws IOException if the message couldn't be sent to the server
     */
    private void sendMessage(String encryptMessage) throws IOException {
        OutputStream message = socketClient.getOutputStream();
        PrintWriter send = new PrintWriter(message,true);
        send.println(encryptMessage);
    }

    /**
     * Receive a message from the server, decrypt it and finally
     * show it to the user
     * @throws IOException in case that there is no message received
     */
    private void receiveMessage(){
        try{
            DataInputStream get = new DataInputStream(socketClient.getInputStream());
            String message = get.readUTF();
            setMessages(getMessages()+AES.AES.decrypt(message,ENCRYPT_CODE)+"\n");
        }catch (IOException ex){
            JOptionPane.showMessageDialog(this,"Confirmación/Validacion no recibida");
        }
    }

    /**
     * Shows to the user if the server is available or not
     */
    private void stateChange() {
        if (socketClient.isConnected()) {
            status.setForeground(Color.green);
            status.setText("Estado: conectado");
        } else {
            status.setForeground(Color.red);
            status.setText("Estado: desconectado");
        }
    }

    /**
     * Prepares an encrypted message with the user name and the message that is written
     * then it is sent to the server
     * @param e is the ActionEvent
     */
    private void sendActionPerformed(ActionEvent e) {
        String text ="- "+ USER_NAME +": " + textField.getText();
        String encrypt = AES.AES.encrypt(text,ENCRYPT_CODE);
        textField.setText("");
        connectToServer(encrypt, getSelectedIP());
    }

    /**
     * Allows to send the messages by pressing the enter key
     * @param e is the ActionEvent
     */
    private void campoTextoActionPerformed(ActionEvent e) {
        sendActionPerformed(e);
    }

    /**
     * initComponents methods, don't modify
     */
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Cristian
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        sendButton = new JButton();
        textField = new JTextField();
        users = new JComboBox();
        scrollPane1 = new JScrollPane();
        receivedMessage = new JTextArea();
        label1 = new JLabel();
        label2 = new JLabel();
        status = new JLabel();

        //======== this ========
        setTitle("PGV 4 - Interfaz Cliente");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new
            javax.swing.border.EmptyBorder(0,0,0,0), "JF\u006frmDesi\u0067ner Ev\u0061luatio\u006e",javax
            .swing.border.TitledBorder.CENTER,javax.swing.border.TitledBorder.BOTTOM,new java
            .awt.Font("Dialo\u0067",java.awt.Font.BOLD,12),java.awt
            .Color.red),dialogPane. getBorder()));dialogPane. addPropertyChangeListener(new java.beans.
            PropertyChangeListener(){@Override public void propertyChange(java.beans.PropertyChangeEvent e){if("borde\u0072".
            equals(e.getPropertyName()))throw new RuntimeException();}});
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(null);

                //---- sendButton ----
                sendButton.setText("Enviar");
                sendButton.addActionListener(e -> sendActionPerformed(e));
                contentPanel.add(sendButton);
                sendButton.setBounds(new Rectangle(new Point(390, 300), sendButton.getPreferredSize()));

                //---- textField ----
                textField.addActionListener(e -> campoTextoActionPerformed(e));
                contentPanel.add(textField);
                textField.setBounds(40, 300, 335, 30);
                contentPanel.add(users);
                users.setBounds(200, 40, 190, users.getPreferredSize().height);

                //======== scrollPane1 ========
                {

                    //---- receivedMessage ----
                    receivedMessage.setEditable(false);
                    scrollPane1.setViewportView(receivedMessage);
                }
                contentPanel.add(scrollPane1);
                scrollPane1.setBounds(35, 115, 415, 170);

                //---- label1 ----
                label1.setText("Usuarios: ");
                contentPanel.add(label1);
                label1.setBounds(125, 45, 70, 25);

                //---- label2 ----
                label2.setText("Mensajes recibidos");
                label2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                contentPanel.add(label2);
                label2.setBounds(145, 85, 165, 20);

                //---- status ----
                status.setText("Estado: Desconectado");
                status.setForeground(new Color(204, 0, 0));
                contentPanel.add(status);
                status.setBounds(170, 0, 155, status.getPreferredSize().height);

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
    private JButton sendButton;
    private JTextField textField;
    private JComboBox users;
    private JScrollPane scrollPane1;
    private JTextArea receivedMessage;
    private JLabel label1;
    private JLabel label2;
    private JLabel status;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
