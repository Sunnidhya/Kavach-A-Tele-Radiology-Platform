package com.example.had_backend.Radiologist.Service;

import com.example.had_backend.Model.LoginDTO;
import com.example.had_backend.Model.LoginMessage;
import com.example.had_backend.Radiologist.Entity.Radiologist;
import com.example.had_backend.Radiologist.Entity.RadiologistL;
import com.example.had_backend.Radiologist.Model.RadiologistChangePasswordDTO;
import com.example.had_backend.Radiologist.Model.RadiologistRegistrationDTO;
import com.example.had_backend.Radiologist.Repository.IRadiologistLoginRepository;
import com.example.had_backend.Radiologist.Repository.IRadiologistRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RadiologistService {

    @Autowired
    private IRadiologistLoginRepository iRadiologistLoginRepository;
    @Autowired
    private IRadiologistRegistrationRepository iRadiologistRegistrationRepository;

    public RadiologistL authenticate(LoginDTO loginDTO) {
        RadiologistL radiologistL = new RadiologistL();
        try {
            radiologistL = iRadiologistLoginRepository.findByEmailAndPassword(loginDTO.getUserName() , loginDTO.getPassword());
            return radiologistL;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return radiologistL;
    }

    public LoginMessage register(RadiologistRegistrationDTO radiologistRegistrationDTO) {

        Radiologist radiologist = new Radiologist();
        RadiologistL radiologistL = new RadiologistL();

        Radiologist radiologist2=iRadiologistRegistrationRepository.getRadiologist(radiologistRegistrationDTO.getUserName(),radiologistRegistrationDTO.getEmail());
        if (radiologist2 != null) {
            LoginMessage loginMsg = new LoginMessage();
            loginMsg.setMessage("User is already registered");
            return loginMsg;
        }

        radiologist.setName(radiologistRegistrationDTO.getName());
        radiologist.setDegree(radiologistRegistrationDTO.getDegree());
        radiologist.setSpecialization(radiologistRegistrationDTO.getSpecialization());
        radiologist.setEmail(radiologistRegistrationDTO.getEmail());
        radiologist.setUserName(radiologistRegistrationDTO.getUserName());
        radiologist.setDepartment(radiologistRegistrationDTO.getDept());
        iRadiologistRegistrationRepository.save(radiologist);

        Radiologist radiologist1=iRadiologistRegistrationRepository.getRadiologist(radiologistRegistrationDTO.getUserName(),radiologistRegistrationDTO.getEmail());
        radiologistL.setRadiologistId(radiologist1.getRadiologistId());
        radiologistL.setUserName(radiologistRegistrationDTO.getUserName());
        radiologistL.setPassword(radiologistRegistrationDTO.getPassword());
        iRadiologistLoginRepository.save(radiologistL);

        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setMessage("Registration Successful");
        return loginMessage;
    }

    public Radiologist profile(Radiologist radiologist) {
        return iRadiologistRegistrationRepository.getProfile(radiologist.getUserName());
    }

    public LoginMessage changePassword(RadiologistChangePasswordDTO radiologistChangePasswordDTO) {
        RadiologistL radiologistL=iRadiologistLoginRepository.findByEmailAndPassword(radiologistChangePasswordDTO.getUserName(),radiologistChangePasswordDTO.getCurrentPassword());

        if (radiologistL == null) {
            LoginMessage loginMsg = new LoginMessage();
            loginMsg.setMessage("Current Password or User Name entered wrongly ");
            return loginMsg;
        } else if (radiologistChangePasswordDTO.getCurrentPassword().equals(radiologistChangePasswordDTO.getNewPassword())) {
            LoginMessage loginMessage = new LoginMessage();
            loginMessage.setMessage("Same Password entered");
            return loginMessage;
        }

        iRadiologistLoginRepository.changePassword(radiologistChangePasswordDTO.getUserName(),radiologistChangePasswordDTO.getNewPassword());
        LoginMessage loginMsg = new LoginMessage();
        loginMsg.setMessage("Password updated successfully");
        return loginMsg;
    }
}
