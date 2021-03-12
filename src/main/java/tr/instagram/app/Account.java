package tr.instagram.app;

import tr.instagram.reader.PropertiesReader;

public class Account {

    private String username ;
    private String password ;

    PropertiesReader propertiesReader = null ;

    public Account(){
        try {
            propertiesReader = new PropertiesReader("properties-from-pom.properties");
        }catch (Exception e){
            e.printStackTrace();
        }
        this.username = propertiesReader.getProperty("username.property") ;
        this.password = propertiesReader.getProperty("password.property") ;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

}
