import { HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { catchError, retry, Subscription, throwError } from 'rxjs';
import { Environment } from '../../environments/environment';
import { RestService } from '../rest-service';
import { HotelVO } from '../vo/interface-objects';

@Component({
  selector: 'app-hotel-view',
  templateUrl: './hotel-view.component.html',
  styleUrls: ['./hotel-view.component.css']
})
export class HotelViewComponent implements OnInit {
	
	private hotelUrl = Environment.getHotelHost() + "/hotel/info/";
	
	@Input()
	hotelId: string;
	
	sub: Subscription;
	error: string;
	hotel: HotelVO;

    isLoading: boolean;
	
    constructor(private rest: RestService) {
        this.rest = rest;
        this.hotel = new HotelVO();
    }
    ngOnInit() {
        this.isLoading = true;
        console.log('Starting REST call');
        if (this.hotel || this.hotel === new HotelVO()) {
            if (this.sub)
                this.sub.unsubscribe();
            this.sub = this.rest.sendGet<HotelVO>(this.hotelUrl + this.hotelId, new HttpHeaders({
                'content-type': 'application/json'
            })).pipe(
                retry(3),
                catchError(err => {
                    this.isLoading = false;
                    return this.handleError(err);
                })
            )
            .subscribe(r => {
                if (!r || !r.body || r.body == null) {
                    this.error = 'Invalid ID, hotel not found';
                    this.isLoading = false;
                } else if (r.ok) {
                    this.error = undefined;
                    console.log('Rest call finished');
                    this.isLoading = false;
                    this.hotel = r.body;
                }
            });
        }
    }
    fakeArray(i: any) {
        return new Array(i);
    }

    private handleError(err: HttpErrorResponse) {
        console.error('Error during REST call for hotel details', err);
        this.error = err.message;
        return throwError(() => new Error(this.error));
    }
}