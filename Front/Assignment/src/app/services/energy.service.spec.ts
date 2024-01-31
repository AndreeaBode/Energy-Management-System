import { TestBed } from '@angular/core/testing';
import { EnergyService} from './energy.service'

describe('HeroService', () => {
  let service: EnergyService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EnergyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
