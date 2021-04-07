package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangePasswordController extends AccountController

{

    @FXML
    private TextField currentPasswordField, newPasswordField, confirmPasswordField;

    @FXML
    private Label changePasswordErrorField;

    private AccountSettingsController accountSettingsController;

    private ControllerComponents controllerComponents;

    public ChangePasswordController(ControllerComponents controllerComponents, Stage accountStage, AccountSettingsController accountSettingsController) throws IOException
    {
        super(controllerComponents, accountStage);
        this.controllerComponents = controllerComponents;
        this.accountSettingsController = accountSettingsController;
    }

    public void initialize()
    {
    }

    @FXML
    private void exitChangePasswordMenu()
    {
        getAccountPanel().setCenter(accountSettingsController.getAccountSettingsPanel());
        getAccountStage().sizeToScene();
    }

    @FXML
    private void confirmNewPassword()
    {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (checkValidityOfCurrentPassword(currentPassword, newPassword) && checkPassword(newPassword, confirmPassword, changePasswordErrorField))
        {
            controllerComponents.getAccount().setPassword(newPassword);
            accountSettingsController.getPasswordFeedbackLabel().setText("Password changed successfully");
            exitChangePasswordMenu();
        }
    }
    private boolean checkValidityOfCurrentPassword(String currentPassword, String newPassword)
    {
        boolean valid = false;
        String password = controllerComponents.getAccount().getPassword();

        if (!currentPassword.equals(password))
        {
            changePasswordErrorField.setText("Current password is incorrect");
        } else if (newPassword.equals(password))
        {
            changePasswordErrorField.setText("New password is the same as current");
        } else
        {
            valid = true;
        }

        return valid;
    }

        public void resetPasswordFields()
        {
            currentPasswordField.setText("");
            newPasswordField.setText("");
            confirmPasswordField.setText("");
            changePasswordErrorField.setText("");
        }
}
