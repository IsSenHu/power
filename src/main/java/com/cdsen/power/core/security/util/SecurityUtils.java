package com.cdsen.power.core.security.util;

import com.cdsen.power.core.security.model.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author HuSen
 */
public class SecurityUtils {

	public static String currentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null == authentication) {
			return null;
		}

		Session session = (Session) authentication.getPrincipal();
		if (null == session) {
			return null;
		}

		return session.getUsername();
	}

	public static Session currentSession() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null == authentication) {
			return null;
		}

		return (Session) authentication.getPrincipal();
	}
}