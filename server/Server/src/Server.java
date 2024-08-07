import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.border.EmptyBorder;
public class Server extends JFrame implements ActionListener{
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
    //LOGIN FRAME CREATION FOR SERVER
    public Server()
    {
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
        frame.setTitle(" Server Login Page");
        frame.setResizable(false);
 
    }
    public void nextFrame()
    {
        JFrame jFrame=new JFrame("Server");
        jFrame.setSize(900,900);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(),BoxLayout.Y_AXIS));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel jTitle= new JLabel("File Receiver");
        jTitle.setFont(new Font("Arial",Font.BOLD,25));
        jTitle.setBorder(new EmptyBorder(20,0,10,0));
        jTitle.setAlignmentX(CENTER_ALIGNMENT);
        jFrame.add(jTitle);        
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
                    if(i.equals(t2.getText())&&i1.equals("s"))//password checking and role(client ('c')or server('s')--implementation of authorization)
                    {
                        flag=1;
                        JOptionPane.showMessageDialog((Component)evt.getSource(),"Login Successfull","Message",JOptionPane.PLAIN_MESSAGE);
                        nextFrame();
                    }
                }
                if(flag==0){
                    JOptionPane.showMessageDialog((Component)evt.getSource(),"Invalid password and check your role correspondingly","Message",JOptionPane.PLAIN_MESSAGE);
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
        String password = "dharshini1109";
        conn = DriverManager.getConnection(url1, user, password);
        System.out.println("Loaded success");
    }
    catch(Exception e){
        System.out.println("Failed");
    }
 }
 //MAIN
 public static void main(String[] args) throws Exception { 
    Server aobj=new Server();
    aobj.getcon();
    } 
}
    

