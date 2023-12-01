import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms'; 
import { EnergyService } from '../../services/energy.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-edit-device',
  templateUrl: './edit-device.component.html',
  styleUrls: ['./edit-device.component.scss']
})
export class EditDeviceComponent {
  deviceForm: FormGroup; 

  constructor(
    public dialogRef: MatDialogRef<EditDeviceComponent>,
    public energyService : EnergyService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public router:Router,
    private formBuilder: FormBuilder 
  ) {
   
    this.deviceForm = this.formBuilder.group({
      name: [data.name, Validators.required],
      address: [data.address, [Validators.required]],
      description: [data.description, Validators.required],
      maximumHourlyEnergyConsumption: [data.maximumHourlyEnergyConsumption, [Validators.required]],
    });
  }

  save(): void {
    console.log('Starea formularului înainte de validare:', this.deviceForm.value);
    if (this.deviceForm.valid) {
      const { name, address, description,maximumHourlyEnergyConsumption } = this.deviceForm.value;
      const updatedDevice = { name, address, description,maximumHourlyEnergyConsumption };
      const deviceId = this.data.id;
      console.log("Id=")
  console.log(this.deviceForm.value);
      if (deviceId) {
        console.log("daaa")
        this.energyService.updateDevice(deviceId, updatedDevice)
          .subscribe(
            device => {
              console.log("Iddd")
              console.log(deviceId)
              console.log('Utilizatorul a fost actualizat cu succes:', device);
              this.dialogRef.close(device);
              window.location.reload();
            },
            error => {
              console.error('Eroare la actualizarea utilizatorului:', error);
            }
          );
      } else {
        console.error('ID-ul utilizatorului nu este definit.');
      }
    } else {
      console.error('Formularul nu este valid.');
    }
  }
  

  

  close(): void {
    this.dialogRef.close();
  }
}
