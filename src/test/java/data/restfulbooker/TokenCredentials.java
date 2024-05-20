package data.restfulbooker;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class TokenCredentials {

    private String username;
    private String password;

}
