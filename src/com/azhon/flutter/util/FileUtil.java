package com.azhon.flutter.util;

import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.fileTypes.PlainTextFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiManager;
import com.intellij.util.ThrowableRunnable;
import com.azhon.flutter.common.Config;
import com.azhon.flutter.common.Constants;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

/**
 * createDate: 2022/11/16 on 16:22
 * desc:
 *
 * @author azhon
 */


public class FileUtil {

    /**
     * 获取目录下的所有文件
     */
    public static Collection<VirtualFile> getDirFile(Project project, Config config, String dir) {
        VirtualFile libFile = ProjectUtil.guessProjectDir(project).findChild(dir);
        if (libFile == null || !libFile.exists()) {
            NotificationUtil.showNotify(project, "Error: [" + dir + "] directory not found!");
            return null;
        }
        Collection<VirtualFile> files = new ArrayList<>();
        recursiveGetAllFiles(files, config, libFile);
        return files;
    }

    /**
     * 递归获取文件
     */
    private static void recursiveGetAllFiles(Collection<VirtualFile> list, Config config, VirtualFile file) {
        VirtualFile[] children = file.getChildren();
        for (VirtualFile child : children) {
            if (child.isDirectory()) {
                if (config.getIgnoreDir() != null) {
                    if (config.getIgnoreDir().contains(child.getName())) {
                        continue;
                    }
                }
                recursiveGetAllFiles(list, config, child);
            } else {
                if (!child.getName().startsWith(".")) {
                    list.add(child);
                }
            }
        }
    }

    /**
     * 创建 lib/generated/ 文件夹
     */
    public static VirtualFile createDir(Project project, String dir) {
        VirtualFile libFile = ProjectUtil.guessProjectDir(project).findChild(Constants.LIB_DIR);
        if (libFile == null) return null;
        try {
            WriteAction.run((ThrowableRunnable<Throwable>) () -> {
                VirtualFile generated = libFile.findChild(Constants.GENERATED_DIR);
                if (generated == null) {
                    generated = libFile.createChildDirectory(project, Constants.GENERATED_DIR);
                }
                VirtualFile bridge = generated.findChild(dir);
                if (bridge == null) {
                    generated.createChildDirectory(project, dir);
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return libFile.findFileByRelativePath(Constants.GENERATED_DIR + File.separator + dir);
    }

    /**
     * 创建辅助类
     */
    public static void writeFile(Project project, VirtualFile dirFile, String fileName, String code) {
        PsiDirectory psiDirectory = PsiManager.getInstance(project).findDirectory(dirFile);
        if (psiDirectory == null) return;
        PsiFileFactory factory = PsiFileFactory.getInstance(project);
        PsiFile psiFile = psiDirectory.findFile(fileName);
        if (psiFile != null) {
            try {
                OutputStream out = psiFile.getVirtualFile().getOutputStream(dirFile);
                out.write(code.getBytes(StandardCharsets.UTF_8));
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            psiDirectory.add(factory.createFileFromText(fileName, PlainTextFileType.INSTANCE, code));
        }
    }
}
