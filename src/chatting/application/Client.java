package chatting.application;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.Calendar;
import java.text.*;
import java.net.*;
import java.io.*;

public class Client implements ActionListener{
    static Box vertical=Box.createVerticalBox();
    static JPanel textPanel;
    JTextField text;
    static DataOutputStream dout;
    static JFrame frame=new JFrame();

    Client(){
        frame.setLayout(null);

        JPanel topPanel=new JPanel();
        topPanel.setBounds(0,0,450,70);
        topPanel.setBackground(new Color(7,94,84));
        topPanel.setLayout(null);
        frame.add(topPanel);

        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2=i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel back=new JLabel(i3);
        back.setBounds(5,20,25,25);
        topPanel.add(back);

        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });

        ImageIcon i21=new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image i22=i21.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i23=new ImageIcon(i22);
        JLabel dp=new JLabel(i23);
        dp.setBounds(40,10,50,50);
        topPanel.add(dp);
        
        JLabel name=new JLabel("Sukanya");
        name.setBounds(110,15,100,18);
        name.setFont(new Font("SAN.SERIF",Font.BOLD,18));
        name.setForeground(Color.white);
        topPanel.add(name);

        JLabel online=new JLabel("Online");
        online.setBounds(110,35,100,18);
        online.setFont(new Font("SAN.SERIF",Font.PLAIN,12));
        online.setForeground(Color.white);
        topPanel.add(online);

        ImageIcon i31=new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i32=i31.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i33=new ImageIcon(i32);
        JLabel video=new JLabel(i33);
        video.setBounds(300,20,30,30);
        topPanel.add(video);

        ImageIcon i41=new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i42=i41.getImage().getScaledInstance(35,30,Image.SCALE_DEFAULT);
        ImageIcon i43=new ImageIcon(i42);
        JLabel phone=new JLabel(i43);
        phone.setBounds(360,20,35,30);
        topPanel.add(phone);

        ImageIcon i51=new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i52=i51.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon i53=new ImageIcon(i52);
        JLabel _3dots=new JLabel(i53);
        _3dots.setBounds(420,20,10,25);
        topPanel.add(_3dots);

        textPanel=new JPanel();
        textPanel.setBounds(5,75,440,570);
        frame.add(textPanel);
        
        text=new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SEROF",Font.BOLD,16));
        frame.add(text);
        
        JButton send=new JButton("Send");
        send.setBackground(new Color(7,94,84));
        send.setBounds(320,655,123,40);
        send.setForeground(Color.white);
        send.setFont(new Font("SAN_SEROF",Font.BOLD,16));
        send.addActionListener(this);
        frame.add(send);

        frame.getContentPane().setBackground(Color.white);
        frame.setSize(450,700);
        frame.setUndecorated(true);
        frame.setLocation(800,20);

        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        try{
            String msg=text.getText();

            JPanel p2=formatLabel(msg);

            textPanel.setLayout(new BorderLayout());

            JPanel right=new JPanel(new BorderLayout());
            right.add(p2,BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            textPanel.add(vertical,BorderLayout.PAGE_START);

            dout.writeUTF(msg);

            text.setText("");
            frame.repaint();
            frame.invalidate();
            frame.validate();
        }catch(Exception e1){
            e1.printStackTrace();
        }
    }
    public static JPanel formatLabel(String msg){
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel output=new JLabel("<html><p style=\"width:150px\">"+msg+"</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));

        panel.add(output);

        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:MM");
        JLabel time=new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);

        return panel;
    }
    public static void main(String args[])
    {
        new Client();

        try{
            Socket s=new Socket("127.0.0.1",6001);
            DataInputStream din=new DataInputStream(s.getInputStream());
            dout=new DataOutputStream(s.getOutputStream());

            while(true){
                textPanel.setLayout(new BorderLayout());
                String msg=din.readUTF();
                JPanel panel=formatLabel(msg);

                JPanel left=new JPanel(new BorderLayout());
                left.add(panel,BorderLayout.LINE_START);
                vertical.add(left);

                vertical.add(Box.createVerticalStrut(15));

                frame.validate();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
 
}
