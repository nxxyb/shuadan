package com.photography.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.photography.controller.propertyeditor.DateEditor;
import com.photography.controller.propertyeditor.DoubleEditor;
import com.photography.controller.propertyeditor.FloatEditor;
import com.photography.controller.propertyeditor.IntegerEditor;
import com.photography.controller.propertyeditor.LongEditor;

/**
 * 
 * @author 徐雁斌
 * @since 2015-2-11
 */
public class BaseController {
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {

		binder.registerCustomEditor(Date.class, new DateEditor());
		binder.registerCustomEditor(int.class, new IntegerEditor());
		binder.registerCustomEditor(long.class, new LongEditor());
		binder.registerCustomEditor(double.class, new DoubleEditor());
		binder.registerCustomEditor(float.class, new FloatEditor());
	}

}
