// websocket.service.ts

import { Injectable } from '@angular/core';
import { Message, Stomp } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { Observable } from 'rxjs';

export interface Notification{
  idDevice:number;
  maximumHourlyEnergyConsumption: number;
  sum: number;
  message: string;
}
@Injectable({
  providedIn: 'root',
})
export class WebSocketService {

  private stompClient!:any;
  constructor() {}
  
  public connect(): Observable<void> {
    console.log("Service");
    this.stompClient = Stomp.over(() => new SockJS("http://localhost:9001/socket"));
    return new Observable(observer => {
      this.stompClient.connect({}, () => {
        observer.next();
      });
    });
  }
  
 
   public subscribe(topic: string, callback: (message: Notification) => void): void {
    console.log("Subscribe");
   
    this.stompClient.subscribe(topic, (message: any) => {
      try {  console.log("try");console.log(message);
       
        const parsedMessage = JSON.parse(message.body) as Notification;
        callback(parsedMessage);
      } catch (error) {
        console.error('Error parsing JSON:', message.body);
      }
    });
  }

public disconnect(): void{
  console.log("disconnect");
  if(this.stompClient){
    this.stompClient.disconnect();
  }
}


}
