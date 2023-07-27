import { Component, OnInit } from '@angular/core';
import { trigger, style, animate, transition } from '@angular/animations';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  animations: [
    trigger('slideAnimation', [
      transition(':increment', [
        style({ transform: 'translateX(0%)' }),
        animate('2.7s', style({ transform: 'translateX(-100%)' })),
      ]),
      transition(':decrement', [
        style({ transform: 'translateX(-100%)' }),
        animate('2.7s', style({ transform: 'translateX(0%)' })),
      ]),
    ]),
  ],
})
export class HomeComponent{
  images: any[] = [
    { url: 'assets/image1.jpg' },
    { url: 'assets/image2.jpg' },
    { url: 'assets/image3.jpg' },
    {url:  'assets/image4.jpg'}
    // Add more image URLs as needed
  ];

  currentSlideIndex = 0;

  ngOnInit() {
    this.startAutoSlide();
  }

  startAutoSlide() {
    setInterval(() => {
      this.slideToNext();
    }, 2700);
  }

  slideToNext() {
    this.currentSlideIndex = (this.currentSlideIndex + 1) % this.images.length;
  }

  slideToPrevious() {
    this.currentSlideIndex = (this.currentSlideIndex - 1 + this.images.length) % this.images.length;
  }
}
