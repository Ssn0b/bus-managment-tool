import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PhotoCarouselComponent } from './photo-carousel.component';

describe('PhotoCarouselComponent', () => {
  let component: PhotoCarouselComponent;
  let fixture: ComponentFixture<PhotoCarouselComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PhotoCarouselComponent]
    });
    fixture = TestBed.createComponent(PhotoCarouselComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
