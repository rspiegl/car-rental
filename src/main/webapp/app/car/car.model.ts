export class CarDTO {

  constructor(data:Partial<CarDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  description?: string|null;

}
