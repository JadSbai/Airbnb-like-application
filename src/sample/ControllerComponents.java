package sample;

import java.io.IOException;

public class ControllerComponents
{
    private static final AirbnbDataLoader dataLoader = new AirbnbDataLoader();

    private Account currentAccount;
    
    public ControllerComponents(Account account){
        this.currentAccount = account;
    }

    protected static AirbnbDataLoader getDataLoader() {
        return dataLoader;
    }

    protected Account getAccount() {
        return currentAccount;
    }

    protected void setCurrentAccount(Account account) {
        this.currentAccount = account;
    }


}
