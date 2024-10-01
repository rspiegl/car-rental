import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { CarService } from 'app/car/car.service';
import { CarDTO } from 'app/car/car.model';


@Component({
  selector: 'app-car-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './car-list.component.html'})
export class CarListComponent implements OnInit, OnDestroy {

  carService = inject(CarService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  cars?: CarDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@car.delete.success:Car was removed successfully.`,
      'car.rental.car.referenced': $localize`:@@car.rental.car.referenced:This entity is still referenced by Rental ${details?.id} via field Car.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.loadData();
    this.navigationSubscription = this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.loadData();
      }
    });
  }

  ngOnDestroy() {
    this.navigationSubscription!.unsubscribe();
  }
  
  loadData() {
    this.carService.getAllCars()
        .subscribe({
          next: (data) => this.cars = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(id: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.carService.deleteCar(id)
          .subscribe({
            next: () => this.router.navigate(['/cars'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => {
              if (error.error?.code === 'REFERENCED') {
                const messageParts = error.error.message.split(',');
                this.router.navigate(['/cars'], {
                  state: {
                    msgError: this.getMessage(messageParts[0], { id: messageParts[1] })
                  }
                });
                return;
              }
              this.errorHandler.handleServerError(error.error)
            }
          });
    }
  }

}
