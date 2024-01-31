// chat-message.ts
export class ChatMessage {
  message: string = '';
  user: string = '';
  isTyping: boolean = false;
  message_side?: 'sender' | 'receiver';
}
