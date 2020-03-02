# How to have `onXYZ` event hooks in a ReactNative Native UI Component in Swift

I wanted to have event syntax on my JSX with a native component written in Swift.

```js
// Basically just this:
<MyComponent onChange={changes => console.log(changes)} />
```

No matter where I looked, no place seemed to have clear documentation on how to do all of this in one clear, concise place. After reading over StackOverflow posts and some Gists and some blog posts and putting it all together, this is what I got working. This solution works for both Android and iOS. If any part of it is unclear, please let me know and I will update the documentation to clarify. I'm hoping that this will help anyone who is having trouble with the docs (which I'll make a PR at some point to update) to make sense of them.

This is the file structure of the Native Module (created via `create-react-native-module`):

```text
├── README.md
├── android
│   └── src/main/java/com/reactlibrary
│       // These files are most easily modified and added via Android Studio.
│       ├── RichTextEditorManager.java
│       ├── RichTextEditorPackage.java
│       └── RTEEventReceiver.java
|   // This file is where everything from the native modules ties in to the final exported React Native component
├── index.js
├── ios
|   // These files are modified and added via XCode. Be sure they're part of the project "Target Membership"!
│   ├── RTEEventEmitter.m
│   ├── RTEEventEmitter.swift
│   ├── RichTextEditor-Bridging-Header.h
│   ├── RichTextEditor.m
│   ├── RichTextEditorManager.swift
│   ├── RichTextEditor.xcworkspace
│   └── RichTextEditor.xcodeproj
├── package.json
├── react-native-rich-text-editor.podspec
└── yarn.lock
```

This module is then installed in a React Native app and used:

```js
import RichTextEditor from 'react-native-rich-text-editor'
...
class ...{
  render() {
    return (
      <RichTextEditor
        style={styles.input}
        // This line was the goal
        onMentionStart={text => console.log('An "@" was typed:', text)}
      />
    )
  }
}
```

I also added an extra feature of an EventReveiver on the Native side that can be called via:

```js
import { insertMentionText } from 'react-native-rich-text-editor'
...
class ... {
  onSelectMentionOption = mention => insertMentionText(mention)

  render() {
    ...
  }
}
```

This isn't published, but you can do `yarn add '../path/to/this/folder'` to add it locally and try it in an actual React Native project.

Thanks to [eschos24](https://github.com/eschos24) for his help with all of this!

[gist](https://gist.github.com/devtanc/8ef2c8afcc4d8f87061b42f4a9c7dc80)
