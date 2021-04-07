package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangePasswordController extends AccountController

{

    /**
    * The text fields for the current password, new password and confirm password
    */
    @FXML
    private TextField currentPasswordField, newPasswordField, confirmPasswordField;

    /**
    * A label to hold the error text in case the password fails to be changed
    */
    @FXML
    private Label changePasswordErrorField;

    /**
    * An AccountSettingsController object
    */
    private AccountSettingsController accountSettingsController;

    /**
    * A ControllerComponents object
    */
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

    /**
    * Exits the change password menu
    */
    @FXML
    private void exitChangePasswordMenu()
    {
        getAccountPanel().setCenter(accountSettingsController.getAccountSettingsPanel());
        getAccountStage().sizeToScene();
    }

    /**
    * Confirms the new password
    */
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

    /**
    * Checks the validity of the current password
    */
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

    /**
    * Resets the password fields: current password, new password and confirm password
    */
        public void resetPasswordFields()
        {
            currentPasswordField.setText("");
            newPasswordField.setText("");
            confirmPasswordField.setText("");
            changePasswordErrorField.setText("");
        }
}
