package org.ees.api.agenda.resource.bean;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ees.api.agenda.infra.auth.DigestUtil;

/**
 * Created by silvanei on 24/07/16.
 */
public class UserBean {

	private String email;
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		try {
			this.password = DigestUtil.generateDigest(password);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
			Logger.getLogger(RegistrarSalaoBean.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
