//package com.xiaohai.generator;
//
//import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
//import com.baomidou.mybatisplus.core.toolkit.StringPool;
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.FastAutoGenerator;
//import com.baomidou.mybatisplus.generator.InjectionConfig;
//import com.baomidou.mybatisplus.generator.config.*;
//import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
//import com.baomidou.mybatisplus.generator.config.po.TableInfo;
//import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
//import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Scanner;
//
///**
// *  代码生成器
// *  AutoGenerator 是 MyBatis-Plus 的代码生成器，通过 AutoGenerator 可以快速生成 Entity、Mapper、Mapper XML、Service、Controller 等各个模块的代码，极大的提升了开发效率。
// *  适用版本：mybatis-plus-generator 3.5.1 以下版本
// *
// * @author wangchenghai
// * @date 2023/01/09 9:45:15
// */
//public class Generator {
//    /** 作者*/
//    private static final String AUTHOR = "xiaohai";
//    /**数据库地址*/
//    private static final String HOST = "jdbc:mysql://127.0.0.1:3306/offcial?&characterEncoding=utf8&useSSL=false&serverTimezone=UTC";
//    /** 数据库连接类型*/
//    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
//    /** 数据库账号*/
//    private static final String DATABASE_USERNAME = "root";
//    private static final String DATABASE_PASSWORD = "123456";
//    /** 生成包路径 */
//    private static final String PARENT_PACKAGE = "com.xiaohai.system";
//    /** 公共类 */
//    private static final String COMMON_ENTITY = "com.xiaohai.admin.daomain.BaseEntity";
//    /** 公共字段*/
//    private static final String COMMON_FIELD = "updated_by,updated_time,created_by,created_time";
//    /** 自动去除表前缀，默认是false*/
//    private static final boolean REMOVE_PRE = false;
//    /** 表前缀*/
//    private static final String TABLE_PREDIX = "sys_";
//
//    /**
//     * <p>
//     * 读取控制台内容
//     * </p>
//     */
//    public static String scanner(String tip) {
//        Scanner scanner = new Scanner(System.in);
//        StringBuilder help = new StringBuilder();
//        help.append("请输入" + tip + "：");
//        System.out.println(help.toString());
//        if (scanner.hasNext()) {
//            String ipt = scanner.next();
//            if (StringUtils.isNotEmpty(ipt)) {
//                return ipt;
//            }
//        }
//        throw new MybatisPlusException("请输入正确的" + tip + "！");
//    }
//
//
//    public static void main(String[] args) {
//
//        FastAutoGenerator.create(HOST,DATABASE_USERNAME,DATABASE_PASSWORD)
//                // 数据库配置
//                .dataSourceConfig((scanner, builder) -> builder.schema(scanner.apply("请输入表名称")))
//                // 全局配置
//                .globalConfig((scanner, builder) -> builder.author(scanner.apply("请输入作者名称")))
//                // 包配置
//                .packageConfig((scanner, builder) -> builder.parent(scanner.apply("请输入包名")))
//                // 策略配置
//                .strategyConfig((scanner, builder) -> builder.addInclude(scanner.apply("请输入表名，多个表名用,隔开")))
//                /*
//                    模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker 或 Enjoy
//                   .templateEngine(new BeetlTemplateEngine())
//                   .templateEngine(new FreemarkerTemplateEngine())
//                   .templateEngine(new EnjoyTemplateEngine())
//                 */
//                .execute();
//
//
//
//
//        // 代码生成器
//        AutoGenerator mpg = new AutoGenerator();
//
//        // 全局配置
//        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
//        gc.setOutputDir(projectPath + "/src/main/java");
//        //设置作者
//        gc.setAuthor(AUTHOR);
//        gc.setOpen(false);
//        //XML中的ResultMap标签
//        gc.setBaseResultMap(true);
//        // xml columlist
//        gc.setBaseColumnList(true);
//        //是否覆盖文件
//        gc.setFileOverride(true);
//        //        //主键策略
//        //        gc.setIdType(IdType.AUTO);
//        //        // %s会自动填充表实体属性
//        //        gc.setMapperName("%sMapper");
//        //        gc.setXmlName("%sMapper");
//        //        gc.setServiceName("%sService");
//        //        gc.setServiceImplName("%sServiceImpl");
//        //        gc.setControllerName("%sController");
//        //        gc.setDateType(DateType.ONLY_DATE);
//        //实体属性 Swagger2 注解
//        //        gc.setSwagger2(true);
//        mpg.setGlobalConfig(gc);
//
//        // 数据源配置
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl(HOST);
//        dsc.setDriverName(DRIVER_CLASS_NAME);
//        dsc.setUsername(DATABASE_USERNAME);
//        dsc.setPassword(DATABASE_PASSWORD);
//        dsc.setTypeConvert(new MySqlTypeConvert() {
//            // 自定义数据库表字段类型转换【可选】
//            @Override
//            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
//                if (fieldType.toLowerCase().contains("datetime")) {
//                    return DbColumnType.DATE;
//                }
//                return super.processTypeConvert(globalConfig, fieldType);
//            }
//
//        });
//        mpg.setDataSource(dsc);
//
//        // 包配置
//        PackageConfig pc = new PackageConfig();
//        //        pc.setModuleName(scanner("模块名"));
//        pc.setParent(PARENT_PACKAGE);
//        //        String packgeName = scanner("请输入--- 包名");
//        //        pc.setParent("com.xiaohai.generator");
//        //        pc.setController("controller."+packgeName);
//        pc.setEntity("pojo.entity");
//        //        pc.setService("service." + packgeName);
//        //        pc.setServiceImpl("service." + packgeName +".impl");
//        pc.setMapper("dao");
//
//        mpg.setPackageInfo(pc);
//
//        // 自定义配置
//        InjectionConfig cfg = new InjectionConfig() {
//            @Override
//            public void initMap() {
//                // to do nothing
//            }
//        };
//
//        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
//        // 如果模板引擎是 velocity
//        // String templatePath = "/templates/mapper.xml.vm";
//
//        // 自定义输出配置
//        List<FileOutConfig> focList = new ArrayList<>();
//        // 自定义配置会被优先输出
//        focList.add(new FileOutConfig(templatePath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//            }
//        });
//
//        //        cfg.setFileCreate(new IFileCreate() {
//        //            @Override
//        //            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
//        //                // 判断自定义文件夹是否需要创建
//        //                checkDir("调用默认方法创建的目录");
//        //                return false;
//        //            }
//        //        });
//
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);
//
//        // 配置模板
//        TemplateConfig templateConfig = new TemplateConfig();
//        //配置自定义输出模板
//        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
//        //        String name = "/returnJson";
//        //        templateConfig.setEntity("templates" + name + "/entity.java");
//        //        templateConfig.setService("templates" + name + "/service.java");
//        //        templateConfig.setServiceImpl("templates" + name + "/serviceImpl.java");
//        //        templateConfig.setController("templates" + name + "/controller.java");
//        templateConfig.setXml(null);
//        mpg.setTemplate(templateConfig);
//
//        // 策略配置
//        StrategyConfig strategy = new StrategyConfig();
//        strategy.setNaming(NamingStrategy.underline_to_camel);
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        // 公共父类  你自己的父类控制器,没有就不用设置!
//        //        strategy.setSuperControllerClass("");
//        if (scanner("公共父类(true/false)").equals("true")) {
//            // 公共父类  你自己的父类实体,没有就不用设置!
//            strategy.setSuperEntityClass(COMMON_ENTITY);
//            // 写于父类中的公共字段
//            String[] tags = COMMON_FIELD.split(",");
//            strategy.setSuperEntityColumns(tags);
//        }
//        strategy.setEntityLombokModel(true);
//        strategy.setRestControllerStyle(true);
//
//        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
//        strategy.setControllerMappingHyphenStyle(true);
//        //表前缀
//        //        strategy.setTablePrefix("sys_");
//        mpg.setStrategy(strategy);
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
//        mpg.execute();
//    }
//}
