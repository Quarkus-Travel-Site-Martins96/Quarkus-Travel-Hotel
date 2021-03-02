import { HttpHeaders } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { host } from '../../environments/environment';
import { RestService } from '../rest-service';
import { HotelImagesVO } from '../vo/interface-objects';

const hotelUrl: string = host + "/hotel/images/";

@Component({
  selector: 'app-gallery-view',
  templateUrl: './gallery-view.component.html',
  styleUrls: ['./gallery-view.component.css']
})
export class GalleryViewComponent implements OnInit {
	
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
            this.sub = this.rest.sendGet<HotelImagesVO>(hotelUrl + this.hotelId, new HttpHeaders({
                'content-type': 'application/json'
            })).subscribe(r => {
                this.images = r.body;
            }, error => {
                console.error(error);
                this.error = error;
            });
        }
    }
	
    fakeArray(i: any) {
        return new Array(i);
    }
}
