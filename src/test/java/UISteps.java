import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.linkText;

public class UISteps extends TestBase {

    @Step("Открываем главную страницу github.com")
    public void openMainPage() {
        open("/");
    }

    @Step("Ищем репозиторий {repo}")
    public void searchRepository(String repository) {
        $(".search-input-container").click();
        $("#query-builder-test").sendKeys(repository);
        $("#query-builder-test").submit();
    }

    @Step("Открываем страницу с репозиторием {repo}")
    public void openRepository(String repository) {
        $(linkText(repository)).click();
    }

    @Step("Открываем таб Issues")
    public void openIssueTab() {
        $("#issues-tab").click();
    }

    @Step("Проверяем название Issue {issueNumber}")
    public void checkLinkIssue(String repository, String issueText, String issueNumber) {
        $("a[href='/" + repository + "/issues/" + issueNumber + "']")
                .shouldHave(text(issueText));
    }
}