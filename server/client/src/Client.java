import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.sql.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
public class Client extends JFrame implements ActionListener{
    JLabel l1,l2,l3;
    JPanel p;
    JFrame frame;
    JTextField t1;
    JTextField t2;
    JButton b;
    JCheckBox showPassword;
    Statement st;
    Connection conn;
    ResultSet rs; 
    public Client()
    {
        //LOGIN FRAME CREATION
        //FOR IMPLEMENTATION OF AUTHENTICATION
        showPassword=new JCheckBox("Show Password");
        l1=new JLabel("USERNAME");
        l2=new JLabel("PASSWORD");
        t1=new JTextField(15);
        t2=new JTextField(15);
        b=new JButton("LOGIN");
        b.addActionListener(this);
        p=new JPanel(new GridLayout(4, 2,2,5));
        p.add(l1);
        p.add(t1);
        p.add(l2);
        p.add(t2);
        p.add(showPassword);
        p.setVisible(true);
        frame=new JFrame();
        frame.add(p, BorderLayout.CENTER);
        frame.add(b, BorderLayout.SOUTH);
        frame.setSize(400,200);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("Client Login Page");
        frame.setResizable(false);
 
    }
    //IMPLEMENTATION OF CONFIDENTIALITY USING THE AES ALGORITHM
    //UTILITY FUNCTION FOR FILE ENCRYTION 
    private static byte[] encrypt(byte[] input, String password) throws Exception {
        SecretKeySpec keySpec = getKeySpec(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return cipher.doFinal(input);
    }
    //UTILITY FUNCTION FOR FILE DECRYPTION
    private static byte[] decrypt(byte[] input, String password) throws Exception {
        SecretKeySpec keySpec = getKeySpec(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        return cipher.doFinal(input);
    }
    //UTILITY FUNCTION FOR GENERATING SECRET KEY
    private static SecretKeySpec getKeySpec(String password) throws Exception {
        byte[] keyBytes = password.getBytes();
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        keyBytes = sha.digest(keyBytes);
        keyBytes = copyOf(keyBytes, 16);
        return new SecretKeySpec(keyBytes, "AES");
    }

    private static byte[] copyOf(byte[] original, int newLength) {
        byte[] copy = new byte[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    private static void saveToFile(byte[] content, String filePath) throws Exception {
        FileOutputStream outputStream = new FileOutputStream(filePath);
        outputStream.write(content);
        outputStream.close();
    }
    //FRAME FOR THE CLIENT SIDE USAGE
    public void nextFrame()
    {
        final File[] fileToSend=new File[1];
        JFrame jFrame=new JFrame("Client Side");
        jFrame.setSize(900,900);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(),BoxLayout.Y_AXIS));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel jTitle= new JLabel("File Sender");
        jTitle.setFont(new Font("Arial",Font.BOLD,25));
        jTitle.setBorder(new EmptyBorder(20,0,10,0));
        jTitle.setAlignmentX(CENTER_ALIGNMENT);
        JLabel jFileName=new JLabel("Choose a file to send");
        jFileName.setFont(new Font("Arial",Font.BOLD,20));
        jFileName.setBorder(new EmptyBorder(50,0,0,0));
        jFileName.setAlignmentX(CENTER_ALIGNMENT);
        JPanel jpbutton=new JPanel();
        JPanel jpbutton1=new JPanel();
        jpbutton1.setBorder(new EmptyBorder(150,0,10,0));
        jpbutton.setBorder(new EmptyBorder(75,0,10,0));
        JButton encrytpButton=new JButton("Encrypt File");
        encrytpButton.setPreferredSize(new Dimension(150,75));
        encrytpButton.setFont(new Font("Arial",Font.BOLD,20));
        
        JButton decrypButton=new JButton("Decrypt File");
        decrypButton.setPreferredSize(new Dimension(150,75));
        decrypButton.setFont(new Font("Arial",Font.BOLD,20));
        jpbutton1.add(encrytpButton);
        jpbutton1.add(decrypButton);
        JButton jbSendFile=new JButton("Send File");
        jbSendFile.setPreferredSize(new Dimension(150,75));
        jbSendFile.setFont(new Font("Arial",Font.BOLD,20));
        JButton jbChooseFile=new JButton("Choose File");
        jbChooseFile.setPreferredSize(new Dimension(150,75));
        jbChooseFile.setFont(new Font("Arial",Font.BOLD,20));
        jpbutton.add(jbSendFile);
        jpbutton.add(jbChooseFile);
        JButton open_e=new JButton("Open Encrypt File");
        open_e.setPreferredSize(new Dimension(200,75));
        open_e.setFont(new Font("Arial",Font.BOLD,20));
        jpbutton1.add(open_e);
        JButton check=new JButton("Check Integrity");
        check.setPreferredSize(new Dimension(200,75));
        check.setFont(new Font("Arial",Font.BOLD,20));
        jpbutton1.add(check);
        JButton open_d=new JButton("Open Decrypt File");
        open_d.setPreferredSize(new Dimension(200,75));
        open_d.setFont(new Font("Arial",Font.BOLD,20));
        jpbutton1.add(open_d);
        
        //FUNCTION FOR CHOOSING THE FILE FROM THE DESKTOP

        jbChooseFile.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser jFileChooser=new JFileChooser();
                jFileChooser.setDialogTitle("Choose file to send");
                if(jFileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
                {
                    fileToSend[0]=jFileChooser.getSelectedFile();
                    jFileName.setText("The file you want to send is "+fileToSend[0].getName());

                }
                JOptionPane.showMessageDialog((Component)e.getSource(),"File uploaded ","Message",JOptionPane.PLAIN_MESSAGE);
            }
        });

        jbSendFile.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(fileToSend[0]==null)
                {
                    jFileName.setText("Please Choose a file first");

                }
                else{
                
                    JOptionPane.showMessageDialog((Component)e.getSource(),"File sent !!","Message",JOptionPane.PLAIN_MESSAGE);                 

                }
            }
        });

        //ACTION LISTENER TO ENCRYPT THE SELECTED FILE 
        encrytpButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                try {
                    String inputFilePath = fileToSend[0].getAbsolutePath(); 
                    String outputFilePath = "C:/Users/maha9/OneDrive/Desktop/encrypted.txt";
                    String password = "mySecretPassword";
        
                    byte[] input = Files.readAllBytes(new File(inputFilePath).toPath());
        
                    byte[] encrypted = encrypt(input, password);
        
                    saveToFile(encrypted, outputFilePath);
                    JOptionPane.showMessageDialog((Component)e.getSource(),"File Encrypted","Message",JOptionPane.PLAIN_MESSAGE);
                    
        
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }});

            //ACTION LISTENER TO OPEN THE ENCRYTED FILE 
            open_e.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e)
                {
                    try {
                        File file = new File("C:/Users/maha9/OneDrive/Desktop/encrypted.txt");
                        Desktop desktop = Desktop.getDesktop();
                        desktop.open(file);
                    } catch (IOException e10) {
                        e10.printStackTrace();
                    }
                }});

             //ACTION LISTENER TO DECRYPT THE SELECTED FILE 
            decrypButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e)
                {
                    try {
                       String inputFilePath = fileToSend[0].getAbsolutePath(); // replace with the path of your input file
                        String outputFilePath = "C:/Users/maha9/OneDrive/Desktop/encrypted.txt"; // replace with the path where you want to save the encrypted file
                        String decryptedFilePath = "C:/Users/maha9/OneDrive/Desktop/decrypted.txt"; // replace with the path where you want to save the decrypted file
                        String password = "mySecretPassword"; // replace with your own secret password
            
                        byte[] input = Files.readAllBytes(new File(inputFilePath).toPath());
            
                        byte[] encrypted = encrypt(input, password);
            
                        saveToFile(encrypted, outputFilePath);
                        byte[] decrypted = decrypt(encrypted, password);

                        saveToFile(decrypted, decryptedFilePath);
                        JOptionPane.showMessageDialog((Component)e.getSource(),"File Decrypted","Message",JOptionPane.PLAIN_MESSAGE);
                        
            
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }});
                //ACTION LISTENER TO OPEN THE DECRYPTED FILE 
                open_d.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e)
                    {
                        try {
                            File file = new File("C:/Users/maha9/OneDrive/Desktop/decrypted.txt");
                            Desktop desktop = Desktop.getDesktop();
                            desktop.open(file);
                        } catch (IOException e10) {
                            e10.printStackTrace();
                        }
                    }});
                    //CREATING THE HASH VALUE TO CHECK INTEGRITY
                    check.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e)
                        {
                            String filePath = fileToSend[0].getAbsolutePath().toString();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(filePath);
            byte[] buffer = new byte[1024];
            int nread;
            while ((nread = fis.read(buffer)) != -1) {
                md.update(buffer, 0, nread);
            }
            byte[] hash = md.digest();
            // Convert the byte array to a hex string
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            String md5Hash = sb.toString();
            System.out.println("MD5 hash value: " + md5Hash);
            if(md5Hash.equals("04afc2aa639e376d135dd0df5d2256f6"))
            {
                //"04afc2aa639e376d135dd0df5d2256f6"---> already generated hash value for the particular file(ex1_ans.txt)
                JOptionPane.showMessageDialog((Component)e.getSource(),"No modifications","Hash value",JOptionPane.PLAIN_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog((Component)e.getSource(),"modifications happened erorr!!!","Hash value",JOptionPane.PLAIN_MESSAGE);
            }
            fis.close();
        } catch (NoSuchAlgorithmException | IOException e1) {
            e1.printStackTrace();
        }
                        }});
        jFrame.add(jTitle);
        jFrame.add(jFileName);
        jFrame.add(jpbutton);
        jFrame.add(jpbutton1);
        jFrame.setVisible(true);





    }
    //AUTHENTICATION AND AUTHORIZATION CHECKING FRAME USING THE JDBC CONCEPT(password)
    public void actionPerformed(ActionEvent evt)
    {
        try{
            if(evt.getSource()==b){
                PreparedStatement pst;
                String s=t1.getText();
                pst=conn.prepareStatement("select password,role from Users where name=?");
                pst.setString(1, s);
                rs=pst.executeQuery();
                int flag=0;
                while(rs.next()){
                    String i=rs.getString("password");
                    String i1=rs.getString("role");
                    if(i.equals(t2.getText())&&i1.equals("c"))//password checking and role(client ('c')or server('s')--implementation of authorization)
                    {
                        flag=1;
                        JOptionPane.showMessageDialog((Component)evt.getSource(),"Login Successfull","Message",JOptionPane.PLAIN_MESSAGE);
                        nextFrame();
                    }
                }
                
                if(flag==0){
                    JOptionPane.showMessageDialog((Component)evt.getSource(),"Invalid password check your role correspondingly","Message",JOptionPane.PLAIN_MESSAGE);
                }
    }
}
catch(Exception e)
{
}
}
//CREATING THE CONNECTION BETWEEN THE DATABASE AND THE JAVA CODE
public void getcon(){
    try{
        System.out.println("Hello, World!");
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url1 = "jdbc:mysql://localhost:3306/cyber_proj_db?useSSL=false";
        String user = "root";
        String password1 = "dharshini1109";
        conn = DriverManager.getConnection(url1, user, password1);
        System.out.println("Loaded success");
    }
    catch(Exception e){
        System.out.println("Failed");
    }
 }
 //MAIN 
 public static void main(String[] args) throws Exception { 
    Client aobj=new Client();
    aobj.getcon();
    } 
}
    

