package electricity.billing.system;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;


public class MeterInfo extends JFrame implements ActionListener {
    
    JTextField tfname,tfaddress,tfstate,tfcity,tfemail,tfphone;
    JButton next,cancel;
    JLabel lblmeter;
    Choice meterlocation,metertype,phasecode,billtype;
    String meternumber;
    MeterInfo(String meternumber){
        this.meternumber=meternumber;
        setSize(700,600);
        setLocation(400,200);
        
        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBackground(new Color(173,216,230));
        add(p);
        
        JLabel heading = new JLabel("Meter Information");
        heading.setBounds(150,10,200,20);
        heading.setFont(new Font("Tahoma",Font.BOLD,20));
        p.add(heading);
        
        JLabel lblname = new JLabel("Meter Number");
        lblname.setBounds(100,80,100,20);
        p.add(lblname);
        
        JLabel lblmeternumber = new JLabel(meternumber);
        lblmeternumber.setBounds(240,80,100,20);
        p.add(lblmeternumber);
        
        JLabel lblmeterno = new JLabel("Meter Location");
        lblmeterno.setBounds(100,120,100,20);
        p.add(lblmeterno);
        
        meterlocation = new Choice();
        meterlocation.add("Outside");
        meterlocation.add("Inside");
        meterlocation.setBounds(240,120,200,20);
        p.add(meterlocation);
        
        
        JLabel metertype1 = new JLabel("Meter Type");
        metertype1.setBounds(100,160,100,20);
        p.add(metertype1);
        
        metertype = new Choice();
        metertype.add("Solar");
        metertype.add("Electric");
        metertype.add("Smart");
        metertype.setBounds(240,160,200,20);
        p.add(metertype);
        
        JLabel lblcity = new JLabel("Phase Code");
        lblcity.setBounds(100,200,100,20);
        p.add(lblcity);
        
        phasecode = new Choice();
        phasecode.add("011");
        phasecode.add("022");
        phasecode.add("033");
        phasecode.add("044");
        phasecode.add("055");
        phasecode.add("066");
        phasecode.add("077");
        phasecode.add("088");
        phasecode.add("099");
        phasecode.setBounds(240,200,200,20);
        p.add(phasecode);
        
        JLabel lblstate = new JLabel("Bill Type");
        lblstate.setBounds(100,240,100,20);
        p.add(lblstate);
        
        billtype = new Choice();
        billtype.add("Normal");
        billtype.add("Industrial");
        billtype.setBounds(240,240,200,20);
        p.add(billtype);
        
        JLabel lblemail = new JLabel("Days");
        lblemail.setBounds(100,280,100,20);
        p.add(lblemail);
        
        JLabel lblemail1 = new JLabel("30 Days");
        lblemail1.setBounds(240,280,100,20);
        p.add(lblemail1);
        
        
        
        
        JLabel lblphone = new JLabel("NOTE:");
        lblphone.setBounds(100,320,100,20);
        p.add(lblphone);
        
        JLabel lblphone1 = new JLabel("By default bill is calculated for 30 days");
        lblphone1.setBounds(135,320,500,20);
        p.add(lblphone1);
        
        next = new JButton("Submit");
        next.setBounds(200,390,100,25);
        next.setBackground(Color.BLUE);
        next.setForeground(Color.BLACK);
        next.addActionListener(this);
        p.add(next);
        
        
        
        setLayout(new BorderLayout());
        add(p,"Center");
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/icon2.png"));
        Image i2 = i1.getImage().getScaledInstance(200, 250, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        add(image,"West");
        
        getContentPane().setBackground(Color.DARK_GRAY);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == next){
            String meter = meternumber;
            String location = meterlocation.getSelectedItem();
            String type = metertype.getSelectedItem();
            String code = phasecode.getSelectedItem();
            String btype = billtype.getSelectedItem();
            String days = "30";
            
            
            String query1 = "insert into meter_info values('"+meter+"','"+location+"','"+type+"','"+code+"','"+btype+"','"+days+"')";
            //String query2 = "insert into login values('"+meter+"','','"+name+"','','') ";
            
            try{
                Conn c = new Conn();
                c.s.executeUpdate(query1);
                
                
                JOptionPane.showMessageDialog(null,"Meter Information Added Successfuly");
                setVisible(false);
                
               
                
            } catch(Exception e){
                e.printStackTrace();
            }
        } else if(ae.getSource() == cancel){
            setVisible(false);
        }
    }
    public static void main(String args[]){
        new MeterInfo("");
    }
}
