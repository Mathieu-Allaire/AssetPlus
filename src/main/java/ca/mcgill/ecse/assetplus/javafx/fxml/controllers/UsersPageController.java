package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.sql.Date;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFxmlView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;

public class UsersPageController {
  @FXML private TextField createEmailTextField;
  @FXML private TextField createNameTextField;
  @FXML private TextField createPhoneNumberTextField;
  @FXML private TextField createUserPasswordTextField;
  @FXML private TextField confirmUserPasswordTextField;
  @FXML private Button createGuestButton;
  @FXML private Button createEmployeeButton;

  @FXML
  // Event Listener on Button[#createGuestButton].onAction
  public void createGuestClicked(ActionEvent event) {
    String createAccountString = createEmailTextField.getText();
    String createNameString = createNameTextField.getText();
    String createPhoneNumberString = createPhoneNumberTextField.getText();
    String createUserPasswordString = createUserPasswordTextField.getText();
    String confirmUserPasswordString = confirmUserPasswordTextField.getText();
    Boolean isEmployee = false;

    if (createAccountString == null){
      ViewUtils.showError("Email cannot be empty");
      return;
    }
    if(createNameString == null){
      createNameString = "";
    }
    if(createPhoneNumberString == null){
      createPhoneNumberString = "";
    }
    if(createUserPasswordString == null){
      ViewUtils.showError("Password cannot be empty");
      return;
    }
    if(confirmUserPasswordString == null){
      ViewUtils.showError("Confirm Password cannot be empty");
      return;
    }
    
    else if(!createUserPasswordString.equals(confirmUserPasswordString)){
      ViewUtils.showError("Password must be equal to Confirm Password");
      return;
    }
    
    else{
      String errorMessage = AssetPlusFeatureSet1Controller.addEmployeeOrGuest(createAccountString,createUserPasswordString,
      createNameString,createPhoneNumberString,isEmployee);
      if(!errorMessage.isEmpty()){
        ViewUtils.showError(errorMessage);
        return;
      }
      AssetPlusFxmlView.getInstance().refresh();
      return;
    }
  }

  @FXML
  // Event Listener on Button[#createEmployeeButton].onAction
  public void createEmployeeClicked(ActionEvent event) {
    String createAccountString = createEmailTextField.getText();
    String createNameString = createNameTextField.getText();
    String createPhoneNumberString = createPhoneNumberTextField.getText();
    String createUserPasswordString = createUserPasswordTextField.getText();
    String confirmUserPasswordString = confirmUserPasswordTextField.getText();
    Boolean isEmployee = true;

    if (createAccountString == null){
      ViewUtils.showError("Email cannot be empty");
      return;
    }
    if(createNameString == null){
      createNameString = "";
    }
    if(createPhoneNumberString == null){
      createPhoneNumberString = "";
    }
    if(createUserPasswordString == null){
      ViewUtils.showError("Password cannot be empty");
      return;
    }
    if(confirmUserPasswordString == null){
      ViewUtils.showError("Confirm Password cannot be empty");
      return;
    }
    else if(!createUserPasswordString.equals(confirmUserPasswordString)){
      ViewUtils.showError("Password must be equal to Confirm Password");
      return;
    }
   

    else{
      String errorMessage = AssetPlusFeatureSet1Controller.addEmployeeOrGuest(createAccountString,createUserPasswordString,
      createNameString,createPhoneNumberString,isEmployee);
      if(!errorMessage.isEmpty()){
        ViewUtils.showError(errorMessage);
        return;
      }
      AssetPlusFxmlView.getInstance().refresh();
      return;
    }
  }

  @FXML private TextField findEmailTextField;
  @FXML private TextField updateNameTextField;
  @FXML private TextField updatePhoneNumberTextField;
  @FXML private TextField confirmOldUserPasswordTextField;
  @FXML private TextField updateUserPasswordTextField;
  @FXML private TextField confirmNewUserPasswordTextField;
  @FXML private Button updateGuestOrEmployeeButton;
  @FXML private Button deleteGuestOrEmployeeButton;

