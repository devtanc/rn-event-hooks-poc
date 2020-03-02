import UIKit

@objc(RichTextEditorManager)
class RichTextEditorManager : RCTViewManager, UITextViewDelegate {
    let richTextView = UITextView()
    
    override func view() -> UIView! {
        if richTextView.delegate != nil {
            return richTextView
        }
        richTextView.delegate = self
        richTextView.text = "Please type here..."
        
        return richTextView
    }
    
    override class func requiresMainQueueSetup() -> Bool {
        return true
    }
    
    func textViewDidBeginEditing(_ textView: UITextView) {
        richTextView.text = ""
        print("BEGIN EDITING")
    }
    
    func textViewDidEndEditing(_ textView: UITextView) {
        print("END EDITING")
    }
    
    func textView(_ textView: UITextView, shouldChangeTextIn range: NSRange, replacementText text: String) -> Bool {
        print("Range: \(range)")
        print("Text: \(text)")
        
        if (text == "@") {
            RTEEventEmitter.shared?.emitEvent(withName: "StartMention", body: ["data": text])
        }
        
        return true
    }    
}


// This code was placed here because it will be related to the RichTextEditorManager
// When a mention is selected in JS, the result will be received here and need to be
// updated in the textView. This code could be in its own RTEEventReceiver.swift file
// if you want. 
@objc(RTEEventReceiver)
class RTEEventReceiver : NSObject {
    @objc(insertMentionText:)
    func insertMention(text: String) {
        print("Mention text: \(text)")
    }
    
    @objc static func requiresMainQueueSetup() -> Bool {
        return true
    }
}
