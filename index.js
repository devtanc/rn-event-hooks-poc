import React from 'react';
import {
  requireNativeComponent,
  NativeModules,
  NativeEventEmitter,
} from 'react-native';
import isFunction from 'lodash.isfunction';

const { RTEEventEmitter, RTEEventReceiver } = NativeModules;
// Connects the JS and Native event emitters over the RNBridge
const RTVEventEmitter = new NativeEventEmitter(RTEEventEmitter);
const RichTextEditor = requireNativeComponent('RichTextEditor');

// This component is all about abstracting away the native module interface
// and allowing downstream users to use the simple `onXYZ` hooks in the props
export default class RichTextView extends React.Component {
  subscriptions = [];

  componentDidMount() {
    // Only add the listener if the associated prop is a callback function
    if (this.isValidCallback(this.props.onMentionStart)) {
      this.subscriptions.push(
        // This is when the `startObserving` function is called on the native side
        // if this is the first component using `RTEEventEmitter` that mounted
        RTVEventEmitter.addListener('StartMention', this.handleStartMention),
      );
    }
  }

  // Check that a prop exists and is a function
  isValidCallback = prop => prop && isFunction(prop);

  // Call the prop callback (which we already know is a function)
  handleStartMention = text => this.props.onMentionStart(text);

  // Remove all listeners when the component is unmounted
  // This is when the `stopObserving` function is called on the native side
  // if this is the last component using `RTEEventEmitter` that unmounted
  componentWillUnmount = () => this.subscriptions.forEach(sub => sub.remove());

  render() {
    return <RichTextEditor {...this.props} />;
  }
}

export const insertMentionSelection = mention =>
  RTEEventReceiver.insertMentionText(mention);
