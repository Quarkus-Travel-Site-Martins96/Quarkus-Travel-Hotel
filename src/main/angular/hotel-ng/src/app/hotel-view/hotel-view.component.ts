import { HttpHeaders } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { host } from '../../environments/environment';
import { RestService } from '../rest-service';
import { HotelVO } from '../vo/interface-objects';

const hotelUrl = host + "/hotel/info/";

@Component({
  selector: 'app-hotel-view',
  templateUrl: './hotel-view.component.html',
  styleUrls: ['./hotel-view.component.css']
})
export class HotelViewComponent implements OnInit {
	
	@Input()
	hotelId: string;
	
	sub: Subscription;
	error: string;
	hotel: HotelVO;
	
    constructor(private rest: RestService) {
        this.rest = rest;
        this.hotel = new HotelVO();
    }
    ngOnInit() {
        if (this.hotel || this.hotel === new HotelVO()) {
            if (this.sub)
                this.sub.unsubscribe();
            this.sub = this.rest.sendGet<HotelVO>(hotelUrl + this.hotelId, new HttpHeaders({
                'content-type': 'application/json'
            })).subscribe(r => {
                this.hotel = r.body;
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