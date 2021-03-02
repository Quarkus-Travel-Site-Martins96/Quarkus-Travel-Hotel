import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { ToolbarComponent } from './toolbar/toolbar.component';
import { HotelViewComponent } from './hotel-view/hotel-view.component';
import { GalleryViewComponent } from './gallery-view/gallery-view.component';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    HotelViewComponent,
    GalleryViewComponent
  ],
  imports: [
    BrowserModule,
	HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
