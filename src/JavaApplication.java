import java.sql.*;

public class JavaApplication {
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = null;
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5555/postgres", "postgres", "password");
            Statement st = connection.createStatement();
            System.out.println("Connection established");

            ResultSet rs;
            rs = st.executeQuery("select * from person");

            while (rs.next()) {
                String nid = rs.getString("nid");
                String name = rs.getString("name");
                System.out.println("\nPerson with nid "+nid+", is called " +name);
            }
            ResultSet rs2;
            rs2 = st.executeQuery("Select * from car");
            System.out.println("\nTABLE CAR");
            System.out.println("License \tBrand \t\tDriver");

            while (rs2.next()){
                String licensenumber = rs2.getString(1);
                String brand = rs2.getString(2);
                String driver = rs2.getString(3);
                System.out.println(licensenumber +"\t\t"+ brand + "\t\t" + driver);
            }
            connection.close();
        }
        catch (ClassNotFoundException | SQLException e){
            System.out.println(e.toString());
        }
    }
}
