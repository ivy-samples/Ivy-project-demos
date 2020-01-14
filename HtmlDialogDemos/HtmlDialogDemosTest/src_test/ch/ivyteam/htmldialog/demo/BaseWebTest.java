package ch.ivyteam.htmldialog.demo;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;

import ch.ivyteam.htmldialog.server.test.EngineUrl;

public abstract class BaseWebTest
{ 
  protected RemoteWebDriver driver;

  @BeforeEach
  public void setUp()
  {
    Configuration.browser = "firefox";
    Configuration.headless = true;
    Configuration.reportsFolder = "target/senenide/reports";
    Configuration.proxyEnabled = true;
    Configuration.fileDownload = FileDownloadMode.PROXY;
    Selenide.open();
    driver = (RemoteWebDriver) WebDriverRunner.getWebDriver();
  }
  
  protected void startProcess(String pathToIvp)
  {
    Selenide.open(EngineUrl.process() + "/HtmlDialogDemos/" + pathToIvp);
    if (StringUtils.containsNone(pathToIvp, "ClientSideValidationDemo"))
    {
      $(By.id("menuform")).shouldBe(visible);
    }
  }

  protected void clearInput(By inputLocator)
  {
    $(inputLocator).shouldBe(visible).clear();
    $(inputLocator).sendKeys(Keys.TAB);
  }

}