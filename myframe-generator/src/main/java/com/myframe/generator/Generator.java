package com.myframe.generator;

import com.google.common.collect.Lists;
import com.myframe.core.util.FileNameUtils;
import com.myframe.core.util.FileUtils;
import com.myframe.core.util.LogUtils;
import com.myframe.core.util.ResourceUtils;
import com.myframe.core.util.StringUtils;
import com.myframe.dao.util.Table;
import com.myframe.generator.config.GeneratorConfig;
import com.myframe.generator.config.JavaDaoConfig;
import com.myframe.generator.config.JavaModelConfig;
import com.myframe.generator.config.JavaServiceConfig;
import com.myframe.generator.config.JdbcConfig;
import com.myframe.generator.config.SqlMapConfig;
import com.myframe.generator.config.TableConfig;
import com.myframe.generator.util.DbUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.mapper.ClassAliasingMapper;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.slf4j.Logger;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 代码生成器类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public class Generator {
    private static final Logger logger = LogUtils.get();
    public static final String MAPPER_TEMPLATE = "/templates/mapper.xml.tpl";
    public static final String MODEL_TEMPLATE = "/templates/model.java.tpl";
    public static final String DAO_TEMPLATE = "/templates/dao.java.tpl";
    public static final String DAO_IMPL_TEMPLATE = "/templates/daoImpl.java.tpl";
    public static final String SERVICE_TEMPLATE = "/templates/service.java.tpl";
    public static final String SERVICE_IMPL_TEMPLATE = "/templates/serviceImpl.java.tpl";
    public static final String CONFIGURATION_TEMPLATE = "/templates/configuration.xml.tpl";

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

    private String prepareTargetPath(String targetDir, String targetPackage) {
        String baseDir = targetDir.replace('\\', '/');
        String pkg = targetPackage.replace('.', '/');
        String fullPath = FileNameUtils.concat(baseDir, pkg);
        FileUtils.createDir(fullPath);
        // FileUtils.cleanDir(fullPath);

        return fullPath;
    }

    private void generateMapper(List<Table> tables, GeneratorConfig generatorConfig) throws Exception {
        SqlMapConfig sqlMapConfig = generatorConfig.getSqlMapConfig();
        String fullPath = prepareTargetPath(sqlMapConfig.getTargetDir(),
                sqlMapConfig.getTargetPackage());
        if (StringUtils.isNotEmpty(sqlMapConfig.getResourceDir())) {
            fullPath = FileNameUtils.concat(sqlMapConfig.getTargetDir(), sqlMapConfig.getResourceDir());
            FileUtils.createDir(fullPath);
        } else {
            sqlMapConfig.setResourceDir(sqlMapConfig.getTargetPackage().replace('.', '/'));
        }
        logger.info("Mapper路径：{}", fullPath);

        final Template t = getTemplate(MAPPER_TEMPLATE);
        t.binding("generateConfig", generatorConfig.getGenerateConfig());
        t.binding("tableConfig", generatorConfig.getTableConfig());
        t.binding("pkg", generatorConfig.getJavaModelConfig().getTargetPackage());
        for (Table tbl : tables) {
            t.binding("table", tbl);
            String file = FileNameUtils.concat(fullPath, tbl.getClassName() + "Mapper.xml");
            FileUtils.createFile(file);
            FileUtils.write(t.render(), new File(file));
        }
    }

    private void generateModel(List<Table> tables, Map<String, Set<String>> imports,
                               GeneratorConfig generatorConfig) throws Exception {
        JavaModelConfig javaModelConfig = generatorConfig.getJavaModelConfig();
        String fullPath = prepareTargetPath(javaModelConfig.getTargetDir(),
                javaModelConfig.getTargetPackage());
        logger.info("Model路径：{}", fullPath);
        final Template t = getTemplate(MODEL_TEMPLATE);
        t.binding("pkg", javaModelConfig.getTargetPackage());
        for (Table tbl : tables) {
            t.binding("table", tbl);
            t.binding("imports", imports.get(tbl.getTableName()));
            String file = FileNameUtils.concat(fullPath, tbl.getClassName() + ".java");
            FileUtils.createFile(file);
            FileUtils.write(t.render(), new File(file));
        }
    }

    private void generateDao(List<Table> tables, GeneratorConfig generatorConfig) throws Exception {
        JavaDaoConfig javaDaoConfig = generatorConfig.getJavaDaoConfig();
        String fullPath = prepareTargetPath(javaDaoConfig.getTargetDir(),
                javaDaoConfig.getTargetPackage());
        logger.info("Dao路径：{}", fullPath);
        final Template t = getTemplate(DAO_TEMPLATE);
        t.binding("pkg", javaDaoConfig.getTargetPackage());
        t.binding("daoConfig", javaDaoConfig);
        t.binding("modelConfig", generatorConfig.getJavaModelConfig());
        for (Table tbl : tables) {
            t.binding("table", tbl);
            String file = FileNameUtils.concat(fullPath, tbl.getClassName() + "Dao.java");
            FileUtils.createFile(file);
            FileUtils.write(t.render(), new File(file));
        }

        final Template implTpl = getTemplate(DAO_IMPL_TEMPLATE);
        implTpl.binding("pkg", javaDaoConfig.getTargetPackage());
        implTpl.binding("daoConfig", javaDaoConfig);
        implTpl.binding("modelConfig", generatorConfig.getJavaModelConfig());
        String implPath = FileNameUtils.concat(fullPath, "impl");
        FileUtils.createDir(implPath);

        for (Table tbl : tables) {
            implTpl.binding("table", tbl);
            String file = FileNameUtils.concat(implPath, tbl.getClassName() + "DaoImpl.java");
            FileUtils.createFile(file);
            FileUtils.write(implTpl.render(), new File(file));
        }
    }

    private void generateService(List<Table> tables, GeneratorConfig generatorConfig) throws Exception {
        JavaServiceConfig javaServiceConfig = generatorConfig.getJavaServiceConfig();
        String fullPath = prepareTargetPath(javaServiceConfig.getTargetDir(),
                javaServiceConfig.getTargetPackage());
        logger.info("Service路径：{}", fullPath);
        final Template t = getTemplate(SERVICE_TEMPLATE);
        t.binding("pkg", javaServiceConfig.getTargetPackage());
        t.binding("modelConfig", generatorConfig.getJavaModelConfig());
        for (Table tbl : tables) {
            t.binding("table", tbl);
            String file = FileNameUtils.concat(fullPath, tbl.getClassName() + "Service.java");
            FileUtils.createFile(file);
            FileUtils.write(t.render(), new File(file));
        }

        final Template implTpl = getTemplate(SERVICE_IMPL_TEMPLATE);
        implTpl.binding("pkg", javaServiceConfig.getTargetPackage());
        implTpl.binding("modelConfig", generatorConfig.getJavaModelConfig());
        implTpl.binding("daoConfig", generatorConfig.getJavaDaoConfig());
        implTpl.binding("serviceConfig", javaServiceConfig);
        String implPath = FileNameUtils.concat(fullPath, "impl");
        FileUtils.createDir(implPath);

        for (Table tbl : tables) {
            implTpl.binding("table", tbl);
            implTpl.binding("tableNameFirstLower", StringUtils.uncapitalize(tbl.getClassName()));
            String file = FileNameUtils.concat(implPath, tbl.getClassName() + "ServiceImpl.java");
            FileUtils.createFile(file);
            FileUtils.write(implTpl.render(), new File(file));
        }
    }

    private void generateConfiguration(List<Table> tables, GeneratorConfig generatorConfig) throws Exception {
        SqlMapConfig sqlMapConfig = generatorConfig.getSqlMapConfig();
        String fullPath = prepareTargetPath(sqlMapConfig.getTargetDir(), "");
        fullPath = fullPath.replace('\\', '/');
        if (fullPath.endsWith("/")) {
            fullPath = fullPath.substring(0, fullPath.length() - 1);
        }
        logger.info("mybatis-config路径：{}", fullPath);
        final Template t = getTemplate(CONFIGURATION_TEMPLATE);
        t.binding("tables", tables);
        t.binding("modelConfig", generatorConfig.getJavaModelConfig());
        t.binding("sqlMapConfig", sqlMapConfig);
        String file = FileNameUtils.concat(fullPath, "mybatis-config.xml");
        FileUtils.createFile(file);
        FileUtils.write(t.render(), new File(file));
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
                    ResourceUtils.getResourceStream(configFile));

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
            generateMapper(tables, gc);
            generateModel(tables, imports, gc);
            generateDao(tables, gc);
            generateService(tables, gc);
            generateConfiguration(tables, gc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (util != null) {
                util.closeDataSource();
            }
        }
    }
}
