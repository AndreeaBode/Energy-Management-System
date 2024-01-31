import { Injectable } from '@angular/core';
import { Stomp } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { ChatMessage } from '../chat-message';
import { BehaviorSubject } from 'rxjs';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs';
import { webSocket } from 'rxjs/webSocket';
import { io, Socket } from 'socket.io-client';
import { TypingEvent } from '../typing-event';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private stompClient: any
  private wsSubject: Subject<any> = webSocket('//localhost:8005/chat-socket');
  private typingSubject = new Subject<boolean>();
  private chatUrl = 'http://localhost:8005/chat'
  private typingResponseSubject: Subject<any> = new Subject<any>(); 
  private messageSubject: BehaviorSubject<ChatMessage[]> = new BehaviorSubject<ChatMessage[]>([]);

  constructor(private http: HttpClient) { 
    this.initConnenctionSocket();
  }



  initConnenctionSocket() {
    const url = '//localhost:8005/chat-socket';
    const socket = new SockJS(url);
    this.stompClient = Stomp.over(socket)
  }

  joinRoom(roomId: string) {
    this.stompClient.connect({}, ()=>{
      this.stompClient.subscribe(`/topic/${roomId}`, (messages: any) => {
        const messageContent = JSON.parse(messages.body);
        const currentMessage = this.messageSubject.getValue();
        currentMessage.push(messageContent);

        this.messageSubject.next(currentMessage);

      })
    })
  }

  sendMessage(roomId: string, chatMessage: ChatMessage) {
    this.stompClient.send(`/app/chat/${roomId}`, {}, JSON.stringify(chatMessage))
  }

  getMessageSubject(){
    return this.messageSubject.asObservable();
  }


  sendTyping(roomId: string, typingEvent: TypingEvent): Observable<any> {
    this.stompClient.send(`/app/typing/${roomId}`, {}, JSON.stringify(typingEvent));
  
    // No need to return an Observable here, as we're not waiting for a response
    // Remove the following line:
     return this.typingResponseSubject.asObservable();
  }
  
  

  getTypingSubject(): Observable<boolean> {
    console.log("b");
    return this.typingSubject.asObservable();
  }

  sendAdminNotification(roomId: string, message: ChatMessage): void {
    this.wsSubject.next({ type: 'adminNotification', roomId, message });
  }

  
  getChatMessages(roomId: string, userId: string): Observable<any[]> {
    const url = `${this.chatUrl}/messages?room=${roomId}&user=${userId}`;
    return this.http.get<any[]>(url);
  }
  
}