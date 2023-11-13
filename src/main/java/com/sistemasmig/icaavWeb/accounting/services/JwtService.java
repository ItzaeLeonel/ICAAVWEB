package com.sistemasmig.icaavWeb.accounting.services;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.sistemasmig.icaavWeb.accounting.entity.Usuario;
import com.sistemasmig.icaavWeb.accounting.model.TokenDetalles;
import com.sistemasmig.icaavWeb.accounting.utils.Constantes;
import com.sistemasmig.icaavWeb.accounting.utils.Utils;

import java.util.Date;
import java.util.jar.JarException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final String secretKey = Utils.generateSecretKey();
    private final long expirationTime = 3600000; //
    private final JWSVerifier verifier;
    
    @Autowired
    UsuarioService usuarioService;

    public JwtService() throws JOSEException {
        this.verifier = new MACVerifier(secretKey);
    }

    public String generateToken(String username) {
        try {
        	
            Date now = new Date();
            Date expiration = new Date(now.getTime() + expirationTime);
            
            Usuario usuario = usuarioService.findAll().stream()
                    .filter(c -> c.getUsuario().equals(username))
                    .findFirst()
                    .orElse(new Usuario());
            usuario.setId(1);
            usuario.setUsuario(username);
            
            if(usuario == null) {
            	return null;
            }
            
            TokenDetalles tokenDetalles = usuario.toTokenDetalles();
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(username)
                    .issueTime(now)
                    .expirationTime(expiration)
                    .claim(Constantes.TOKEN_SUB, tokenDetalles)
                    .claim(Constantes.TOKEN_IAT, now)
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

    public String validateToken(String token) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            if (jwsObject.verify(verifier)) {
                return jwsObject.getPayload().toString();
            }
        } catch (JOSEException | java.text.ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}