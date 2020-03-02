#import <React/RCTBridgeModule.h>

// This is exporting the class with a few lines at the end of RichTextEditorManager.swift
// If those lines were moved to their own swift file, it would need to be included in the
// RichTextEditor-Bridging-Header.h file and this would be exporting that module instead.
@interface RCT_EXTERN_MODULE(RTEEventReceiver, NSObject)

RCT_EXTERN_METHOD(insertMentionText:(NSString *)text)

@end
