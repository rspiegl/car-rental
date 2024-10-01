export class RentalDTO {

  constructor(data:Partial<RentalDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  mileage?: number|null;
  car?: number|null;
  customer?: number|null;

}
