package sample;

public abstract class Controller
{
    AirbnbDataLoader dataLoader = new AirbnbDataLoader();
    private Account account;

    protected abstract void initialize();

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
