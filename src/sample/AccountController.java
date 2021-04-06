package sample;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AccountController extends Controller
{
    private Stage accountStage;

    private ArrayList<Account> listOfAccounts;

    private HashMap<String, Account> accountsMap;

    public AccountController(Account account, Stage accountStage){
        super(account);
        listOfAccounts = new ArrayList<>();
        accountsMap = new HashMap<>();
        this.accountStage = accountStage;
    }

    public Stage getAccountStage(){
        return accountStage;
    }

    public void setAccountStage(Stage accountStage){
        this.accountStage = accountStage;
    }
    /**
     * List of current accounts created in the app
     */
    public ArrayList<Account> getListOfAccounts() {
        return listOfAccounts;
    }

    /**
     * Maps the email of each account to the corresponding account object
     */
    public HashMap<String, Account> getAccountsMap() {
        return accountsMap;
    }
}
