package org.ees.api.agenda.infra.auth;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/**
 * Created by silvanei on 24/07/16.
 */


public class Digest {

    private static final Logger LOG = Logger.getLogger("Digest");

    public static String generate(String value)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(value.getBytes("UTF-8"));

            byte[] byteData = md.digest();
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);

                if (hex.length() == 1) {
                    sb.append('0');
                }

                sb.append(hex);
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            LOG.warning("No such algorithm.");
            return null;
        } catch (UnsupportedEncodingException ex) {
            LOG.warning("Usupported encoding.");
            return null;
        }
    }
}
