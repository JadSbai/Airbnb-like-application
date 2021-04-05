package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ChangePasswordController extends AccountSettingsController
{
    public void initialize()
    {

    }

    @FXML
    private void exitChangePasswordMenu()
    {
        getAccountPanel().setCenter(getAccountSettings());
    }

    @FXML
    private void confirmNewPassword()
    {
        String currentPassword = getCurrentPasswordField().getText();
        String newPassword = getNewPasswordField().getText();
        String confirmPassword = getConfirmPasswordField().getText();

        if (checkValidityOfCurrentPassword(currentPassword, newPassword) && checkPassword(newPassword, confirmPassword, getChangePasswordErrorField()))
        {
            getAccount().setPassword(newPassword);
            getPasswordFeedbackLabel().setText("Password changed successfully");
            exitChangePasswordMenu();
        }
    }
    private boolean checkValidityOfCurrentPassword(String currentPassword, String newPassword)
    {
        boolean valid = false;
        String password = getAccount().getPassword();

        if (!currentPassword.equals(password))
        {
            getChangePasswordErrorField().setText("Current password is incorrect");
        } else if (newPassword.equals(password))
        {
            getChangePasswordErrorField().setText("New password is the same as current");
        } else
        {
            valid = true;
        }

        return valid;
    }
}
