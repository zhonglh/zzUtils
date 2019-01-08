package com.zz.bms.util.spring;

import com.zz.bms.util.web.ThreadLocalRequestHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Administrator
 */
public class ContextHolderUtils {
	/**
	 * SpringMvc下获取request
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		
		if(RequestContextHolder.getRequestAttributes() == null) {
			return ThreadLocalRequestHolder.getRequests();
		}else {
			return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		}
		

	}

}
