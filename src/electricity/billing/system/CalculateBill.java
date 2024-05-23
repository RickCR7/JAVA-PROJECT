
package electricity.billing.system;

//package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.sql.*;

public class CalculateBill extends JFrame implements ActionListener {
    
    JTextField tfname,tfaddress,tfstate,tfunits;
    JButton next,cancel;
    JLabel lblname,labeladdress;
    Choice meternumber,month;
    CalculateBill(){
        setSize(700,600);
        setLocation(500,150);
        
        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBackground(new Color(173,216,230));
        add(p);
        
        JLabel heading = new JLabel("Calculate Electricity Bill");
        heading.setBounds(150,20,400,20);
        heading.setFont(new Font("Tahoma",Font.BOLD,20));
        p.add(heading);
        
        JLabel lblmeternumber = new JLabel("Meter Number");
        lblmeternumber.setBounds(100,80,100,20);
        p.add(lblmeternumber);
        
        meternumber = new Choice();
        
        try{
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("Select * from customer");
            while(rs.next()){
               meternumber.add(rs.getString("meter_no"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        
        meternumber.setBounds(240,80,200,20);
        p.add(meternumber);
        
        JLabel lblmeterno = new JLabel("Name");
        lblmeterno.setBounds(100,120,100,20);
        p.add(lblmeterno);
        
        lblname = new JLabel("");
        lblname.setBounds(240,120,100,20);
        p.add(lblname);
        
        
        
        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(100,160,100,20);
        p.add(lbladdress);
        
        labeladdress = new JLabel();
        labeladdress.setBounds(240,160,200,20);
        p.add(labeladdress);
        
        try{
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("Select * from customer where meter_no='"+meternumber.getSelectedItem()+"'");
            while(rs.next()){
                lblname.setText(rs.getString("name"));
                labeladdress.setText(rs.getString("address"));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        
        meternumber.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent ie){
                try{
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("Select * from customer where meter_no='"+meternumber.getSelectedItem()+"'");
            while(rs.next()){
                lblname.setText(rs.getString("name"));
                labeladdress.setText(rs.getString("address"));
                }
            } catch(Exception e){
            e.printStackTrace();
            }
        }
    });
        JLabel lblcity = new JLabel("Units Consumed");
        lblcity.setBounds(100,200,100,20);
        p.add(lblcity);
        
        tfunits = new JTextField();
        tfunits.setBounds(240,200,200,20);
        p.add(tfunits);
        
        JLabel lblstate = new JLabel("Month");
        lblstate.setBounds(100,240,100,20);
        p.add(lblstate);
        
        month = new Choice();
        month.setBounds(240,240,200,20);
        month.add("January");
        month.add("February");
        month.add("March");
        month.add("April");
        month.add("May");
        month.add("June");
        month.add("July");
        month.add("August");
        month.add("September");
        month.add("October");
        month.add("November");
        month.add("December");
        p.add(month);
        
        next = new JButton("Submit");
        next.setBounds(150,390,100,25);
        next.setBackground(Color.GREEN);
        next.setForeground(Color.WHITE);
        next.addActionListener(this);
        p.add(next);
        
        cancel = new JButton("Cancel");
        cancel.setBounds(300,390,100,25);
        cancel.setBackground(Color.RED);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        p.add(cancel);
        
        setLayout(new BorderLayout());
        add(p,"Center");
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/hicon2.jpg"));
        Image i2 = i1.getImage().getScaledInstance(150, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        add(image,"West");
        
        getContentPane().setBackground(Color.WHITE);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == next){
            
            String meter = meternumber.getSelectedItem();
            String units = tfunits.getText();
            String mon = month.getSelectedItem();
            
            int totalBill = 0;
            int unit_consumed = Integer.parseInt(units);
            //String query1 = "insert into customer values('"+name+"','"+meter+"','"+address+"','"+city+"','"+state+"','"+email+"','"+phone+"')";
            String query1 = "select * from tax ";
            
            try{
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query1);
                
                while(rs.next()){
                   totalBill += unit_consumed * Integer.parseInt(rs.getString("cost_per_unit"));
                   totalBill += Integer.parseInt(rs.getString("meter_rent"));
                   totalBill += Integer.parseInt(rs.getString("service_charge"));
                   totalBill += Integer.parseInt(rs.getString("service_tax"));
                }
                
                
            } catch(Exception e){
                e.printStackTrace();
            }
            String query2 = "insert into bill values('"+meter+"','"+mon+"','"+units+"','"+totalBill+"','Not Paid')";
            
            try{
                Conn c = new Conn();
                c.s.executeUpdate(query2);
                
                JOptionPane.showMessageDialog(null, "Customer Bill Updated");
                setVisible(false);
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else if(ae.getSource() == cancel){
            setVisible(false);
        }
    }
    public static void main(String args[]){
        new CalculateBill();
    }
}


