export class CustomerDTO {

  constructor(data:Partial<CustomerDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  name?: string|null;
  age?: number|null;
  rentals?: string[]|null;
}
