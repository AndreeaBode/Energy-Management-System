import { EnergyService } from 'src/app/services/energy.service';
import { AuthService } from 'src/app/auth.service';
import { ChartTypeRegistry } from 'chart.js/auto';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { MeasureDevice } from 'src/app/measure-device';
import { Chart } from 'chart.js/auto';
import { OnInit, Component } from '@angular/core';
import { Device } from 'src/app/device';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-my-devices',
  templateUrl: './my-devices.component.html',
  styleUrls: ['./my-devices.component.scss']
})
export class MyDevicesComponent implements OnInit {
  devices: Device[] = [];
  userId: number = 0;
  displayedColumns: string[] = ['id', 'name', 'description', 'address', 'maximumHourlyEnergyConsumption'];

  chartType: keyof ChartTypeRegistry = 'line';

  selectedDay: Date | null = new Date();
  
  constructor(private energyService: EnergyService, private authService: AuthService) {
    this.userId = (this.authService.userId() ?? 0) as number;
  }

  ngOnInit(): void {
    this.loadMyDevices(this.userId);
  }

  loadMyDevices(userId: number): void {
    console.log("UserId");
    console.log(userId);
    this.energyService.getMyDevices(userId)
      .subscribe(devices => {
        this.devices = devices;
      });
  }

  onDateChange(event: MatDatepickerInputEvent<Date>): void {
    console.log('Date changed:', event.value);
    this.selectedDay = event.value;
    console.log("Selected")
    console.log(this.selectedDay);
  



    this.devices.forEach(device => {
      if (this.selectedDay) {
        this.loadEnergyConsumptionForAllDevices(device.id, this.selectedDay);
      } else {
        console.error('selectedDay is not defined');
      }
    });
  }
  
  loadEnergyConsumptionForAllDevices(deviceId: number, selectedDay: Date): void {
    if (selectedDay) {
      this.energyService.getChartsDataForAllDevices(deviceId, selectedDay)
        .subscribe((data: MeasureDevice[]) => {
          console.log(`Energy consumption data for device ${deviceId}:`, data);
          const canvas = document.createElement('canvas');
          canvas.width = 400; 
          canvas.height = 300; 
          document.body.appendChild(canvas); 
  
          const ctx = canvas.getContext('2d');
  
          if (ctx) {
            const labels: string[] = data.map(entry => {
              if (Array.isArray(entry.time) && entry.time.length === 3) {
                const [hour] = entry.time;
                return `${hour}`;
              } else {
                return '';
              }
            });
            
          
            const values: number[] = data.map(entry => entry.hourlyEnergyConsumption);
          
            const chart = new Chart(ctx, {
              type: 'line',
              data: {
                labels: labels,  
                datasets: [
                  {
                    label: `Device ${deviceId}`,
                    data: values,  
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1,
                    fill: false,
                  },
                ],
              },
              options: {
                responsive: true,
                scales: {
                  x: {
                    type: 'linear',
                    position: 'bottom',
                  },
                  y: {
                    type: 'linear',
                    position: 'left',
                    suggestedMin: 0,  
                    suggestedMax: 40, 
                  },
                },
              },
            });
          
            console.log("Labels");
            console.log(labels);
            console.log("Values");
            console.log(values);
          } else {
            console.error('Canvas context is null');
          }
        });
    } else {
      console.error('selectedDay is not defined');
    }
  }
  
}
