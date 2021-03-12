package tr.instagram.scrapper;

import org.openqa.selenium.WebElement;
import tr.instagram.app.App;

import java.util.List;

public class Scrapper {

    public static void getFollowers(){
        App app = new App();
        app.setUsername();
        app.setPassword();
        app.clickSubmitButton();
        app.navigateToProfile();
        app.clickFollowers();
        app.scrollDownModal();
        List<WebElement> followers = app.getFollowers();
        for (WebElement follower : followers){
            System.out.println(follower.getText());
        }
    }

    public static void main(String[] args) {
        getFollowers();
    }

}
