import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { CarListComponent } from './car/car-list.component';
import { CarAddComponent } from './car/car-add.component';
import { CarEditComponent } from './car/car-edit.component';
import { CustomerListComponent } from './customer/customer-list.component';
import { CustomerAddComponent } from './customer/customer-add.component';
import { CustomerEditComponent } from './customer/customer-edit.component';
import { RentalListComponent } from './rental/rental-list.component';
import { RentalAddComponent } from './rental/rental-add.component';
import { RentalEditComponent } from './rental/rental-edit.component';
import { ErrorComponent } from './error/error.component';


export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: $localize`:@@home.index.headline:Welcome to your new app!`
  },
  {
    path: 'cars',
    component: CarListComponent,
    title: $localize`:@@car.list.headline:Cars`
  },
  {
    path: 'cars/add',
    component: CarAddComponent,
    title: $localize`:@@car.add.headline:Add Car`
  },
  {
    path: 'cars/edit/:id',
    component: CarEditComponent,
    title: $localize`:@@car.edit.headline:Edit Car`
  },
  {
    path: 'customers',
    component: CustomerListComponent,
    title: $localize`:@@customer.list.headline:Customers`
  },
  {
    path: 'customers/add',
    component: CustomerAddComponent,
    title: $localize`:@@customer.add.headline:Add Customer`
  },
  {
    path: 'customers/edit/:id',
    component: CustomerEditComponent,
    title: $localize`:@@customer.edit.headline:Edit Customer`
  },
  {
    path: 'rentals',
    component: RentalListComponent,
    title: $localize`:@@rental.list.headline:Rentals`
  },
  {
    path: 'rentals/add',
    component: RentalAddComponent,
    title: $localize`:@@rental.add.headline:Add Rental`
  },
  {
    path: 'rentals/edit/:id',
    component: RentalEditComponent,
    title: $localize`:@@rental.edit.headline:Edit Rental`
  },
  {
    path: 'error',
    component: ErrorComponent,
    title: $localize`:@@error.headline:Error`
  },
  {
    path: '**',
    component: ErrorComponent,
    title: $localize`:@@notFound.headline:Page not found`
  }
];
