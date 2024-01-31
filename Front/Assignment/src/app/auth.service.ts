import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private isAuthenticated: boolean = false;

  constructor() { }

  login() {
    this.isAuthenticated = true;
  }

  logout() {
    this.isAuthenticated = false;
  }
  isLoggedIn(): boolean {
    return !!localStorage.getItem('jwtToken');
  }

  getToken(): string | null {
    return localStorage.getItem('jwtToken');
  }

  userRole(): string {
    const token = this.getToken();

    if (token) {
      const tokenParts = token.split('.');

      if (tokenParts.length === 3) {
        const payload = JSON.parse(atob(tokenParts[1]));
        const userRole = payload.role;
        return userRole || 'user';
      }
    }
    return 'user';
  }

  userId(): number {
    const token = this.getToken();


    if (token) {
      const tokenParts = token.split('.');

      if (tokenParts.length === 3) {
        const payload = JSON.parse(atob(tokenParts[1]));
        const userId = payload.userId;

        return userId || 0;
      }
    }
    return 0;
  }
  isTokenExpired(): boolean {
    const token = this.getToken();
  
    if (!token) {
      return true; 
    }
  
    const tokenParts = token.split('.');
    if (tokenParts.length === 3) {
      const payload = JSON.parse(atob(tokenParts[1]));
      const expire = payload.expire;
      if (expire && expire !== 0) {
        const currentTime = Math.floor(Date.now() / 1000); 
        return expire < currentTime; 
      }
    }
  
    return true;
  }
  
}
