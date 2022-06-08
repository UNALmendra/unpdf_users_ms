package unpdf.users_ms.Model;



import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String username;
    private String password;

    public User(String username,String password) {
        this.username = username;
        this.password = password;
    }

    // contructors
    // getters
    // setters

}