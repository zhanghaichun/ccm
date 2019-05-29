package com.saninco.ccm.filter;

import java.lang.reflect.Method;
import java.util.List;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.saninco.ccm.util.SystemUtil;

public class AuthorizeInterceptor implements Interceptor {
	
	private static final long serialVersionUID = 1L;
	private Class<?>[] parameterTypes =  new Class[] {};;
	private Class<AllGranted> AllGrantedClass = AllGranted.class;
	private Class<AnyGranted> AnyGrantedClass = AnyGranted.class;

	
	public void destroy() {
		
	}

	public void init() {
		
	}

	public String intercept(ActionInvocation arg0) throws Exception {
		Method method = arg0.getAction().getClass().getMethod(arg0.getProxy().getMethod(), parameterTypes);
		AllGranted allGranted = method.getAnnotation(AllGrantedClass);
		AnyGranted anyGranted = method.getAnnotation(AnyGrantedClass);
		
		if (allGranted == null && anyGranted == null) {
			return arg0.invoke();
		}else{
			if(allGranted != null ){
				String all = allGranted.value();
				String[] alls = all.split(",");
				List<String> authorities = SystemUtil.getCurrentUserAuthorities();
				for(String a : alls){
					if(!authorities.contains(a)){
						return "input";
					}
				}
			}else{
				String any = anyGranted.value();
				String[] anys = any.split(",");
				List<String> authorities = SystemUtil.getCurrentUserAuthorities();
				boolean flag = false; 
				for(String a : anys){
					if(authorities.contains(a)){
						flag = true;
						break;
					}
				}
				if(!flag){
					return "input";
				}
			}
			return arg0.invoke();
		}
		
	}

	
}