package sample;

public abstract class Controller
{
    private static final AirbnbDataLoader dataLoader = new AirbnbDataLoader();

    private Account account;

    protected void initialize(Account account){
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
