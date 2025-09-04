package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String username="root";
    private static final String password="Diamo@565";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner sc=new Scanner(System.in);


        try {
            Connection cn = DriverManager.getConnection(url,username,password);
            patient patient=new patient(cn,sc);
            Doctor doctor=new Doctor(cn);

            while (true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM ");
                System.out.println("1. Add Patient");
                System.out.println("2. View patient");
                System.out.println("3. View Doctor");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter Your Choice: ");
                int choice =sc.nextInt();

                switch (choice){
                    case 1:
                        //add patient
                        patient.addPatient();
                        System.out.println();
                        break;

                    case 2:
                        //view patient
                        patient.viewPatients();
                        System.out.println();
                        break;

                    case 3:
                        //view doctor
                        doctor.viewDoctors();
                        System.out.println();
                        break;

                    case 4:
                        //Book Appointments
                        bookAppointment(patient,doctor,cn,sc);
                        System.out.println();
                        break;


                    case 5:
                        System.out.println("THANK YOU FOR USING HOSPITAL MANAGEMENT SYSTEM ");
                        return;
                    default:
                        System.out.println("Enter valid Choice!!!");
                        break;

                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void bookAppointment(patient patient,Doctor doctor,Connection cn, Scanner sc){
        System.out.print("Enter Patient Id: ");
        int patientId = sc.nextInt();
        System.out.print("Enter Doctor Id: ");
        int doctorId = sc.nextInt();
        System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
        String appointmentDate = sc.next();

        if (patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)){
            if (checkDoctorAvailablity(doctorId,appointmentDate,cn)){
                String appointmentQuery = "INSERT INTO appointments(patient_id,doctor_id,appointment_date) VALUES(?,?,?)";
                try {
                    PreparedStatement ps = cn.prepareStatement(appointmentQuery);
                    ps.setInt(1,patientId);
                    ps.setInt(2,doctorId);
                    ps.setString(3,appointmentDate);
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected>0){
                        System.out.println("Appointment Booked");
                    }else {
                        System.out.println("Failed to Book Appointment");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }

            }else {
                System.out.println("Doctors not available on this date");
            }

        }else {
            System.out.println("Either doctor or Patient Doesn't Exist!!!");
        }
    }


    public static boolean checkDoctorAvailablity(int doctorid, String appointmentDate, Connection cn){
           String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date =?";
           try {
               PreparedStatement ps = cn.prepareStatement(query);
               ps.setInt(1,doctorid);
               ps.setString(2,appointmentDate);
               ResultSet rl = ps.executeQuery();
               if (rl.next()){
                   int count =rl.getInt(1);
                   if (count==0){
                       return true;
                   }else {
                       return false;
                   }
               }

           }catch (SQLException e){
               e.printStackTrace();
           }
           return false;
    }
}
