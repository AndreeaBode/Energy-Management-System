import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyDevicesComponent } from './my-devices.component';

describe('MyDevicesComponent', () => {
  let component: MyDevicesComponent;
  let fixture: ComponentFixture<MyDevicesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MyDevicesComponent]
    });
    fixture = TestBed.createComponent(MyDevicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
