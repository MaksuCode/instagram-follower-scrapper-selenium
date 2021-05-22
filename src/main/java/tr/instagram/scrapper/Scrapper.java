package tr.instagram.scrapper;


import tr.instagram.app.Account;
import tr.instagram.app.App;

import java.util.List;
import java.util.Scanner;

public class Scrapper {

    static Scanner sc = new Scanner(System.in);
    static Account account = new Account();

    public static void main(String[] args){

        System.out.println("Please enter your username : ");
        String username = sc.nextLine();
        System.out.println("Please enter your password : ");
        String password = sc.nextLine();
        System.out.println("Please enter target profile name : ");
        String targetProfile = sc.nextLine();

        account.setUsername(username);
        account.setPassword(password);

        App app = new App(account);
        app.login();

        app.navigateToTargetProfile(targetProfile);

        List<String> followers = app.getFollowers(targetProfile);

        app.quitApp();

        System.out.println(targetProfile + " has " + followers.size() + " followers... :");

        for (String str : followers){
            System.out.println(str);
        }



    }







}
