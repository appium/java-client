/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
	public static <T> T getEnhancedProxy(Class<T> requiredClazz, MethodInterceptor interceptor){
		return (T) Enhancer.create(requiredClazz, interceptor);
	}

}
