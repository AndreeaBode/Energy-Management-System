import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms'; 
import { EnergyService } from '../../services/energy.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-device',
  templateUrl: './add-device.component.html',
  styleUrls: ['./add-device.component.scss']
})
export class AddDeviceComponent {
  deviceForm: FormGroup; 

  constructor(
    public dialogRef: MatDialogRef<AddDeviceComponent>,
    public energyService : EnergyService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public router:Router,
    private formBuilder: FormBuilder 
  ) {
   
    this.deviceForm = this.formBuilder.group({
      name: ['', Validators.required],
      address: ['', [Validators.required]],
      description: ['', Validators.required],
      maximumHourlyEnergyConsumption: ['', [Validators.required]],
    });
  }

  add() {
    if (this.deviceForm.valid) {
      const deviceData = this.deviceForm.value;
      this.energyService.addDevice(deviceData).subscribe(
        (response) => {
          console.log('Dispozitiv adăugat cu succes:', response);
          this.dialogRef.close();
          window.location.reload();
        },
        (error) => {
          console.error('Eroare la adăugarea dispozitivului:', error);
        }
      );
    } else {
      this.deviceForm.markAllAsTouched();
    }}

  

  close(): void {
    this.dialogRef.close();
  }
}
