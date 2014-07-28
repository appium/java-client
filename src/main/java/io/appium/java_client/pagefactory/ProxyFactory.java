package io.appium.java_client.pagefactory;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * Original class is a super class of a 
 * proxy object here
 */
abstract class ProxyFactory {

	private ProxyFactory() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	static <T extends Object> T getEnhancedProxy(Class<T> requiredClazz, MethodInterceptor interceptor){
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(requiredClazz);
		enhancer.setCallback(interceptor);
		return (T) enhancer.create(new Class<?>[] {}, new Object[] {});
	}

}
