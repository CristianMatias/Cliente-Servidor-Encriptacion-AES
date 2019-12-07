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
 * This class is the GUI of an User that wants to send
 * a message to a dedicated server and receive a message from them
 * @author Cristian
 */
public class Client extends JFrame {
    private Socket socketClient;
    private static final String USER_NAME = "Grupo EduManRobCri";
    private static final int DOCK = 5555;

    /**
     * The constructor, calls the methods initComponents() and fillComboBox();
     */
    public Client() {
        initComponents();
        fillComboBox();
    }

    /**
     * This method fills the ComboBox with the name and ip
     * of the different groups
     */
    private void fillComboBox(){
        users.addItem("Grupo EMRC - 192.168.0.23");
        users.addItem("Grupo 1 - 1.0.0.0");
        users.addItem("Grupo 2 - 1.0.0.2");
        users.addItem("Grupo 3 - 1.0.0.3");
    }

    /**
     * Prepares an encrypted message with the user name and the message that is written
     * then it is sent to the server
     * @param e is the ActionEvent
     */
    private void sendActionPerformed(ActionEvent e) {
        String text ="- "+ USER_NAME +": " + textField.getText();
        String encrypt = AES.AES.encrypt(text,"2DAMA");
        send(encrypt, getSelectedIP());
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
     * Send to the server an encrypted message
     * @param encryptMessage is the message that will be sent
     * @param ip is the ip of the server
     */
    private void send(String encryptMessage, String ip){
        try{
            socketClient = new Socket();
            InetSocketAddress address = new InetSocketAddress(ip, DOCK);
            socketClient.connect(address);
            stateChange();

            OutputStream message = socketClient.getOutputStream();
            PrintWriter send = new PrintWriter(message,true);
            send.println(encryptMessage);

            receive();

            socketClient.close();
        }catch (IOException ex){
            JOptionPane.showMessageDialog(this,"Error al conectarse");
        }
    }

    /**
     * Receive a message from the server, decrypt it and finally
     * show it to the user
     * @throws IOException in case that there is no message received
     */
    private void receive() throws IOException {
        DataInputStream get = new DataInputStream(socketClient.getInputStream());
        String message = get.readUTF();
        receivedMessage.setText(receivedMessage.getText()+AES.AES.decrypt(message,"2DAMA")+"\n");
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
     * Allows to send the messages by pressing the enter key
     * @param e is the ActionEvent
     */
    private void textFieldActionPerformed(ActionEvent e) {
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
            dialogPane.setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border .EmptyBorder (
            0, 0 ,0 , 0) ,  "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn" , javax. swing .border . TitledBorder. CENTER ,javax . swing. border .TitledBorder
            . BOTTOM, new java. awt .Font ( "Dia\u006cog", java .awt . Font. BOLD ,12 ) ,java . awt. Color .
            red ) ,dialogPane. getBorder () ) ); dialogPane. addPropertyChangeListener( new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java .
            beans. PropertyChangeEvent e) { if( "\u0062ord\u0065r" .equals ( e. getPropertyName () ) )throw new RuntimeException( ) ;} } );
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(null);

                //---- botonEnviar ----
                sendButton.setText("Enviar");
                sendButton.addActionListener(e -> sendActionPerformed(e));
                contentPanel.add(sendButton);
                sendButton.setBounds(new Rectangle(new Point(360, 280), sendButton.getPreferredSize()));

                //---- campoTexto ----
                textField.addActionListener(e -> textFieldActionPerformed(e));
                contentPanel.add(textField);
                textField.setBounds(20, 280, 335, 30);

                //---- usuarios ----
                contentPanel.add(users);
                users.setBounds(205, 25, 190, users.getPreferredSize().height);

                //======== scrollPane1 ========
                {

                    //---- mensajesRecibidos ----
                    receivedMessage.setEditable(false);
                    scrollPane1.setViewportView(receivedMessage);
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
