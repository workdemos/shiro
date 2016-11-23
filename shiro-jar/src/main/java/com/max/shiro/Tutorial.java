package com.max.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tutorial {

	// log4j2
	// static final Logger logger =
	// LogManager.getLogger(Tutorial.class.getName());

	private static final transient Logger logger = LoggerFactory.getLogger(Tutorial.class);

	public static void main(String[] args) {
		System.out.println("start....");
		logger.info("My First Apache Shiro Application");

		// 1.
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");

		// 2.
		SecurityManager securityManager = factory.getInstance();

		// 3.
		SecurityUtils.setSecurityManager(securityManager);

		// get the currently executing user:
		Subject currentUser = SecurityUtils.getSubject();

		// Do some stuff with a Session (no need for a web or EJB container!!!)
		Session session = currentUser.getSession();
		session.setAttribute("someKey", "aValue");
		String value = (String) session.getAttribute("someKey");
		if (value.equals("aValue")) {
			logger.info("Retrieved the correct value! [" + value + "]");
		}

		// let's login the current user so we can check against roles and
		// permissions:
		if (!currentUser.isAuthenticated()) {
			UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
			token.setRememberMe(true);
			try {
				currentUser.login(token);
			} catch (UnknownAccountException uae) {
				logger.info("There is no user with username of " + token.getPrincipal());
			} catch (IncorrectCredentialsException ice) {
				logger.info("Password for account " + token.getPrincipal() + " was incorrect!");
			} catch (LockedAccountException lae) {
				logger.info("The account for username " + token.getPrincipal() + " is locked.  "
						+ "Please contact your administrator to unlock it.");
			}
			// ... catch more exceptions here (maybe custom ones specific to
			// your application?
			catch (AuthenticationException ae) {
				// unexpected condition? error?
			}
		}

		// say who they are:
		// print their identifying principal (in this case, a username):
		logger.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");

		// test a role:
		if (currentUser.hasRole("schwartz")) {
			logger.info("May the Schwartz be with you!");
		} else {
			logger.info("Hello, mere mortal.");
		}

		// test a typed permission (not instance-level)
		if (currentUser.isPermitted("lightsaber:weild")) {
			logger.info("You may use a lightsaber ring.  Use it wisely.");
		} else {
			logger.info("Sorry, lightsaber rings are for schwartz masters only.");
		}

		// a (very powerful) Instance Level permission:
		if (currentUser.isPermitted("winnebago:drive:eagle5")) {
			logger.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  "
					+ "Here are the keys - have fun!");
		} else {
			logger.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
		}

		// all done - log out!
		currentUser.logout();

		System.exit(0);
	}
}