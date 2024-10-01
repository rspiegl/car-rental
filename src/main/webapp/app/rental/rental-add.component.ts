import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { RentalService } from 'app/rental/rental.service';
import { RentalDTO } from 'app/rental/rental.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-rental-add',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './rental-add.component.html'
})
export class RentalAddComponent implements OnInit {

  rentalService = inject(RentalService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  carValues?: Map<number,string>;
  customerValues?: Map<number,string>;

  addForm = new FormGroup({
    mileage: new FormControl(null, [Validators.required]),
    car: new FormControl(null, [Validators.required]),
    customer: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@rental.create.success:Rental was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.rentalService.getCarValues()
        .subscribe({
          next: (data) => this.carValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.rentalService.getCustomerValues()
        .subscribe({
          next: (data) => this.customerValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new RentalDTO(this.addForm.value);
    this.rentalService.createRental(data)
        .subscribe({
          next: () => this.router.navigate(['/rentals'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
