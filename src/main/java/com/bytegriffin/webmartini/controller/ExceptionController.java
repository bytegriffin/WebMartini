package com.bytegriffin.webmartini.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * 此类目的是为了方便在页面级获取到
 * status,message,exception,trace等信息
 */
@Controller
@EnableConfigurationProperties({ ServerProperties.class })
public class ExceptionController implements ErrorController {
	
	private ErrorAttributes errorAttributes;

	private static final String ERROR_PATH = "/error";

    @Autowired
    private ServerProperties serverProperties;
    

	@RequestMapping("/error/500")
	public String error500(HttpServletRequest request) {
		Exception ex = (Exception) request.getAttribute("exception");
		request.setAttribute("message", ex.getMessage());
		return "error/500";
	}

	@RequestMapping("/error/404")
	public String error404(HttpServletRequest request, HttpServletResponse response) {
//		response.setStatus(getStatus(request).value());
//        Map<String, Object> model = getErrorAttributes(request,
//                isIncludeStackTrace(request, MediaType.TEXT_HTML));
//		User user = WebHelper.getUserFromSession();
        return "error/404";
	}

	@RequestMapping("/error/403")
	public ModelAndView error403(HttpServletRequest request, HttpServletResponse response) {
		response.setStatus(getStatus(request).value());
        Map<String, Object> model = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.TEXT_HTML));
        return new ModelAndView("error/403", model);
	}

	@RequestMapping("/error/405")
	public ModelAndView error405(HttpServletRequest request, HttpServletResponse response) {
		response.setStatus(getStatus(request).value());
        Map<String, Object> model = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.TEXT_HTML));
        return new ModelAndView("error/405", model);
	}

	@RequestMapping("/error/401")
	public ModelAndView error401(HttpServletRequest request, HttpServletResponse response) {
		response.setStatus(getStatus(request).value());
        Map<String, Object> model = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.TEXT_HTML));
        return new ModelAndView("error/401", model);
	}

	@RequestMapping("/error/400")
	public ModelAndView error400(HttpServletRequest request, HttpServletResponse response) {
		response.setStatus(getStatus(request).value());
        Map<String, Object> model = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.TEXT_HTML));
        return new ModelAndView("error/400", model);
	}

	@RequestMapping("/error/440")
	public ModelAndView error440(HttpServletRequest request, HttpServletResponse response) {
		response.setStatus(getStatus(request).value());
        Map<String, Object> model = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.TEXT_HTML));
        return new ModelAndView("error/440", model);
	}

	protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
		ErrorProperties.IncludeStacktrace include = this.serverProperties.getError().getIncludeStacktrace();
		if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
			return true;
		}
		if (include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM) {
			return getTraceParameter(request);
		}
		return false;
	}

	/**
	 * 获取错误的信息
	 * 
	 * @param request
	 * @param includeStackTrace
	 * @return
	 */
	private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		return this.errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
	}

	/**
	 * 是否包含trace
	 * 
	 * @param request
	 * @return
	 */
	private boolean getTraceParameter(HttpServletRequest request) {
		String parameter = request.getParameter("trace");
		if (parameter == null) {
			return false;
		}
		return !"false".equals(parameter.toLowerCase());
	}

	private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return ERROR_PATH;
	}

}
