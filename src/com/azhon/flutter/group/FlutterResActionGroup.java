package com.azhon.flutter.group;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;

import org.jetbrains.annotations.NotNull;

/**
 * createDate: 2022/11/22 on 10:00
 * desc:
 *
 * @author azhon
 */


public class FlutterResActionGroup extends DefaultActionGroup {
    @Override
    public void update(@NotNull AnActionEvent e) {
        e.getPresentation().setEnabledAndVisible(true);
    }

}
