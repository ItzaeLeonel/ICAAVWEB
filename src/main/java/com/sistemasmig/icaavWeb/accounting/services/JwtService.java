package com.sistemasmig.icaavWeb.accounting.services;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.sistemasmig.icaavWeb.accounting.utils.Utils;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final String secretKey = Utils.generateSecretKey();
    private final long expirationTime = 3600000;
    private final JWSVerifier verifier;

    public JwtService() throws JOSEException {
        this.verifier = new MACVerifier(secretKey);
    }

    public String generateToken(String username) {
        try {
            Date now = new Date();
            Date expiration = new Date(now.getTime() + expirationTime);

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(username)
                    .issueTime(now)
                    .expirationTime(expiration)
                    .claim("sub", username)
                    .claim("iat", now)
                    .build();

            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256).build();
            Payload payload = new Payload(claimsSet.toJSONObject());

            JWSObject jwsObject = new JWSObject(header, payload);

            JWSSigner signer = new MACSigner(secretKey);
            jwsObject.sign(signer);

            return jwsObject.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            return jwsObject.verify(verifier);
        } catch (JOSEException | java.text.ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}