package com.azhon.flutter.common;

import com.intellij.openapi.project.Project;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * createDate: 2024/1/30 on 17:11
 * desc: pubspec.yaml配置
 *
 * @author azhon
 */

public class Config {
    /**
     * 配置的key
     */
    private static final String FLUTTER_RES = "flutter_res";
    /**
     * 模块名称
     */
    private String moduleName;
    /**
     * 当前模块是否是子模块，对生成的图片资源路径有影响，默认为true
     */
    private boolean module;
    /**
     * 配置生成的文件名和类名
     */
    private String prefix;
    /**
     * 忽略的文件目录(名称相等)
     */
    private List<String> ignoreDir;

    public static Config init(Project project) {
        Map<String, Object> map = parserYaml(project);
        Object isModule = getYamlSetting(map, "isModule");
        Object prefix = getYamlSetting(map, "prefix");
        Object ignoreDir = getYamlSetting(map, "ignoreDir");
        //生成配置
        Config config = new Config();
        config.setModuleName(map.get("name").toString());
        config.setModule(isModule == null || (Boolean) isModule);
        config.setPrefix(prefix == null ? null : prefix.toString());
        config.setIgnoreDir(ignoreDir == null ? null : (List<String>) ignoreDir);
        return config;
    }

    /**
     * 获取配置的前缀
     */
    private static Object getYamlSetting(Map<String, Object> map, String key) {
        if (map == null) return null;
        Object flutterRes = map.get(FLUTTER_RES);
        if (flutterRes == null) return null;
        return ((Map<String, Object>) flutterRes).get(key);
    }

    /**
     * 解析yaml文件
     */
    public static Map<String, Object> parserYaml(Project project) {
        try {
            Yaml yaml = new Yaml();
            FileInputStream inputStream = new FileInputStream(project.getBasePath() + File.separator + Constants.PUBSPEC_FILE);
            return yaml.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public void setModule(boolean module) {
        this.module = module;
    }

    public boolean isModule() {
        return module;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public List<String> getIgnoreDir() {
        return ignoreDir;
    }

    public void setIgnoreDir(List<String> ignoreDir) {
        this.ignoreDir = ignoreDir;
    }
}
