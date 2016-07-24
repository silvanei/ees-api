package org.ees.api.agenda.resource;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.joda.time.DateTime;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() throws JOSEException {

        JWTClaimsSet claim = new JWTClaimsSet();
        claim.setSubject("1");
        claim.setIssuer("host");
        claim.setIssueTime(DateTime.now().toDate());
        claim.setExpirationTime(DateTime.now().plusDays(1).toDate());
        claim.setCustomClaim("user", "Silvanei Soares Santos");
        claim.setCustomClaim("roles", "Roles");

        JWSSigner signer = new MACSigner("adsiinwonderland**");
        SignedJWT jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claim);
        jwt.sign(signer);

        System.out.println(jwt.serialize());

        return "Got it!";
    }
}
