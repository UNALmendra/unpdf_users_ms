package unpdf.users_ms.POJO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRes {
    boolean success;
    String message;
    String token;

    public TokenRes(boolean success, String message, String token) {
        this.success = success;
        this.message = message;
        this.token = token;
    }

    public TokenRes(boolean success, String message){
        this.success = success;
        this.message = message;
    }
}
