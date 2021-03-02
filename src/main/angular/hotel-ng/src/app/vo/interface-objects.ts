export class HotelVO {
	
	hotelID: string;
	title: string;
	shortDescription: string;
	longDescription: string;
	location: string;
	stars: number;
	previewImage: string;
	
}


export class HotelImagesVO {
	
	images: string[];
	hotelId: string;
	
}