package com.finapp.tests.configs.jwtoauth;

import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.store.JwtClaimsSetVerifier;

import java.util.Map;

/**
 * Class name: CustomClaimVerifier
 * Creater: wgicheru
 * Date:6/21/2019
 */
public class CustomClaimVerifier  implements JwtClaimsSetVerifier {
    @Override
    public void verify(Map<String, Object> claims) throws InvalidTokenException {
        final String username = (String) claims.get("user_name"),clientid=(String)claims.get("client_id");
        if ((username == null) || (username.length() == 0) ) {
            throw new InvalidTokenException("user_name claim is empty");
        }else if( !clientid.equals("finmanager_clientid_130")){
            throw new InvalidTokenException("invalid issuer");
        }
    }
}