  @FXML
  // Event Listener on Button[#updateGuestOrEmployeeButton].onAction
  public void updateGuestOrEmployeeClicked(ActionEvent event) {
    String findEmailString = findEmailTextField.getText();
    String updateNameString = updateNameTextField.getText();
    String updatePhoneNumberString = updatePhoneNumberTextField.getText();
    String confirmOldUserPasswordString = confirmOldUserPasswordTextField.getText();
    String updateUserPasswordString = updateUserPasswordTextField.getText();
    String confirmNewUserPasswordString = confirmNewUserPasswordTextField.getText();

    if(findEmailString == null || findEmailString.isEmpty()){
      ViewUtils.showError("The account email cannot be empty");
      return;
    }
    if(updateNameString == null){
      updateNameString = "";
    }
    if(updatePhoneNumberString == null){
      updateNameString = "";
    }
    if(confirmOldUserPasswordString == null){
      ViewUtils.showError("The old user Password cannot be empty");
      return;
    }
    if(updateUserPasswordString == null){
      ViewUtils.showError("The new user password cannot be empty");
      return;
    }
    if(confirmNewUserPasswordString == null){
      ViewUtils.showError("The confirm new user password cannot be empty");
      return;
    }
    else if(!confirmNewUserPasswordString.equals(updateUserPasswordString)){
      ViewUtils.showError("The new user password must be equal to the confirm new user password");
    }
    else{

      String errorMessage = AssetPlusFeatureSet1Controller.updateEmployeeOrGuest(findEmailString,updateUserPasswordString,updateNameString,updatePhoneNumberString);
      if(!errorMessage.isEmpty()){
        ViewUtils.showError(errorMessage);
        return;
      }
      AssetPlusFxmlView.getInstance().refresh();
      return;
    }
  }

  @FXML
  // Event Listener on Button[#deleteGuestOrEmployeeButton].onAction
  public void deleteGuestOrEmployeeClicked(ActionEvent event) {
    String findEmailString = findEmailTextField.getText();
    
    if(findEmailString == null || findEmailString.isEmpty()){
      ViewUtils.showError("The account email cannot be empty");
      return;
    }
    if(!AssetPlusFeatureSet6Controller.hasUser(findEmailString)){
      ViewUtils.showError("This user does not exist");
      return;
    }
    else{
      AssetPlusFeatureSet6Controller.deleteEmployeeOrGuest(findEmailString);
      AssetPlusFxmlView.getInstance().refresh();
      return;
    }
  }

  @FXML private TextField confirmOldManagerPasswordTextField;
  @FXML private TextField updateManagerPasswordTextField;
  @FXML private TextField confirmNewManagerPasswordTextField;
  @FXML private Button updateManagerButton;

  @FXML
    // Event Listener on Button[#updateManagereButton].onAction
    public void updateManagerClicked(ActionEvent event){
      String confirmOldManagerPasswordTextString = confirmOldManagerPasswordTextField.getText();
      String updateManagerPasswordString = updateManagerPasswordTextField.getText();
      String confirmNewManagerPasswordString = confirmNewManagerPasswordTextField.getText();

      if(confirmOldManagerPasswordTextString == null){
        ViewUtils.showError("The old manager password cannot be empty");
        return;
      }
      if(updateManagerPasswordString == null){
        ViewUtils.showError("The new password cannot be empty");
        return;
      }
      if(confirmNewManagerPasswordString == null){
        ViewUtils.showError("The confirm new password cannot be empty");
        return;
      }
      if(!updateManagerPasswordString.equals(confirmNewManagerPasswordString)){
        ViewUtils.showError("The new password must be equal to the confirm new password");
        return;
      }
      String errorString = AssetPlusFeatureSet1Controller.updateManager(updateManagerPasswordString);
      if (!errorString.isEmpty()){
        ViewUtils.showError(errorString);
      }
      AssetPlusFxmlView.getInstance().refresh();
      return;


    }
  


}
