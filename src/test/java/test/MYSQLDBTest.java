package test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import pages.AccountPage;
import pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static data.DBHelper.deleteALLDB;
import static data.DBHelper.deleteCodes;
import static data.DataHelper.*;

public class MYSQLDBTest {
    @BeforeAll
    static void setUpAll() {
        Configuration.headless = false;
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterEach
    void setDown() {
        deleteCodes();
    }

    @AfterAll
    static void setDownAll() {
        deleteALLDB();
    }

    @Test
    void shouldReturnAccessWithFirstValidLogin() {
        new LoginPage().validLogin(getFirstAuthInfo()).accessPage();
    }

    @Test
    void shouldReturnAccessWithSecondValidLogin() {
        new LoginPage().validLogin(getSecondAuthInfo()).accessPage();
    }

    @Test
    void shouldReturnFailWithInvalidLogin() {
        new LoginPage().invalidLogin(getInvalidAuthInfo());
    }

    @Test
    void shouldReturnFailWithEmptyLogin() {
        new LoginPage().emptyLoginOrPass(getEmptyLogin());
    }

    @Test
    void shouldReturnFailWithEmptyPass() {
        new LoginPage().emptyLoginOrPass(getEmptyPass());
    }

    @Test
    void shouldReturnFailWithEmptyLoginAndPass() {
        new LoginPage().emptyLoginOrPass(getEmptyAuthInfo());
    }

    @Test
    void shouldReturnFailWithInvalidCode() {
        new LoginPage().validLogin(getFirstAuthInfo()).invalidCode();
    }

    @Test
    void shouldReturnFailWithEmptyCode() {
        new LoginPage().validLogin(getSecondAuthInfo()).emptyCode();
    }

    @Test
    void shouldEnterAccountPageFirstAccount() {
        new LoginPage().validLogin(getFirstAuthInfo()).validCode();
        new AccountPage().accessLogin();
    }

    @Test
    void shouldEnterAccountPageSecondAccount() {
        new LoginPage().validLogin(getSecondAuthInfo()).validCode();
        new AccountPage().accessLogin();
    }

    @Test
    void shouldReturnFailAfterTripleEntry() {
        new LoginPage().validLogin(getInvalidPassFirstAuthInfo());
        new LoginPage().validLogin(getInvalidPassFirstAuthInfo());
        new LoginPage().validLogin(getInvalidPassFirstAuthInfo());
        new LoginPage().invalidLogin(getFirstAuthInfo());
    }
}
