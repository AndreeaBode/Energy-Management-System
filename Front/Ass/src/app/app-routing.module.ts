import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { UserDetailComponent } from './user-detail/user-detail.component';
import { DeviceDetailComponent } from './device-detail/device-detail.component';
import { AuthGuard } from './auth.guard';
import { MyDevicesComponent } from './device-detail/my-devices/my-devices.component';
import { HomePageComponent } from './home-page/home-page.component';
import { ChartComponent } from './chart/chart.component';
import { ChatComponent } from './chat/chat.component';
import { ChatAdminComponent } from './chat-admin/chat-admin.component';

const routes: Routes = [
  { path: '', redirectTo: '', pathMatch: 'full' },
  { path: 'login', component: LoginComponent},
  { path: '', component: HomePageComponent},
  { path: 'user-detail', component: UserDetailComponent , canActivate: [AuthGuard], data: { roles: ['Admin'] }},
  { path: 'device-detail', component: DeviceDetailComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] }  },
  { path: 'my-devices', component:MyDevicesComponent,  canActivate: [AuthGuard], data: { roles: ['User'] } },
  { path: 'chart', component:ChartComponent,  canActivate: [AuthGuard], data: { roles: ['User'] } },
  { path: 'chat/:user', component:ChatComponent,  canActivate: [AuthGuard], data: { roles: ['User'] } },
  { path: 'chat-admin/:admin', component:ChatAdminComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] }  },
  {path: 'chat/:userId', component: ChatComponent}
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
