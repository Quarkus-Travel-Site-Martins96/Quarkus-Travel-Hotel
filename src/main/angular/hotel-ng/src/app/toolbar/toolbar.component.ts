import { HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import { catchError, Subscription, throwError } from 'rxjs';
import { Environment } from 'src/environments/environment';
import { CookieManager } from '../cookie-utils';
import { RestService } from '../rest-service';
import { JWT } from './jwt-decode-vo';

@Component({
	selector: 'app-toolbar',
	templateUrl: './toolbar.component.html',
	styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit, AfterViewInit {
	
	private validateUrl: string = Environment.getLoginHost() + "/validate";

	homeUrl: string = Environment.getHomeHost();
	loginUrl: string = Environment.getLoginHost();
	userManagementUrl: string = Environment.getUserManagerHost();

	jwt: JWT = new JWT();
	cookieJWT: string;

	private sub: Subscription;

	constructor(private cookieMng: CookieManager, private rest: RestService) {
		this.cookieJWT = this.cookieMng.getCookie('user.jwt');
	}


	ngOnInit(): void {
		if (this.sub)
			this.sub.unsubscribe();

		if (this.cookieJWT) {
			
			//JWT found, check if is valid
			this.sub = this.rest.sendPost<JWT>(this.validateUrl, this.cookieJWT, new HttpHeaders({
				'content-type': 'text/plain'
			})).pipe(
                catchError(this.handleError)
			).subscribe((resp) => {
					//JWT correct, move to Home Page
					console.log('JWT loaded');
					this.jwt = resp.body;
			})
		}
	}

	ngAfterViewInit(): void {
		let prevScrollpos = window.pageYOffset;
		window.onscroll = function() {
			var currentScrollPos = window.pageYOffset;
			if (prevScrollpos > currentScrollPos) {
				document.getElementById("navbar").style.top = "0";
			} else {
				document.getElementById("navbar").style.top = "-80px";
			}
			prevScrollpos = currentScrollPos;
		}
	}
	
	logout(): void {
		this.cookieMng.deleteCookie('user.jwt');
		window.top.location.href = this.homeUrl;
	}

	private handleError(err: HttpErrorResponse) {
		//JWT non correct or service not available
		if (err.status === 401) {
			console.error('The token JWT is not valid, relogin required', err)
			this.logout();
		} else {
			console.error('The call is not end correct', err)
			this.jwt = null;
		}
        return throwError(() => new Error(err.message));
    }

}
