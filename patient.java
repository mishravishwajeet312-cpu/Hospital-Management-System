package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class patient {
  private Connection cn;
  private Scanner sc;

  public patient(Connection cn,Scanner sc){
      this.cn = cn;
      this.sc = sc;
  }

  public void addPatient(){
      System.out.print("Enter Patient Name: ");
      String name = sc.next();
      sc.nextLine();
      System.out.print("Enter Patient Age: ");
      int age =sc.nextInt();
      sc.nextLine();
      System.out.print("Enter Patient Gender: ");
      String gender = sc.next();
      sc.nextLine();


      try {
         String query = "INSERT INTO patients(name, age, gender) VALUES(?,?,?)";
          PreparedStatement ps = cn.prepareStatement(query);
          ps.setString(1,name);
          ps.setInt(2,age);
          ps.setString(3,gender);
          int affectedRows = ps.executeUpdate();
          if (affectedRows>0){
              System.out.println("Patients added Successfully");
          }else {
              System.out.println("Failed to Add patients");
          }





      }catch (SQLException e){
          e.printStackTrace();
      }
  }


  public void viewPatients(){
      String query = "Select * from patients";
      try {
          PreparedStatement ps = cn.prepareStatement(query);
          ResultSet rl = ps.executeQuery();
          System.out.println("Patients: ");
          System.out.println("+-------------+---------------------+-----------+------------------+");
          System.out.println("| Patient Id  | Name                 | Age       | Gender        | ");
          System.out.println("+-------------+---------------------+-----------+------------------+");
          while (rl.next()){
              int id =rl.getInt("id");
              String name =rl.getString("name");
              int age =rl.getInt("age");
              String gender = rl.getString("gender");
              System.out.printf("|%-13d|%-22s|%-11d|%-15s|\n",id, name,age,gender);
              System.out.println("+-------------+---------------------+-----------+------------------+");
          }
          Scanner sc= new Scanner(System.in);
          System.out.println("\nPress Enter to Continue");
          sc.nextLine();




      }catch (SQLException e){
          e.printStackTrace();
      }
  }

  public boolean getPatientById(int id){
      String query = "SELECT * FROM patients WHERE id = ?";
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
