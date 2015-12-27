package beans;

import components.ProxyLogDataManager;
import components.UsersFacade;
import entity.Users;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

public class IndexBean implements Serializable {
    @EJB
    private UsersFacade usersFacade;
    @EJB
    private ProxyLogDataManager logDataManager;
    private List <Users> users;
    private String inputLogin;
    private String inputPass;
    private String errorMsg;
    
    public Object authCheck() {
        users = usersFacade.findAllUsers();
        boolean authSuccessful = false;
         
        for (int i = 0; i < users.size(); i++) {
           if (inputLogin.equals( users.get(i).getLogin() ) ) {
               if (users.get(i).getPassword().equals(inputPass) ) {
                   authSuccessful = true;
               }
               break;
           }
        }
         
        if (authSuccessful == false) {
            errorMsg = "Неверный логин или пароль";
            return null;
        } 
        else {
            errorMsg = "";
            logDataManager.setDataViewMode("DEFAULT");
            logDataManager.clearSelectedFields();
            return "GoToData";
        }
    }
     
    public String getInputLogin() {
        return inputLogin;
    }

    public void setInputLogin(String inputLogin) {
        this.inputLogin = inputLogin;
    }

    public String getInputPass() {
        return inputPass;
    }

    public void setInputPass(String inputPass) {
        this.inputPass = inputPass;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
