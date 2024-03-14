package com.example.had_backend.Radiologist.Controller;

import com.example.had_backend.Email.EmailService;
import com.example.had_backend.Model.LoginDTO;
import com.example.had_backend.Model.LoginMessage;
import com.example.had_backend.Radiologist.Entity.Radiologist;
import com.example.had_backend.Radiologist.Entity.RadiologistL;
import com.example.had_backend.Radiologist.Model.RadiologistChangePasswordDTO;
import com.example.had_backend.Radiologist.Model.RadiologistRegistrationDTO;
import com.example.had_backend.Radiologist.Service.RadiologistService;
import com.example.had_backend.WebSecConfig.UserAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RadiologistController {

    @Autowired
    private RadiologistService radiologistService;

    @Autowired
    private UserAuthProvider userAuthProvider;

    @Autowired
    private EmailService emailService;

    @CrossOrigin
    @PostMapping("/radiologist/login")
    public ResponseEntity<LoginMessage> login(@RequestBody @Validated LoginDTO login) {
        RadiologistL radiologistL = radiologistService.authenticate(login);
        LoginMessage message = new LoginMessage();
        if(radiologistL.getRadiologistId() != null){
            message.setMessage("Login Successful");
            message.setToken(userAuthProvider.createToken(radiologistL.getUserName()));
        }else{
            message.setMessage("Login failed, Check username/password");
        }
        return ResponseEntity.ok(message);
    }

    @CrossOrigin
    @PostMapping("/radiologist/register")
    public ResponseEntity<LoginMessage> register(@RequestBody @Validated RadiologistRegistrationDTO radiologistRegistrationDTO) {
        LoginMessage loginMessage = radiologistService.register(radiologistRegistrationDTO);
        if(!loginMessage.getMessage().equals("User is already registered")){
            emailService.sendSimpleMessage(
                    radiologistRegistrationDTO.getEmail(),
                    "Registration in Kavach portal was successful",
                    "Username: "+radiologistRegistrationDTO.getUserName()+ "\n"+"Password: "+radiologistRegistrationDTO.getPassword());
        }
        return ResponseEntity.ok(loginMessage);
    }

    @CrossOrigin
    @PostMapping("/radiologist/getProfileDetails")
    public ResponseEntity<Radiologist> getProfileDetails(@RequestBody @Validated Radiologist radiologist) {
        Radiologist radiologist1 = radiologistService.profile(radiologist);
        return ResponseEntity.ok(radiologist1);
    }

    @CrossOrigin
    @PostMapping("/radiologist/changePassword")
    public ResponseEntity<LoginMessage> changePassword(@RequestBody @Validated RadiologistChangePasswordDTO radiologistChangePasswordDTO ) {
        LoginMessage loginMessage1 = radiologistService.changePassword(radiologistChangePasswordDTO);
        if(loginMessage1.getMessage().equals("Password updated successfully")){
            //will get change after applying join between tables as email has been hardcoded
            emailService.sendSimpleMessage(
                    "roy.sunnidhya96@gmail.com",
                    "Password has been changed successfully",
                    "Username: "+radiologistChangePasswordDTO.getUserName()+ "\n"+"Password: "+radiologistChangePasswordDTO.getNewPassword());
        }
        return ResponseEntity.ok(loginMessage1);
    }
}
