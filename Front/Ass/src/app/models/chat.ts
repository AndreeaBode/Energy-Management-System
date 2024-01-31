import { Message } from "./message";

export class Chat {
  chatId: number = 0; 
  firstUserName: string = '';
  secondUserName: string = '';
  messageList: Message[] = [];

  constructor() {
  }
}
