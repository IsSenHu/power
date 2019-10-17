package com.cdsen.power.core.security.util;

import com.cdsen.power.core.security.model.UserDetailsImpl;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author HuSen
 */
public class SecurityUtils {

	@NonNull
	public static String currentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return userDetails.getUsername();
	}

	@NonNull
	public static UserDetailsImpl currentUserDetails() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			return (UserDetailsImpl) authentication.getPrincipal();
		} catch (Exception e) {
			return UserDetailsImpl.empty();
		}
	}
}