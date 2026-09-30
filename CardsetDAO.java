import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardsetDAO {

    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String JDBC_USUARIO = "postgres";
    private static final String JDBC_SENHA = "postgres";

    public List<Cardset> listar() {
        List<Cardset> resultado = new ArrayList<>();
        try {
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USUARIO, JDBC_SENHA);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT id, set_name, set_code, num_of_cards, set_image FROM cardset"
            );
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Cardset cardset = new Cardset();
                cardset.setId(resultSet.getInt("id"));
                cardset.setSet_name(resultSet.getString("set_name"));
                cardset.setSet_code(resultSet.getString("set_code"));
                cardset.setNum_of_cards(resultSet.getInt("num_of_cards"));
                cardset.setSet_image(resultSet.getString("set_image"));
                resultado.add(cardset);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return resultado;
        }
        return resultado;
    }

    public Cardset obter(int id) {
        Cardset cardset = null;
        try {
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USUARIO, JDBC_SENHA);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT id, set_name, set_code, num_of_cards, set_image FROM cardset WHERE id = ?"
            );
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                cardset = new Cardset();
                cardset.setId(resultSet.getInt("id"));
                cardset.setSet_name(resultSet.getString("set_name"));
                cardset.setSet_code(resultSet.getString("set_code"));
                cardset.setNum_of_cards(resultSet.getInt("num_of_cards"));
                cardset.setSet_image(resultSet.getString("set_image"));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return cardset;
    }

    public boolean inserir(int id, String set_name, String set_code, int num_of_cards, String set_image) {
        boolean sucesso = false;
        try {
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USUARIO, JDBC_SENHA);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO cardset (id, set_name, set_code, num_of_cards, set_image) VALUES (?, ?, ?, ?, ?)"
            );
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, set_name);
            preparedStatement.setString(3, set_code);
            preparedStatement.setInt(4, num_of_cards);
            preparedStatement.setString(5, set_image);

            sucesso = (preparedStatement.executeUpdate() == 1);

            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return sucesso;
    }

    public boolean atualizar(String set_name, String set_code, int num_of_cards, String set_image, int id) {
        boolean sucesso = false;
        try {
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USUARIO, JDBC_SENHA);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE cardset SET set_name = ?, set_code = ?, num_of_cards = ?, set_image = ? WHERE id = ?"
            );
            preparedStatement.setString(1, set_name);
            preparedStatement.setString(2, set_code);
            preparedStatement.setInt(3, num_of_cards);
            preparedStatement.setString(4, set_image);
            preparedStatement.setInt(5, id);

            sucesso = (preparedStatement.executeUpdate() == 1);

            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return sucesso;
    }

    public boolean excluir(int id) {
        boolean sucesso = false;
        try {
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USUARIO, JDBC_SENHA);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM cardset WHERE id = ?"
            );
            preparedStatement.setInt(1, id);

            sucesso = (preparedStatement.executeUpdate() == 1);

            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return sucesso;
    }

    public static void main(String[] args) {
        CardsetDAO dao = new CardsetDAO();

        System.out.println("=== 1) INSERIR ===");
        boolean inseriu = dao.inserir(1, "Base Set", "BS", 102, "bs.png");
        System.out.println("Inseriu? " + inseriu);

        boolean inseriu2 = dao.inserir(2, "Jungle", "JU", 64, "ju.png");
        System.out.println("Inseriu 2? " + inseriu2);

        System.out.println("\n=== 2) LISTAR ===");
        List<Cardset> lista = dao.listar();
        for (Cardset c : lista) {
            System.out.printf("id=%d | nome=%s | codigo=%s | cartas=%d | imagem=%s%n",
                    c.getId(), c.getSet_name(), c.getSet_code(),
                    c.getNum_of_cards(), c.getSet_image());
        }

        System.out.println("\n=== 3) OBTER id=1 ===");
        Cardset c1 = dao.obter(1);
        if (c1 != null) {
            System.out.printf("Encontrado: %s (%s)%n", c1.getSet_name(), c1.getSet_code());
        } else {
            System.out.println("Nao encontrado.");
        }

        System.out.println("\n=== 4) ATUALIZAR id=1 ===");
        boolean atualizou = dao.atualizar("Base Set (rev)", "BS2", 110, "bs2.png", 1);
        System.out.println("Atualizou? " + atualizou);

        Cardset atualizado = dao.obter(1);
        System.out.printf("Depois do update: %s | %s | %d%n",
                atualizado.getSet_name(), atualizado.getSet_code(),
                atualizado.getNum_of_cards());

        System.out.println("\n=== 5) EXCLUIR id=2 ===");
        boolean excluiu = dao.excluir(2);
        System.out.println("Excluiu? " + excluiu);

        System.out.println("\n=== 6) LISTAR final ===");
        for (Cardset c : dao.listar()) {
            System.out.printf("id=%d | nome=%s%n", c.getId(), c.getSet_name());
        }
    }
}