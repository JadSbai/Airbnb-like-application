package sample;

import java.io.IOException;

public class ControllerComponents
{

    /**
    * An AirbnbDataLoader object
    */
    private static final AirbnbDataLoader dataLoader = new AirbnbDataLoader();

    /**
    * A 'current account' account object
    */
    private Account currentAccount;

    /**
    * The constructor of the class. Initializes the current account
    * @param account
    */
    public ControllerComponents(Account account){
        this.currentAccount = account;
    }

    /**
    * @return the data loader
    */
    protected static AirbnbDataLoader getDataLoader() {
        return dataLoader;
    }

    /**
    * @return the current account
    */
    protected Account getAccount() {
        return currentAccount;
    }

    /**
    * Sets the current account
    * @param account
    */
    protected void setCurrentAccount(Account account) {
        this.currentAccount = account;
    }


}
