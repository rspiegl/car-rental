import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { RentalService } from 'app/rental/rental.service';
import { RentalDTO } from 'app/rental/rental.model';


@Component({
  selector: 'app-rental-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './rental-list.component.html'})
export class RentalListComponent implements OnInit, OnDestroy {

  rentalService = inject(RentalService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  rentals?: RentalDTO[];
  navigationSubscription?: Subscription;

  getCount(): number {
    return this.rentals?.length || 0;
  }

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@rental.delete.success:Rental was removed successfully.`    };
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
    this.rentalService.getAllRentals()
        .subscribe({
          next: (data) => this.rentals = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(id: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.rentalService.deleteRental(id)
          .subscribe({
            next: () => this.router.navigate(['/rentals'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => this.errorHandler.handleServerError(error.error)
          });
    }
  }

}
