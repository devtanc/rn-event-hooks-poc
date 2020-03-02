package com.reactlibrary;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.SimpleViewManager;

public class RichTextEditorManager extends SimpleViewManager<EditText> {
  public static final String REACT_CLASS = "RichTextEditor";

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public EditText createViewInstance(final ThemedReactContext context) {
    EditText editable = new EditText(context);

    editable.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {}

      @Override
      public void afterTextChanged(Editable changes) {
        String text = changes.toString();
        String typed = text.substring(text.length() - 1);

        if (typed.equals("@")) {
          WritableMap params = new WritableNativeMap();
          params.putString("data", typed);

          context
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit("StartMention", params);
        }
      }
    });

    return editable;
  }
}
