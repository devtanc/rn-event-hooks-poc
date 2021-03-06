package com.reactlibrary;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import com.facebook.react.ReactPackage;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.NativeModule;

import com.facebook.react.uimanager.ViewManager;

public class RichTextEditorPackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();

        modules.add(new RTEEventReceiver(reactContext));

        return modules;
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList(new RichTextEditorManager());
    }
}
