package by.it_academy.jd2.user_service.controller;


import by.it_academy.jd2.user_service.dto.UserDto;
import by.it_academy.jd2.user_service.dto.UserLoginDto;
import by.it_academy.jd2.user_service.dto.UserRegistrationDto;
import by.it_academy.jd2.user_service.dto.VerificationDto;
import by.it_academy.jd2.user_service.service.api.IAuthenticationService;
import by.it_academy.jd2.user_service.service.api.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cabinet")
public class CabinetController {

    private final IAuthenticationService authenticationService;
    private final IUserService userService;

    public CabinetController(IAuthenticationService authenticationService, IUserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void registration(@Valid @RequestBody UserRegistrationDto registrationDto) {
        authenticationService.registerUser(registrationDto);
    }

    @GetMapping("/verification")
    public void verification(@Valid VerificationDto verificationDto) {
        userService.verify(verificationDto);
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@Valid @RequestBody UserLoginDto userLoginDTO) {
        return  authenticationService.getToken(userLoginDTO);
    }

    @GetMapping("/me")
    public UserDto me() {
        return userService.getMe();
    }
}
