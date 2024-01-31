import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ChatMessage } from '../chat-message';
import { ChatService } from 'src/app/services/chat.service';
import { AuthService } from '../auth.service';
import { TypingService } from '../services/typing.service';
import { io } from 'socket.io-client';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TypingEvent } from '../typing-event';

@Component({
  selector: 'app-chat-admin',
  templateUrl: './chat-admin.component.html',
  styleUrls: ['./chat-admin.component.scss'],
})
export class ChatAdminComponent implements OnInit {
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
    private typingService: TypingService,
    public snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params: ParamMap) => {
      const userParam = params.get('admin');
      if (userParam !== null) {
        this.userId = userParam;
        this.chatService.joinRoom("ABC");
        this.listenForMessages();
      } else {
        console.error("user is null");
      }
    });

    this.chatService.getTypingSubject().subscribe((typingStatus: boolean) => {
      this.isAdminTyping = typingStatus;
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
          this.displayNotification('Message read by the other user');
        }
      }

      const isTypingEventReceived = messages.length > 0 && messages[messages.length - 1].user === 'user';

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



  displayNotification(message: string) {
  
    console.log('Notification:', message);
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
