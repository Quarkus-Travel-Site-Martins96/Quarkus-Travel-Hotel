import { HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { catchError, Subscription, throwError } from 'rxjs';
import { Environment } from '../../environments/environment';
import { RestService } from '../rest-service';
import { HotelImagesVO } from '../vo/interface-objects';

@Component({
  selector: 'app-gallery-view',
  templateUrl: './gallery-view.component.html',
  styleUrls: ['./gallery-view.component.css']
})
export class GalleryViewComponent implements OnInit {
	
	private hotelUrl: string = Environment.getHotelHost() + "/hotel/images/";
	
	@Input()
	hotelId: string;
	
	sub: Subscription;
	error: string;
	images: HotelImagesVO;
	
    constructor(private rest: RestService) {
        this.rest = rest;
        this.images = new HotelImagesVO();
    }
	
    ngOnInit() {
        if (this.images || this.images === new HotelImagesVO()) {
            if (this.sub)
                this.sub.unsubscribe();
            this.sub = this.rest.sendGet<HotelImagesVO>(this.hotelUrl + this.hotelId, 
				new HttpHeaders({
	                'content-type': 'application/json'
	            })).pipe(
                    catchError(err => {
                        return this.handleError(err);
                    })
                ).subscribe(r => {
                    if (!r || !r.body || r.body == null) {
                        this.error = 'Invalid ID, hotel not found';
                    } else if (r.ok) {
                        this.error = undefined;
                        console.log('Rest call finished');
                        this.images = r.body;
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
