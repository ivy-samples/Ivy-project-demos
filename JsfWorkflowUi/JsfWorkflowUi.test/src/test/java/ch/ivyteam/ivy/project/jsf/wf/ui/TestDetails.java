package ch.ivyteam.ivy.project.jsf.wf.ui;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class TestDetails extends BaseJsfWorkflowUiTest
{
  @Test
  public void testTaskDetails() throws Exception
  {
    createTask("task","Test if shows details", 2);
    navigate().taskList();
    driverHelper.clickAndWaitForAjax(By.id("buttonTaskDetail"));
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("task");
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("Test if shows details");
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("NORMAL");
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("SUSPENDED");
    closeTask();
  }

  @Test
  public void testCaseDetails() throws Exception
  {
    createTask("case","Test if shows details", 2);
    navigate().caseList();
    driverHelper.clickAndWaitForAjax(By.id("buttonCaseDetail"));
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("Test Workflow Jsf");
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("Sample WF using Html Dialogs");
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("JSF case");
    closeTask();
  }
  
  @Test
  public void testAddNoteToTask() throws Exception
  {
    createTask("taskForAddNote","Test add note", 2);
    navigate().taskList();
    driverHelper.clickAndWaitForAjax(By.id("buttonTaskDetail"));
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("taskForAddNote");
    driverHelper.clickAndWaitForAjax(By.id("formTaskDetails:openAddNote"));
    addNote();
    closeTask();
  }
  
  @Test
  public void testAddNoteToCase() throws Exception
  {
    createTask("taskForAddNoteToCase","Test add note", 2);
    navigate().caseList();
    driverHelper.clickAndWaitForAjax(By.id("buttonCaseDetail"));
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("taskForAddNoteToCase");
    driverHelper.clickAndWaitForAjax(By.id("formCaseDetails:openAddNoteCase"));
    addNote();
    closeTask();
  }
  
  @Test
  public void testDeleteNote() throws Exception
  {
    createTask("taskForDeleteNote","Test delete note", 2);
    navigate().taskList();
    driverHelper.clickAndWaitForAjax(By.id("buttonTaskDetail"));
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("taskForDeleteNote");
    driverHelper.clickAndWaitForAjax(By.id("formTaskDetails:openAddNote"));
    addNote();
    deleteNote();
    closeTask();
  }

  private void addNote()
  {
    driverHelper.findElementById("formAddNote:note").sendKeys("This is the description of the new note");
    driverHelper.clickAndWaitForAjax(By.id("formAddNote:saveNote"));
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("This is the description of the new note");
  }
  
  private void deleteNote()
  {
    driverHelper.clickAndWaitForAjax(By.id("formTaskDetails:j_id_g_2_2p:0:openDeleteCaseNote"));
    driverHelper.clickAndWaitForAjax(By.id("formConfirmDeleteNote:confirmAction"));
    assertThat(driverHelper.getWebDriver().getPageSource()).doesNotContain("This is the description of the new note");
  }

  @Test
  public void testChangeExpiryToFuture() throws Exception
  {
    createTask("taskForChangeExpiry","Test change expiry", 2, "30.4.2030");
    navigate().taskList();
    driverHelper.clickAndWaitForAjax(By.id("buttonTaskDetail"));
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("taskForChangeExpiry");
    driverHelper.clickAndWaitForAjax(By.id("formTaskDetails:openChangeExpiry"));
    driverHelper.findElementById("formDetailsChangeExpiry:expiryDate_input").click();
    driverHelper.findElementById("formDetailsChangeExpiry:expiryDate_input").clear();
    driverHelper.findElementById("formDetailsChangeExpiry:expiryDate_input").sendKeys("30.04.2030");
    driverHelper.findElementById("formDetailsChangeExpiry:expiryTime_input").click();
    driverHelper.findElementById("formDetailsChangeExpiry:expiryTime_input").clear();
    driverHelper.findElementById("formDetailsChangeExpiry:expiryTime_input").sendKeys("10:10");
    driverHelper.clickAndWaitForAjax(By.id("formDetailsChangeExpiry:saveChangeExpiry"));
    navigate().taskList();
    driverHelper.clickAndWaitForAjax(By.id("buttonTaskDetail"));
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("4/30/30 10:10 AM");
    closeTask();
  }
  
  @Test
  public void testChangeExpiryToPast() throws Exception
  {
    createTask("taskForChangeExpiryOlderDate","Test change expiry", 2, "30.4.2030");
    navigate().taskList();
    driverHelper.clickAndWaitForAjax(By.id("buttonTaskDetail"));
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("taskForChangeExpiry");
    driverHelper.clickAndWaitForAjax(By.id("formTaskDetails:openChangeExpiry"));
    driverHelper.findElementById("formDetailsChangeExpiry:expiryDate_input").click();
    driverHelper.findElementById("formDetailsChangeExpiry:expiryDate_input").clear();
    driverHelper.findElementById("formDetailsChangeExpiry:expiryDate_input").sendKeys("15.07.2013");
    driverHelper.findElementById("formDetailsChangeExpiry:expiryTime_input").click();
    driverHelper.findElementById("formDetailsChangeExpiry:expiryTime_input").clear();
    driverHelper.findElementById("formDetailsChangeExpiry:expiryTime_input").sendKeys("10:10");
    driverHelper.clickAndWaitForAjax(By.id("formDetailsChangeExpiry:saveChangeExpiry"));
    navigate().taskList();
    assertThat(driverHelper.getWebDriver().getPageSource()).doesNotContain("JSF taskForChangeExpiryOlderDate");
  }
  
  @Test
  public void testDelegateTask() throws Exception
  {
    createTask("taskDelegateTask","Test change expiry", 2);
    
    navigate().taskList();
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("taskDelegateTask");
    driverHelper.clickAndWaitForAjax(By.id("buttonTaskDetail"));
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("taskDelegateTask");
    driverHelper.clickAndWaitForAjax(By.id("formTaskDetails:openDelegateTask"));
    WebElement selectOneMenu = driverHelper.findElementById("formDelegateTask:selectionOfUser");
    prime().selectOneMenu(selectOneMenu).selectItemByLabel("Test User 1 (user1)");
    driverHelper.clickAndWaitForAjax(By.id("formDelegateTask:saveDelegateTask"));
    
    navigate().taskList();
    assertThat(driverHelper.getWebDriver().getPageSource()).doesNotContain("taskDelegateTask");
    createTask("taskDelegateTaskToRole","Test delegate to role", 2);
    
    navigate().taskList();
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("taskDelegateTaskToRole");
    driverHelper.clickAndWaitForAjax(By.id("buttonTaskDetail"));
    driverHelper.clickAndWaitForAjax(By.id("formTaskDetails:openDelegateTask"));
    WebElement selectOneRadio = driverHelper.findElementById("formDelegateTask");
    prime().selectOneRadio(selectOneRadio).selectItemById("formDelegateTask:delegateToRole");
    driverHelper.clickAndWaitForAjax(By.id("formDelegateTask:saveDelegateTask"));
    
    navigate().taskList();
    driverHelper.clickAndWaitForAjax(By.id("buttonTaskDetail"));
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("Everybody");
    closeTask();
  }
  
  @Test
  public void testDestroyWorkflow() throws Exception
  {
    createTask("caseDestroyWorkflow","Test destroy workflow", 2);
    navigate().caseList();
    driverHelper.clickAndWaitForAjax(By.id("buttonCaseDetail"));
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("caseDestroyWorkflow");
    driverHelper.clickAndWaitForAjax(By.id("formCaseDetails:openDeleteCase"));
    driverHelper.clickAndWaitForAjax(By.id("formConfirmDeleteReset:confirmAction"));
    navigate().taskList();
    assertThat(driverHelper.getWebDriver().getPageSource()).doesNotContain("JSF caseDestroyWorkflow");
  }
  
  @Test
  public void testCancelDestroyWorkflow() throws Exception
  {
    createTask("caseCancelDestroyWorkflow","Test destroy workflow", 2);
    navigate().caseList();
    driverHelper.clickAndWaitForAjax(By.id("buttonCaseDetail"));
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("caseCancelDestroyWorkflow");
    driverHelper.clickAndWaitForAjax(By.id("formCaseDetails:openDeleteCase"));
    driverHelper.clickAndWaitForAjax(By.id("formConfirmDeleteReset:notConfirmAction"));
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("SUSPENDED");
    navigate().taskList();
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("JSF caseCancelDestroyWorkflow");
    closeTask();
  }
  
  @Test
  public void testResetTask() throws Exception
  {
    createTask("resetTask","Test reset task", 2);
    navigate().taskList();
    driverHelper.clickAndWaitForAjax(By.id("taskLinkRow_0"));
    navigate().taskList();
    driverHelper.clickAndWaitForAjax(By.id("buttonTaskDetail"));
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("resetTask");
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("RESUMED");
    driverHelper.clickAndWaitForAjax(By.id("formTaskDetails:openResetTask"));
    driverHelper.clickAndWaitForAjax(By.id("formConfirmDeleteReset:confirmAction"));
    navigate().taskList();
    driverHelper.clickAndWaitForAjax(By.id("buttonTaskDetail"));
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("SUSPENDED");
    closeTask();
  }
  
  @Test
  public void testParkTask() throws Exception
  {
    createTask("parkTask","Test park task", 2);
    navigate().taskList();
    driverHelper.clickAndWaitForAjax(By.id("taskLinkRow_0"));
    navigate().taskList();
    driverHelper.clickAndWaitForAjax(By.id("buttonTaskDetail"));
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("parkTask");
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("RESUMED");
    driverHelper.clickAndWaitForAjax(By.id("formTaskDetails:openParkTask"));
    navigate().taskList();
    driverHelper.clickAndWaitForAjax(By.id("buttonTaskDetail"));
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("PARKED");
    closeTask();
  }
  
  @Test
  public void testPageArchive() throws Exception
  {
    createHtmlTask("pageArchive", "Test page archive");
    navigate().caseList();
    driverHelper.clickAndWaitForAjax(By.id("buttonCaseDetail"));
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("A Html Case");
    driverHelper.clickAndWaitForAjax(By.id("formCaseDetails:openPageArchive"));
    navigate().taskList();
    driverHelper.findElementById("taskLinkRow_0").click();
    driverHelper.clickAndWaitForAjax(By.id("submit"));
    navigate().taskHistory();
    driverHelper.clickAndWaitForAjax(By.id("buttonTaskHistoryDetail"));
    driverHelper.clickAndWaitForAjax(By.id("formTaskDetails:openPageArchive"));
  }
  
  @Test
  public void testChangePriority() throws Exception
  {
    createTask("changePriorityTask","Test change priority", 2);
    navigate().taskList();
    driverHelper.clickAndWaitForAjax(By.id("buttonTaskDetail"));
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("changePriorityTask");
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("NORMAL");
    driverHelper.clickAndWaitForAjax(By.id("formTaskDetails:openChangePriority"));
    WebElement selectOneRadio = driverHelper.findElementById("formDetailsChangePriority");
    prime().selectOneRadio(selectOneRadio).selectItemById("formDetailsChangePriority:high");
    driverHelper.clickAndWaitForAjax(By.id("formDetailsChangePriority:saveChangePriority"));
    navigate().taskList();
    driverHelper.clickAndWaitForAjax(By.id("buttonTaskDetail"));
    assertThat(driverHelper.getWebDriver().getPageSource()).contains("HIGH");
    closeTask();
  }
}
