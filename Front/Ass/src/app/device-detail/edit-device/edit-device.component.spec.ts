import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditDeviceComponent } from './edit-device.component';

describe('EditDeviceComponent', () => {
  let component: EditDeviceComponent;
  let fixture: ComponentFixture<EditDeviceComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditDeviceComponent]
    });
    fixture = TestBed.createComponent(EditDeviceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
