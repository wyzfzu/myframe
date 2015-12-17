package com.myframe.core.util;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.beetl.core.resource.WebAppResourceLoader;
import org.slf4j.Logger;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

/**
 * 模板工具类。
 *
 * 基于Beetl。
 * 配置信息可查看org/beetl/core/beetl-default.properties。
 * 需要覆盖配置，可在类路径下编写beetl.properties文件，配置相应属性。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class TemplateUtils {
    private static final Logger logger = LogUtils.get();
    private static GroupTemplate gt;

    static {
        try {
            gt = new GroupTemplate(Configuration.defaultConfiguration());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("初始化GroupTemplate出错！");
        }
    }

    public static StringTemplateResourceLoader stringLoader() {
        return new StringTemplateResourceLoader();
    }

    public static ClasspathResourceLoader classpathLoader() {
        return new ClasspathResourceLoader();
    }

    public static WebAppResourceLoader webAppLoader() {
        return new WebAppResourceLoader();
    }

    public static String render(String template, Map<String, Object> bindings) {
        return render(template, bindings, gt.getResourceLoader());
    }

    public static String render(String template, Map<String, Object> bindings,
                                ResourceLoader resourceLoader) {
        Template t = gt.getTemplate(template, resourceLoader);
        t.binding(bindings);
        return t.render();
    }

    public static void render(String templateFile, Map<String, Object> bindings,
                              OutputStream target) {
        render(templateFile, bindings, target, gt.getResourceLoader());
    }

    public static void render(String templateFile, Map<String, Object> bindings,
                              OutputStream target, ResourceLoader resourceLoader) {
        render(templateFile, bindings, StreamUtils.toBufferedWriter(target), resourceLoader);
    }

    public static void render(String template, Map<String, Object> bindings,
                              Writer target) {
        render(template, bindings, target, gt.getResourceLoader());
    }

    public static void render(String template, Map<String, Object> bindings,
                              Writer target, ResourceLoader resourceLoader) {
        Template t = gt.getTemplate(template, resourceLoader);
        t.binding(bindings);
        t.renderTo(target);
    }
}
