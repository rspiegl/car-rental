import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { RentalService } from 'app/rental/rental.service';
import { RentalDTO } from 'app/rental/rental.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-rental-edit',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './rental-edit.component.html'
})
export class RentalEditComponent implements OnInit {

  rentalService = inject(RentalService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  carValues?: Map<number,string>;
  customerValues?: Map<number,string>;
  currentId?: number;

  editForm = new FormGroup({
    id: new FormControl({ value: null, disabled: true }),
    mileage: new FormControl(null, [Validators.required]),
    car: new FormControl(null, [Validators.required]),
    customer: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@rental.update.success:Rental was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentId = +this.route.snapshot.params['id'];
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
    this.rentalService.getRental(this.currentId!)
        .subscribe({
          next: (data) => updateForm(this.editForm, data),
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.editForm.markAllAsTouched();
    if (!this.editForm.valid) {
      return;
    }
    const data = new RentalDTO(this.editForm.value);
    this.rentalService.updateRental(this.currentId!, data)
        .subscribe({
          next: () => this.router.navigate(['/rentals'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
