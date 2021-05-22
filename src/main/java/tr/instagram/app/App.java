package tr.instagram.app;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class App {

    WebDriver driver ;
    String BASE_URL = "https://www.instagram.com/";
    Account account ;

    public App(Account account){
        this.account = account;
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(BASE_URL);
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

    public void login(){
        setUsername();
        setPassword();
        clickSubmitButton();
    }

    public List<String> getFollowers(String profileName){
        clickFollowers(profileName);
        scrollDownModal();
        List<WebElement> followers = getAccountNames();
        List<String> followerStrList = new ArrayList<>();
        for (WebElement follower : followers){
            followerStrList.add(follower.getText());
        }
        closeModal();
        return followerStrList;
    }

    public List<String> getFollowings(String profileName){
        clickFollowings(profileName);
        scrollDownModal();
        List<WebElement> followings = getAccountNames();
        List<String> followingList = new ArrayList<>();
        for (WebElement follower : followings){
            followingList.add(follower.getText());
        }
        closeModal();
        return followingList;
    }

    public void navigateToTargetProfile(String profile){
        driver.navigate().to(BASE_URL.concat(profile));
    }

    public void quitApp(){
        driver.quit();
    }

    private void setUsername(){
        WebElement username = driver.findElement(new By.ByCssSelector("input[name='username']"));
        username.click();
        username.sendKeys(account.getUsername());
    }

    private void setPassword(){
        WebElement password = driver.findElement(new By.ByCssSelector("input[name='password']"));
        password.click();
        password.sendKeys(account.getPassword());
    }

    private void clickSubmitButton(){
        driver.findElement(new By.ByCssSelector("button[type='submit']")).click();
        try {
            Thread.sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void clickFollowers(String profileName){
        clickFollowerOrFollowing(profileName,"followers");
    }

    private void clickFollowings(String profileName){
        clickFollowerOrFollowing(profileName, "following");
    }

    private void clickFollowerOrFollowing(String profileName , String str){
        driver.findElement(new By.ByCssSelector("a[href='/"+profileName+"/".concat(str)+"/'")).click();
        try {
            Thread.sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void scrollDownModal(){
        String jsScript = "page = document.querySelector('.isgrP');" +
                "page.scrollTo(0,page.scrollHeight);" +
                "var pageEnd = page.scrollHeight;" +
                "return pageEnd;";
        int pageEnd = Integer.parseInt(((JavascriptExecutor)driver).executeScript(jsScript).toString());
        while (true){
            try {
                Thread.sleep(800);
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

    private List<WebElement> getAccountNames(){
        return driver.findElements(new By.ByCssSelector(".FPmhX.notranslate._0imsa"));
    }

    private void closeModal(){
        driver.findElement(new By.ByCssSelector("svg._8-yf5[aria-label='Close']")).click();
    }



}
