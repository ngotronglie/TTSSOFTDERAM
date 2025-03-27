import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserHelloComponent } from './user-hello.component';

describe('UserHelloComponent', () => {
  let component: UserHelloComponent;
  let fixture: ComponentFixture<UserHelloComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserHelloComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(UserHelloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
