package elements.spring.explore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserWithRoleService implements UserDetailsService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public UserWithRole signIp(UserWithRole userWithRole){
        jdbcTemplate.update("insert into dum_role_emp values(?,?,?)",new Object[]{userWithRole.getUsername(),userWithRole.getPassword(),userWithRole.getRole()});
        return userWithRole;
    }
    public UserWithRole lookUsername(String username){
        UserWithRole userWithRole = jdbcTemplate.queryForObject("select * from dum_role_emp where username=?",new Object[]{username},
                new BeanPropertyRowMapper<>(UserWithRole.class));
        return userWithRole;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserWithRole userWithRole=lookUsername(username);
        if(userWithRole==null){
            return (UserDetails) new UsernameNotFoundException("Invalid Username entered");
        }
        return userWithRole;
    }
}
