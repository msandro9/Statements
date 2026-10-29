import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> drzave = new ArrayList<>();
        drzave.add("Japan");
        drzave.add("Brazil");
        drzave.add("Argentina");
        drzave.add("Turska");
        drzave.add("Portugal");
        drzave.add("Maroko");
        drzave.add("Poljska");
        drzave.add("Egipat");
        drzave.add("Italija");
        drzave.add("Kanada");

        DataSource dataSource = createDataSource();

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Drzava (Naziv) VALUES (?)");

            for (String drzava : drzave) {
                preparedStatement.setString(1, drzava);
                int result = preparedStatement.executeUpdate();
                System.out.println("Uneseno drzava: " + result + " - " + drzava);
            }

            CallableStatement callableStatement = connection.prepareCall("{CALL obrisi_drzave(?)}");
            callableStatement.setInt(1, 10);
            int result = callableStatement.executeUpdate();
            System.out.println("Obrisano drzava: " + result);

        } catch (SQLException e) {
            System.err.println("Greška prilikom spajanja na bazu podataka:");
            e.printStackTrace();
        }
    }

    private static DataSource createDataSource() {
        SQLServerDataSource dataSource = new SQLServerDataSource();
        dataSource.setServerName("localhost");
        dataSource.setDatabaseName("AdventureWorksOBP");
        dataSource.setUser("sa");
        dataSource.setPassword("SQL");
        dataSource.setTrustServerCertificate(true);
        return dataSource;
    }
}
