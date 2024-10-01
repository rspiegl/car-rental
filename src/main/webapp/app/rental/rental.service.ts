import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { RentalDTO } from 'app/rental/rental.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class RentalService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/rentals';

  getAllRentals() {
    return this.http.get<RentalDTO[]>(this.resourcePath);
  }

  getRental(id: number) {
    return this.http.get<RentalDTO>(this.resourcePath + '/' + id);
  }

  createRental(rentalDTO: RentalDTO) {
    return this.http.post<number>(this.resourcePath, rentalDTO);
  }

  updateRental(id: number, rentalDTO: RentalDTO) {
    return this.http.put<number>(this.resourcePath + '/' + id, rentalDTO);
  }

  deleteRental(id: number) {
    return this.http.delete(this.resourcePath + '/' + id);
  }

  getCarValues() {
    return this.http.get<Record<string,string>>(this.resourcePath + '/carValues')
        .pipe(map(transformRecordToMap));
  }

  getCustomerValues() {
    return this.http.get<Record<string,number>>(this.resourcePath + '/customerValues')
        .pipe(map(transformRecordToMap));
  }

}
