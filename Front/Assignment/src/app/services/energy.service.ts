import { catchError, tap } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MessageService } from './message.service';
import { Observable, of } from 'rxjs';
import { User } from '../user';
import { Device } from '../device';
import { map } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { Chat } from '../models/chat';
import { Message } from '../models/message';
import { BehaviorSubject} from 'rxjs';

@Injectable({ providedIn: 'root' })
export class EnergyService {
  private userUrl = 'http://localhost:8080';  
  private deviceUrl = 'http://localhost:8081/device';
  private measureUrl = 'http://localhost:9001';
  private chatUrl = 'http://localhost:8005/chat'

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  private typingSubject = new BehaviorSubject<string>('');
  typing$: Observable<string> = this.typingSubject.asObservable();


  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.userUrl)
      .pipe(
        tap(_ => this.log('fetched users')),
        catchError(this.handleError<User[]>('getUsers', []))
      );
  }


updateUser(userId: number, updatedUser: { name: string, email: string }): Observable<User> {
  console.log(userId);
  const updateUrl = `${this.userUrl}/update/${userId}`;
  return this.http.put<User>(updateUrl, updatedUser, this.httpOptions)
    .pipe(
      tap(_ => this.log(`updated user with id=${userId}`)),
      catchError(this.handleError<User>('updateUser'))
    );
}


deleteUser(userId: number): Observable<User> {
  const deleteUrl = `${this.userUrl}/delete/${userId}`;

  return this.http.delete<User>(deleteUrl, this.httpOptions)
    .pipe(
      tap(_ => this.log(`deleted user with id=${userId}`)),
      catchError(this.handleError<User>('deleteUser'))
    );
}

getDevices(): Observable<Device[]> {
  const deviceUrl2 = `${this.deviceUrl}/getAllDevices`;
  return this.http.get<Device[]>(deviceUrl2)
    .pipe(
      tap(_ => this.log('fetched devices')),
      catchError(this.handleError<Device[]>('getDevices', []))
    );
}


updateDevice(deviceId: number, updatedUser: { name: string, 
  description: string,address: string,maximumHourlyEnergyConsumption: number  }): Observable<User> {
 console.log("hei")
    console.log(deviceId);
  const updateUrl = `${this.deviceUrl}/update/${deviceId}`;
  console.log(updateUrl);
  return this.http.put<User>(updateUrl, updatedUser, this.httpOptions)
    .pipe(
      tap(_ => this.log(`updated user with id=${deviceId}`)),
      catchError(this.handleError<User>('updateDevice'))
    );
}


deleteDevice(deviceId: number): Observable<User> {
  const deleteUrl2 = `${this.deviceUrl}/delete/${deviceId}`;

  return this.http.delete<User>(deleteUrl2, this.httpOptions)
    .pipe(
      tap(_ => this.log(`deleted device with id=${deviceId}`)),
      catchError(this.handleError<User>('deleteDevice'))
    );
}

addDevice(device: Device): Observable<Device> {
  const addDeviceUrl = `${this.deviceUrl}/addDevices`; 

  return this.http.post<Device>(addDeviceUrl, device, this.httpOptions)
    .pipe(
      tap((addedDevice: Device) => this.log(`added device with id=${addedDevice.id}`)),
      catchError(this.handleError<Device>('addDevice'))
    );
}

addDeviceToUser3(userId: number, deviceId: number): Observable<any> {
  console.log("Am ajuns");
  console.log(userId);
  console.log(deviceId);
  const body = { userId: userId, deviceId: deviceId };
  const addDeviceToUserUrl = 'http://localhost:8081/device/addDeviceToUser';
  return this.http.post<any>(addDeviceToUserUrl, body, this.httpOptions)
    .pipe(
      tap(_ => this.log('added device to user')),
      catchError(this.handleError('addDeviceToUser'))
    );
}


getMyDevices(userId: number): Observable<Device[]> {
  const url = `${this.deviceUrl}/mapping/${userId}`;
  return this.http.get<Device[]>(url);
}

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }

  getUsersByRole(): Observable<User[]> {
    return this.http.get<User[]>(this.userUrl)
      .pipe(
        map(users => users.filter(user => user.role === 'User')),
        tap(_ => this.log('fetched users')),
        catchError(this.handleError<User[]>('getUsers', []))
      );
  }

  private log(message: string) {
    this.messageService.add(`EnergyService: ${message}`);
  }

  getUsersForDevice(deviceId: string): Observable<any[]> {
    console.log("GetUsers")
    return this.http.get<any[]>(`${this.deviceUrl}/notification/${deviceId}`);
  }

  getChartsDataForAllDevices(deviceId: number, selectedDay: Date): Observable<any[]> {
    const formattedDate = this.formatDate(selectedDay);
    const url = `${this.measureUrl}/charts/${deviceId}?date=${formattedDate}`;
    return this.http.get<any[]>(url);
}

  private formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
  }


}
