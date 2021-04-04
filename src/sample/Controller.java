package sample;

import java.io.IOException;

public abstract class Controller
{
    AirbnbDataLoader dataLoader = new AirbnbDataLoader();

    private Account currentAccount;

    protected abstract void initialize() throws IOException;

    protected Account getCurrentAccount() {
        return currentAccount;
    }

    protected void setCurrentAccount(Account account) {
        this.currentAccount = account;
    }


}
