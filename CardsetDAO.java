import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardsetDAO {

    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/loja_cartas";
    private static final String JDBC_USUARIO = "aluno";
    private static final String JDBC_SENHA = "ufc123";

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
            return false;
        }
        return sucesso;
    }

    public static void main(String[] args) {

    }
}