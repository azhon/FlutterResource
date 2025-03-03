package com.azhon.flutter.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.ThrowableRunnable;
import com.azhon.flutter.common.Config;
import com.azhon.flutter.common.Constants;
import com.azhon.flutter.util.FileUtil;
import com.azhon.flutter.util.NotificationUtil;
import com.azhon.flutter.util.StringUtil;

import org.apache.http.util.TextUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * createDate: 2022/11/22 on 09:34
 * desc:
 *
 * @author azhon
 */


public class AssetsAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);
        if (project == null) return;
        Config config = Config.init(project);
        Collection<VirtualFile> assetsFiles = FileUtil.getDirFile(project, config, Constants.ASSETS_DIR);
        if (assetsFiles == null) {
            NotificationUtil.showNotify(project, "No assets file need to be generate！");
            return;
        }
        //需要过滤一下分辨率文件夹（1.5x，2.0x，3.0x）和iconfont相关的 不进行生成
        assetsFiles = assetsFiles.stream().filter(file -> {
            String pattern = "\\d.\\dx|iconfont";
            Matcher matcher = Pattern.compile(pattern).matcher(file.getPath());
            return !matcher.find();
        }).collect(Collectors.toList());
        if (assetsFiles.isEmpty()) {
            NotificationUtil.showNotify(project, "No assets file need to be generate！");
            return;
        }
        generateAssets(project, config, assetsFiles);
        NotificationUtil.showNotify(project, "Generate assets successful！");
    }

    /**
     * 生成资源文件
     */
    private void generateAssets(Project project, Config config, Collection<VirtualFile> assetsFiles) {
        VirtualFile dirFile = FileUtil.createDir(project, Constants.ASSETS_DIR);
        if (dirFile == null) return;
        try {
            String prefix = config.getPrefix();
            if (TextUtils.isEmpty(config.getPrefix())) {
                prefix = config.getModuleName();
            }
            //生成代码
            String fileName = prefix + "_assets.dart";
            String code = createCode(config, prefix, assetsFiles);
            WriteAction.run((ThrowableRunnable<Throwable>) () -> FileUtil.writeFile(project, dirFile, fileName, code));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    /**
     * 生成代码
     */
    private String createCode(Config config, String prefix, Collection<VirtualFile> assetsFiles) {
        final StringBuilder codes = new StringBuilder();
        codes.append("///This file is automatically generated by the [FlutterResource], your modifications will be lost.").append("\n");
        codes.append("class ").append(StringUtil.toCamelCase(prefix, true));
        codes.append("Assets").append(" {").append("\n");
        for (VirtualFile assetsFile : assetsFiles) {
            codes.append("  static const ");
            codes.append(StringUtil.toCamelCase(StringUtil.removeSuffix(assetsFile.getName()), false));
            codes.append(" = '");
            if (config.isModule()) {
                codes.append("packages/").append(config.getModuleName()).append("/");
            }
            codes.append(getPath(assetsFile.getPath())).append("';\n");
        }
        codes.append("}");
        codes.append("\n");
        return codes.toString();
    }

    private String getPath(String path) {
        int index = path.indexOf(Constants.ASSETS_DIR);
        return path.substring(index);
    }
}
