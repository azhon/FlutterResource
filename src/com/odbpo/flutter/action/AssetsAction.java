package com.odbpo.flutter.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.ThrowableRunnable;
import com.odbpo.flutter.common.Constants;
import com.odbpo.flutter.util.FileUtil;
import com.odbpo.flutter.util.NotificationUtil;
import com.odbpo.flutter.util.StringUtil;

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
        Collection<VirtualFile> assetsFiles = FileUtil.getDirFile(project, Constants.ASSETS_DIR);
        if (assetsFiles == null) {
            NotificationUtil.showNotify("No assets file need to be generate！");
            return;
        }
        //需要过滤一下分辨率文件夹（1.5x，2.0x，3.0x）不进行生成
        assetsFiles = assetsFiles.stream().filter(file -> {
            String pattern = "\\d.\\dx";
            Matcher matcher = Pattern.compile(pattern).matcher(file.getPath());
            return !matcher.find();
        }).collect(Collectors.toList());
        if (assetsFiles.isEmpty()) {
            NotificationUtil.showNotify("No assets file need to be generate！");
            return;
        }
        generateAssets(project, assetsFiles);
        NotificationUtil.showNotify("Generate assets successful！");
    }

    /**
     * 生成资源文件
     */
    private void generateAssets(Project project, Collection<VirtualFile> assetsFiles) {
        VirtualFile dirFile = FileUtil.createDir(project, Constants.ASSETS_DIR);
        if (dirFile == null) return;
        try {
            String moduleName = FileUtil.getModuleName(project);
            String prefix = FileUtil.getGeneratePrefix(project, moduleName);
            //生成代码
            String fileName = prefix + "_assets.dart";
            String code = createCode(moduleName, prefix, assetsFiles);
            WriteAction.run((ThrowableRunnable<Throwable>) () -> FileUtil.writeFile(project, dirFile, fileName, code));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    /**
     * 生成代码
     */
    private String createCode(String moduleName, String prefix, Collection<VirtualFile> assetsFiles) {
        final StringBuilder codes = new StringBuilder();
        codes.append("///This file is automatically generated, your modifications will be lost.").append("\n");
        codes.append("class ").append(StringUtil.toCamelCase(prefix, true));
        codes.append("Assets").append(" {").append("\n");
        for (VirtualFile assetsFile : assetsFiles) {
            codes.append("  static const ");
            codes.append(StringUtil.toCamelCase(StringUtil.removeSuffix(assetsFile.getName()), false));
            codes.append(" = '").append("packages/").append(moduleName).append("/");
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
