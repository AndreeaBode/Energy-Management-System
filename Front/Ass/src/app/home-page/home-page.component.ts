import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../auth.service';
import { EnergyService } from '../services/energy.service';
import { WebSocketService } from '../services/web-socket.service';
import { OnInit, ViewChild } from '@angular/core';
import { ToastContainerDirective } from 'ngx-toastr';

export interface Notification{
  idDevice:number;
  maximumHourlyEnergyConsumption: number;
  sum: number;
  message: string;
}

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent {
 

  constructor( private webSocketService: WebSocketService,
    private authService: AuthService,
    private toastr: ToastrService,
    private energyService: EnergyService){}

ngOnInit(): void{
    this.connectWebSocket();
  }
  
 
  onClick() {
    this.toastr.success('in div');
  }
  
  private connectWebSocket(): void {
    this.webSocketService.connect().subscribe(() => {
      this.webSocketService.subscribe('/topic/socket/client', (message: Notification) => {
        const userUid = this.authService.userId();
        this.energyService.getMyDevices(userUid).subscribe(
          (response) => { 
            const devices = response;
            console.log(devices);
            if (devices.length > 0) {
              devices.forEach(device => {
                console.log("Mes");
                console.log(message.idDevice)
                if (device.id === message.idDevice) {
                  console.log("Notificareeeeee");
                  this.toastr.warning(`Pentru dispozitivul cu ID ${message.idDevice}, s-a depășit consumul maxim orar de energie (${message.maximumHourlyEnergyConsumption}). Energia actuală este ${message.sum}.`, "Hourly Energy Consumption Exceeded", {
                    positionClass: 'toast-top-right',
                    timeOut: 100000,
                    progressBar: true,
                    progressAnimation: 'increasing'
                  });                  
                }
              })
            }
          }, (error) => {
            console.log(error);
          }
        )
      })
    })
  }
  showSuccess() {
    this.toastr.success('Hello world!', 'Toastr fun!');
  }


}
