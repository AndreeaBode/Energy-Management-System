import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Router } from "@angular/router";
import { Observable } from 'rxjs';
import { catchError, retry } from 'rxjs/operators'; 
import { AuthService } from './auth.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor( private router: Router, private authService: AuthService) { }

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {

    const authToken = this.authService.getToken();

    if (authToken) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${authToken}`,
        },
      });
    } 

    return next.handle(request)
      .pipe(
        retry(1),
        catchError((response: HttpErrorResponse) => {

          if (response.status === 401) {
            this.authService.logout();
            this.router.navigateByUrl('/home');
          }
          throw response; 
        })
      );
  }
}