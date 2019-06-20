package com.myframe.generator;

import com.google.common.collect.Lists;
import com.myframe.generator.config.GeneratorConfig;
import com.myframe.generator.config.JavaMapperConfig;
import com.myframe.generator.config.JavaModelConfig;
import com.myframe.generator.config.JdbcConfig;
import com.myframe.generator.config.SqlMapConfig;
import com.myframe.generator.config.TableConfig;
import com.myframe.generator.util.DbUtils;
import com.myframe.generator.util.Table;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.mapper.ClassAliasingMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 代码生成器类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public class Generator {
    private static final Logger logger = LoggerFactory.getLogger(Generator.class);
    public static final String MAPPER_JAVA_TEMPLATE = "/templates/mapper.java.tpl";
    public static final String JPA_MODEL_TEMPLATE = "/templates/model.java.tpl";
    public static final String MAPPER_XML_TEMPLATE = "/templates/mapper.xml.tpl";

    private String configFile;

    private static GroupTemplate gt;

    static {
        try {
            ResourceLoader resourceLoader = new ClasspathResourceLoader();
            Configuration cfg = Configuration.defaultConfiguration();

            gt = new GroupTemplate(resourceLoader, cfg);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public Generator(String configFile) {
        this.configFile = configFile;
    }

    private Template getTemplate(String tpl) {
        return gt.getTemplate(tpl);
    }

    private File createFile(String path) {
        File f = new File(path);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                logger.error("create file error: {}", e.getMessage(), e);
            }
        }
        return f;
    }

    private String prepareTargetPath(String targetDir, String targetPackage) throws Exception {
        String baseDir = targetDir.replace('\\', '/');
        String pkg = targetPackage.replace('.', '/');
        String fullPath = FilenameUtils.concat(baseDir, pkg);
        FileUtils.forceMkdir(new File(fullPath));

        return fullPath;
    }

    private void generateModel(List<Table> tables, Map<String, Set<String>> imports,
                               GeneratorConfig generatorConfig) throws Exception {
        JavaModelConfig javaModelConfig = generatorConfig.getJavaModelConfig();
        String fullPath = prepareTargetPath(javaModelConfig.getTargetDir(),
                javaModelConfig.getTargetPackage());
        logger.info("Model路径：{}", fullPath);
        String templateFilePath = javaModelConfig.getTemplateFilePath();
        String tpl = (StringUtils.isNotEmpty(templateFilePath)) ? templateFilePath : JPA_MODEL_TEMPLATE;
        final Template t = getTemplate(tpl);
        t.binding("pkg", javaModelConfig.getTargetPackage());
        for (Table tbl : tables) {
            t.binding("table", tbl);
            t.binding("imports", imports.get(tbl.getTableName()));
            String file = FilenameUtils.concat(fullPath, tbl.getClassName() + ".java");
            File f = createFile(file);
            FileUtils.write(f, t.render());
        }
    }

    private void generateMapper(List<Table> tables, GeneratorConfig generatorConfig) throws Exception {
        JavaMapperConfig javaMapperConfig = generatorConfig.getJavaMapperConfig();
        String fullPath = prepareTargetPath(javaMapperConfig.getTargetDir(),
                javaMapperConfig.getTargetPackage());
        logger.info("Mapper路径：{}", fullPath);
        String templateFilePath = javaMapperConfig.getTemplateFilePath();
        String tpl = (StringUtils.isNotEmpty(templateFilePath)) ? templateFilePath : MAPPER_JAVA_TEMPLATE;
        String suffix = "Mapper";
        final Template t = getTemplate(tpl);
        t.binding("pkg", javaMapperConfig.getTargetPackage());
        t.binding("modelConfig", generatorConfig.getJavaModelConfig());
        for (Table tbl : tables) {
            t.binding("table", tbl);
            String file = FilenameUtils.concat(fullPath, tbl.getClassName() + suffix + ".java");
            File f = new File(file);
            FileUtils.write(f, t.render());
        }
    }

    private void generateMapperXml(List<Table> tables, GeneratorConfig generatorConfig) throws Exception {
        SqlMapConfig sqlMapConfig = generatorConfig.getSqlMapConfig();
        String fullPath = prepareTargetPath(sqlMapConfig.getTargetDir(), "");
        if (StringUtils.isNotEmpty(sqlMapConfig.getResourceDir())) {
            fullPath = FilenameUtils.concat(sqlMapConfig.getTargetDir(), sqlMapConfig.getResourceDir());
            FileUtils.forceMkdir(new File(fullPath));
        } else {
            sqlMapConfig.setResourceDir("");
        }
        logger.info("Mapper XML路径：{}", fullPath);
        String templateFilePath = sqlMapConfig.getTemplateFilePath();
        String tpl = (StringUtils.isNotEmpty(templateFilePath)) ? templateFilePath : MAPPER_XML_TEMPLATE;
        final Template t = getTemplate(tpl);
        String ns = generatorConfig.getJavaMapperConfig().getTargetPackage() + ".";
        String suffix = "Mapper";

        for (Table tbl : tables) {
            t.binding("ns", ns + tbl.getClassName() + suffix);
            String file = FilenameUtils.concat(fullPath, tbl.getClassName() + "Mapper.xml");
            createFile(file);
            FileUtils.write(new File(file), t.render());
        }
    }

    public void generate() {
        DbUtils util = null;
        try {
            XStream xstream = new XStream();
            xstream.autodetectAnnotations(true);
            xstream.alias("generatorConfig", GeneratorConfig.class);

            ClassAliasingMapper mapper = new ClassAliasingMapper(xstream.getMapper());
            mapper.addClassAlias("include", String.class);
            mapper.addClassAlias("exclude", String.class);
            CollectionConverter cc = new CollectionConverter(mapper);
            xstream.registerLocalConverter(TableConfig.class, "includes", cc);
            xstream.registerLocalConverter(TableConfig.class, "excludes", cc);

            GeneratorConfig gc = (GeneratorConfig) xstream.fromXML(
                    Generator.class.getClassLoader().getResourceAsStream(configFile));

            util = new DbUtils(gc);
            List<Table> tables = Lists.newArrayList();
            TableConfig tc = gc.getTableConfig();
            String tablePattern = tc.getTablePattern();

            String[] tps = tablePattern.split(",");
            JdbcConfig jdbcConfig = gc.getJdbcConfig();
            for (String tp : tps) {
                List<Table> tbs = util.getTableInfo(jdbcConfig.getSchema(), tp);
                tables.addAll(tbs);
            }
            Map<String, Set<String>> imports = util.getNonPrimitiveImport();
            generateModel(tables, imports, gc);
            generateMapper(tables, gc);
            if (gc.getSqlMapConfig() != null && gc.getSqlMapConfig().isEnable()) {
                generateMapperXml(tables, gc);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (util != null) {
                util.closeDataSource();
            }
        }
    }
}
