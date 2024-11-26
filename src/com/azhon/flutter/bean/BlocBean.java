package com.azhon.flutter.bean;

import com.azhon.flutter.util.StringUtil;

/**
 * createDate: 2023/9/16 on 14:02
 * desc:
 *
 * @author azhon
 */

public class BlocBean {

    private final String blocFileName;
    private final String eventFileName;
    private final String stateFileName;
    private final String blocClsName;
    private final String eventClsName;
    private final String initEventClsName;
    private final String stateClsName;
    private final String initStateName;

    public BlocBean(String input) {
        blocFileName = input + "_bloc.dart";
        eventFileName = input + "_event.dart";
        stateFileName = input + "_state.dart";

        String clsName = StringUtil.toCamelCase(input, true);

        blocClsName = clsName + "Bloc";
        eventClsName = clsName + "Event";
        initEventClsName = clsName + "InitEvent";
        stateClsName = clsName + "State";

        initStateName = clsName + "InitState";
    }

    public String getBlocFileName() {
        return blocFileName;
    }

    public String getEventFileName() {
        return eventFileName;
    }

    public String getStateFileName() {
        return stateFileName;
    }

    public String getBlocClsName() {
        return blocClsName;
    }

    public String getEventClsName() {
        return eventClsName;
    }

    public String getInitEventClsName() {
        return initEventClsName;
    }

    public String getStateClsName() {
        return stateClsName;
    }

    public String getInitStateName() {
        return initStateName;
    }
}
