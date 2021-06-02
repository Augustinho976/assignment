package example;


import net.proteanit.sql.DbUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class students {
    private JButton saveButton;
    private JPanel Main;
    private JTextField student_id;
    private JTextField full_name;
    private JTextField course;
    private JTable table1;
    private JButton readButton;
    private JTextField date_of_birth;
    private JScrollPane table_1;
    private DbUtils Dbutils;

    public static void main(String[] args) throws Exception{
        JFrame frame = new JFrame("students");
        frame.setContentPane(new students().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        data ob_connect = new data();
        System.out.println(ob_connect.get_connection());



    }



    public students() {
        table_load();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String myId = student_id.getText();
                String myName = full_name.getText();
                String myCourse = course.getText();
                String myDate = date_of_birth.getText();


                Connection connection = null;
                ResultSet rs = null;
                String host = "localhost";
                String port = "5432";
                String db_name = "postgres";
                String username = "postgres";
                String password = "33744525";
                try {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + db_name + "", "" + username + "", "" + password + "");
                    if (connection != null) {
                        String query = "insert into Reg1 values('" + myId + "', '" + myName + "', '" + myCourse + "', '" + myDate + "')";
                        Statement statement = connection.createStatement();
                        int x = statement.executeUpdate(query);
                        if (x == 0) {
                            JOptionPane.showMessageDialog(saveButton, "Already registered!!");
                        } else {
                            JOptionPane.showMessageDialog(saveButton, "Registered successfully");
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                table_load();
            }
        });


    }
    void table_load(){
        Statement statement =null;
        ResultSet rs = null;
        Connection connection = null;
        String host="localhost";
        String port="5432";
        String db_name="postgres";
        String username="postgres";
        String password="33744525";
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + db_name + "", "" + username + "", "" + password + "");
            String query= "Select * from reg1";
            statement=connection.createStatement();
            rs=statement.executeQuery(query);
            table1.setModel(Dbutils.resultSetToTableModel(rs));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public Connection get_connection() throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        String host = "localhost";
        String port = "5432";
        String db_name = "postgres";
        String username = "postgres";
        String password = "33744525";
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + db_name + "", "" + username + "", "" + password + "");
            String query = "Select * from reg1";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            org.json.simple.JSONObject jsonObject = new org.json.simple.JSONObject();
            org.json.simple.JSONArray array = new JSONArray();
            while (rs.next()) {
                org.json.simple.JSONObject record = new JSONObject();
                //Inserting key-value pairs into the json object
                record.put("student_id", rs.getString("student_id"));
                record.put("full_name", rs.getString("full_name"));
                record.put("course", rs.getString("course"));
                record.put("date_of_birth", rs.getString("date_of_birth"));
                array.add(record);
                jsonObject.put("reg1", array);
                FileWriter file = new FileWriter("C:\\Users\\Augustine\\IdeaProjects\\Tax_calculator\\src\\output.json");
                file.write(jsonObject.toJSONString());
                file.close();


            }




        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return connection;
    }


}

