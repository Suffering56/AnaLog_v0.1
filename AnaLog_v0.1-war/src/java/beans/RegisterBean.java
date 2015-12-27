package beans;

import components.UsersFacade;
import entity.Users;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

public class RegisterBean implements Serializable {

    @EJB
    private UsersFacade usersFacade;
    private List<Users> users;
    private String newLogin;
    private String newPass;
    private String repeatNewPass;
    private String newMail;
    private String regMsg;

    public Object addNewUser() {
        if (newLogin.equals("") || newPass.equals("") || repeatNewPass.equals("")
                || newMail.equals("")) {
            clearInputFields("Вы не заполнили все поля!");
        } else {
            if (!newPass.equals(repeatNewPass)) {
                clearInputFields("Введенные пароли не совпадают!");
            } else {
                userChecking();
            }
        }
        return null;
    }

    private void userChecking() {
        boolean userExists = false;
        users = usersFacade.findAllUsers();

        for (int i = 0; i < users.size(); i++) {
            if (newLogin.equals(users.get(i).getLogin())) {
                newLogin = "";
                clearInputFields("Пользователь с таким логином уже существует");
                userExists = true;
                break;
            }
        }

        if (userExists == false) {
            Users user = new Users(newLogin, newPass, 1, newMail);
            usersFacade.persistUser(user);
            newLogin = "";
            clearInputFields("Registration succesful!");
        }
    }

    private void clearInputFields(String msg) {
        newPass = "";
        repeatNewPass = "";
        newMail = "";
        regMsg = msg;
    }

    public String getNewLogin() {
        return newLogin;
    }

    public void setNewLogin(String newLogin) {
        this.newLogin = newLogin;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getRepeatNewPass() {
        return repeatNewPass;
    }

    public void setRepeatNewPass(String repeatNewPass) {
        this.repeatNewPass = repeatNewPass;
    }

    public String getNewMail() {
        return newMail;
    }

    public void setNewMail(String newMail) {
        this.newMail = newMail;
    }

    public String getRegMsg() {
        return regMsg;
    }

    public void setRegMsg(String regMsg) {
        this.regMsg = regMsg;
    }
}
