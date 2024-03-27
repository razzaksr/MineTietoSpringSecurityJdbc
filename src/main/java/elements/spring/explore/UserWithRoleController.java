package elements.spring.explore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class UserWithRoleController {
    @Autowired
    UserWithRoleService service;
    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/sign")
    public UserWithRole signingUp(@RequestBody UserWithRole userWithRole){
        userWithRole.setPassword(encoder.encode(userWithRole.getPassword()));
        return service.signIp(userWithRole);
    }

}
