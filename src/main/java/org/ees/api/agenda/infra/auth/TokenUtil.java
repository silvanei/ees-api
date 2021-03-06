package org.ees.api.agenda.infra.auth;

import java.text.ParseException;

import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.entity.Funcionario;
import org.ees.api.agenda.infra.filter.SecurityFilter;
import org.joda.time.DateTime;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import javax.ws.rs.ForbiddenException;

/**
 * Created by silvanei on 24/07/16.
 */
public final class TokenUtil {

    private static final JWSHeader JWT_HEADER = new JWSHeader(JWSAlgorithm.HS256);
    private static final String TOKEN_SECRET = "adsiinwonderland**";
    public static final String AUTH_HEADER_KEY = "Authorization";

    public static String getSubject(String authHeader) throws ParseException, JOSEException {
        return decodeToken(authHeader).getSubject();
    }

    public static Integer getSla(String authHeader) throws ParseException, JOSEException {
        return Integer.parseInt(decodeToken(authHeader).getCustomClaim(Parameters.SLA.toString()).toString());
    }

    public static Integer getCli(String authHeader) throws ParseException, JOSEException {
        return Integer.parseInt(decodeToken(authHeader).getCustomClaim(Parameters.CLI.toString()).toString());
    }

    public static ReadOnlyJWTClaimsSet decodeToken(String authHeader) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(getSerializedToken(authHeader));
        if (signedJWT.verify(new MACVerifier(TOKEN_SECRET))) {
            return signedJWT.getJWTClaimsSet();
        }

        throw new JOSEException("Signature verification failed");
    }

    public static Token createToken(Acesso acesso) throws JOSEException {
        JWTClaimsSet claim = new JWTClaimsSet();
        claim.setSubject(Integer.toString(acesso.getId()));
        claim.setIssueTime(DateTime.now().toDate());
        claim.setExpirationTime(DateTime.now().plusMinutes(10).toDate());
        claim.setCustomClaim(Parameters.SLA.toString(), acesso.getSalaoId());
        claim.setCustomClaim(Parameters.CLI.toString(), acesso.getClienteId());
        claim.setCustomClaim(Parameters.PERFIL.toString(), acesso.getPerfil());

        JWSSigner signer = new MACSigner(TOKEN_SECRET);
        SignedJWT jwt = new SignedJWT(JWT_HEADER, claim);
        jwt.sign(signer);

        return new Token(jwt.serialize());
    }

    public static String getSerializedToken(String authHeader) {
        return authHeader.split(" ")[1];
    }

    public static void permissionSla(String authString, Integer salaoId) {
        if (null == authString) {
            throw new ForbiddenException(SecurityFilter.FORBIDDEN_ERROR_MSG);
        }

        try {
            if (! TokenUtil.getSla(authString).equals(salaoId)) {
                throw new ForbiddenException(SecurityFilter.FORBIDDEN_ERROR_MSG);
            }
        } catch (ParseException e) {
            throw new ForbiddenException(e.getMessage());
        } catch (JOSEException e) {
            throw new ForbiddenException(e.getMessage());
        }
    }

    public static void permissionCli(String authString, Integer clienteId) {
        if (null == authString) {
            throw new ForbiddenException(SecurityFilter.FORBIDDEN_ERROR_MSG);
        }

        try {
            if (! TokenUtil.getCli(authString).equals(clienteId)) {
                throw new ForbiddenException(SecurityFilter.FORBIDDEN_ERROR_MSG);
            }
        } catch (ParseException e) {
            throw new ForbiddenException(e.getMessage());
        } catch (JOSEException e) {
            throw new ForbiddenException(e.getMessage());
        }
    }
}
