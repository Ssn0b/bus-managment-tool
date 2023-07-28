import { Component} from '@angular/core';

@Component({
  selector: 'app-photo-carousel',
  templateUrl: './photo-carousel.component.html',
  styleUrls: ['./photo-carousel.component.css'],
})
export class PhotoCarouselComponent {
  imgCollection: Array<object> = [
    {
      image: 'assets/image1.jpg',
      thumbImage: 'assets/image1.jpg',
      alt: 'Image 1',
      title: 'Image 1'
    }, {
      image: 'assets/image2.jpg',
      thumbImage: 'assets/image2.jpg',
      title: 'Image 2',
      alt: 'Image 2'
    }, {
      image: 'assets/image3.jpg',
      thumbImage: 'assets/image1.jpg',
      title: 'Image 3',
      alt: 'Image 3'
    }, {
      image: 'assets/image4.jpg',
      thumbImage: 'assets/image4.jpg',
      title: 'Image 4',
      alt: 'Image 4'
    }, {
      image: 'assets/image4.jpg',
      thumbImage: 'assets/image4.jpg',
      title: 'Image 4',
      alt: 'Image 4'
    }, {
      image: 'assets/image4.jpg',
      thumbImage: 'assets/image4.jpg',
      title: 'Image 4',
      alt: 'Image 4'
    }, {
      image: 'assets/image4.jpg',
      thumbImage: 'assets/image4.jpg',
      title: 'Image 4',
      alt: 'Image 4'
    }, {
      image: 'assets/image4.jpg',
      thumbImage: 'assets/image4.jpg',
      title: 'Image 4',
      alt: 'Image 4'
    }, {
      image: 'assets/image4.jpg',
      thumbImage: 'assets/image4.jpg',
      title: 'Image 4',
      alt: 'Image 4'
    }, {
      image: 'assets/image4.jpg',
      thumbImage: 'assets/image4.jpg',
      title: 'Image 4',
      alt: 'Image 4'
    }, {
      image: 'assets/image4.jpg',
      thumbImage: 'assets/image4.jpg',
      title: 'Image 4',
      alt: 'Image 4'
    }, {
      image: 'assets/image4.jpg',
      thumbImage: 'assets/image4.jpg',
      title: 'Image 4',
      alt: 'Image 4'
    }
];
}
