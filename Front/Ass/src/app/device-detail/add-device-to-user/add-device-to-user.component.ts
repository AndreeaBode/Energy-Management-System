import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { User } from 'src/app/user';
import { EnergyService } from 'src/app/services/energy.service';

@Component({
  selector: 'app-add-device-to-user',
  templateUrl: './add-device-to-user.component.html',
  styleUrls: ['./add-device-to-user.component.scss']
})
export class AddDeviceToUserComponent {
  selectedUserId: number = 0;
  users: User[];
  deviceId: number = 0; 

  constructor(
    public dialogRef: MatDialogRef<AddDeviceToUserComponent>,
    public energyService: EnergyService,
    @Inject(MAT_DIALOG_DATA) public data: { users: User[], deviceId: number } 
  ) {
    this.users = data.users;
    this.deviceId = data.deviceId; 
  }
  addDeviceToUser2(): void {
    if (this.selectedUserId !== 0 && this.deviceId !== 0) { 
      this.energyService.addDeviceToUser3(this.selectedUserId, this.deviceId)
      .subscribe(response => {
        this.dialogRef.close();
      }, error => {
        console.error(error);
      });
    } else {
      console.log('Selectați un utilizator valid și un dispozitiv valid.');
    }
  }
  

  closeDialog(): void {
    this.dialogRef.close();
  }
}
