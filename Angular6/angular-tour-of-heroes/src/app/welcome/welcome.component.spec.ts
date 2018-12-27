import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import {UserService} from './UserService';
import {MockUserService} from './MockUserService';

import { WelcomeComponent } from './welcome.component';

describe('WelcomeComponent', () => {
  let userService: UserService;
  let comp: WelcomeComponent;
  let fixture: ComponentFixture<WelcomeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
       // provide the component-under-test and dependent service
       providers: [
        WelcomeComponent, 
        { provide: UserService, useClass: MockUserService }
       ],
       declarations: [WelcomeComponent]
     })
    .compileComponents();

    userService = TestBed.get(UserService);

    fixture = TestBed.createComponent(WelcomeComponent);
    comp = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(comp).toBeTruthy();
  });

  it('should not have welcome message after construction', () => {
    comp = TestBed.get(WelcomeComponent);
    expect(comp.welcome).toBeUndefined();
  });
  
  it('should welcome logged in user after Angular calls ngOnInit', () => {
    expect(comp.welcome).toContain(userService.user.name);
  });
  
  it('should ask user to log in if not logged in after ngOnInit', () => {
    userService.isLoggedIn = false;
    comp.ngOnInit();
    expect(comp.welcome).not.toContain(userService.user.name);
    expect(comp.welcome).toContain('log in');
  });
});
