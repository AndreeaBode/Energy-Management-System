import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDeviceToUserComponent } from './add-device-to-user.component';

describe('AddDeviceToUserComponent', () => {
  let component: AddDeviceToUserComponent;
  let fixture: ComponentFixture<AddDeviceToUserComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddDeviceToUserComponent]
    });
    fixture = TestBed.createComponent(AddDeviceToUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
