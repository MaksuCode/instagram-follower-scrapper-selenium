package tr.instagram.scrapper;

import org.openqa.selenium.WebElement;
import tr.instagram.app.App;

import java.util.List;


public class Scrapper {

    public static void main(String[] args){
        App app = new App();
        app.goToScrappedProfile();
        List<String> followers = app.getFollowers();
        List<String> followings = app.getFollowings();
        System.out.println(followers.size());
        System.out.println(followings.size());
    }







}
