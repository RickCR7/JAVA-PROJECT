
package electricity.billing.system;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;

public class PayBill extends JFrame implements ActionListener {
    Choice cmonth;
    String meter;
    JButton pay,back;
    PayBill(String meter){
        this.meter = meter;
        setLayout(null);
        setBounds(300,150,900,600);
        
        JLabel heading = new JLabel("Pay Bill");
        heading.setFont(new Font("Tahoma",Font.BOLD,24));
        heading.setBounds(120,5,400,30);
        add(heading);
        
        JLabel lblmeternumber = new JLabel("Meter Number");
        lblmeternumber.setBounds(35,80,200,20);
        add(lblmeternumber);
        
        JLabel meternumber = new JLabel("");
        meternumber.setBounds(300,80,200,20);
        add(meternumber);
        
        JLabel lblname= new JLabel("Name");
        lblname.setBounds(35,120,200,20);
        add(lblname);
        
        JLabel name = new JLabel("");
        name.setBounds(300,120,200,20);
        add(name);
        
        JLabel lblmonth = new JLabel("Month");
        lblmonth.setBounds(35,160,200,20);
        add(lblmonth);
        
        cmonth = new Choice();
        cmonth.setBounds(250,160,200,20);
        cmonth.add("January");
        cmonth.add("February");
        cmonth.add("March");
        cmonth.add("April");
        cmonth.add("May");
        cmonth.add("June");
        cmonth.add("July");
        cmonth.add("August");
        cmonth.add("September");
        cmonth.add("October");
        cmonth.add("November");
        cmonth.add("December");
        add(cmonth);
        
        JLabel lblunits = new JLabel("Units");
        lblunits.setBounds(35,200,200,20);
        add(lblunits);
        
        JLabel units = new JLabel("");
        units.setBounds(300,200,200,20);
        add(units);
        
        JLabel lbltotalbill = new JLabel("Total Bill");
        lbltotalbill.setBounds(35,240,200,20);
        add(lbltotalbill);
        
        JLabel totalbill = new JLabel("");
        totalbill.setBounds(300,240,200,20);
        add(totalbill);
        
        JLabel lblstatus = new JLabel("Bill Status");
        lblstatus.setBounds(35,280,200,20);
        add(lblstatus);
        
        JLabel labelstatus = new JLabel("");
        labelstatus.setBounds(300,280,200,20);
        labelstatus.setForeground(Color.RED);
        add(labelstatus);
        
        try{
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from customer where meter_no = '"+meter+"'");
            while(rs.next()){
                meternumber.setText(meter);
                name.setText(rs.getString("name"));
            }
            rs = c.s.executeQuery("select * from bill where meter_no = '"+meter+"' and month = '"+cmonth.getSelectedItem()+"'");
            while(rs.next()){
                units.setText(rs.getString("units"));
                totalbill.setText(rs.getString("totalbill"));
                labelstatus.setText(rs.getString("status"));
            }    
        } catch (Exception e){
            e.printStackTrace();
        }
        
        cmonth.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent ie){
                try{
                    Conn c = new Conn();
            
                    ResultSet rs = c.s.executeQuery("select * from bill where meter_no = '"+meter+"' and month = '"+cmonth.getSelectedItem()+"'");
                    while(rs.next()){
                        units.setText(rs.getString("units"));
                        totalbill.setText(rs.getString("totalbill"));
                        labelstatus.setText(rs.getString("status"));
                    }    
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        
        pay = new JButton("Pay");
        pay.setBackground(Color.BLACK);
        pay.setForeground(Color.WHITE);
        pay.setBounds(100,360,100,25);
        pay.addActionListener(this);
        add(pay);
        
        back = new JButton("Back");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(250,360,100,25);
        back.addActionListener(this);
        add(back);
        
        getContentPane().setBackground(Color.WHITE);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/bill.png"));
        Image i2 = i1.getImage().getScaledInstance(600, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(400,120,600,300);
        add(image);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == pay){
            try {
                Conn c = new Conn();
                c.s.executeUpdate("update bill set status = 'Paid' where meter_no = '"+meter+"' AND month = '"+cmonth.getSelectedItem()+"'");
            } catch (Exception e){
                e.printStackTrace();
            }
            setVisible(true);
            new Paytm(meter);
        } else {
            setVisible(false);
        }
    }
    
    public static void main(String args[]){
        new PayBill("");
    }
}
