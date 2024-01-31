// typing.service.ts
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TypingService {
  private isTypingSubject: Subject<boolean> = new Subject<boolean>();

  setIsTyping(isTyping: boolean): void {
    this.isTypingSubject.next(isTyping);
  }

  getCurrentIsTypingValue(): Observable<boolean> {
    return this.isTypingSubject.asObservable();
  }

  
}
