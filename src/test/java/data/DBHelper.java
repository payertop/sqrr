package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;

import static java.sql.DriverManager.getConnection;

public class DBHelper {
    private final static QueryRunner queryRunner = new QueryRunner();
    private final static Connection conn = connection();
    private static final String url = "jdbc:mysql://localhost:3306/database";
    private static final String user = "user";
    private static final String password = "pass";
    static String code;

    private DBHelper() {
    }

    @SneakyThrows
    private static Connection connection() {
        return getConnection(url, user, password);
    }

    @SneakyThrows
    public static String getVerifyCode() {
        code = queryRunner.query(conn,
                "SELECT ac.code, ac.created, u.id, ac.user_id FROM auth_codes ac, users u WHERE u.id = ac.user_id ORDER BY created DESC",
                new ScalarHandler<>());
        return code;
    }

    @SneakyThrows
    public static void deleteCodes() {
        queryRunner.update(conn, "DELETE FROM auth_codes");
    }

    @SneakyThrows
    public static void deleteALLDB() {
        queryRunner.update(conn, "DELETE FROM auth_codes");
        queryRunner.update(conn, "DELETE FROM card_transactions");
        queryRunner.update(conn, "DELETE FROM cards");
        queryRunner.update(conn, "DELETE FROM users");
    }
}