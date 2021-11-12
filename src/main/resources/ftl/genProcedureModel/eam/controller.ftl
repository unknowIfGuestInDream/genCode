package ${package}.controller;

import ${package}.annotation.Log;
import ${package}.base.BaseUtils;
import ${package}.service.${module}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author: ${author}
 * @date: ${date}
 * @since: 1.0
 */
@Controller
public class ${module}Controller {
    @Autowired
    private ${module}Service ${module?uncap_first}Service;
${controllerMethod}
}