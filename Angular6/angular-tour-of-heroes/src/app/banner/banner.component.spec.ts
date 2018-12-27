import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BannerComponent } from './banner.component';

describe('BannerComponent', () => {
  let component: BannerComponent;
  let fixture: ComponentFixture<BannerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BannerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BannerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  
});

describe('BannerComponent (minimal)', () => {
  it('should create', () => {
    TestBed.configureTestingModule({
      declarations: [ BannerComponent ]
    });
    //The createComponent method freezes the current TestBed definition, closing it to further configuration.
    //You cannot call any more TestBed configuration methods, not configureTestingModule(), nor get(), nor any of the override... methods. 
    //If you try, TestBed throws an error.
    const fixture = TestBed.createComponent(BannerComponent);
    const component = fixture.componentInstance;
    expect(component).toBeDefined();
  });
});
