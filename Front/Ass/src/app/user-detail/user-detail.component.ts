import { Component, OnInit, ViewChild } from '@angular/core';
import { EnergyService } from '../services/energy.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { EditUserComponent } from './edit-user/edit-user.component';
import { User } from '../user';
import { MatPaginator } from '@angular/material/paginator';
import { DeleteUserComponent } from './delete-user/delete-user.component';


@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.scss']
})
export class UserDetailComponent implements OnInit {
  users: User[] = [];
  displayedColumns: string[] = ['id', 'name', 'email', 'role', 'actions'];
  dataSource = new MatTableDataSource<User>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private userService: EnergyService, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.userService.getUsers().subscribe(users => {
      this.users = users;
      this.dataSource.data = this.users;
      this.dataSource.paginator = this.paginator;
    });
  }

 edit(user: User): void {
    const dialogRef = this.dialog.open(EditUserComponent, {

      data: user 
    });
  
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed', result);
    });
  }
  
  delete(userId: number) {
    const dialogRef = this.dialog.open(DeleteUserComponent, {
      data: {
        id: userId, 
        message: 'Are you sure you want to delete this user?' 
      } 
    });
  }  
  
}
