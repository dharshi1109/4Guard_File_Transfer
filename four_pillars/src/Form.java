import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.*;







public class Form extends JFrame implements ActionListener{
    
    JLabel l1,l2,l3;
    JPanel p;
    JFrame frame;
    JTextField t1;
    JTextField t2;
    JButton b,upload,decrypt,openencrypt,openderypt,encrypt;
    JCheckBox showPassword;
    Statement st;
    Connection conn;
    File file_path;
    ResultSet rs; 
   
    //File outputFile=new File("C:/Users/maha9/OneDrive/Desktop/outputFile.txt");
    public Form()
    {
        showPassword=new JCheckBox("Show Password");
        l1=new JLabel("USERNAME");
        l2=new JLabel("PASSWORD");
        t1=new JTextField(15);
        t2=new JTextField(15);
        b=new JButton("LOGIN");
        b.addActionListener(this);
        p=new JPanel(new GridLayout(4, 2));
        p.add(l1);
        p.add(t1);
        p.add(l2);
        p.add(t2);
        p.add(showPassword);
        p.setVisible(true);
        frame=new JFrame();
        frame.add(p, BorderLayout.CENTER);
        frame.add(b, BorderLayout.SOUTH);
        frame.setSize(300,200);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("Login Page");
        frame.setResizable(false);
 
    }
    
    public void actionPerformed(ActionEvent evt)
    {
        try{
            if(evt.getSource()==b){
                PreparedStatement pst;
                String s=t1.getText();
                pst=conn.prepareStatement("select password from Users where name=?");
                pst.setString(1, s);
                rs=pst.executeQuery();
                int flag=0;
                while(rs.next()){
                    String i=rs.getString("password");
                    if(i.equals(t2.getText())){
                        flag=1;
                        JOptionPane.showMessageDialog((Component)evt.getSource(),"Login Successfull","Message",JOptionPane.PLAIN_MESSAGE);
                        JFrame frame1=new JFrame();
                        frame1.setSize(900,900);
                        frame1.setDefaultCloseOperation(EXIT_ON_CLOSE);
                        frame1.setVisible(true);
                        frame1.setTitle("File sharing system");
                        frame1.setResizable(false);
                        
                        encrypt=new JButton("Encrypt");
                        //upload.addActionListener(this);
                        //frame1.setLayout(null);
                        encrypt.setBounds(30,200,100,20);
                        encrypt.setFocusable(false);
                        frame1.add(encrypt);
                       
                        
                        
                        encrypt.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                
                                   }                       
                             });
                        

                    }
                }
                
                if(flag==0){
                    JOptionPane.showMessageDialog((Component)evt.getSource(),"Invalid password","Message",JOptionPane.PLAIN_MESSAGE);
                }
    }
}
catch(Exception e)
{
}
}

public void getcon(){
    try{
        System.out.println("Hello, World!");
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url1 = "jdbc:mysql://localhost:3306/cyber_proj_db?useSSL=false";
        String user = "root";
        String password = "dharshini1109";
        conn = DriverManager.getConnection(url1, user, password);
        System.out.println("Loaded success");
    }
    catch(Exception e){
        System.out.println(e);
    }
 }
 public static void main(String[] args) throws Exception { 
    Form aobj=new Form();
    aobj.getcon();
    

       
    } 
}
    

