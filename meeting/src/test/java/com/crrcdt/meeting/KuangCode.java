package com.crrcdt.meeting;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class KuangCode {
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        //1、全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setAuthor("liujun");
        gc.setDateType(DateType.ONLY_DATE);
        gc.setOpen(false);
        String path = System.getProperty("user.dir");
        gc.setOutputDir(path+"/src/main/java");
        gc.setServiceName("%sService");//去service的i前缀
        gc.setFileOverride(true);
        gc.setSwagger2(true);
        gc.setIdType(IdType.ID_WORKER);
        mpg.setGlobalConfig(gc);
        //配置数据源
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql:///meeting?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("123456");
        dataSourceConfig.setDbType(DbType.MYSQL);
        mpg.setDataSource(dataSourceConfig);



        //包的配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setController("controller");
        packageConfig.setEntity("entity");
        packageConfig.setMapper("mapper");
        packageConfig.setModuleName("");
        packageConfig.setParent("com.crrcdt.meeting");
        packageConfig.setService("service");
        mpg.setPackageInfo(packageConfig);

        //策略
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("role");
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);//自动生成lombok
        strategy.setLogicDeleteFieldName("deleted");
        //自动填充
//        TableFill createTimee = new TableFill("create_time", FieldFill.INSERT);
//        TableFill updateTime = new TableFill("update_time", FieldFill.INSERT);
//        ArrayList<TableFill> tableFills = new ArrayList<>();
//        tableFills.add(createTimee);
//        tableFills.add(updateTime);
//        strategy.setTableFillList(tableFills);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setRestControllerStyle(true);
        mpg.setStrategy(strategy);



        mpg.execute();//执行
    }
    }

