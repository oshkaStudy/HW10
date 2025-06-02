import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Feature;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

public class AllureStepsTest extends TestBase {

    private static final String repository = "selenide/selenide";
    private static final String issueText = "feat: Add function to Selenide to " +
            "interact with the iOS 18 Limited Contact Access dialog";
    private static final String issueNumber = "2948";

    @BeforeEach
    public void beforeEach() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    //Написать тест на проверку названия Issue в репозитории через Web-интерфейс.
    //Этот тест представить в трех вариантах:
    //1. Чистый Selenide (с Listener)
    //2. Лямбда шаги через step (name, () -> {})
    //3. Шаги с аннотацией @Step

    @Test
    @Feature("Issue в репозитории")
    @DisplayName("Чистый Selenide (с Listener)")
    public void issueSelenideSearchTest() {

        open("");

        $(".search-input-container").click();
        $("#query-builder-test").sendKeys(repository);
        $("#query-builder-test").submit();
        $(linkText(repository)).click();
        $("#issues-tab").click();
        $("a[href='/" + repository + "/issues/" + issueNumber + "']")
                .shouldHave(text(issueText));

    }


    @Test
    @Feature("Issue в репозитории")
    @DisplayName("Лямбда шаги через step (name, () -> {})")
    public void issuesLambdaSearchTest() {

        step("Открываем главную страницу github.com", () -> {
            open("/");
        });

        step("Ищем репозиторий" + repository, () -> {
            $(".search-input-container").click();
            $("#query-builder-test").sendKeys(repository);
            $("#query-builder-test").submit();
        });

        step("Открываем страницу с репозиторием"  + repository , () -> {
            $(linkText(repository)).click();
        });

        step("Открываем таб Issues", () -> {
            $("#issues-tab").click();
        });

        step("Проверяем название Issue " + issueNumber , () -> {
            $("a[href='/" + repository + "/issues/" + issueNumber + "']")
                    .shouldHave(text(issueText));
        });

    }

    @Test
    @Feature("Issue в репозитории")
    @DisplayName("Шаги с аннотацией @Step")
    public void issuesStepAnnotationSearchTest() {

        UISteps uiSteps = new UISteps();

        uiSteps.openMainPage();
        uiSteps.searchRepository(repository);
        uiSteps.openRepository(repository);
        uiSteps.openIssueTab();
        uiSteps.checkLinkIssue(repository,issueText,issueNumber);

    }
}