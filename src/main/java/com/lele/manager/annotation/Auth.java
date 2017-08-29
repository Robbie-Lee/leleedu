package com.lele.manager.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface Auth {
	public enum AuthType{
		PAGE("page", 0), INTERFACE("interface", 1);
		
		private String authName;
		private int authIndex;
		
		AuthType(String authName, int authIndex) {
			this.authName = authName;
			this.authIndex = authIndex;
		}
		
		public int getIndex() {
			return this.authIndex;
		}
		
		public String getName() {
			return this.authName;
		}
/*		
		public boolean equals(AuthType authType) {
			if (this.authName.equals(authType.authName) && this.authIndex == authType.authIndex) {
				return true;
			}
			else {
				return false;
			}
		}
*/	
	};
	
	AuthType auth() default AuthType.PAGE;
	String description() default "Default Description";
}
