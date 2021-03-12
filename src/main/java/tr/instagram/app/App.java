package tr.instagram.app;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import tr.instagram.reader.PropertiesReader;

import java.util.List;

public class App {

    WebDriver driver = null;
    String url = "https://www.instagram.com/";
    Account account = new Account();

    PropertiesReader propertiesReader = null ;

    public App(){
        System.setProperty("webdriver.chrome.driver","drivers/chromedriver");
        driver = new ChromeDriver();
        driver.get(url);
        try{
            boolean isReady = false;
            while (!isReady){
                isReady = ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                Thread.sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setUsername(){
        WebElement username = driver.findElement(new By.ByCssSelector("input[name='username']"));
        username.click();
        username.sendKeys(account.getUsername());
    }

    public void setPassword(){
        WebElement password = driver.findElement(new By.ByCssSelector("input[name='password']"));
        password.click();
        password.sendKeys(account.getPassword());
    }

    public void clickSubmitButton(){
        driver.findElement(new By.ByCssSelector("button[type='submit']")).click();
        try {
            Thread.sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void navigateToProfile(){
        try {
            propertiesReader = new PropertiesReader("properties-from-pom.properties");
        }catch (Exception e){
            e.printStackTrace();
        }
        if (propertiesReader.getProperty("account-to-be-scrapped").equals("")){
            driver.navigate().to("https://www.instagram.com/".concat(account.getUsername()));
        }else{
            driver.navigate().to("https://www.instagram.com/".concat(propertiesReader.getProperty("account-to-be-scrapped")));
        }
    }


    public void clickFollowers(){
        if (!propertiesReader.getProperty("account-to-be-scrapped").equals("")){
            driver.findElement(new By.ByCssSelector("a[href='/"+propertiesReader.getProperty("account-to-be-scrapped")+"/followers/'")).click();
        }else{
            driver.findElement(new By.ByCssSelector("a[href='/"+account.getUsername()+"/followers/'")).click();
        }
        try {
            Thread.sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void scrollDownModal(){
        String jsScript = "page = document.querySelector('.isgrP');" +
                "page.scrollTo(0,page.scrollHeight);" +
                "var pageEnd = page.scrollHeight;" +
                "return pageEnd;";
        int pageEnd = Integer.parseInt(((JavascriptExecutor)driver).executeScript(jsScript).toString());
        while (true){
            try {
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
            int end = pageEnd;
            pageEnd = Integer.parseInt(((JavascriptExecutor)driver).executeScript(jsScript).toString());
            if (pageEnd==end){
                break;
            }
        }
    }

    public List<WebElement> getFollowers(){
        return driver.findElements(new By.ByCssSelector(".FPmhX.notranslate._0imsa"));
    }

}
