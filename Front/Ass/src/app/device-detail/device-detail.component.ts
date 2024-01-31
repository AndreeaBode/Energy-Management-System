import { Component, OnInit, ViewChild } from '@angular/core';
import { EnergyService } from '../services/energy.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { Device } from '../device';
import { User } from '../user';
import { MatPaginator } from '@angular/material/paginator';
import { EditDeviceComponent } from './edit-device/edit-device.component';
import { DeleteDeviceComponent } from './delete-device/delete-device.component';
import { AddDeviceComponent } from './add-device/add-device.component';
import { AuthService } from '../auth.service';
import { AddDeviceToUserComponent } from './add-device-to-user/add-device-to-user.component';

@Component({
  selector: 'app-device-detail',
  templateUrl: './device-detail.component.html',
  styleUrls: ['./device-detail.component.scss']
})
export class DeviceDetailComponent implements OnInit {

  selectedUserId: number = 0;
  devices:Device[] = [];
  displayedColumns: string[] = ['id', 'name', 'description','address', 'maximumHourlyEnergyConsumption', 'actions'];
  dataSource = new MatTableDataSource<Device>();
  users: User[] = [];
  
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private deviceService: EnergyService, 
    public dialog: MatDialog,
     private authService: AuthService,
      private userService: EnergyService) { }

  ngOnInit(): void {

    this.deviceService.getDevices().subscribe(devices => {
      console.log(devices);
      this.devices = devices;
      this.dataSource.data = this.devices;
      this.dataSource.paginator = this.paginator;

      this.userService.getUsersByRole().subscribe(users => {
        this.users = users;
      });
    });
  }

 edit(device: Device): void {
    const dialogRef = this.dialog.open(EditDeviceComponent, {
      data: device
    });
  
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed', result);
    });
  }
  
  delete(deviceId: number) {
    const dialogRef = this.dialog.open(DeleteDeviceComponent, {
      data: {
        id: deviceId, 
        message: 'Are you sure you want to delete this user?' 
      } 
    });
  }  

  add(): void {
    const dialogRef = this.dialog.open(AddDeviceComponent, {
    });
  
    dialogRef.afterClosed().subscribe(newDevice => {
      if (newDevice) {
        this.devices.push(newDevice);
        this.dataSource.data = this.devices;
      }
    });
  }

  isAdmin(): boolean {
    return this.authService.userRole() === 'Admin';
  }
  
  addDeviceToUser1(deviceId: number) {
    const dialogRef = this.dialog.open(AddDeviceToUserComponent, {
     data: {
        users: this.users,
        deviceId: deviceId 
     }
    });

  }
  

}
