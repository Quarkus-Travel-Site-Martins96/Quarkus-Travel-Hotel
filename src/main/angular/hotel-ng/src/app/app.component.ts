import { Component } from '@angular/core';
import { Environment } from '../environments/environment'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
	
	id: string;
	
	constructor() {
        let urlParams = new URLSearchParams(window.location.search);
        this.id = urlParams.get('hotel-id');
        if (!this.id) {
            window.location.href = Environment.getHomeHost();
        }
    }
	
}
