package sample;

import java.io.IOException;

public abstract class Controller
{

    private static final AirbnbDataLoader dataLoader = new AirbnbDataLoader();

    private Account currentAccount;

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
