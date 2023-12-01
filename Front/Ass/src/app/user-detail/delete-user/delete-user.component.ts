import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { EnergyService } from 'src/app/services/energy.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-delete-user',
  templateUrl: './delete-user.component.html',
  styleUrls: ['./delete-user.component.scss']
})
export class DeleteUserComponent {
  constructor(
    public dialogRef: MatDialogRef<DeleteUserComponent>,
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
    this.energyService.deleteUser(this.data.id)
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