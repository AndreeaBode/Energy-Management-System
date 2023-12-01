import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { EnergyService } from 'src/app/services/energy.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-delete-device',
  templateUrl: './delete-device.component.html',
  styleUrls: ['./delete-device.component.scss']
})
export class DeleteDeviceComponent {
  constructor(
    public dialogRef: MatDialogRef<DeleteDeviceComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public router: Router,
    private energyService: EnergyService 
  ) {}

  onNoClick(): void {
    this.dialogRef.close(false);
  }

  onYesClick(): void {
    console.log("Daa");
    console.log(this.data.id);
    this.energyService.deleteDevice(this.data.id)
      .subscribe(
        response => {
          console.log('Utilizatorul a fost șters cu succes:', response);
          this.dialogRef.close(true);
          window.location.reload();
        },
        error => {
          console.error('Eroare la ștergerea utilizatorului:', error);
        }
      );
  }
}
