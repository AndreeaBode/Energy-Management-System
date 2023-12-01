import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms'; // Importați FormBuilder și FormGroup
import { EnergyService } from '../../services/energy.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss']
})
export class EditUserComponent {
  userForm: FormGroup; 

  constructor(
    public dialogRef: MatDialogRef<EditUserComponent>,
    public energyService : EnergyService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public router:Router,
    private formBuilder: FormBuilder 
  ) {
    
    this.userForm = this.formBuilder.group({
      name: [data.name, Validators.required],
      email: [data.email, [Validators.required, Validators.email]],
      
    });
  }

  save(): void {
    console.log('Starea formularului înainte de validare:', this.userForm.value);
    if (this.userForm.valid) {
      const { name, email } = this.userForm.value;
      const updatedUser = { name, email };
      const userId = this.data.id;
  
      if (userId) {
        this.energyService.updateUser(userId, updatedUser)
          .subscribe(
            user => {
              console.log('Utilizatorul a fost actualizat cu succes:', user);
              this.dialogRef.close(user);
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
