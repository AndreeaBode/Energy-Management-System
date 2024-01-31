import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TokenInterceptor } from './token-interceptor'; 
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MessagesComponent } from './messages/messages.component';
import { LoginComponent } from './login/login.component';
import { UserDetailComponent } from './user-detail/user-detail.component';
import { DeviceDetailComponent } from './device-detail/device-detail.component';
import { TitlecasePipe } from './titlecase.pipe';
import { EditUserComponent } from './user-detail/edit-user/edit-user.component';
import { MatDialogModule } from '@angular/material/dialog';
import { DeleteUserComponent } from './user-detail/delete-user/delete-user.component';
import { EditDeviceComponent } from './device-detail/edit-device/edit-device.component';
import { AddDeviceComponent } from './device-detail/add-device/add-device.component';
import { DeleteDeviceComponent } from './device-detail/delete-device/delete-device.component';
import { MyDevicesComponent } from './device-detail/my-devices/my-devices.component';
import { AddDeviceToUserComponent } from './device-detail/add-device-to-user/add-device-to-user.component';
import { HomePageComponent } from './home-page/home-page.component';
import { ToastrModule } from 'ngx-toastr';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { CommonModule } from '@angular/common';
import { ChartComponent } from './chart/chart.component';
import { NgChartsModule } from 'ng2-charts';
import { ChatComponent } from './chat/chat.component';
import { ChatAdminComponent } from './chat-admin/chat-admin.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule, 
    MatInputModule, 
    MatSelectModule,
    MatTableModule,
    MatIconModule,
    MatPaginatorModule,
    MatSortModule,
    MatDialogModule,
    CommonModule,
    MatDatepickerModule,
    NgChartsModule,
    MatNativeDateModule,
    MatSnackBarModule,
    ToastrModule.forRoot({
      timeOut: 10000,
      positionClass: 'toast-bottom-right',
      preventDuplicates: true,
    }),
  ],
  declarations: [
    AppComponent,
    MessagesComponent,
    LoginComponent,
    UserDetailComponent,
    DeviceDetailComponent,
    TitlecasePipe,
    EditUserComponent,
    DeleteUserComponent,
    EditDeviceComponent,
    AddDeviceComponent,
    DeleteDeviceComponent,
    MyDevicesComponent,
    AddDeviceToUserComponent,
    HomePageComponent,
    ChartComponent,
    MyDevicesComponent,
    ChatComponent,
    ChatAdminComponent,

  ],
  exports: [
    MyDevicesComponent,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,
    },
  ],
  bootstrap: [ AppComponent ],
})
export class AppModule { }
