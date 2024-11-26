package com.azhon.flutter.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.ThrowableRunnable;
import com.azhon.flutter.bean.BlocBean;
import com.azhon.flutter.util.FileUtil;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * createDate: 2023/9/16 on 13:09
 * desc:
 *
 * @author azhon
 */

public class BlocAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        String inputName = Messages.showInputDialog("Name", "New Bloc File", new ImageIcon());
        if (inputName == null || inputName.isEmpty()) return;
        VirtualFile file = e.getData(PlatformDataKeys.VIRTUAL_FILE);

        BlocBean blocBean = new BlocBean(inputName);
        String blocCode = generateBlocCode(blocBean);
        String eventCode = generateEventCode(blocBean);
        String stateCode = generateStateCode(blocBean);

        try {
            WriteAction.run((ThrowableRunnable<Throwable>) () -> {
                FileUtil.writeFile(project, file, blocBean.getBlocFileName(), blocCode);
                FileUtil.writeFile(project, file, blocBean.getEventFileName(), eventCode);
                FileUtil.writeFile(project, file, blocBean.getStateFileName(), stateCode);
            });
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }

        if (file != null) {
            file.refresh(true, true);
        }
    }

    private String generateBlocCode(BlocBean bean) {
        StringBuilder sb = new StringBuilder();
        sb.append("import 'package:todo_flutter/todo_flutter.dart';").append("\n\n")
                //part
                .append("part '").append(bean.getEventFileName()).append("';").append("\n\n")
                //part
                .append("part '").append(bean.getStateFileName()).append("';").append("\n\n")
                //class name
                .append("class ").append(bean.getBlocClsName()).append(" extends BaseBloc<").append(bean.getEventClsName()).append(", ").append(bean.getStateClsName()).append("> {").append("\n")
                //constructor
                .append("  ").append(bean.getBlocClsName()).append("() : ").append("super(").append(bean.getInitStateName()).append("());").append("\n")
                //end
                .append("}").append("\n");
        return sb.toString();
    }

    private String generateEventCode(BlocBean bean) {
        StringBuilder sb = new StringBuilder();
        //import
        sb.append("part of '").append(bean.getBlocFileName()).append("';").append("\n\n")
                //class name
                .append("abstract class ").append(bean.getEventClsName()).append(" extends BaseEvent<").append(bean.getBlocClsName()).append(", ").append(bean.getStateClsName()).append("> {}").append("\n\n")
                //constructor
                .append("class ").append(bean.getInitEventClsName()).append(" extends ").append(bean.getEventClsName()).append(" {").append("\n")
                //
                .append("  @override").append("\n").append("  Future<").append(bean.getStateClsName()).append("> on(").append(bean.getBlocClsName()).append(" bloc, ").append(bean.getStateClsName()).append(" currentState) async {").append("\n")
                //
                .append("    return ").append(bean.getInitStateName()).append("();").append("\n")
                //
                .append("  }").append("\n")
                //end
                .append("}");
        return sb.toString();
    }

    private String generateStateCode(BlocBean bean) {
        StringBuilder sb = new StringBuilder();
        //import
        sb.append("part of '").append(bean.getBlocFileName()).append("';").append("\n\n")
                //class
                .append("abstract class ").append(bean.getStateClsName()).append(" {} ").append("\n\n")
                //
                .append("class ").append(bean.getInitStateName()).append(" extends ").append(bean.getStateClsName()).append(" {}").append("\n");
        return sb.toString();
    }
}
