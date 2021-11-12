package ${package}.controller;

import ${package}.annotation.Log;
import ${package}.base.BaseUtils;
import ${package}.service.${module}Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequiredArgsConstructor
public class ${module}Controller {
    private final ${module}Service ${module?uncap_first}Service;
${controllerMethod}
}