package Service;
import Model.Account;
import DAO.AccountDAO;



public class AccountService {
    AccountDAO accountDAO;

    public AccountService(){
        accountDAO=new AccountDAO();
    }
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    
}
    public Account addAccount(Account account){
        String password=account.getPassword();
        String username=account.getUsername();
        if(username.length()==0){
            return null;
        }
        if(password.length()<=4){
            return null;
        }
         
         return accountDAO.insertnewAccount(account);
         
}
   
    public Account login(Account account){
        return accountDAO.checkAccount(account.getUsername(),account.getPassword());


    }
   
}













