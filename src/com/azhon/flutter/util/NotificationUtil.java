package com.azhon.flutter.util;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;

/**
 * createDate: 2022/11/16 on 17:14
 * desc:
 *
 * @author azhon
 */


public class NotificationUtil {
    /**
     * 发送一个通知
     */
    public static void showNotify(Project project, String message) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup("Flutter Resource")
                .createNotification(message, NotificationType.INFORMATION)
                .notify(project);
    }
}
