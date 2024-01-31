import { Component } from '@angular/core';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent {
  constructor(public authService: AuthService,
    private router: Router) { }

  ngOnInit(): void {
    console.log("Role");
    console.log(this.authService.userRole());
    if (this.authService.isTokenExpired()) {
      this.authService.logout();
      this.router.navigate(['/login']);
    }
  }

  onLogout() {
    localStorage.removeItem('jwtToken');
    this.router.navigate(['/home-page']);

  }
  title = 'Energy App';
  
}
