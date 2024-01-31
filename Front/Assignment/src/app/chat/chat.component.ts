// chat-user.component.ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ChatMessage } from '../chat-message';
import { ChatService } from 'src/app/services/chat.service';
import { AuthService } from '../auth.service';
import { io, Socket } from 'socket.io-client';
import { EnergyService } from '../services/energy.service';
import { TypingEvent } from '../typing-event';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss'],
})
export class ChatComponent implements OnInit {
  messageInput: string = '';
  userId: string = '';
  message: string = '';
  messageList: any[] = [];
  isTyping: boolean = false;
  private socket: any;
  userIsTyping: boolean = false;
  isAdminTyping = false;
  chatMessages: any[] = [];
  lastMessageReceivedTime: number = 0;
  aa = false;

  constructor(
    private chatService: ChatService,
    private route: ActivatedRoute,
    private authService: AuthService,
    private toastr: ToastrService,
    private energyService: EnergyService,
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params: ParamMap) => {
      const userParam = params.get('user');
      if (userParam !== null) {
        this.userId = userParam;
        this.chatService.joinRoom("ABC");
        this.listenForMessages();
      } else {
        console.error("user is null");
      }
    });

    this.chatService.getTypingSubject().subscribe((typingStatus: boolean) => {
      this.isTyping = typingStatus;
    });
  }

  sendMessage() {
    const chatMessage: ChatMessage = {
      message: this.messageInput,
      user: this.userId,
      isTyping: this.isTyping,
    };
    this.chatService.sendMessage("ABC", chatMessage);
    this.messageInput = '';
  }


  listenForMessages() {

    this.chatService.getMessageSubject().subscribe((messages: any) => {
      this.aa = false;
      const filteredMessages = messages.filter((item: any) => item.message !== 'typing');
      this.messageList = filteredMessages.map((item: any) => ({
        ...item,
        message_side: item.user === this.userId ? 'sender' : 'receiver',
      }));

      this.lastMessageReceivedTime = Date.now();

      if (this.messageList.length > 0) {
        const latestMessage = this.messageList[this.messageList.length - 1];
        if (latestMessage.user !== this.userId && !latestMessage.isRead) {

          latestMessage.isRead = true;

        }
      }

      const isTypingEventReceived = messages.length > 0 && messages[messages.length - 1].user === 'admin';
      console.log('isTypingEventReceived:', isTypingEventReceived);

      this.aa = isTypingEventReceived;
      console.log('aa:', this.aa);

    });

    setInterval(() => {
      const currentTime = Date.now();
      const elapsedTime = (currentTime - this.lastMessageReceivedTime) / 1000;

      if (elapsedTime > 2) {
        this.aa = false;
        console.log("timp");
      }
    }, 1000);

    this.chatService.getTypingSubject().subscribe((typingStatus: boolean) => {
      this.isTyping = typingStatus;
    });
  }



  onInput() {
    this.isTyping = this.messageInput.length > 0;

    const typingEvent: TypingEvent = {
      message: this.message,
      user: this.userId,
      isTyping: this.isTyping,
    };

    this.chatService.sendTyping("ABC", typingEvent);

    if (this.isTyping) {
      const adminMessage: ChatMessage = {
        message: "User is typing...",
        user: "admin",
        isTyping: true,
      };
    }
  }

}
