import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { CarDTO } from 'app/car/car.model';


@Injectable({
  providedIn: 'root',
})
export class CarService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/cars';

  getAllCars() {
    return this.http.get<CarDTO[]>(this.resourcePath);
  }

  getCar(id: number) {
    return this.http.get<CarDTO>(this.resourcePath + '/' + id);
  }

  createCar(carDTO: CarDTO) {
    return this.http.post<number>(this.resourcePath, carDTO);
  }

  updateCar(id: number, carDTO: CarDTO) {
    return this.http.put<number>(this.resourcePath + '/' + id, carDTO);
  }

  deleteCar(id: number) {
    return this.http.delete(this.resourcePath + '/' + id);
  }

}
