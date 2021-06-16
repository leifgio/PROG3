import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.text.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class App extends JFrame {
  public static void main(String[] args) {
    new App();
  }

  public App(){
    final JTable table = new JTable();
    final JLabel lblId, lblFname, lblLname, lblMname, lblBirth, lblGender, lblCourse;
    final JTextField textId, textFname, textLname, textMname, textSearch;
    final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    final JFormattedTextField textBirthdate;
    final JComboBox textGender, textCourse;
    final JButton btnAdd, btnDelete, btnUpdate, btnSearch;
 
    Object[] columns = { "id", "First Name", "Last Name","Middle Name", "birthdate", "gender", "course"};
    final DefaultTableModel model = new DefaultTableModel();
    model.setColumnIdentifiers(columns);
    
    // set the model to the table
    table.setModel(model);

    // Change A JTable Background Color, Font Size, Font Color, Row Height
    table.setBackground(Color.CYAN.brighter());
    table.setForeground(Color.black);
    Font font = new Font("", 1, 18);
    table.setFont(font);
    table.setRowHeight(30);

    JScrollPane pane = new JScrollPane(table);
    pane.setBounds(0, 0, 880, 200);
    this.add(pane);

    show_table(model);
    
    lblId = new JLabel("id:");
    lblId.setBounds(10, 220, 100, 25);
    this.add(lblId);

    lblFname = new JLabel("first name:");
    lblFname.setBounds(10, 250, 100, 25);
    this.add(lblFname);

    lblLname = new JLabel("last name:");
    lblLname.setBounds(10, 280, 100, 25);
    this.add(lblLname);

    lblMname = new JLabel("middle name:");
    lblMname.setBounds(10, 310, 100, 25);
    this.add(lblMname);

    lblBirth = new JLabel("birthday:");
    lblBirth.setBounds(10, 340, 100, 25);
    this.add(lblBirth);

    lblGender = new JLabel("gender:");
    lblGender.setBounds(10, 370, 100, 25);
    this.add(lblGender);

    lblCourse = new JLabel("course:");
    lblCourse.setBounds(10, 400, 100, 25);
    this.add(lblCourse);

    textId = new JTextField();
    textId.setBounds(170, 220, 100, 25);
    textId.setEditable(false);
    this.add(textId);
    
    textFname = new JTextField();
    textFname.setBounds(170, 250, 100, 25);
    this.add(textFname);
    
    textLname = new JTextField();
    textLname.setBounds(170, 280, 100, 25);
    this.add(textLname);
        
    textMname = new JTextField();
    textMname.setBounds(170, 310, 100, 25);
    this.add(textMname);
    
    textBirthdate = new JFormattedTextField(dateFormat);
    textBirthdate.setBounds(170, 340, 100, 25);
    this.add(textBirthdate);

    textGender = new JComboBox();
    textGender.setBounds(170, 370, 100, 25);
    textGender.setEditable(false);
    addGenders(textGender);
    this.add(textGender);

    textCourse = new JComboBox();
    textCourse.setBounds(170, 400, 100, 25);
    textCourse.setEditable(false);
    addCourses(textCourse);
    this.add(textCourse);

    btnAdd = new JButton("Add");
    btnAdd.setBounds(300, 220, 100, 25);
    this.add(btnAdd);
    
    btnDelete = new JButton("Delete");
    btnDelete.setBounds(300, 310, 100, 25);
    this.add(btnDelete);
        
    btnUpdate = new JButton("Update");
    btnUpdate.setBounds(300, 265, 100, 25);
    this.add(btnUpdate);

    textSearch = new JTextField();
    textSearch.setBounds(450, 220, 100, 25);
    this.add(textSearch);

    btnSearch = new JButton("search");
    btnSearch.setBounds(580, 220, 100, 25);
    this.add(btnSearch);

    btnSearch.addActionListener(new ActionListener() {

        // query
        // OR last_name LIKE ? OR middle_name LIKE ? OR birtdate LIKE ? OR gender LIKE ? OR course LIKE ?
        @Override
        public void actionPerformed(ActionEvent e) {
            try{ 
                delete_table(model);
                String id, fName, lName, mName, bday, gend, cour;
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:4433/object_oriented","leif","admin");
                String queryInsert = "SELECT * FROM student WHERE first_name LIKE ?";
                PreparedStatement ps = con.prepareStatement(queryInsert);
                ps.setString(1, "%" + textSearch.getText() + "%");
                ResultSet rs = ps.executeQuery();
                int i =0;
                while(rs.next()){
                  id = rs.getString("id");
                  fName = rs.getString("first_name");
                  lName = rs.getString("last_name");
                  mName = rs.getString("middle_name");
                  bday = rs.getString("birtdate"); 
                  gend = rs.getString("gender"); 
                  cour = rs.getString("course"); 
                  
                  model.addRow(new Object[]{id, fName, lName, mName, bday, gend, cour});
                  i++; 
                }
              } catch(Exception ex){System.out.println(ex);}
        }
    });

    btnAdd.addActionListener(new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {
        try{           
          Connection con = DriverManager.getConnection("jdbc:mysql://localhost:4433/object_oriented","leif","admin");
          Statement statement = con.createStatement();
          String queryInsert = "INSERT INTO student(first_name, last_name, middle_name, birtdate, gender, course) VALUES"  + "(?,?,?,?,?,?)";
          PreparedStatement ps = con.prepareStatement(queryInsert);
          ps.setString(1, textFname.getText());
          ps.setString(2, textLname.getText());
          ps.setString(3, textMname.getText());
          ps.setDate(4, java.sql.Date.valueOf(textBirthdate.getText()));
          ps.setString(5, String.valueOf(textGender.getSelectedItem()));
          ps.setString(6, String.valueOf(textCourse.getSelectedItem()));
          ps.executeUpdate();
          delete_table(model);
          show_table(model);
      }catch(Exception ex){System.out.println(ex);}
    }
    });

    btnDelete.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try{           
          Connection con = DriverManager.getConnection("jdbc:mysql://localhost:4433/object_oriented","leif","admin");
          Statement statement = con.createStatement();
          String queryInsert = "DELETE FROM student WHERE id=?";
          PreparedStatement ps = con.prepareStatement(queryInsert);
          ps.setString(1, textId.getText());
          ps.executeUpdate();
          delete_table(model);
          show_table(model);
        }catch(Exception ex){System.out.println(ex);}
      }
    });

    // get selected row data From table to textfields
    table.addMouseListener(new MouseAdapter() {

    @Override
    public void mouseClicked(MouseEvent e) {

    // i = the index of the selected row
    int i = table.getSelectedRow();

    textId.setText(model.getValueAt(i, 0).toString());
    textFname.setText(model.getValueAt(i, 1).toString());
    textLname.setText(model.getValueAt(i, 2).toString());
    textMname.setText(model.getValueAt(i, 3).toString());
    textBirthdate.setText(model.getValueAt(i, 4).toString());
    textGender.setSelectedItem(model.getValueAt(i, 5).toString());
    textCourse.setSelectedItem(model.getValueAt(i, 6).toString());
    }
    });

    // button update row - Clicked on Update Button
    btnUpdate.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        
        try{           
          Connection con = DriverManager.getConnection("jdbc:mysql://localhost:4433/object_oriented","leif","admin");
          Statement statement = con.createStatement();
          String queryInsert = "UPDATE student SET first_name=?, last_name=?, middle_name=?, birtdate=?, gender=?, course=? WHERE id=?";
          PreparedStatement ps = con.prepareStatement(queryInsert);
          ps.setString(1, textFname.getText());
          ps.setString(2, textLname.getText());
          ps.setString(3, textMname.getText());
          ps.setDate(4, java.sql.Date.valueOf(textBirthdate.getText()));
          ps.setString(5, String.valueOf(textGender.getSelectedItem()));
          ps.setString(6, String.valueOf(textCourse.getSelectedItem()));
          ps.setString(7, textId.getText());
          ps.executeUpdate();
          delete_table(model);
          show_table(model);
        }catch(Exception ex){System.out.println(ex);}
      }
    });

    this.setTitle("Leif Gio Villanueva");
    this.setLayout(null);
    this.setSize(900, 600);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }
  
  public static void addCourses(JComboBox cmb){
    try{           
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:4433/object_oriented","leif","admin");
      Statement statement = con.createStatement();
      ResultSet rs = statement.executeQuery("select * from Courses_Ref");         
      while (rs.next()){
          cmb.addItem(rs.getString("course")); 
      }	
    }catch(Exception ex){System.out.println("error");}	
  }

  public static void addGenders(JComboBox cmb) {
    try{           
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:4433/object_oriented","leif","admin");
      Statement statement = con.createStatement();
      ResultSet rs = statement.executeQuery("select * from Gender_Ref");         
      while (rs.next()){
          cmb.addItem(rs.getString("gender")); 
      }	
    }catch(Exception ex){System.out.println("error");}	
  }

  public static void delete_table(DefaultTableModel m){
    if (m.getRowCount() > 0) {
      for (int i = m.getRowCount() - 1; i > -1; i--) {
          m.removeRow(i);
      }
    }
  }

  public static void show_table(DefaultTableModel m){
    try{ 
      String id, fName, lName, mName, bday, gend, cour;
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:4433/object_oriented","leif","admin");
      String queryInsert = "SELECT * FROM student";
      PreparedStatement ps = con.prepareStatement(queryInsert);
      ResultSet rs = ps.executeQuery();
      int i =0;
      while(rs.next()){
        id = rs.getString("id");
        fName = rs.getString("first_name");
        lName = rs.getString("last_name");
        mName = rs.getString("middle_name");
        bday = rs.getString("birtdate"); 
        gend = rs.getString("gender"); 
        cour = rs.getString("course"); 
        
        m.addRow(new Object[]{id, fName, lName, mName, bday, gend, cour});
        i++; 
      }
    } catch(Exception ex){System.out.println(ex);}
  }

}