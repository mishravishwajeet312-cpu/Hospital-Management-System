package HospitalManagementSystem;


import javax.swing.text.html.HTMLDocument;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor{


private Connection cn;


public Doctor(Connection cn){
    this.cn = cn;

}




public void viewDoctors(){
    String query = "Select * from doctors";
    try {
        PreparedStatement ps = cn.prepareStatement(query);
        ResultSet rl = ps.executeQuery();
        System.out.println("Doctor: ");
        System.out.println("+-------------+---------------------+--------------------+");
        System.out.println("| Doctor Id  | Name                 | Specialization     | ");
        System.out.println("+-------------+---------------------+--------------------+");
        while (rl.next()){
            int id =rl.getInt("id");
            String name =rl.getString("name");
            String specialization =rl.getString("specialization");

            System.out.printf("| %-10s | %-20s | %-18s |\n", id,name,specialization);
            System.out.println("+-------------+---------------------+--------------------+");
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("\nPress Enter to Continue");
        sc.nextLine();




    }catch (SQLException e){
        e.printStackTrace();
    }
}

public boolean getDoctorById(int id){
    String query = "SELECT * FROM doctors WHERE id = ?";
    try {
        PreparedStatement ps = cn.prepareStatement(query);
        ps.setInt(1,id);
        ResultSet rl =ps.executeQuery();
        if (rl.next()){
            return true;
        }else {
            return false;
        }

    }catch (SQLException e){
        e.printStackTrace();
    }
    return false;
}
}
