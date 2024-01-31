import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  authType: 'login' | 'signup' = 'login';
  email: string = '';
  password: string = '';
  confirmedPassword = '';
  selectedRole = '';
  emailError: boolean = false;
  passwordError: boolean = false;
  passwordConfirmedError: boolean = false;
  errorMessage: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {}

  onLogin() {
    this.errorMessage = '';
    this.emailError = false;
    this.passwordError = false;

    if (this.authType === 'login') {
      if (!this.email || !this.password) {
        this.passwordError = true;
        this.emailError = true;
        return;
      }

      const loginData = {
        email: this.email,
        password: this.password
      };

      this.http.post('http://localhost:8080/login', loginData, {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          Authorization: `Bearer ${localStorage.getItem('jwtToken')}`
        })
      }).subscribe(
        (response: any) => {
          if (response && response.token) {
            localStorage.setItem('jwtToken', response.token);
            console.log('Login successful', response);
            this.router.navigateByUrl('/');
          } else {
            console.error('Invalid response from server');
          }
        },
        (error) => {
          console.error('Login error', error);
          this.errorMessage = 'Eroare la autentificare.';
        }
      );
    }
  }

  onSignup() {
    this.errorMessage = '';
    if (this.authType === 'signup') {
      if (!this.email || !this.password) {
        this.passwordError = true;
        this.emailError = true;
        return;
      }

      if (this.password !== this.confirmedPassword) {
        this.passwordConfirmedError = true;
        this.emailError = false;
        return;
      }

      const signupData = {
        email: this.email,
        password: this.password,
        confirmedPassword: this.confirmedPassword,
        role: 'User',
      };

      this.http.post('http://localhost:8080/register', signupData, {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          Authorization: `Bearer ${localStorage.getItem('jwtToken')}`
        })
      }).subscribe(
        (response: any) => {
          if (response && response.token) {
            console.log('Signup successful', response);
            this.authType = 'login';
          } else {
            console.error('Invalid response from server');
          }
        },
        (error) => {
          console.error('Signup error', error);
          this.errorMessage = 'Eroare la înregistrare.';
        }
      );
    }
  }

  onLogout() {
    localStorage.removeItem('jwtToken');
    this.router.navigate(['/user-detail']);
  }

  setAuthType(type: 'login' | 'signup') {
    this.authType = type;
    this.errorMessage = '';
  }
}
