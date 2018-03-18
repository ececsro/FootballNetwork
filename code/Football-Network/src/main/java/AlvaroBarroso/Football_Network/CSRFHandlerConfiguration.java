package AlvaroBarroso.Football_Network;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Configuration
public class CSRFHandlerConfiguration extends WebMvcConfigurerAdapter {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CSRFHandlerInterceptor());
	}
}

class CSRFHandlerInterceptor extends HandlerInterceptorAdapter {
	final AntPathRequestMatcher[] requestMatchers = {
	          new AntPathRequestMatcher("/login"),
	          new AntPathRequestMatcher("/user"),
	          new AntPathRequestMatcher("/logout"),
	          new AntPathRequestMatcher("/signup"),
	          new AntPathRequestMatcher("/register"),
	          new AntPathRequestMatcher("/comment/**"),
	          new AntPathRequestMatcher("/player/**"),
	          new AntPathRequestMatcher("/"),
	          
	    };
	@Override
    public void postHandle(final HttpServletRequest request,
            final HttpServletResponse response, final Object handler,
            final ModelAndView modelAndView) throws Exception {
		
		

		CsrfToken token = (CsrfToken) request.getAttribute("_csrf"); 
		if(matches(request)) {
			System.out.println("SI");
			modelAndView.addObject("token", token.getToken());
		}
		/*
		if (request.getAttribute("_csrf")==null) {
			System.out.println("hola");
		}
		if (modelAndView==null) {
			System.out.println("adios");
		}
		if(token.getToken() != null) {
			System.out.println("token -- "+token.toString());
			modelAndView.addObject("token", token.getToken());
		}*/
		
    }
	public boolean matches(HttpServletRequest request) {
        // If the request match one url the CSFR protection will be enabled
        for (AntPathRequestMatcher rm : requestMatchers) {
          if (rm.matches(request)) { return true; }
        }
        return false;
      } 
}