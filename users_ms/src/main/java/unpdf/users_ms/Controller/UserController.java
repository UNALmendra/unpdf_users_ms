package unpdf.users_ms.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import unpdf.users_ms.Model.User;
import unpdf.users_ms.POJO.TokenRes;
import unpdf.users_ms.Repository.UserRepository;
import unpdf.users_ms.Service.JwtService;

import com.unboundid.ldap.sdk.controls.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    /*
    @Autowired
    private LdapTemplate ldap;
    */
    @GetMapping("hello")
    public String hello(){
        return "Hello fellow user!";
    }

    @PostMapping("signin")
    public TokenRes signIn(@RequestParam String username, @RequestParam String password){
        if(username.equals("")){
            return new TokenRes(false,"Empty username");
        }
        if(password.equals("")){
            return new TokenRes(false,"Empty password");
        }
        //LDAP TODO
        if(true){
            Optional<User> foundUser = userRepository.findByUsername(username);
            if(foundUser.isEmpty()){
                return new TokenRes(false,"User not found on database");
            }
            if(bCryptPasswordEncoder.matches(password,((User) foundUser.get()).getPassword())) {
                return new TokenRes(true,"Successfull sign in", jwtService.generateToken(foundUser.get()));
            }else{
                return new TokenRes(false,"Incorrect password");
            }
        }
        return new TokenRes(false,"User not found on LDAP");
    }

    @PostMapping("signup")
    public String signUp(@RequestParam String username, @RequestParam String password){
        if(username.equals("")){
            return "Empty username";
        }
        if(password.equals("")){
            return "Empty password";
        }
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        User newUser = new User(username,encodedPassword);
        userRepository.save(newUser);
        return "Successful Sign up, welcome user "+username+" ";
    }

    @PostMapping("verifyToken")
    public boolean verifyToken(@RequestParam String token){
        return jwtService.validateToken(token);
    }

    /*
    @GetMapping
    public String hello(Authentication authentication) {
        return "Hello, " + authentication.getName();
    }

    @GetMapping("/cn")
    public List<String> cn(Authentication authentication) {
        AttributesMapper<String> mapper = (attrs) -> attrs.get("cn").get().toString();
        return this.ldap.search("ou=people", "uid=" + authentication.getName(), mapper);
    }
    */
}
