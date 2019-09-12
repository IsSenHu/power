package com.cdsen.power.core.security.util;

import com.cdsen.power.core.security.model.Session;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author HuSen
 */
public class SecurityUtils {

	@NonNull
	public static String currentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Session session = (Session) authentication.getPrincipal();
		return session.getUsername();
	}

	@NonNull
	public static Session currentSession() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (Session) authentication.getPrincipal();
	}
}